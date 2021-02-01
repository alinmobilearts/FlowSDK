package com.mag.dream;

import android.os.Bundle;


import com.generalflow.bridge.GeneralSDK;
import com.generalflow.bridge.GeneralSplashActivity;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneralSDK.initialize()
                .with(this)
                .setApplication(getApplication())
                .setScheme(getString(R.string.app_scheme))
                .setEndPoint("d4hel6cgfmgdz.cloudfront.net")
                .withFCMNotification("dnx8j0")
//                .setPackageId("com.mag.dream")
                .launch(this);
        finish();
    }

}