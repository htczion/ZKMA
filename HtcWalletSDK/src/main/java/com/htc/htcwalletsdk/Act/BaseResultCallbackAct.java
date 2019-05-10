package com.htc.htcwalletsdk.Act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Protect.ScreenProtector;
import com.htc.htcwalletsdk.Utils.ResultCallback;

public class BaseResultCallbackAct extends AppCompatActivity {

    public static ResultCallback sResultCallback;

    public int ScreenProtector(int errorcode, int param1, String param2) {
        ScreenProtector.forbidScreenCaptureOnRelease(this);
        ScreenProtector.ActivityPortraitModeOnly(this);
        return RESULT.SUCCESS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenProtector(RESULT.UNKNOWN, 1234, "TEST ISdkProtector callback");
    }
}
