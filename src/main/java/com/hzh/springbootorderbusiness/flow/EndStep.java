package com.hzh.springbootorderbusiness.flow;

import lombok.Data;

import java.util.function.Function;
import java.util.function.Predicate;

@Data
public class EndStep<Context, Response> implements Step<Context, Response> {

    private String step;
    private Predicate<Context> predicate;
    private Function<Context, Response> function;

    public EndStep(String step, Predicate<Context> predicate, Function<Context, Response> function) {
        this.step = step;
        this.predicate = predicate;
        this.function = function;
    }

    @Override
    public Response runStep(Context context) {
        return function.apply(context);
    }
}
