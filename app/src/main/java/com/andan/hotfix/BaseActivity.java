package com.andan.hotfix;

import android.support.v7.app.AppCompatActivity;

import sj.mblog.L;

/**
 * Created by nongyudi on 2017/10/28.
 */

public class BaseActivity extends AppCompatActivity {

    public void onTest(String test){
        if(test!=null){
            L.e(test);
        }
    }
}
