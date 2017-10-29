package com.andan.hotfix;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andan.pluginsdk.TestInterFace;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import sj.mblog.L;

public class MainActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    Object object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mdexPath=null;
        try {
            AssetManager manager=AssetManager.class.newInstance();
            Method addAssetPath=manager.getClass().getMethod("addAssetPath",String.class);
            addAssetPath.invoke(manager,mdexPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ContextCompat.getColor(this,R.color.colorAccent);
        File outPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "plugin-debug.apk");// 外部路径
        L.e(outPath.getAbsolutePath());
        File toPath=this.getDir("dex",MODE_PRIVATE);
        DexClassLoader dexClassLoader=new DexClassLoader(outPath.getAbsolutePath(),toPath.getAbsolutePath(),null,getClassLoader());
        Class libProviderClazz = null;
        L.e(TestInterFace.class.getClassLoader());
        try {
            libProviderClazz = dexClassLoader.loadClass("com.andan.plugin.Imp");
            object= (TestInterFace) libProviderClazz.newInstance();
            Method start = libProviderClazz.getDeclaredMethod("log");// 获取方法
            start.setAccessible(true);// 把方法设为public，让外部可以调用
            start.invoke(libProviderClazz.newInstance());// 调用方法并获取返回值
            TestInterFace interFace=(TestInterFace)libProviderClazz.newInstance();
            interFace.log();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        ((Button)findViewById(R.id.btn_log)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TestInterFace)object).log();
            }
        });

        ((Button)findViewById(R.id.btn_toast)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TestInterFace)object).toast();
            }
        });

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
