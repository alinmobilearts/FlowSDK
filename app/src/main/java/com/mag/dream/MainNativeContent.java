package com.mag.dream;

import android.os.Bundle;
import android.widget.Button;

import com.mag.general.GeneralBaseActivity;

public class MainNativeContent extends GeneralBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_native_content);
        Button portal_btn = findViewById(R.id.portal_btn);
        registerPButton(portal_btn);

    }
}