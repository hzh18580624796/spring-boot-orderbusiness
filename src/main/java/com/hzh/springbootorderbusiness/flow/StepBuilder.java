package com.hzh.springbootorderbusiness.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StepBuilder<Context, Response> {

    private String mainStep;
    private String step;
    private Predicate<Context> predicate;
    private List<Step<Context, Response>> steps = new ArrayList<>();

    public StepBuilder(String mainStep) {
        this.mainStep = mainStep;
    }

    public StepBuilder<Context, Response> step(String step) {
        this.step = step;
        return this;
    }

    public StepBuilder<Context, Response> predicate(Predicate<Context> predicate) {
        this.predicate = predicate;
        return this;
    }

    public StepBuilder<Context, Response> invoke(Consumer<Context> consumer) {
        steps.add(new DefaultStep<>(this.step, this.predicate, consumer));
        this.clear();
        return this;
    }

    public StepBuilder<Context, Response> end(Function<Context, Response> function) {
        steps.add(new EndStep<>(this.step, this.predicate, function));
        this.clear();
        return this;
    }

    public FlowTemplate<Context, Response> build() {
        return new FlowTemplate<>(this.mainStep, this.steps);
    }

    private void clear() {
        this.step = "";
        this.predicate = null;
    }
}
