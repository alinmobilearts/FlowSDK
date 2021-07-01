package com.generalflow.bridge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.adjust.sdk.webbridge.AdjustBridge;
import com.google.firebase.analytics.FirebaseAnalytics;

import static com.generalflow.bridge.Constants.END_POINT_EXTRA;

public class GeneralMainActivity extends BaseWebViewActivity {

    private String endPoint;
    public final static String TAG = "WebBridge";
    public final static String CLASS_NAME = "GeneralMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_general);
        webView = findViewById(R.id.webview);
        connection_layout = findViewById(R.id.connection_layout);
        webView.getSettings().setJavaScriptEnabled(true);
        endPoint = getIntent().getStringExtra(END_POINT_EXTRA);

        try {
            FirebaseAnalytics.getInstance(this).getAppInstanceId().addOnCompleteListener(task -> {
                Log.d("firebase instance id: ", task.getResult());
                Utils.saveFirebaseToken(GeneralMainActivity.this, task.getResult());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

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
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String token = Utils.getFCMToken(GeneralMainActivity.this);
                String appInstanceId = Utils.getFirebaseInstanceId(GeneralMainActivity.this);
                Log.d("FCM Token bridge: ", token);

                if (token != null && !token.isEmpty()) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                        webView.evaluateJavascript("sendToken('" + token + "','" + GeneralSDK.getInstance().getAdjustEventFCMToken() + "');", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d(TAG, "onReceiveValue: " + s);
                            }
                        });
                    } else {
                        webView.loadUrl("javascript:sendToken('" + token + "','" + GeneralSDK.getInstance().getAdjustEventFCMToken() + "');");
                    }
                }

                if (appInstanceId != null && !appInstanceId.isEmpty()) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                        webView.evaluateJavascript("sendAppInstanceId('" + appInstanceId + "');", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.d(TAG, "onReceiveValue: " + s);
                            }
                        });
                    } else {
                        webView.loadUrl("javascript:sendAppInstanceId('" + appInstanceId + "');");
                    }
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                String url = request.getUrl().toString();
                if (url.startsWith(GeneralSDK.appScheme)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    String myURL = Uri.parse(url).getQuery();
                    if (myURL != null && !myURL.isEmpty()) {
                        Constants.LOOK_URL = myURL;
                    }
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent;
                if (url.startsWith("http")) {
                    return false;
                } else if (url.startsWith(GeneralSDK.appScheme)) {
                    Log.d(TAG, CLASS_NAME + "Constants.LOOK_URL: " + Constants.LOOK_URL);
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    String myURL = Uri.parse(url).getQuery();
                    try {
                        if (Uri.parse(url).getQueryParameter("showAds") != null) {
                            GeneralSDK.showAds = Boolean.parseBoolean(Uri.parse(url).getQueryParameter("showAds"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GeneralSDK.showAds = false;
                    }

                    Utils.savePUrl(GeneralMainActivity.this, myURL);
                    if (myURL != null && !myURL.isEmpty()) {
                        Constants.LOOK_URL = myURL;
                    }

                    startActivity(intent);
                    finish();
                    return true;
                } else {

                    //open app scheme
                    Intent nativeIntent = new Intent(Intent.ACTION_VIEW);
                    nativeIntent.setData(Uri.parse(GeneralSDK.appScheme + "://"));

                    /* String myURL = Uri.parse(url).getQuery();
                    if (myURL != null && !myURL.isEmpty()) {
                        nativeIntent.putExtra(Constants.EXTERNAL_URL_EXTRA, myURL);
                    }*/
                    startActivity(nativeIntent);
//                    try {
//                        Intent extIntent = new Intent(Intent.ACTION_VIEW);
//                        extIntent.setData(Uri.parse(url));
//                        startActivity(extIntent);
//                    } catch (Exception e) {
//                        Intent nativeIntent = new Intent(Intent.ACTION_VIEW);
//                        String myURL = Uri.parse(url).getQuery();
//                        if (myURL != null && !myURL.isEmpty()) {
//                            nativeIntent.putExtra(Constants.EXTERNAL_URL_EXTRA, myURL);
//                        }
//                        startActivity(nativeIntent);
//                    }
                    GeneralSDK.showAds = false;
                    try {
                        //open the scheme after opening native
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    finish();
                }
                return true;
            }
        });

        AdjustBridge.registerAndGetInstance(GeneralSDK.getInstance().getApplication(), webView);
        loadUrl(Utils.constructUrl(endPoint));
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