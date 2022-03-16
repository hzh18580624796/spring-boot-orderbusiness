package com.hzh.springbootorderbusiness.orderbusiness.service;


import com.hzh.springbootorderbusiness.orderbusiness.core.GateWayRequest;
import com.hzh.springbootorderbusiness.orderbusiness.core.GateWayResponse;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.CreditRequest;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.CreditResponse;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.LoanRequest;
import com.hzh.springbootorderbusiness.orderbusiness.hander.dto.LoanResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public GateWayResponse<CreditResponse> credit(GateWayRequest<CreditRequest> gateWayRequest) {

        GateWayResponse<CreditResponse> gateWayResponse = new GateWayResponse<>();

        CreditResponse creditResponse = new CreditResponse();
        gateWayResponse.setSuccess(true);
        gateWayResponse.setResponse(creditResponse);

        creditResponse.setData("this is creditResponse syn data");

        return gateWayResponse;
    }

    public GateWayResponse<LoanResponse> loan(GateWayRequest<LoanRequest> gateWayRequest) {

        GateWayResponse<LoanResponse> gateWayResponse = new GateWayResponse<>();

        LoanResponse loanResponse = new LoanResponse();
        gateWayResponse.setSuccess(true);
        gateWayResponse.setResponse(loanResponse);

        loanResponse.setData("this is loanResponse syn data");

        return gateWayResponse;
    }
}
