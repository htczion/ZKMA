package com.htc.htcwalletsdk.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.htc.htcwalletsdk.Security.Trusted.TrustedPartners;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hawk_wei on 2018/9/28.
 */

public class GenericUtils {
    static final String TAG = "GenericUtils";

    public final static boolean isDeviceSupportHardwareWallet(Context context) {
        final String feature = "com.htc.hardware.wallet";
        final PackageManager packageManager = context.getPackageManager();
        final FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo f : featuresList) {
            if (f.name != null && f.name.equals(feature)) {
                ZKMALog.i(TAG, "isDeviceSupportHardwareWallet  true , " + f.name);
                return true;
            }
        }

        return false;
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static String byteArrayToHexString(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x,", b));
        return sb.toString();
    }

    public static boolean IsAppLaunched(Context context){
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        boolean bIsAppLaunched =  appSharedPrefs.getBoolean("bIsAppLaunched",false);

        if(bIsAppLaunched == false){
            ZKMALog.i(TAG,"bIsAppLaunched == false");
            SharedPreferences.Editor editor = appSharedPrefs.edit();
            editor.putBoolean("bIsAppLaunched", true);
            editor.apply();
            return false;
        } else {
            ZKMALog.i(TAG,"bIsAppLaunched == true");
            return true;
        }
    }

    public static int bytesToInt(byte[] bytes) {
        int val = (bytes[3]<<24)&0xff000000|
                (bytes[2]<<16)&0x00ff0000|
                (bytes[1]<< 8)&0x0000ff00|
                (bytes[0])&0x000000ff;

        return val;
    }

    public final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getHash(String... input)
    {
        String callerHash = "";
        for(String param : input){
            callerHash += param;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA256");
            digest.update(callerHash.getBytes());
            callerHash = TrustedPartners.bytesToHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return callerHash;
    }
}
