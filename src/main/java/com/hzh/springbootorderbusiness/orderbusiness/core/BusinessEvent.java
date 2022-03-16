package com.hzh.springbootorderbusiness.orderbusiness.core;

import lombok.Data;

import java.util.function.Function;

@Data
public class BusinessEvent<Context, Request, Response> {

    private Context context;
    private Request request;
    private String event;
    private String businessCode = "";
    private Function<GateWayRequest<Request>, GateWayResponse<Response>> functionInvoker;
}
