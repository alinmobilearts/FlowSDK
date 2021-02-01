package com.generalflow.bridge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.generalflow.bridge.Constants.EXTERNAL_URL_EXTRA;

public class GeneralBaseActivity extends AppCompatActivity {
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
            openPortalActivity();
        } else {
            //TODO initialize Ads
        }
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

        if (portalUrl != null && !portalUrl.isEmpty()) {
            button.setVisibility(View.VISIBLE);
            button.setText(Utils.getSTitle());
            button.setOnClickListener(v -> {
                openPortalActivity();
            });
        } else {
            button.setVisibility(View.GONE);
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

}
