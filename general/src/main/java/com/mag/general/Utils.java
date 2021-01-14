package com.mag.general;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;

import java.net.URLDecoder;

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
        String packageName = GeneralSDK.getInstance().getAppContext().getPackageName();
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
}
