package com.generalflow.bridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class GeneralSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String action = b.getString(Constants.ACTION_ID);
            String deeplink = b.getString(Constants.DEEPLINK);
            if (action != null && action.equalsIgnoreCase("1")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
    }
}