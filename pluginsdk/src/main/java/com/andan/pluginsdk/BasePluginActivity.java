package com.andan.pluginsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nongyudi on 2017/10/29.
 */

public class BasePluginActivity extends AppCompatActivity implements ActivityInterface {


    /**
     * 宿主的Activity
     */
    protected Activity proxyActivity;

    /**
     * 是否作为插件运行
     */
    public boolean isProxyMode = false;

    public void setProxy(Activity activity){
        this.proxyActivity = activity;
        isProxyMode = true;
    }

    public Activity getActivity(){
        if(isProxyMode&&proxyActivity!=null){
            return proxyActivity;
        }else{
            return this;
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if(!isProxyMode){
            super.startActivity(intent);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(!isProxyMode){
            super.onCreate(savedInstanceState);
        }else{
            ((ActivityInterface)proxyActivity).onCreate(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        if(!isProxyMode){
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if(!isProxyMode){
            super.onRestart();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!isProxyMode){
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onResume() {
        if(!isProxyMode){
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if(!isProxyMode){
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if(!isProxyMode){
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if(!isProxyMode){
            super.onDestroy();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(!isProxyMode){
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(!isProxyMode){
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(!isProxyMode){
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

}
