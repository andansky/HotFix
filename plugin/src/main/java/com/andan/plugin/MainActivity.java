package com.andan.plugin;

import android.os.Bundle;
import android.widget.TextView;

import com.andan.pluginsdk.BasePluginActivity;

public class MainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("这是插件一的Activity");
        setContentView(tv);
        setContentView(tv);
    }


}
