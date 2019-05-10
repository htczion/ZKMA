package com.htc.htcwalletsdk;

import android.util.Log;

import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * Created by hawk_wei on 2018/9/27.
 */

public class GlobalVariable {
    static final String TAG = "GlobalVariable";
    static int ErrorCode = RESULT.SUCCESS;

    static public int GetErrorCode() {
        ZKMALog.v(TAG, "GetErrorCode="+ErrorCode);
        return ErrorCode;
    }

    static public void SetErrorCode(int err) {
        ZKMALog.v(TAG, "SetErrorCode="+err);
        ErrorCode = err;
    }

}
