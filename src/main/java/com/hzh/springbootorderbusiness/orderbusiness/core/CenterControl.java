package com.hzh.springbootorderbusiness.orderbusiness.core;


import com.alibaba.fastjson.JSONObject;
import com.hzh.springbootorderbusiness.orderbusiness.hander.BusinessSynAsynHander;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * fixme 中心控制器
 */
@Slf4j
@Data
@Component
public class CenterControl {

    @Autowired
    private ApplicationContext applicationContext;
    private Map<String, Map<String, BusinessSynAsynHander>> store = new HashMap<>();
    private Map<String, Map<String, String>> businessSynAsynHanderNameStore = new HashMap<>();
    private String defaultBusinessCode = "default";

    @PostConstruct
    public void register() {
        Map<String, BusinessSynAsynHander> businessSynAsynHanderMap = applicationContext.getBeansOfType(BusinessSynAsynHander.class);

        businessSynAsynHanderMap.forEach((key, value) -> {
            BusinessEventListener listener = value.getClass().getAnnotation(BusinessEventListener.class);

            if (listener == null) {
                throw new RuntimeException(value.getClass().getSimpleName() + "实现类异常，没有添加@BusinessEventListener注解");
            }

            String makedKey = makeKey(listener.businessCode(), listener.event());

            Map<String, BusinessSynAsynHander> map = store.getOrDefault(makedKey, new HashMap<>());

            map.put(listener.status(), value);

            store.put(makedKey, map);
        });

        storeBusinessSynAsynHanderName();

        log.info("CenterControl businessSynAsynHanderNameStore info={}", JSONObject.toJSONString(businessSynAsynHanderNameStore));
    }

    public <Context, Request, Response> BusinessResponse publishEvent(BusinessEvent<Context, Request, Response> businessEvent) {
        GateWayRequest<Request> gateWayRequest = new GateWayRequest<>();
        gateWayRequest.setRequest(businessEvent.getRequest());

        log.info("===========================publishEvent eventType={}=============================", businessEvent.getEvent());
        log.info("publishEvent={},gateWayRequest={}", businessEvent.getEvent(), JSONObject.toJSONString(gateWayRequest));

        GateWayResponse<Response> gateWayResponse = businessEvent.getFunctionInvoker().apply(gateWayRequest);

        log.info("publishEvent={},gateWayResponse={}", businessEvent.getEvent(), JSONObject.toJSONString(gateWayResponse));


        if (gateWayResponse.getResponse().equals(GatewayResponseStatus.Processing)) {
            BusinessResponse businessResponse = new BusinessResponse();
            businessResponse.setStatus("P");
            return businessResponse;
        }

        return synDownStream(businessEvent, gateWayResponse);
    }

    //fixme 同步处理
    public <Context, Request, Response> BusinessResponse synDownStream(BusinessEvent<Context, Request, Response> businessEvent, GateWayResponse<Response> gateWayResponse) {
        log.info("synDownStream enter");

        Map<String, BusinessSynAsynHander> map = store.getOrDefault(
                makeKey(businessEvent.getBusinessCode(), businessEvent.getEvent()),
                store.get(makeKey(defaultBusinessCode, businessEvent.getEvent()))
        );

        return gateWayResponse.isSuccess()
                ? map.get(GatewayResponseStatus.Success).syn(businessEvent.getContext(), gateWayResponse)
                : map.get(GatewayResponseStatus.Fail).syn(businessEvent.getContext(), gateWayResponse);
    }

    //fixme 异步处理
    public <Response> BusinessResponse asynDownStream(GateWayResponse<Response> gateWayResponse, String businessCode, String event) {
        log.info("asynDownStream enter");
        Map<String, BusinessSynAsynHander> map = store.getOrDefault(
                makeKey(businessCode, event),
                store.get(makeKey(defaultBusinessCode, event))
        );

        return gateWayResponse.isSuccess()
                ? map.get(GatewayResponseStatus.Success).asyn(gateWayResponse)
                : map.get(GatewayResponseStatus.Fail).asyn(gateWayResponse);
    }

    private String makeKey(String businessCode, String event) {
        return businessCode.concat("#").concat(event);
    }

    public void storeBusinessSynAsynHanderName() {
        store.forEach((k, v) -> {

            Map<String, String> tmpStore = new HashMap<>();

            v.forEach((key, value) -> {
                tmpStore.put(key, value.getClass().getSimpleName());
            });

            businessSynAsynHanderNameStore.put(k, tmpStore);
        });
    }
}
