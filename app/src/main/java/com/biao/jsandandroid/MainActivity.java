package com.biao.jsandandroid;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;

public class MainActivity extends AppCompatActivity {

    private WebView wv_jsandandroidtest;
    private BridgeWebView bridgeView;
    private String url;
    private String url1;
    private Button btn_antojs, btn_pantojs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();
    }

    private void initView() {
        wv_jsandandroidtest = findViewById(R.id.wv_jsandandroidtest);
        bridgeView = findViewById(R.id.bridgeView);
        btn_antojs = findViewById(R.id.btn_antojs);
        btn_pantojs = findViewById(R.id.btn_pantojs);

        btn_pantojs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                String str = "json data";
                String jsMethodName = "jsMethod1('" + str + "')"; //需要参数的JS函数名
                wv_jsandandroidtest.evaluateJavascript(jsMethodName, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String responseJson) { //这里传入的参数就是JS函数的返回值
                        Toast.makeText(MainActivity.this, "调用js返回值:" + responseJson, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_antojs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Java 调JS的functionJs方法并得到返回值
                bridgeView.callHandler("functionIn123", "Android传值到js", new CallBackFunction() {
                    @Override
                    public void onCallBack(String data) {
                        Toast.makeText(MainActivity.this, "接收到JS传值：" + data, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initData() {
        url = "file:///android_asset/Test.html";
        url1 = "file:///android_asset/Test1.html";
        //支持javascript
        wv_jsandandroidtest.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        wv_jsandandroidtest.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        wv_jsandandroidtest.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        wv_jsandandroidtest.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        wv_jsandandroidtest.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv_jsandandroidtest.getSettings().setLoadWithOverviewMode(true);

        //如果不设置WebViewClient，请求会跳转系统浏览器
        wv_jsandandroidtest.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

        });
        //进度条
        wv_jsandandroidtest.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    Log.i("MainActivity", "加载完成");
                }
            }
        });
        wv_jsandandroidtest.loadUrl(url);
        bridgeView.loadUrl(url1);

        wv_jsandandroidtest.addJavascriptInterface(new JsToAndroid(this), "Test");
        registerHandler1();
    }

    private void registerHandler() {
        bridgeView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("MainActivity", "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data from Java");
//                function.onCallBack("123");
//                Toast.makeText(MainActivity.this, data.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registerHandler1() {
        bridgeView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Toast.makeText(MainActivity.this, "得到JS传过来的数据 data =" + data, Toast.LENGTH_SHORT).show();
                function.onCallBack("传递数据给JS");
            }
        });
    }
}
