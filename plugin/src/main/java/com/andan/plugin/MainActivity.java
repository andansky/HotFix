package com.andan.plugin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.andan.pluginsdk.BasePluginActivity;

public class MainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(!isProxyMode){
            super.onCreate(savedInstanceState);
        }
        getActivity().setContentView(R.layout.plugin_activity_main);
        ImageView imageView=(ImageView)getActivity().findViewById(R.id.img);
        imageView.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.tuzi));
        Log.e("aaaaaaa","bbbbbbb");
    }


}
