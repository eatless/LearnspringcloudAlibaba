package com.pzhu.spring.cloud.alibaba.consumer.Util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtil implements EnvironmentAware {

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
    }

    public static String get(String key, String defVal) {
        return env.getProperty(key, defVal);
    }

    public static String get(String key) {
        return env.getProperty(key);
    }

    public static <T> T get(String key, Class<T> targetType, T defVal) {
        return env.getProperty(key, targetType, defVal);
    }


}
