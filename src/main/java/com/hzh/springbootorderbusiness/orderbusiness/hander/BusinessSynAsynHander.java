package com.hzh.springbootorderbusiness.orderbusiness.hander;


import com.hzh.springbootorderbusiness.orderbusiness.core.BusinessResponse;
import com.hzh.springbootorderbusiness.orderbusiness.core.GateWayResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 业务同步 异步处理器
 */
@Slf4j
public abstract class BusinessSynAsynHander<Context, Response> implements Serializable {

    /**
     * fixme 同步处理
     */
    public BusinessResponse syn(Context context, GateWayResponse<Response> gateWayResponse) {

        log.info(this.getClass().getSimpleName() + "开始同步处理-合并");
        accumulate(context, gateWayResponse);

        log.info(this.getClass().getSimpleName() + "开始handling");
        BusinessResponse handleResult = handle(context);

        log.info("handleResult={},identityHashCode={}", handleResult, System.identityHashCode(handleResult));

        return handleResult;
    }

    /**
     * fixme 异步处理
     */
    public BusinessResponse asyn(GateWayResponse<Response> gateWayResponse) {

        log.info(this.getClass().getSimpleName() + "开始异步处理-幂等 或 判重");
        boolean checkMatch = check(gateWayResponse);

        if (checkMatch) {
            //假如判重后，返回成功
            log.info("系统判定幂等 或 判重，直接返回");
            //直接返回null，因为异步 没有地方接受返回值
            return null;
        }

        log.info(this.getClass().getSimpleName() + "开始异步处理-异步上下文准备");
        Context context = asynContextPrepare(gateWayResponse);

        log.info(this.getClass().getSimpleName() + "异步上下文准备完毕，准备拉起同步处理");
        return syn(context, gateWayResponse);
    }

    /**
     * fixme 幂等 或 判重
     */
    abstract boolean check(GateWayResponse<Response> gateWayResponse);

    abstract Context asynContextPrepare(GateWayResponse<Response> gateWayResponse);

    abstract void accumulate(Context context, GateWayResponse<Response> gateWayResponse);

    abstract BusinessResponse handle(Context context);
}
