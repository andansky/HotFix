package com.andan.hotfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andan.pluginsdk.ActivityInterface;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import sj.mblog.L;

/**
 * Created by nongyudi on 2017/10/29.
 */

public class ProxyActivity extends AppCompatActivity implements ActivityInterface {

    public final static String PROXIED_CLASS_NAME = "PROXIED_CLASS_NAME";
    public final static String EXTRA_APK_PATH = "EXTRA_APK_PATH";

    private DexClassLoader classLoader;
    private ActivityInterface anInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initClassLoader();
        if(anInterface!=null){
            anInterface.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if(anInterface!=null){
            anInterface.onStart();
        }
        super.onStart();
    }

    @Override
    public void onRestart() {
        if(anInterface!=null){
            anInterface.onRestart();
        }
        super.onRestart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(anInterface!=null){
            anInterface.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onResume() {
        if(anInterface!=null){
            anInterface.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if(anInterface!=null){
            anInterface.onPause();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if(anInterface!=null){
            anInterface.onStop();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if(anInterface!=null){
            anInterface.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(anInterface!=null){
            anInterface.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(anInterface!=null){
            anInterface.onNewIntent(intent);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(anInterface!=null){
            anInterface.onRestoreInstanceState(savedInstanceState);
        }
    }

    /**
     * 初始化classLoader
     */
    private void initClassLoader() {
        // 插件放在sd卡的根目录下
        String apkPath = getIntent().getStringExtra(EXTRA_APK_PATH);

        L.e(apkPath);
        File file = new File(apkPath);
        if(file.exists()){
            Log.e("dex", "文件存在！");
        }else{
            Log.e("dex", "文件不存在！");
        }

        // dex文件的释放目录
        File releasePath = getDir("dexs", 0);

        // 类加载器
        classLoader = new DexClassLoader(file.getAbsolutePath(), releasePath.getAbsolutePath(), null, getClassLoader());
        try {
            Class<?> classk=classLoader.loadClass(getIntent().getStringExtra(PROXIED_CLASS_NAME));
            anInterface= (ActivityInterface)classk.newInstance();
            Method method=classk.getMethod("setProxy",Activity.class);
            method.setAccessible(true);
            method.invoke(anInterface,ProxyActivity.this);
        } catch (Exception e) {
            L.e(e);
        }
    }
}
