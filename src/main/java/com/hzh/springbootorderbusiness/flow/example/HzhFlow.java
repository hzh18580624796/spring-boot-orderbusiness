package com.hzh.springbootorderbusiness.flow.example;

import com.hzh.springbootorderbusiness.flow.Flow;
import com.hzh.springbootorderbusiness.flow.example.dto.HzhContext;
import com.hzh.springbootorderbusiness.flow.example.dto.HzhResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HzhFlow implements Flow<HzhContext, HzhResponse> {

    @Override
    public HzhResponse flowApply(HzhContext context) {
        return startStep("hzh进件")
                .step("进件1").predicate(c -> true).invoke(this::tt)
                .step("进件2").end(this::value)
                .build()
                .apply(context);
    }

    private void tt(HzhContext context) {
        log.info("tt");
    }

    private HzhResponse value(HzhContext context) {
        log.info("value");

        HzhResponse response = new HzhResponse();
        response.setStatus("yes or success");

        return response;
    }
}
