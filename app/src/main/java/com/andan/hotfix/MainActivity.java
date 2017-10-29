package com.andan.hotfix;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

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
//        String mdexPath=null;
//        try {
//            AssetManager manager=AssetManager.class.newInstance();
//            Method addAssetPath=manager.getClass().getMethod("addAssetPath",String.class);
//            addAssetPath.invoke(manager,mdexPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        ((Button)findViewById(R.id.btn_log)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ProxyActivity.class);
                intent.putExtra(ProxyActivity.PROXIED_CLASS_NAME,"com.andan.plugin.MainActivity");
                intent.putExtra(ProxyActivity.EXTRA_APK_PATH,Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "plugin-debug.apk");
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.btn_toast)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
