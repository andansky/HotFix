package com.andan.hotfix;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import sj.mblog.L;

/**
 * Created by nongyudi on 2017/10/31.
 */

public class InjectDex {
    public static void init(Context context){
        String pluginPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"plugin-debug.apk";
        File file=new File(pluginPath);
        if(file.exists()){
            hookResource(context);
        }
    }
    public static Object getField(Class<?> mClass,String fielName,Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field=mClass.getDeclaredField(fielName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static void setField(Class<?> mClass,String fielName,Object object,Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field=mClass.getDeclaredField(fielName);
        field.setAccessible(true);
        field.set(object,value);
    }

    public void doInject(Context mContext,String outDexPath, ClassLoader classLoader){
        try {

            File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"path_dex.jar");
            if(file.exists()){
                Class<?> cl1=Class.forName("davik.system.BaseDexClassLoader");
                Object pathList=getField(cl1,"pathList",classLoader);
                Object elementls=getField(pathList.getClass(),"dexElements",pathList);

                String  optPath=mContext.getDir("dex",0).getAbsolutePath();
                DexClassLoader dexClassLoader=new DexClassLoader(file.getAbsolutePath(),optPath,null,classLoader);
                Object outPathList=getField(dexClassLoader.getClass(),"pathList",dexClassLoader);
                Object outElementls=getField(outDexPath.getClass(),"dexElements",outPathList);

                Object newElements=combineArray(outElementls,elementls);

                setField(pathList.getClass(),"pathList",pathList,newElements);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Object combineArray(Object element1,Object element2){
        int l1=Array.getLength(element1);
        int l2=Array.getLength(element2);
        int lenght=l1+l2;
        Class<?> compomentType=element2.getClass().getComponentType();
        Object newArray= Array.newInstance(compomentType,lenght);
        for(int i=0;i<lenght;i++){
            if(i<l1){
                Array.set(newArray,i,Array.get(element1,i));
            }else{
                Array.set(newArray,i,Array.get(element2,i-l1));
            }
        }
        return newArray;
    }


    public static void hookResource(Context context){
        try {
            Class<?> activityThreadClass=Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod=activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object activityThread=currentActivityThreadMethod.invoke(null);

            Field mInstrumentationField=activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation instrumentation= (Instrumentation) mInstrumentationField.get(activityThread);

            Instrumentation proxy=new MInstrumentation(context,instrumentation);

            mInstrumentationField.set(activityThread,proxy);
        } catch (Exception e) {
            L.e(e);
        }
    }
}
