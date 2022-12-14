package org.e4s.model.registry;


import java.sql.Timestamp;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.ToStringMethod;
import net.bytebuddy.matcher.ElementMatchers;
import org.e4s.model.Timestamped;
import org.e4s.model.registry.interceptor.TimeKeyInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class DTORegisterEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        Class<?> foo1 = new ByteBuddy()
            .subclass(Object.class)
            .implement(Timestamped.class)
            .name("org.e4s.model.Foo1")
            .defineField("foo", String.class, Visibility.PRIVATE)
            .defineMethod("getFoo", String.class, Visibility.PUBLIC)
            .intercept(FieldAccessor.ofBeanProperty())
            .defineMethod("setFoo", void.class, Visibility.PUBLIC).withParameter(String.class)
            .intercept(FieldAccessor.ofBeanProperty())
            .defineField("timeKey", Long.class, Visibility.PRIVATE)
            .defineMethod("getTimeKey", Timestamp.class, Visibility.PUBLIC)
            .intercept(MethodDelegation.to(new TimeKeyInterceptor()))
            .defineMethod("setTimeKey", void.class, Visibility.PUBLIC).withParameter(Timestamp.class)
            .intercept(MethodDelegation.to(new TimeKeyInterceptor()))
            .method(ElementMatchers.isToString())
            .intercept(ToStringMethod.prefixedByCanonicalClassName())
            .make()
            .load(getClass().getClassLoader(), Default.INJECTION)
            .getLoaded();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
