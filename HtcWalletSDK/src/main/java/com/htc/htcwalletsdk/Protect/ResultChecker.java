package com.htc.htcwalletsdk.Protect;

import android.content.Context;
import android.os.Bundle;

import com.htc.htcwalletsdk.Act.UITryOftenAct;
import com.htc.htcwalletsdk.CONSTANT;
import com.htc.htcwalletsdk.Export.ExportFields;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.GlobalVariable;
import com.htc.htcwalletsdk.Security.Core.KeyAgent;
import com.htc.htcwalletsdk.Utils.ParamHolder;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * To prevent anyone trying to hack by UI input hacking
 * Created by hawk_wei on 2018/10/25.
 */

public class ResultChecker extends SdkProtector {
    private static final String TAG = "ResultChecker";
    public static ISdkProtector mSdkProtectorListener;

    static public void setSdkProtectorListener(ISdkProtector sdkProtectorListener){
        mSdkProtectorListener = sdkProtectorListener;
    }

    static public void Diagnostic(final Context context, final int errcode) {
        if(context!= null && errcode!=RESULT.SUCCESS) {
            Bundle in = new Bundle();
            ParamHolder holder = new ParamHolder();
            holder.setIn(in);
            in.putString("test", "input data ready");
            int lock_mins = 0;
            ZKMALog.i(TAG, "Diagnostic    errcode=" + errcode);
            boolean bRet;
            GlobalVariable.SetErrorCode(errcode);
            switch (errcode) {
                case RESULT.E_TEEKM_RETRY_45M:
                    lock_mins += 15;
                case RESULT.E_TEEKM_RETRY_30M:
                    lock_mins += 15;
                case RESULT.E_TEEKM_RETRY_15M:
                    lock_mins += 15;
                    in.putString("flag", CONSTANT.FLAG_UI_TRY_OFTEN.TRY_LATER);
                    in.putString(CONSTANT.UI_IN_KEY.MINS, String.valueOf(lock_mins));
                    bRet = KeyAgent.doShowUIActivity(UITryOftenAct.class, context, holder);
                    ZKMALog.d(TAG, "Verify output data : " + holder.getOut().get("test"));
                    break;
                case RESULT.E_TEEKM_RETRY_RESET:
                    in.putString("flag", CONSTANT.FLAG_UI_TRY_OFTEN.TRY_TOO_OFTEN);
                    bRet = KeyAgent.doShowUIActivity(UITryOftenAct.class, context, holder);
                    ZKMALog.d(TAG, "Verify output data : " + holder.getOut().get("test"));
                    break;
                default:
                    ZKMALog.d(TAG, "bShowErrorDialog="+ExportFields.bShowErrorDialog+",  Unhandle error code="+errcode);
                    if(ExportFields.bTZ_support == true && ExportFields.bShowErrorDialog == true)
                        SdkProtector.GenericErrorChecker(context, errcode);
                    else
                        ZKMALog.d(TAG, "SW wallet do no any prompt.");
            }
            if (mSdkProtectorListener != null) // App have to set it first.
                mSdkProtectorListener.onErrorFeedback(errcode, 0, null); // callback to APP for notify.

        } else if (context == null) {
            ZKMALog.e(TAG, "Unhandled ERROR   since  context= null");
        }
    }

}
