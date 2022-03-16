package com.hzh.springbootorderbusiness.orderbusiness.hander;


import com.hzh.springbootorderbusiness.orderbusiness.core.BusinessEventListener;
import com.hzh.springbootorderbusiness.orderbusiness.core.BusinessResponse;
import com.hzh.springbootorderbusiness.orderbusiness.core.GateWayResponse;
import com.hzh.springbootorderbusiness.orderbusiness.core.GatewayResponseStatus;
import com.hzh.springbootorderbusiness.orderbusiness.hander.context.CreditContext;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.CreditResponse;
import lombok.Data;

@Data
@BusinessEventListener(event = "credit", status = GatewayResponseStatus.Fail)
public class CreditFailBusinessSynAsynHander extends BusinessSynAsynHander<CreditContext, CreditResponse> {
    private String name = "CreditFailBusinessSynAsynHander";

    //fixme 整一个flow进来
    //@Autowired
    //private Flow flow;

    @Override
    public boolean check(GateWayResponse<CreditResponse> gateWayResponse) {

        return false;
    }

    @Override
    public CreditContext asynContextPrepare(GateWayResponse<CreditResponse> gateWayResponse) {
        System.out.println("CreditFailBusinessSynAsynHander asynContextPrepare");
        return new CreditContext();
    }

    @Override
    public void accumulate(CreditContext creditContext, GateWayResponse<CreditResponse> gateWayResponse) {
        System.out.println("CreditFailBusinessSynAsynHander accumulate");
    }

    @Override
    public BusinessResponse handle(CreditContext creditContext) {

        //flow.do
        BusinessResponse businessResponse = new BusinessResponse();
        businessResponse.setStatus("S");
        return businessResponse;
    }
}
