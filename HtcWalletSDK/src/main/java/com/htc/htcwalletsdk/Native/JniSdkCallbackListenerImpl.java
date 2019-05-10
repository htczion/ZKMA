package com.htc.htcwalletsdk.Native;

import android.util.Log;

import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * @comment
 * Developer can implement IJniCallback in SDK or APP either.
 * This is an example to implement the callback in APP.
 * Created by hawk_wei on 2018/9/18.
 */

public class JniSdkCallbackListenerImpl implements IJniCallbackListener {
    private final String TAG = this.getClass().getSimpleName();
    public  String JNIcallback_status = "not available";
    @Override
    public void CallbackStatus(String fromNative){
        ZKMALog.d(TAG, " JNI Native CallbackStatus!!! " + fromNative);
        JNIcallback_status = fromNative;
    }

    /**
     function: UICreatePin (Page.3 1.1~1.2, Page.7 3.1~3.2)
     input:
     int flag, show error message when non-zero (Page.3 1.2.A)
     String pin (6bytes), the buffer to get users' pin
     output:
     String pin (6bytes), users' pin
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int UICreatePin(int flag, String pin) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

    /**
     function: UIShowAndVerifyWords (Page.4~5 2.1~2.3)
     input:
     String words (delimited by space)
     ex:
     "borrow visit lizard primary defy marine item about nurse crew guess abuse"
     int words_size, words size
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int	UIShowAndVerifyWords(String words, int words_size) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

    /**
     function: UIRestoreWords (Page.6 1.1)
     input:
     String words
     int words_size, words buffer size
     output:
     String words (delimited by space)
     ex:
     "borrow visit lizard primary defy marine item about nurse crew guess abuse"
     int word_size, size of returned words
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int	UIRestoreWords(String words, int words_size) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

    /**
     function: UIVerifyPin
     input:
     String pin (6bytes), users' pin
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int	UIVerifyPin(String pin) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }


    /**
     function: UIShowWords
     input:
     String words (delimited by space)
     int words_size, words buffer size
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int UIShowWords(String words, int words_size) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

    /**
     function: UIResetConfirm
     input:
     int *reset (1bytes)
     output:
     int *reset (1bytes), 1 to reset
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int UIResetConfirm(int reset) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

    /**
     function: UITransaction
     input:
     uint32_t coin_type
     ex: https://github.com/satoshilabs/slips/blob/master/slip-0044.md
     0 BTC
     2 LTC
     60 ETH
     String to, SEND TO
     String fee, FEE
     String fee_usd
     String amount, AMOUNT
     String amount_usd
     String pin, users' pin
     return:
     0: success
     < 0: other errors
     */
    @Override
    public int UITransaction(long coin_type, String to, String fee, String fee_usd, String amount, String amount_usd, String pin) {
        // TODO: implement in JAVA and C++
        return RESULT.SUCCESS;
    }

}
