//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pzhu.spring.cloud.alibaba.consumer.Util;

import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);
    private static ApplicationContext applicationContext;

    public SpringContextHolder() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        Map beanMaps = applicationContext.getBeansOfType(clazz);
        return beanMaps != null && !beanMaps.isEmpty() ? (T) beanMaps.values().iterator().next() : null;
    }

    public <T> T registerBean(Class<T> clazz, Map<String, Object> propertys) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        propertys.forEach((k, v) -> {
            beanDefinitionBuilder.addPropertyValue(k, v);
        });
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        beanFactory.registerBeanDefinition(clazz.getName(), beanDefinitionBuilder.getBeanDefinition());
        return getBean(clazz);
    }

    public String[] beans() {
        return applicationContext.getBeanDefinitionNames();
    }

    public static String[] annotationBeans(Class c) {
        return applicationContext.getBeanNamesForAnnotation(c);
    }

    public void removeBean(String beanName) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        beanFactory.removeBeanDefinition(beanName);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

    public static String getProperty(String parentKey, String sonKey) {
        ResourceBundle bundle = ResourceBundle.getBundle(parentKey);
        return bundle.getString(sonKey);
    }
}
