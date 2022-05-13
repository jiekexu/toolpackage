package com.xjc.tool.yeepay.utils;

import com.xjc.tool.yeepay.exception.YeePayException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class QueryFormUtils {

    public static Map<String, String> getEncodedQueryParams(Object obj) {
        try {
            Map<String, String> params = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass(), Object.class);
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : descriptors) {
                Method readMethod = descriptor.getReadMethod();
                if (readMethod != null) {
                    String value = (String) readMethod.invoke(obj);
                    if (value != null && !"".equals(value)) {
                        params.put(descriptor.getName(), URLEncoder.encode(value, "GBK"));
                    }
                }
            }
            return params;
        } catch (Exception e) {
            throw new YeePayException(e.getMessage(),e,"ENCODE_ERROR");
        }
    }
}
