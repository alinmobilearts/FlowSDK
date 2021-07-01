package com.generalflow.bridge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;

import com.adjust.sdk.AdjustReferrerReceiver;

import java.net.URLDecoder;

import static android.content.Context.MODE_PRIVATE;
import static com.generalflow.bridge.Constants.FCM_TOKEN;
import static com.generalflow.bridge.Constants.FIREBASE_INSTANCE_ID;
import static com.generalflow.bridge.Constants.FLUTTER_PREF_NAME;
import static com.generalflow.bridge.Constants.FLUTTER_P_URL;
import static com.generalflow.bridge.Constants.PREF_NAME;

public class Utils {

    static String constructUrl(String url) {
        // fix the url
        if (url != null) {

            if (url.startsWith("http://")) {
                url = url.replaceAll("http://", "https://");
            }

            if (!url.startsWith("https://")) {
                url = "https://" + url;
            }

        }

        // add path & parameters
        String packageName = getPackageName();

        url = url + "/" + packageName + ".html?" + getDeviceId();

        return url;
    }

    static String getDeviceId() {
        Context context = GeneralSDK.getInstance().getAppContext();
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        if (androidId == null || androidId.isEmpty() || androidId.equalsIgnoreCase("null")) {
            androidId = "";
        }
        return androidId;
    }

    public static String getPortalUrl() {
        String portalUrl;
        if (Constants.LOOK_URL != null && !Constants.LOOK_URL.startsWith("http")
                && !Constants.LOOK_URL.startsWith(GeneralSDK.appScheme)) {
            portalUrl = Uri.parse(Constants.LOOK_URL).getQuery();
        } else {
            portalUrl = Constants.LOOK_URL;
        }
        return portalUrl;
    }

    /*private void openPortalActivity(Context context) {
        if (Constants.LOOK_URL != null && !Constants.LOOK_URL.startsWith("http")
                && !Constants.LOOK_URL.startsWith(GeneralSDK.appScheme)) {
            String myURL = Uri.parse(Constants.LOOK_URL).getQuery();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            portalUrl = myURL;
            intent.setData(Uri.parse(myURL));
            context.startActivity(intent);
        } else {
            portalUrl = Constants.LOOK_URL;
            Intent intent = new Intent(context, PortalLookActivity.class);
            context.startActivity(intent);
        }
    }*/

    public static String getSTitle() {
        String title = "";
        try {
            String url = URLDecoder.decode(Constants.LOOK_URL, "UTF-8");
            Uri uri = Uri.parse(url);
            title = uri.getQueryParameter("sName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title;
    }

    public static String getFCMToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preferences.getString(FCM_TOKEN, "");
    }

    public static void saveFCMToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FCM_TOKEN, token);
        editor.apply();
    }

    public static String getFirebaseInstanceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return preferences.getString(FIREBASE_INSTANCE_ID, "");
    }

    public static void saveFirebaseToken(Context context, String token) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FIREBASE_INSTANCE_ID, token);
        editor.apply();
    }

    public static void savePUrl(Context context, String pUrl) {
        SharedPreferences preferences = context.getSharedPreferences(FLUTTER_PREF_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FLUTTER_P_URL, pUrl);
        editor.apply();
        System.out.println("saved url sdk: " + pUrl);
        /*SharedPreferences preferencesReact = context.getSharedPreferences(REACT_NATIVE_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editorReact = preferencesReact.edit();
        editorReact.putString(REACT_P_URL, pUrl);
        editorReact.apply();*/
    }

    public static void sendInstallReferrer(Context context, Intent intent) {
        new AdjustReferrerReceiver().onReceive(context, intent);
    }

    public static String getPackageName() {
        String packageName;
        if (GeneralSDK.getInstance().getPackageId() != null
                && !GeneralSDK.getInstance().getPackageId().isEmpty()) {
            packageName = GeneralSDK.getInstance().getPackageId();
        } else {
            packageName = GeneralSDK.getInstance().getAppContext().getPackageName();
        }
        return packageName;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo networkInfo : info)
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
