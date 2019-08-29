package com.htc.htcwalletsdk.Security.Core;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.htc.htcwalletsdk.Export.IJavaCallbackListener;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Native.IJniCallbackListener;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * API re-name, re-arch and re-wrap for SDK inside usage at here
 * Created by hawk_wei on 2018/9/7.
 */

public class KeySecuritySW implements IKeySecurity {
    static final String TAG = "KeySecuritySW";
    public long mUnique_id;
    private Context mContext;
    private ISdkProtector mSdkProtectorListener;

    public KeySecuritySW(Context context) {
        ZKMALog.d(TAG, "KeySecuritySW");
        mContext = context;
    }

    public String GetApiVersion() {
        return "0000.0000000";
    }

    public void SetCallBackListener(IJniCallbackListener listener) {
        // super.jSetJniCallBackListener(listener);
    }

    public void SetCallBackListener(IJavaCallbackListener listener) {
        // super.jSetJavaCallBackListener(listener);
    }

    @Override
    public void SetSdkProtectorListener(ISdkProtector listener) {
        mSdkProtectorListener = listener;
    }

    public int SetEnvironment(int env) {
        return RESULT.SUCCESS;
    }

    public long Register(String wallet_name, String sha256) {
        ZKMALog.d(TAG, "Register");
        return 0x1234567;
    }

    public int Unregister(String wallet_name, String sha256, long unique_id) {
        ZKMALog.d(TAG, "Unregister");
        return RESULT.UNKNOWN;
    }

    public int IsSeedExists(long unique_id) {
        ZKMALog.d(TAG, "IsSeedExists");
        return RESULT.UNKNOWN;
    }

    public int CreateSeed(long unique_id) {
        ZKMALog.d(TAG, "CreateSeed");
        return RESULT.UNKNOWN;
    }

    public int RestoreSeed(long unique_id) {
        ZKMALog.d(TAG, "RestoreSeed");
        return RESULT.UNKNOWN;
    }

    public int ShowSeed(long unique_id) {
        ZKMALog.d(TAG, "getModuleVersion");
        return RESULT.UNKNOWN;
    }

    public int ClearSeed(long unique_id) {
        ZKMALog.d(TAG, "ClearSeed");
        return RESULT.UNKNOWN;
    }

    public int ChangePIN(long unique_id) {
        ZKMALog.d(TAG, "ChangePIN");
        return RESULT.UNKNOWN;
    }

    public int ConfirmPIN(long unique_id, int resId) {
        ZKMALog.d(TAG, "ConfirmPIN");
        return RESULT.UNKNOWN;
    }

    public PublicKeyHolder GetPublicKey(long unique_id, String path, PublicKeyHolder publicKeyHolder) {
        ZKMALog.d(TAG, "GetPublicKey");
        return publicKeyHolder;
    }

    public PublicKeyHolder GetExtPublicKey(long unique_id, String path, PublicKeyHolder publicKeyHolder) {
        ZKMALog.d(TAG, "GetExtPublicKey");
        return publicKeyHolder;
    }


    public int SignTransaction(long unique_id, int coin_type, float rates, String strJson, ByteArrayHolder byteArrayHolder ) {
        ZKMALog.d(TAG, "SignTransaction");
        return RESULT.UNKNOWN;
    }

    public int signMultipleTransaction(long unique_id, int coin_type, float rates, String strJson, ByteArrayHolder[] byteArrayHolderArray) {
        return RESULT.UNKNOWN;
    }

    public byte[] GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key) // Ood Interface
    {
        return null;
    }

    public int GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder) {
        return RESULT.UNKNOWN;
    }


    public int CombineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3) {
        ZKMALog.d(TAG, "CombineSeeds");
        return RESULT.UNKNOWN;

    }

    public int GetTZIDHash(ByteArrayHolder ext_idhash) {
        // SW wallet can not support Trust Zone HW
        return RESULT.E_TEEKM_FAILURE;
    }

    public int IsRooted() {
        ZKMALog.d(TAG, "IsRooted");
        boolean bRooted = false;
        int ret;
        if(bRooted == true) {
            ret = RESULT.UNKNOWN;
        }
        else {
            ret = RESULT.SUCCESS; // align TZ isTempered(), 0 is not root;
        }
        return ret;
    }

    public int ClearTzData() {
        ZKMALog.d(TAG, "ClearTzData");
        return RESULT.UNKNOWN;
    }

    public int GetEncAddr(long unique_id, String path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature) {
        ZKMALog.d(TAG, "GetEncAddr");
        return RESULT.UNKNOWN;
    }

    public int GetPartialSeed_v2(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code_pubkey, ByteArrayHolder aes_key, ByteArrayHolder out_seed) {
        return RESULT.UNKNOWN;
    }

    public int CombineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1, ByteArrayHolder enc_seed_2, ByteArrayHolder enc_seed_3) {
        return RESULT.UNKNOWN;
    }

    public int ShowVerificationCode(long unique_id, byte seed_index, String name, String phone_num, String phone_model) {
        return RESULT.UNKNOWN;
    }

    public int EnterVerificationCode(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code) {
        return RESULT.UNKNOWN;
    }

    public int SetERC20BGColor(Color[] colors) {
        return RESULT.UNKNOWN;
    }

    public int ReadTzDataSet(int dataSet, ByteArrayHolder data) {
        return RESULT.UNKNOWN;
    }

    public int WriteTzDataSet(int dataSet, ByteArrayHolder data, ByteArrayHolder signature) {
        return RESULT.UNKNOWN;
    }

    public int SetKeyboardType(long unique_id, int nType) {
        return RESULT.UNKNOWN;
    }

    public int ChangePIN_v2(long unique_id, int nType) {
        return RESULT.UNKNOWN;
    }

}
