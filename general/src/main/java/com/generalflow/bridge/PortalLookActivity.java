package com.generalflow.bridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PortalLookActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_look);
        webView = findViewById(R.id.webview);
        connection_layout = findViewById(R.id.connection_layout);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    Intent intent = new Intent(PortalLookActivity.this, PortalActivity.class);
                    intent.putExtra("portalUrl", url);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (url.startsWith(GeneralSDK.appScheme)) {
                    //return the user to the native content
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return true;
                } else {
                    // go back to main to show apps factory btn + native content
                    Constants.LOOK_URL = Uri.parse(url).toString();
                    finish();
                    return true;
                }
            }
        });
        loadUrl((Constants.LOOK_URL));
    }
}