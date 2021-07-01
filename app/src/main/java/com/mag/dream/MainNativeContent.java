package com.mag.dream;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//import com.generalflow.bridge.GeneralBaseActivity;
//import com.generalflow.bridge.GeneralSDK;
import com.generalflow.bridge.GeneralBaseActivity;
import com.generalflow.bridge.GeneralSDK;
import com.video.glitcheffect.GlitchEffectPlugin;

//import static com.generalflow.bridge.Constants.REACT_NATIVE_PREF_NAME;
//import static com.generalflow.bridge.Constants.REACT_P_URL;

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

//        SharedPreferences preferences = getSharedPreferences(REACT_NATIVE_PREF_NAME, MODE_MULTI_PROCESS);
//        String url = preferences.getString(REACT_P_URL, "");

        if (shouldShowPButton()) {
            customview_btn.setVisibility(View.VISIBLE);
            customview_btn.setText(getSTitle());
            customview_btn.setOnClickListener(v -> openPortalActivity());
        } else {
            customview_btn.setVisibility(View.GONE);
        }
    }

    @Override
    protected void manageButton() {
        registerPButton(findViewById(R.id.portal_container), findViewById(R.id.portal_text));
    }
}