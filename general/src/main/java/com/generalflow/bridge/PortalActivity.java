package com.generalflow.bridge;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

public class PortalActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        String url = getIntent().getStringExtra("portalUrl");
        webView = findViewById(R.id.webview);
        connection_layout = findViewById(R.id.connection_layout);
        ProgressBar webProgress = findViewById(R.id.progressBar);
        webView.getSettings().setJavaScriptEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Utils.getSTitle());
        toolbar.setNavigationOnClickListener(v -> finish());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                if (i < 100 && webProgress.getVisibility() == View.GONE) {
                    webProgress.setVisibility(View.VISIBLE);
                }
                webProgress.setProgress(i);
                if (i == 100) {
                    webProgress.setProgress(View.VISIBLE);
                    webProgress.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        loadUrl(url);
    }

}