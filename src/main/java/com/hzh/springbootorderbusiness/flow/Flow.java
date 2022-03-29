package com.hzh.springbootorderbusiness.flow;

public interface Flow<Context, Response> {

    default StepBuilder<Context, Response> startStep(String mainStep) {
        return new StepBuilder<>("【" + mainStep + "】");
    }

    Response flowApply(Context context);
}
