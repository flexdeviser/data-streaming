package org.e4s.model.registry.interceptor;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import org.apache.commons.lang.StringUtils;

public class TimeKeyInterceptor {


    MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

    @RuntimeType
    public Object intercept(@Origin Method m, @AllArguments Object[] args, @This Object proxy) {

        if (m.getReturnType().equals(Timestamp.class)) {
            // getter
            Timestamp result;
            try {
                String fieldName = m.getName().replace("get", "");
                Field field = proxy.getClass().getDeclaredField(StringUtils.uncapitalize(fieldName));
                field.setAccessible(true);
                if (field.get(proxy) == null) {
                    result = null;
                } else {
                    result = new Timestamp((Long) field.get(proxy));
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return result;
        } else {
            // setter
            try {
                String fieldName = m.getName().replace("set", "");
                Field field = proxy.getClass().getDeclaredField(StringUtils.uncapitalize(fieldName));
                field.setAccessible(true);
                if (args[0] == null) {
                    field.set(proxy, null);
                } else {
                    field.set(proxy, ((Timestamp) args[0]).getTime());
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

}
