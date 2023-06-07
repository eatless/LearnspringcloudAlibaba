package com.pzhu.spring.cloud.alibaba.common.Util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

public class BeanConvertUtil {

    /**
     * 将源对象的属性值复制到目标对象中
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    目标对象类型
     */
    public static <T> void copyProperties(Object source, T target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreError(true));
    }

    /**
     * 将源对象的属性值复制到目标对象中，并忽略源对象中值为空字符串的属性
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    目标对象类型
     */
    public static <T> void copyPropertiesIgnoreEmptyString(Object source, T target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create()
            .setIgnoreNullValue(true)
            .setIgnoreError(true)
            .setIgnoreProperties(getEmptyStringFields(source.getClass(), source)));
    }


    private static String[] getEmptyStringFields(Class<?> clazz, Object obj) {
        return Arrays.stream(ReflectUtil.getFields(clazz))
            .filter(field -> CharSequence.class.isAssignableFrom(field.getType()))
            .map(field -> {
                Object value = ReflectUtil.getFieldValue(obj, field);
                if (value instanceof CharSequence && CharSequenceUtil.isBlank((CharSequence) value)) {
                    return field.getName();
                } else {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .toArray(String[]::new);
    }


}