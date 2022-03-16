package com.hzh.springbootorderbusiness.orderbusiness.core;

import lombok.Data;

@Data
public class GateWayRequest<Request> {
    private Request request;
}
