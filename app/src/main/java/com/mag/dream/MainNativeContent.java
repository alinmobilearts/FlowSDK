package com.mag.dream;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.generalflow.bridge.GeneralBaseActivity;
import com.generalflow.bridge.GeneralSDK;
import com.video.glitcheffect.GlitchEffectPlugin;

public class MainNativeContent extends GeneralBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_native_content);
        Button portal_btn = findViewById(R.id.portal_btn);
        Button glitch_video = findViewById(R.id.glitch_video);
        Button customview_btn = findViewById(R.id.customview_btn);

        registerPButton(portal_btn);
        glitch_video.setOnClickListener(v -> GlitchEffectPlugin.getInstance().launch(MainNativeContent.this));

        if (GeneralSDK.getInstance().shouldShowAds()) {
            Log.d("ADS", "should show ads");
        } else {
            Log.d("ADS", "should not show ads");
        }

        if (shouldShowPButton()) {
            customview_btn.setVisibility(View.VISIBLE);
            customview_btn.setText(getSTitle());
            customview_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPortalActivity();
                }
            });
        } else {
            customview_btn.setVisibility(View.GONE);
        }

    }
}