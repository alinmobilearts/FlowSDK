package com.generalflow.bridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.generalflow.bridge.Constants.EXTERNAL_URL_EXTRA;

public abstract class GeneralBaseActivity extends AppCompatActivity {
    public static final String TAG = " FlowSDK";
    public static final String CLASS_NAME = " GeneralBaseActivity ";
    protected String portalUrl = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String externalUrl = getIntent().getStringExtra(EXTERNAL_URL_EXTRA);
        if (externalUrl != null && externalUrl.isEmpty()) {
            Intent extIntent = new Intent(Intent.ACTION_VIEW);
            extIntent.setData(Uri.parse(externalUrl));
            startActivity(extIntent);
        } else if (Constants.LOOK_URL != null && !Constants.LOOK_URL.isEmpty()) {
            Log.d(TAG, CLASS_NAME + "Constants.LOOK_URL: " + Constants.LOOK_URL);
            openPortalActivity();
        } else {
            //TODO initialize Ads
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        manageButton();
    }

    protected void openPortalActivity() {
        if (Constants.LOOK_URL != null && !Constants.LOOK_URL.startsWith("http")
                && !Constants.LOOK_URL.startsWith(GeneralSDK.appScheme)) {
            String myURL = Uri.parse(Constants.LOOK_URL).getQuery();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            portalUrl = myURL;
            intent.setData(Uri.parse(myURL));
            startActivity(intent);
        } else {
            portalUrl = Constants.LOOK_URL;

            Intent intent = new Intent(this, PortalLookActivity.class);
            startActivity(intent);
        }
    }

    protected void registerPButton(TextView button) {
        Log.d(TAG, CLASS_NAME + "registerPButton portalUrl: " + portalUrl);
        if (portalUrl != null && !portalUrl.isEmpty()) {
            button.setVisibility(View.VISIBLE);
            button.setText(Utils.getSTitle());
            button.setOnClickListener(v -> openPortalActivity());
        } else {
            button.setVisibility(View.GONE);
        }
    }

    protected void registerPButton(View container, TextView textView) {
        Log.d(TAG, CLASS_NAME + "registerPButton portalUrl: " + portalUrl);
        if (portalUrl != null && !portalUrl.isEmpty()) {
            container.setVisibility(View.VISIBLE);
            textView.setText(Utils.getSTitle());
            container.setOnClickListener(v -> openPortalActivity());
        } else {
            container.setVisibility(View.GONE);
        }
    }

    protected boolean shouldShowPButton() {
        if (portalUrl != null && !portalUrl.isEmpty()) {
            return true;
        }
        return false;
    }

    protected String getSTitle() {
        return Utils.getSTitle();
    }

    /*protected void registerPMenuDrawer(TextView button) {
        String portalUrl = Utils.getPortalUrl();

        if (portalUrl != null && !portalUrl.isEmpty()) {
            button.setVisibility(View.VISIBLE);
            button.setText(Utils.getSTitle());
            button.setOnClickListener(v -> {
                openPortalActivity();
            });
        } else {
            button.setVisibility(View.GONE);
        }
    }*/
    protected abstract void manageButton();
}
