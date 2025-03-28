package com.example.util;

import com.example.type.SystemType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
@Slf4j
public class ConverterUtils {
    public static <T> T convert(Object request,T entity){
        if(request == null || entity == null){
            return entity;
        }
        Class<?> requestClass = request.getClass();
        Class<?> entityClass = entity.getClass();
        Field[] requestFields = requestClass.getDeclaredFields();
        for (Field requestField : requestFields) {
            requestField.setAccessible(true);
            try {
                String fieldName = requestField.getName();
                Object fieldValue = requestField.get(request);
                if (fieldValue != null) {
                    try {
                        Field entityField = entityClass.getDeclaredField(fieldName);
                        entityField.setAccessible(true);
                        entityField.set(entity, fieldValue);
                    } catch (NoSuchFieldException e) {
                        // 实体类中不存在该字段，忽略
                        log.error(SystemType.CONVERT_FAILED.getValue() + e);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
