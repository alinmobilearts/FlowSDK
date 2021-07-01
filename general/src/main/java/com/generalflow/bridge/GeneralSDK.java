package com.generalflow.bridge;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import static com.generalflow.bridge.Constants.APP_SCHEME_EXTRA;
import static com.generalflow.bridge.Constants.END_POINT_EXTRA;

public class GeneralSDK {

    private static GeneralSDK mInstance = null;

    public static String appScheme = "";
    public static String endPoint = "";
    public static String packageId = "";
    public static String adjustEventFCMToken = "";
    private Context context;
    private Application application;
    public static boolean showAds = true;

    public GeneralSDK() {
    }

    public static GeneralSDK initialize() {
        if (mInstance == null) {
            mInstance = new GeneralSDK();
        }
        return mInstance;
    }

    public static GeneralSDK getInstance() {
        if (mInstance == null) {
            mInstance = new GeneralSDK();
        }
        return mInstance;
    }

    public GeneralSDK with(Context context) {
        this.context = context;
        return mInstance;
    }

    public GeneralSDK setScheme(String appScheme) {
        this.appScheme = appScheme;
        return mInstance;
    }

    public GeneralSDK setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return mInstance;
    }

    @SuppressWarnings("only for testing")
    public GeneralSDK setPackageId(String packageId) {
        this.packageId = packageId;
        return mInstance;
    }

    public void launch(Activity activity) {
        Intent intent = new Intent(activity, GeneralMainActivity.class);
        intent.putExtra(APP_SCHEME_EXTRA, this.appScheme);
        intent.putExtra(END_POINT_EXTRA, this.endPoint);
        activity.startActivity(intent);
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            String action = b.getString(Constants.ACTION_ID);
            String deeplink = b.getString(Constants.DEEPLINK);
            Intent notificationIntent;
            if (action != null && action.equalsIgnoreCase("1")) {
                try {
                    notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    notificationIntent.setPackage("com.android.chrome");
                    activity.startActivity(notificationIntent);
                } catch (Exception e) {
                    notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
                    notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(notificationIntent);
                }
            }
        }
    }

    public Context getAppContext() {
        return context;
    }

    public void setFcmToken(String token) {
        Utils.saveFCMToken(context, token);
    }

    public void sendInstallReferrer(Context context, Intent intent) {
        Utils.sendInstallReferrer(context, intent);
    }

    public GeneralSDK setApplication(Application application) {
        this.application = application;
        return mInstance;
    }

    public Application getApplication() {
        return application;
    }

    public GeneralSDK withFCMNotification(String adjustEventFCMToken) {
        this.adjustEventFCMToken = adjustEventFCMToken;
        return mInstance;
    }

    public String getAdjustEventFCMToken() {
        return adjustEventFCMToken;
    }

    public boolean shouldShowAds() {
        String portalUrl = null;
        if (Constants.LOOK_URL != null && !Constants.LOOK_URL.startsWith("http") && !Constants.LOOK_URL.startsWith(GeneralSDK.appScheme)) {
            String myURL = Uri.parse(Constants.LOOK_URL).getQuery();
            portalUrl = myURL;
        } else {
            portalUrl = Constants.LOOK_URL;
        }

        if (portalUrl != null && !portalUrl.isEmpty()) {
            return false;
        }

        return showAds;
    }

    public String getPackageId() {
        return packageId;
    }
}
