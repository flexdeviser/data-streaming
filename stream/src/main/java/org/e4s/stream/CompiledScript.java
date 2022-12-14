package org.e4s.stream;

import java.util.Map;
public interface CompiledScript {

    public Object execute(String function, Object... args);

    public <T> T as(Class<T> targetType);

    public <T> T as(String function, Class<T> targetType);

    public void bind(String name, Object value);

    public void bindAll(Map<String, Object> nameValues);
}
