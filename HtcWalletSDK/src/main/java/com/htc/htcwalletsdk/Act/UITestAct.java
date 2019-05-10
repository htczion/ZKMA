package com.htc.htcwalletsdk.Act;

import android.os.Bundle;
import android.view.View;

import com.htc.htcwalletsdk.R;

public class UITestAct extends BaseResultCallbackAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test);

        sResultCallback.getOutput().putString("test", "output data ready");

    }

    public void onSuccess(View view)
    {
        if (null != sResultCallback)
        {
            sResultCallback.makeSuccess();
            sResultCallback = null;
            finish();
        }
    }

    public void onFailed(View view)
    {
        if (null != sResultCallback)
        {
            sResultCallback.makeFailure(0x823506);
            sResultCallback = null;
            finish();
        }
    }
}
