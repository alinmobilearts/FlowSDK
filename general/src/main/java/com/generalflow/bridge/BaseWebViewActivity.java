package com.generalflow.bridge;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class BaseWebViewActivity extends AppCompatActivity {
    View connection_layout;
    WebView webView;
    String url;

    public void handleNoInternetConnection() {
        connection_layout.setVisibility(View.VISIBLE);
        Button retryBtn = findViewById(R.id.retry_btn);
        retryBtn.setOnClickListener(view -> {
            connection_layout.setVisibility(View.GONE);
            loadUrl(url);
        });
    }

    protected void loadUrl(String url) {
        this.url = url;
        if (Utils.isConnectedToInternet(this)) {
            webView.loadUrl(url);
        } else {
            handleNoInternetConnection();
        }
    }

}
