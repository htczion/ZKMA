package com.htc.htcwalletsdk.Protect;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;

import com.htc.htcwalletsdk.BuildConfig;

/**
 * Created by hawk_wei on 2018/11/2.
 */

public class ScreenProtector {

    public static void forbidScreenCaptureOnRelease(Activity activity) {
        if(!BuildConfig.DEBUG)
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    public static void ActivityPortraitModeOnly(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
