package com.mav.CustomerService.utill;

import java.lang.reflect.Field;

class ReflectionUtils {
    public static Field findField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Field not found in the current class, check superclass
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                return findField(superclass, fieldName);
            }
        }
        return null;
    }
}