package com.htc.htcwalletsdk.Act;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.htc.htcwalletsdk.CONSTANT;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Protect.ResultChecker;
import com.htc.htcwalletsdk.R;
import com.htc.htcwalletsdk.Utils.ZKMALog;

public class UITryOftenAct extends BaseResultCallbackAct {
    private static final String TAG = "WalletSecure_TryOftenAct";
    private boolean bTryTooOftenActivity = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_try_often);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // For not opaque(transparent) color.
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        // Get title, description and button
        TextView title = findViewById(R.id.try_often_title);
        TextView description = findViewById(R.id.try_often_description);
        Button checkBtn = findViewById(R.id.check_button);

        Bundle bundle = getIntent().getBundleExtra("input");
        String flag = bundle.getString("flag");
        String lock_mins = bundle.getString(CONSTANT.UI_IN_KEY.MINS);


        if(CONSTANT.FLAG_UI_TRY_OFTEN.TRY_LATER.equals(flag)) {
            title.setText(getResources().getText(R.string.text_try_often_title1));
            if(lock_mins!=null){
                description.setText(String.format(getString(R.string.text_try_often_description1), lock_mins));
            }
            else {
                ZKMALog.i(TAG,"Not get lock-mins input, use 10 mins to instead");
                description.setText(String.format(getString(R.string.text_try_often_description1), "10"));
            }
            checkBtn.setText(getResources().getText(R.string.text_try_often_button1));
        } else if(CONSTANT.FLAG_UI_TRY_OFTEN.TRY_TOO_OFTEN.equals(flag)){
            title.setText(getResources().getText(R.string.text_try_often_title2));
            description.setText(getResources().getText(R.string.text_try_often_description2));
            checkBtn.setText(getResources().getText(R.string.text_try_often_button2));
            if (ResultChecker.mSdkProtectorListener != null) // App have to set it first.
            {
                bTryTooOftenActivity = true;
                ResultChecker.mSdkProtectorListener.onNotify(ISdkProtector.NotifyType.UIEVENT, RESULT.E_TEEKM_RETRY_RESET, "UITryOftenAct.Activity.onCreate", null ); // callback to APP for notify.
            }

        } else {
            ZKMALog.e(TAG, "no flag");
            finish(CONSTANT.UI_RESULT.ERROR);
            return;
        }

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(CONSTANT.UI_RESULT.SUCCESS);
                if (ResultChecker.mSdkProtectorListener != null) // App have to set it first.
                {
                    if(bTryTooOftenActivity == true) // notify APP the clearSeed occurred
                        ResultChecker.mSdkProtectorListener.onNotify(ISdkProtector.NotifyType.UIEVENT, RESULT.E_TEEKM_RETRY_RESET,"UITryOftenAct.Button.onClick", null ); // callback to APP for notify.
                }
            }
        });
    }

    //finish activity and return result to WalletSecureUI
    int activityResultValue = CONSTANT.UI_RESULT.CRASH;
    public void finish(int result_id) {
        activityResultValue = result_id;
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ZKMALog.d(TAG, "onPause()");
    }


    @Override
    protected void onDestroy() {
        bTryTooOftenActivity = false;
        if(null!= sResultCallback){

            if(activityResultValue == CONSTANT.UI_RESULT.SUCCESS){
                sResultCallback.makeSuccess();
                sResultCallback = null;
            }
            else {
                sResultCallback.makeFailure(activityResultValue);
                sResultCallback = null;
            }
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (ResultChecker.mSdkProtectorListener != null) // App have to set it first.
        {
            if(bTryTooOftenActivity == true) // notify APP the clearSeed occurred
                ResultChecker.mSdkProtectorListener.onNotify(ISdkProtector.NotifyType.UIEVENT, RESULT.E_TEEKM_RETRY_RESET,"UITryOftenAct.Button.onBackPressed", null ); // callback to APP for notify.
        }
        finish(CONSTANT.UI_RESULT.ON_BACK);
    }
}
