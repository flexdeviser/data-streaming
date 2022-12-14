package org.e4s.stream.imp;

import java.util.Map;
import org.e4s.stream.CompiledScript;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

public class ScriptAction implements CompiledScript {

    private final Context ctx;

    public ScriptAction(final Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public Object execute(String function, Object... args) {
        Value func = ctx.getBindings("js").getMember(function);
        if (func == null) {
            throw new RuntimeException(String.format("Function not found '%s'", function));
        }
        Value result = func.execute(args);
        return result;
    }

    @Override
    public <T> T as(Class<T> targetType) {
        return null;
    }

    @Override
    public <T> T as(String function, Class<T> targetType) {
        return null;
    }

    @Override
    public void bind(String name, Object value) {
        this.ctx.getBindings("js").putMember(name, value);
    }

    @Override
    public void bindAll(Map<String, Object> nameValues) {
        nameValues.forEach((k, v) -> this.ctx.getBindings("js").putMember(k, v));
    }
}
