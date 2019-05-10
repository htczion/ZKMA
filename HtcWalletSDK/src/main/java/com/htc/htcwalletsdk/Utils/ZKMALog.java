package com.htc.htcwalletsdk.Utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.util.Log;

import static com.htc.htcwalletsdk.Utils.GenericUtils.byteArrayToHexString;

public final class ZKMALog {
    // public static final boolean DEBUG =  com.htc.WalletTestApp.BuildConfig.DEBUG ||
    //         (RomVersionCheckUtil.isHTCDevice() && RomVersionCheckUtil.isUserTrialOrTestRom());
    public static final boolean bDEBUG =  com.htc.htcwalletsdk.BuildConfig.DEBUG;
    public static final String TAG = "ZKMALog";

    public static boolean isLoggable(String tag, int var1){
        return Log.isLoggable(TAG, var1);
    }

    public static void partial(String tag, String message, int endIndex) {
        if(endIndex > message.length())
            Log.v(TAG, message);
        else
            Log.v(TAG, message.substring(0,endIndex));
    }

    // Android Logcat can't print char over MAX count, so print it by this method.
    private static final int CHUNKMAX = 1024;
    private static final int MSG_OFFSET = 3; // extend length from 1 (byte) to 6 (string) since "%02x,"
    public static void byteArray(String tag, byte[] byteArray) {
        if (bDEBUG) {
            if(byteArray != null ) {
                String message = byteArrayToHexString(byteArray);
                Log.v(TAG, "byteArray.length="+byteArray.length+"  message.length = " + message.length());
                int chunkCount = byteArray.length / CHUNKMAX;     // integer division
                int byteRemained = byteArray.length % CHUNKMAX;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = CHUNKMAX * (i + 1);
                    if (max >= byteArray.length) {
                        Log.v(TAG, "BYTE[" + i*CHUNKMAX + "/" + byteArray.length + "] = " + message.substring(CHUNKMAX * MSG_OFFSET * i));
                    } else {
                        Log.v(TAG, "BYTE[" + (i*CHUNKMAX+byteRemained) + "/" + byteArray.length + "] = " + message.substring(CHUNKMAX * MSG_OFFSET * i, max * MSG_OFFSET));
                    }
                }
            } else {
                Log.e(TAG, "byteArray = null");
            }
        }
    }

    // critical inform
    public static void c(String tag, String message) {
        if(bDEBUG)
            Log.i(TAG, message);
    }

    public static void v(String tag, String message) { Log.v(TAG, message); }

    public static void d(String tag, String message) {
        if(bDEBUG)
            Log.d(TAG, message);
    }

    public static void i(String tag, String message) {
        Log.i(TAG, message);
    }

    public static void w(String tag, String message) {
        Log.w(TAG, message);
    }

    public static void e(String tag, String message) {
        Log.e(TAG, message);
    }

    public static void e(String tag, String message, RuntimeException e) throws RuntimeException {
        Log.e(TAG, message);
        if (bDEBUG) {
            throw e;
        }
    }

    public static void wtf(String tag, String message, RuntimeException e) throws RuntimeException {
        Log.wtf(TAG, message, e);
    }

}
