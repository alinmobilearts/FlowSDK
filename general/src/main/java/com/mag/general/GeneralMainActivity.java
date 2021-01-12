package com.mag.general;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.adjust.sdk.webbridge.AdjustBridge;

import androidx.appcompat.app.AppCompatActivity;

import static com.mag.general.Constants.APP_SCHEME_EXTRA;
import static com.mag.general.Constants.END_POINT_EXTRA;

public class GeneralMainActivity extends AppCompatActivity {

    private WebView webView;
    private String appScheme, endPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_general);
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        appScheme = getIntent().getStringExtra(APP_SCHEME_EXTRA);
        endPoint = getIntent().getStringExtra(END_POINT_EXTRA);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d("WebviewConsole", "Line: " + consoleMessage.lineNumber() + " - Message: " + consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                setSplash();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent;
                if (url.startsWith("http")) {

                    return false;
                } else if (url.startsWith(appScheme)) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    
                    Intent nativeIntent = new Intent(Intent.ACTION_VIEW);
                    nativeIntent.setData(Uri.parse(appScheme));
                    startActivity(nativeIntent);

                    try {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finish();
                }
                return false;

            }
        });

        AdjustBridge.registerAndGetInstance(getApplication(), webView);

        webView.loadUrl(Utils.constructUrl(endPoint));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AdjustBridge.unregister();
    }

    @Override
    public void onBackPressed() {
    }
}