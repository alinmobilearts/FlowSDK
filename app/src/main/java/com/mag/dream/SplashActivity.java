package com.mag.dream;

import android.os.Bundle;

import com.mag.general.GeneralSDK;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Intent intent = new Intent(this, MainNativeContent.class);
        startActivity(intent);
        finish();*/
        GeneralSDK.initialize()
                .with(this)
                .setScheme(getString(R.string.app_scheme))
                .setEndPoint("d4hel6cgfmgdz.cloudfront.net")
                .launch(this);
        finish();
    }
}