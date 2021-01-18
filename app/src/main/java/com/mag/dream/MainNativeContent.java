package com.mag.dream;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mag.general.GeneralBaseActivity;
import com.video.glitcheffect.GlitchEffectPlugin;

public class MainNativeContent extends GeneralBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_native_content);
        Button portal_btn = findViewById(R.id.portal_btn);
        registerPButton(portal_btn);

        Button glitch_video = findViewById(R.id.glitch_video);
        glitch_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlitchEffectPlugin.getInstance().launch(MainNativeContent.this);
            }
        });

    }
}