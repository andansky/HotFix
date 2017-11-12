package com.andan.hotfix;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import sj.mblog.L;

/**
 * Created by nongyudi on 2017/11/12.
 */

public class MInstrumentation extends Instrumentation {

    private Context mContext;
    private Instrumentation instrumentation;

    public MInstrumentation(Context mContext, Instrumentation instrumentation) {
        this.mContext = mContext;
        this.instrumentation = instrumentation;
    }

    @Override
    public void callActivityOnCreate(Activity activity, Bundle icicle) {
        //重写这个方法，重点是为了替换掉ContextImpl的mResource
        //1.需要重构一个我们的Resource
        //2.构造Resource需要AssetManager
        //3.反射AssetManager中的addAssertPath添加资源，addAssertPath调用的是native方法，native方法中用的是栈来有存储的，可以多次调用。
        try {
            Class<?> temp=activity.getClass().getSuperclass();
            while (!"ContextWrapper".equals(temp.getSimpleName())){
                temp=temp.getSuperclass();
            }
            Field mBaseField=temp.getDeclaredField("mBase");
            mBaseField.setAccessible(true);
            Context mBase= (Context) mBaseField.get(activity);
            Class<?>  contextImpl=Class.forName("android.app.ContextImpl");
            Field resuorceField=contextImpl.getDeclaredField("mResources");
            resuorceField.setAccessible(true);

            AssetManager newAssetManager=AssetManager.class.newInstance();
            Method addAssetPathMethod=newAssetManager.getClass().getDeclaredMethod("addAssetPath",String.class);
            addAssetPathMethod.setAccessible(true);

            String dexPath= mContext.getPackageResourcePath();
            String outDexPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"plugin-debug.apk";

            File file=new File(outDexPath);
            if(file.exists()){
                L.e("外部资源文件存在!");
            }
            addAssetPathMethod.invoke(newAssetManager,outDexPath);
            addAssetPathMethod.invoke(newAssetManager,dexPath);



            Method ensureStringBlocksMethod=AssetManager.class.getDeclaredMethod("ensureStringBlocks");
            ensureStringBlocksMethod.setAccessible(true);
            ensureStringBlocksMethod.invoke(newAssetManager);

            Resources oldResource=mContext.getResources();
            L.e(oldResource.getString(R.string.app_name));
            Resources resources=new Resources(newAssetManager,oldResource.getDisplayMetrics(),oldResource.getConfiguration());
            L.e(resources.getString(R.string.app_name));
            resuorceField.set(mBase,resources);

        } catch (Exception e) {
            L.e(e);
        }
        super.callActivityOnCreate(activity, icicle);
    }
}
