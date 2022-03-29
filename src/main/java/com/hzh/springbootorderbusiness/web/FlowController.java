package com.hzh.springbootorderbusiness.web;

import com.alibaba.fastjson.JSONObject;
import com.hzh.springbootorderbusiness.flow.example.HzhFlow;
import com.hzh.springbootorderbusiness.flow.example.dto.HzhContext;
import com.hzh.springbootorderbusiness.flow.example.dto.HzhResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FlowController {

    @Autowired
    private HzhFlow hzhFlow;

    @GetMapping("/hzhFlow")
    public void hzhFlow() {

        HzhContext context = new HzhContext();
        HzhResponse response = hzhFlow.flowApply(context);

        log.info("response= " + JSONObject.toJSONString(response));
    }
}
