package com.hzh.springbootorderbusiness.flow;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Data
public class DefaultStep<Context, Response> implements Step<Context, Response> {

    private String step;
    private Predicate<Context> predicate;
    private Consumer<Context> consumer;

    public DefaultStep(String step, Predicate<Context> predicate, Consumer<Context> consumer) {
        this.step = step;
        this.predicate = predicate;
        this.consumer = consumer;
    }

    @Override
    public Response runStep(Context context) {
        consumer.accept(context);
        return null;
    }
}
