package com.biao.jsandandroid;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by benxiang on 2019/4/22.
 */

public class JsToAndroid {
    private Context context;


    JsToAndroid(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void showAndroid() {
        Toast.makeText(context, "js调用了Android方法！", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public String getString() {
        return "android data";
    }

    @JavascriptInterface
    public void setString(String str) {
        Toast.makeText(context, "js返回："+str, Toast.LENGTH_LONG).show();
    }
}
