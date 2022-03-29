package com.hzh.springbootorderbusiness.flow;

import java.util.function.Predicate;

public interface Step<Context, Response> {

    String getStep();

    Predicate<Context> getPredicate();

    Response runStep(Context context);
}
