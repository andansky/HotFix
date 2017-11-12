package com.andan.hotfix;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by nongyudi on 2017/11/12.
 */

public class FieldUtil {

    public static Object getField(Object object,String fielName) throws NoSuchFieldException, IllegalAccessException {
        Field field=object.getClass().getDeclaredField(fielName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static void setField(Object object,String fielName,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field=object.getClass().getDeclaredField(fielName);
        field.setAccessible(true);
        field.set(object,value);
    }

    /***
     * 反射调用方法
     * @param cl
     * @param methodName
     */
    public static Object invokeMethos(Class<?> cl,String methodName,Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method=cl.getMethod(methodName,cl);
        method.setAccessible(true);
        return method.invoke(cl,methodName,args);
    }


}
