package com.mag.general;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import static com.mag.general.Constants.ADS_GAME_ID_EXTRA;
import static com.mag.general.Constants.APP_SCHEME_EXTRA;
import static com.mag.general.Constants.END_POINT_EXTRA;

public class GeneralSDK {

    private static GeneralSDK mInstance = null;

    public static String appScheme = "";
    public static String endPoint = "";
    public static String gameId = "";
    private Context context;

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

    public GeneralSDK setUnityAdsGameId(String gameId) {
        this.gameId = gameId;
        return mInstance;
    }

    public void launch(Activity activity) {
        Intent intent = new Intent(activity, GeneralMainActivity.class);
        intent.putExtra(APP_SCHEME_EXTRA, this.appScheme);
        intent.putExtra(END_POINT_EXTRA, this.endPoint);
        intent.putExtra(ADS_GAME_ID_EXTRA, this.gameId);
        activity.startActivity(intent);
    }

    public Context getAppContext() {
        return context;
    }
}
