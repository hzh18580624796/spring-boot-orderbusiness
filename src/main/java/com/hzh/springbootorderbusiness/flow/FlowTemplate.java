package com.hzh.springbootorderbusiness.flow;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class FlowTemplate<Context, Response> {

    private String mainStep;
    private List<Step<Context, Response>> steps;
    private Response response;

    public FlowTemplate(String mainStep, List<Step<Context, Response>> steps) {
        this.mainStep = mainStep;
        this.steps = steps;
    }

    public Response apply(Context context) {
        try {
            log.info("mainStep={},flow执行任务-start", mainStep);

            for (Step<Context, Response> step : steps) {
                if (step.getPredicate() != null && !step.getPredicate().test(context)) {
                    log.info("mainStep={},执行步骤-step::{},跳过", mainStep, step.getStep());
                    continue;
                } else {
                    log.info("mainStep={},执行步骤-step::{}", mainStep, step.getStep());
                }

                if (step instanceof DefaultStep) {
                    step.runStep(context);
                }

                if (step instanceof EndStep) {
                    Response response = step.runStep(context);
                    this.response = response;

                    return response;
                }
            }
        } catch (Throwable e) {
            log.warn("mainStep={},flow执行任务-exception", mainStep, e);
        } finally {
            log.info("mainStep={},flow执行任务-end,response={}", mainStep, JSONObject.toJSONString(response));
        }

        return null;
    }
}
