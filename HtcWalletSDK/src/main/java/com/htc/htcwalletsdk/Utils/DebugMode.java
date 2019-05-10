package com.htc.htcwalletsdk.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.htc.htcwalletsdk.BuildConfig;
import com.htc.htcwalletsdk.R;

/**
 * You can module func block and control debug target module dynamically by set properties.
 * Created by hawk_wei on 2018/9/28.
 */

public class DebugMode extends Activity {
    static final String TAG = "DebugMode";
    public static int NoDebug       = 0;      // 0x000000000
    public static int DebugFunc1    = 1 << 0; // 0x000000001
    public static int DebugFunc2    = 1 << 1; // 0x000000002
    public static int DebugFunc3    = 1 << 2; // 0x000000004
    public static int DebugFunc4    = 1 << 3; // 0x000000008
    public static int DebugFunc5    = 1 << 4; // 0x000000010=16
    public static int DebugFunc6    = 1 << 5; // 0x000000020=32

    public static int sDebugMode = NoDebug;
    EditText etDebugNumber;

    public static void CheckDebugMode() {
        // APP properties can only set by self-process.
        // String preProperty = PropertiesWrapper.setString("TZ_support", "true");
        // ZKMALog.d(TAG, "preProperty="+ preProperty);
        // String property = PropertiesWrapper.getString("TZ_support", "false");
        // ZKMALog.d(TAG, "   property="+property);

        // the user can only set this number during APP running.
        if (BuildConfig.DEBUG) {
            sDebugMode = PropertiesWrapper.getInt("htcwalletsdk_DebugMode", "0"); // defValue=NoDebug=0
        }
        else {
            sDebugMode = PropertiesWrapper.getInt("htcwalletsdk_DebugMode", "0"); // defValue=NoDebug=0
        }
        ZKMALog.d(TAG, "sDebugMode="+ sDebugMode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debugmode_activity);
        etDebugNumber = findViewById(R.id.etDebugNumber);
    }

    public void onClickDebugNumber(View v) {
        switch(Integer.parseInt(v.getTag().toString())) {
            case 999:
                int number = Integer.parseInt(etDebugNumber.getText().toString());
                ZKMALog.d(TAG, "number="+number);
                break;
            default:
                ZKMALog.d(TAG, "Not support this number");
        }
    }

}
