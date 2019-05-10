package com.htc.htcwalletsdk.Act;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.htc.htcwalletsdk.R;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * Using the transparent Activity to simulate a Dialog like Activity,
 * since SDK can only get Context of ApplicationContext, not Activity context.
 * Created by shihshi-mac on 2018/11/16.
 */
public class UIErrorDialogAct extends BaseResultCallbackAct {
    private static final String TAG = "UIErrorDialogAct";

    @SuppressLint("StringFormatMatches")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = this.getWindow();
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // fix Android App crash if use this version
        // https://stackoverflow.com/questions/28279726/android-get-targetsdkversion-in-runtime
        setContentView(R.layout.activity_default_error_dialog);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Bundle bundle = getIntent().getBundleExtra("input");

        if (bundle != null) {
            final int errorCode = bundle.getInt("errorCode");
            final String errorMessage = bundle.getString("errorMessage");

            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            //adb.setTitle(R.string.aar_name);
            if(errorMessage != null)
                adb.setMessage(errorMessage);
            else
                adb.setMessage(String.format(this.getString(R.string.text_generic_error_description), errorCode));
            adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(sResultCallback != null)
                        sResultCallback.makeSuccess();
                    else
                        ZKMALog.e(TAG, "sResultCallback is null, An Error has occurred. Error code="+errorCode);
                    finish();
                }
            });

            adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(sResultCallback != null)
                        sResultCallback.makeSuccess();
                    else
                        ZKMALog.e(TAG, "sResultCallback is null, An Error has occurred. Error code="+errorCode);
                    finish();

                }
            });
            adb.show();
        }
    }
}
