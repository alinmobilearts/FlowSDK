package com.mag.general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PortalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        String url = getIntent().getStringExtra("portalUrl");
        WebView portalWeb = findViewById(R.id.portalWeb);
        ProgressBar webProgress = findViewById(R.id.progressBar);
        portalWeb.getSettings().setJavaScriptEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(Utils.getSTitle());
        toolbar.setNavigationOnClickListener(v -> finish());

        portalWeb.setWebChromeClient(new WebChromeClient() {
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

        portalWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        portalWeb.loadUrl(url);
    }
}