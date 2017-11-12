package com.andan.hotfix;

import android.app.Application;

/**
 * Created by nongyudi on 2017/10/31.
 */

public class MApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //hooke加载外部插件资源
        InjectDex.init(this);
    }
}
