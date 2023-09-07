package tech.wiktor.libs.payments.utils;

import java.lang.reflect.Field;

public class StringUtils {
    public static String removeLastChar(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }
    public static String getValues(Object o) {
        StringBuilder builder = new StringBuilder();
        for (Field field : o.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object object = field.get(o);
                if (object == null) continue;
                builder.append(object).append("|");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return removeLastChar(builder.toString());
    }
}
