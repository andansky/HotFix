package com.andan.plugin;

import android.util.Log;

import com.andan.pluginsdk.TestInterFace;

/**
 * Created by nongyudi on 2017/10/29.
 */

public class Imp implements TestInterFace {
    @Override
    public void log() {
        Log.e("aaaa","bbbb");
    }

    @Override
    public void toast() {
        Log.e("bbbb","bbbb");
    }
}
