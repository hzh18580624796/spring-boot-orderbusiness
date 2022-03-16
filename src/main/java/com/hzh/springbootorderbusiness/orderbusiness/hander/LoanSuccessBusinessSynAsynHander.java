package com.hzh.springbootorderbusiness.orderbusiness.hander;


import com.hzh.springbootorderbusiness.orderbusiness.core.BusinessEventListener;
import com.hzh.springbootorderbusiness.orderbusiness.core.BusinessResponse;
import com.hzh.springbootorderbusiness.orderbusiness.core.GateWayResponse;
import com.hzh.springbootorderbusiness.orderbusiness.core.GatewayResponseStatus;
import com.hzh.springbootorderbusiness.orderbusiness.hander.context.LoanContext;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.LoanResponse;
import lombok.Data;

@Data
@BusinessEventListener(event = "loan", status = GatewayResponseStatus.Success)
public class LoanSuccessBusinessSynAsynHander extends BusinessSynAsynHander<LoanContext, LoanResponse> {
    private String name = "LoanSuccessBusinessSynAsynHander";

    //fixme 整一个flow进来
    //@Autowired
    //private Flow flow;

    @Override
    public boolean check(GateWayResponse<LoanResponse> gateWayResponse) {

        return false;
    }

    @Override
    LoanContext asynContextPrepare(GateWayResponse<LoanResponse> gateWayResponse) {
        System.out.println("LoanSuccessBusinessSynAsynHander asynContextPrepare");

        return new LoanContext();
    }

    @Override
    void accumulate(LoanContext loanContext, GateWayResponse<LoanResponse> gateWayResponse) {
        System.out.println("LoanSuccessBusinessSynAsynHander accumulate");
    }

    @Override
    BusinessResponse handle(LoanContext loanContext) {

        //flow.do
        BusinessResponse businessResponse = new BusinessResponse();
        businessResponse.setStatus("S");
        return businessResponse;
    }

}
