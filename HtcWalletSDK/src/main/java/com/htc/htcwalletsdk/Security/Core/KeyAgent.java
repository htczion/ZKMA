package com.htc.htcwalletsdk.Security.Core;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;

import com.htc.htcwalletsdk.Act.BaseResultCallbackAct;
import com.htc.htcwalletsdk.Act.UIErrorDialogAct;
import com.htc.htcwalletsdk.Export.ExportFields;
import com.htc.htcwalletsdk.Export.HtcWalletSdkManager;
import com.htc.htcwalletsdk.Export.IJavaCallbackListener;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Native.IJniCallbackListener;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Protect.ResultChecker;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;
import com.htc.htcwalletsdk.Utils.BinanceEncodeUtils;
import com.htc.htcwalletsdk.Utils.BinanceTxAssembler;
import com.htc.htcwalletsdk.Utils.GenericUtils;
import com.htc.htcwalletsdk.Utils.JsonParser;
import com.htc.htcwalletsdk.Utils.ParamHolder;
import com.htc.htcwalletsdk.Utils.Result;
import com.htc.htcwalletsdk.Utils.ResultCallback;
import com.htc.htcwalletsdk.Utils.ZKMALog;
import com.htc.htcwalletsdk.Wallet.Coins.CoinType;

import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

/**
 * All common key control behaviors for HW and SW
 * Created by hawk_wei on 2018/9/7.
 */

public class KeyAgent {
    static final String TAG = "KeyAgent";
    static final String BIT_DNUM_INDEX = "BitDigitalNum_Index";
    static final String LIT_DNUM_INDEX = "LitDigitalNum_Index";
    static final String ETH_DNUM_INDEX = "EthDigitalNum_Index";
    private static HtcWalletSdkManager mHtcWalletSdkManager;
    public IJavaCallbackListener mJavaCallbackListener;
    String  mWalletName;
    String mSha256;
    long    mUnique_id;
    private boolean bUseHW = false;
    IKeySecurity mKeySecurity;
    ExportFields mExportFields;
    private Context mContext;

    public KeyAgent(HtcWalletSdkManager htcWalletSdkManager) {
        ZKMALog.d(TAG, "KeyAgent");
        // mHtcWalletSdkManager = htcWalletSdkManager;
    }

    public void setCallBackListener(IJniCallbackListener listener) {
        ZKMALog.d(TAG, "setCallBackListener IJniCallbackListener="+listener);
        mKeySecurity.SetCallBackListener(listener);
    }

    public void setCallBackListener(IJavaCallbackListener listener) {
        ZKMALog.d(TAG, "setCallBackListener IJavaCallbackListener="+listener);
        mJavaCallbackListener = listener;
        // mKeySecurity.SetCallBackListener(listener); // Design for JAVA layer only, no need to inform JNI layer
    }

    public IJavaCallbackListener getJavaCallbackListener(){
        return mJavaCallbackListener;
    }

    public void setSdkProtectorListener(ISdkProtector listener) {
        ResultChecker.setSdkProtectorListener(listener);
    }

    public String getModuleVersion() {
        return com.htc.htcwalletsdk.BuildConfig.VERSION_NAME;
    }

    public String getApiVersion() {
        String prefix="1.";
        ZKMALog.d(TAG, "getApiVersion");
        if(mExportFields.bTZ_support == true)
            prefix = "0."; // hw wallet
        else
            prefix = "1."; // sw wallet
        return prefix+mKeySecurity.GetApiVersion();
    }

    private int checkFwVersions() {
        String strApiVersion;
        int ret = RESULT.UNKNOWN;
        ret = mKeySecurity.IsRooted();
        if( ret == RESULT.NOT_ROOTED ) {
            ZKMALog.e(TAG, "NOT_ROOTED");
            strApiVersion = mKeySecurity.GetApiVersion();
            try {
                if (strApiVersion != null) {
                    String[] split_Array = strApiVersion.split("\\.");
                    if (split_Array.length != 0) {
                        int serviceVer = Integer.parseInt(split_Array[0], 16); //NumberFormatException
                        int tzapiVer = Integer.parseInt(split_Array[1], 16); //NumberFormatException
                        ZKMALog.i(TAG, "serviceVer=" + serviceVer + "  tzapiVer=" + tzapiVer + "   minTzApiVer=" + ExportFields.minTzApiVer);
                        if (serviceVer < ExportFields.minServiceVer) {
                            ZKMALog.e(TAG, "ERROR! serviceVer=" + serviceVer + " <  minServiceVer=" + ExportFields.minServiceVer);
                            ret = RESULT.E_SDK_ROM_SERVICE_TOO_OLD;
                        } else if (tzapiVer < ExportFields.minTzApiVer) {
                            ZKMALog.e(TAG, "ERROR! tzapiVer=" + tzapiVer + " <  minTzApiVer=" + ExportFields.minTzApiVer);
                            ret = RESULT.E_SDK_ROM_TZAPI_TOO_OLD;
                        } else {
                            ret = RESULT.SUCCESS;
                        }
                    }
                }
            } catch (Exception e) {
                ZKMALog.e(TAG, "E_SDK_VERISON_UNKNOWN");
                e.printStackTrace();
                ret = RESULT.E_SDK_VERISON_UNKNOWN;
            }
        } else if ( ret == RESULT.E_TEEKM_UNKNOWN_ERROR) {
            ZKMALog.e(TAG, "E_TEEKM_UNKNOWN_ERROR");
            ret = RESULT.E_TEEKM_UNKNOWN_ERROR;
        } else {
            ZKMALog.e(TAG, "E_TEEKM_TAMPERED");
            ret = RESULT.E_TEEKM_TAMPERED;
        }
        return ret;
    }


    public int init(Context context) {
        int ret = RESULT.UNKNOWN;
        mExportFields = HtcWalletSdkManager.getInstance().getExportFields();
        mExportFields.bTZ_support = GenericUtils.isDeviceSupportHardwareWallet(context);
        ZKMALog.e(TAG, "HtcWalletSDK:"+getModuleVersion()+"  bTZ_support = "+ mExportFields.bTZ_support);

        // 2. if property set.. always go HW solution bUseHW = true
        if(mExportFields.bTZ_support) { // hw wallet
            mKeySecurity = new KeySecurityHW(context);
            if (((KeySecurityHW)mKeySecurity).isLoadSuccess() == true ) {
                ret = checkFwVersions();
                // ret = RESULT.SUCCESS;
            } else {
                ret = RESULT.E_SDK_SERVICE_LOAD_FAILURE;
            }

        } else { // sw wallet
            mKeySecurity = new KeySecuritySW(context);
            ret = RESULT.SUCCESS;
        }

        this.mContext = context;
        ResultChecker.Diagnostic(context, ret);
        return ret;
    }

    /**
     * Generate a unique ID for APP
     * @param wallet_name
     * @param sha256
     * @return a unique ID for your APP
     */
    public long register(String wallet_name, String sha256) {
        mWalletName = wallet_name;
        mSha256 = sha256;
        long nRet = 0; // Unique_id is null.
        int  errorcode = RESULT.UNKNOWN;
        nRet = mKeySecurity.Register(wallet_name, sha256);
        /*
            long return value masks:
            if( 0xFFFFFFFF????????) // uid ok, OLD rom
            if( 0x00000000????????) // NEW rom, OK,RESULT.SUCCESS
            if( 0x00000001????????) // NEW rom, OK,RESULT.E_TEEKM_REGISTER_ALREADY
            if( 0x????????00000000)  // NEW rom, Fail
            if( 0x????????,????????) // NEW rom, Fail and LSB32bits are unknown.
        */

        mUnique_id = nRet & 0x00000000FFFFFFFFL; // Always filter 64byte long as 32byte unsigned int
        errorcode = (int)((nRet >> 32) & 0xFFFFFFFF);
        switch(errorcode) {
            case 0xFFFFFFFF: // for compatible with OLD rom(2018/11/27)
            case RESULT.SUCCESS:
            case RESULT.E_TEEKM_REGISTER_ALREADY:
                return mUnique_id;
            default: // register failed with negative value
                if(errorcode > 0)
                    errorcode = errorcode*-1; // negative number are error code for APP check easily.

                ResultChecker.Diagnostic(mContext, errorcode);
                return (long)errorcode;
        }
    }

    public int isSeedExists(long unique_id) {
        int ret = mKeySecurity.IsSeedExists(unique_id);
        if(ret != RESULT.SUCCESS) {
            String strUID = ""+unique_id;
            String strUIDHash = GenericUtils.getHash(strUID);
            ZKMALog.w(TAG,"isSeedExists err=" + ret + "  " +strUIDHash.substring(0,10));
        }
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }


    public int createSeed(long unique_id){
        int ret = mKeySecurity.CreateSeed(unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int restoreSeed(long unique_id){
        int ret = mKeySecurity.RestoreSeed(unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int showSeed(long unique_id){
        int ret = mKeySecurity.ShowSeed(unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int clearSeed(long unique_id){
        int ret = mKeySecurity.ClearSeed(unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int changePIN(long unique_id) {
        int ret = mKeySecurity.ChangePIN(unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int confirmPIN(long unique_id, int resId) {
        int ret = mKeySecurity.ConfirmPIN(unique_id, resId);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }


    // BIP44 defined this part:
    // m/purpose'/coin'/account'/change/address_index 
    public PublicKeyHolder getPublicKey(long unique_id, int coinType, int change, int index) {
        PublicKeyHolder publicKeyHolder = new PublicKeyHolder();
        String path = "UNKNOWN path";
        switch(coinType) {
            case CoinType.BitCoin:
                path = "m/44'/0'/0'/" +Integer.toString(change)+"/"+ index;
                // BitCoin_idx++;
                break;
            case CoinType.LiteCoin:
                path = "m/44'/2'/0'/" +Integer.toString(change)+"/"+ index;
                // LiteCoin_idx++;
                break;
            case CoinType.Ethereum:
                path = "m/44'/60'/0'/0/0";
                // Ethereum_idx++; // ETH has no idx concept.
                break;
            case CoinType.BCH:
                path = "m/44'/145'/0'/" +Integer.toString(change)+"/"+ index;
                break;
            case CoinType.XLM:
                path = "m/44'/148'/0'/0'/0'";
                break;
            case CoinType.BNB:
                path = "m/44'/714'/0'/" +Integer.toString(change)+"/"+ index;
                break;

        }
        publicKeyHolder.setKeyPath(path);
        return mKeySecurity.GetPublicKey(unique_id, path, publicKeyHolder);
    }

    // BIP44 defined this part:
    // m/purpose'/coin'/account'/change/address_index 
    public PublicKeyHolder getExtPublicKey(long unique_id, int purpose, int coinType, int account) {
        PublicKeyHolder publicKeyHolder = new PublicKeyHolder();
        String path = "UNKNOWN path";
        path = "m/" +Integer.toString(purpose)+"'/"+Integer.toString(coinType)+"'/"+Integer.toString(account)+"'"; // Not support ETH
        publicKeyHolder.setKeyPath(path);
        return mKeySecurity.GetExtPublicKey(unique_id, path, publicKeyHolder);
    }

    // BIP44 defined this part:
    // m/purpose'/coin'/account'/change/address_index 
    public PublicKeyHolder getExtPublicKey(long unique_id, int purpose, int coinType, int account, int change, int index) {
        PublicKeyHolder publicKeyHolder = new PublicKeyHolder();
        String path = "UNKNOWN path";
        path = "m/" +Integer.toString(purpose)+"'/"+Integer.toString(coinType)+"'/"+Integer.toString(account)+"'/"+Integer.toString(change);  // Not support ETH
        publicKeyHolder.setKeyPath(path);
        return mKeySecurity.GetExtPublicKey(unique_id, path, publicKeyHolder);
    }


    static int BitCoin_idx;
    static int LiteCoin_idx;
    static int Ethereum_idx;
    public PublicKeyHolder getPublicKey(long unique_id, int coinType, int change) {
        PublicKeyHolder publicKeyHolder = new PublicKeyHolder();
        String path = "UNKNOWN path";
        String post_index;
        final int PURPOSE_SEND = 0;
        final int PURPOSE_RECEIVE = 1;

        SharedPreferences sp = mContext.getSharedPreferences(TAG, MODE_PRIVATE);
        String digitalNumTag = null;
        int digitalNumVal = 0;
        switch(coinType) {
            case CoinType.BitCoin:
                digitalNumTag = BIT_DNUM_INDEX;
                digitalNumVal = sp.getInt(digitalNumTag, 0);
                path = "m/44'/0'/0'/" +Integer.toString(change)+"/"+ digitalNumVal;
                BitCoin_idx++;
                break;
            case CoinType.LiteCoin:
                digitalNumTag = LIT_DNUM_INDEX;
                digitalNumVal = sp.getInt(digitalNumTag, 0);
                path = "m/44'/2'/0'/" +Integer.toString(change)+"/"+ digitalNumVal;
                LiteCoin_idx++;
                break;
            case CoinType.Ethereum:
                path = "m/44'/60'/0'/0/0";
                // Ethereum_idx++; // ETH has no idx concept.
                break;
            case CoinType.BCH:
                path = "m/44'/145'/0'/" +Integer.toString(change)+"/"+ digitalNumVal;
                break;
            case CoinType.XLM:
                path = "m/44'/148'/0'/0'/0'";
                break;
            case CoinType.BNB:
                digitalNumTag = LIT_DNUM_INDEX;
                digitalNumVal = sp.getInt(digitalNumTag, 0);
                path = "m/44'/714'/0'/" +Integer.toString(change)+"/"+ digitalNumVal;
                break;
        }
        if(digitalNumTag != null){
            if(digitalNumVal > 65535)
                digitalNumVal = 0;
            digitalNumVal++;
            sp.edit().putInt(digitalNumTag, digitalNumVal)
                    .commit();
        }
        ZKMALog.c(TAG, Integer.toString(digitalNumVal));
        publicKeyHolder.setKeyPath(path);
        return mKeySecurity.GetPublicKey(unique_id, path, publicKeyHolder);
    }

    public int signTransaction(long unique_id, int coin_type, float rates, String strJson, ByteArrayHolder byteArrayHolder) {
        int ret = RESULT.E_SDK_GENERIC_ERROR;
        if(coin_type == CoinType.BNB) {
            BinanceEncodeUtils.BNBJsonObj bnb = null;
            try {
                bnb = new BinanceEncodeUtils.BNBJsonObj(strJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ByteArrayHolder signBytes = new ByteArrayHolder();
            ret = mKeySecurity.SignTransaction(unique_id, coin_type, rates, bnb.getSignJsonStr(), signBytes);
            if (ret == RESULT.SUCCESS) {
                byte[] stdTx = new byte[2*1024];
                try {
                    PublicKeyHolder publicKeyHolder = new PublicKeyHolder();
                    publicKeyHolder.setKeyPath(bnb.getPath());
                    publicKeyHolder = mKeySecurity.GetPublicKey(unique_id, bnb.getPath(), publicKeyHolder);
                    bnb.pubKeyHex = publicKeyHolder.mPublicKey;
                    stdTx = BinanceTxAssembler.encodeStdTx(signBytes.byteArray, bnb);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteArrayHolder.byteArray = Arrays.copyOf(stdTx, stdTx.length);
                byteArrayHolder.receivedLength = stdTx.length;
                // String outHexString = GenericUtils.byteArrayToHexString(byteArrayHolder.byteArray);
                // ZKMALog.d(TAG, outHexString); // compare with output at SignerTests.cpp
            }
        }
        else{
            ret = mKeySecurity.SignTransaction(unique_id, coin_type, rates, strJson, byteArrayHolder);
        }
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }



    public int signMultipleTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder[] byteArrayHolderArray) {
        int ret = mKeySecurity.signMultipleTransaction(unique_id, coin_type, rates, strJson, byteArrayHolderArray);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int signMessage(long unique_id, int coin_type, String strJson, ByteArrayHolder byteArrayHolder) {
        int ret = RESULT.UNKNOWN;
        int messagelen = 0;
        switch (coin_type) {
            case CoinType.Ethereum:
                JsonParser jsonParser = new JsonParser();
                messagelen = jsonParser.ParserJsonEthSignMessage(strJson);
                if(messagelen > 2550 ) {
                    ZKMALog.e(TAG, "data field length="+messagelen+" in JSON is too long.");
                    return ret;
                } else if(messagelen == 0 ) {
                    ZKMALog.e(TAG, "Can't get data field in JSON");
                    return ret;
                }

                ret = mKeySecurity.SignTransaction(unique_id, coin_type, 1, strJson, byteArrayHolder);
                ResultChecker.Diagnostic(mContext, ret);
                break;
            default:
                ZKMALog.e(TAG, "Not support this coin type!");
        }
        return ret;
    }

    public int deinit() {
        mKeySecurity = null;
        return RESULT.SUCCESS;
    }

    public int unregister(String wallet_name, String sha256, long unique_id) {
        int ret = mKeySecurity.Unregister(wallet_name, sha256, unique_id);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int clearTzData() {
        int ret = mKeySecurity.ClearTzData();
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public byte[] getPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key) {
        return mKeySecurity.GetPartialSeed(unique_id, seed_index, public_key);
    }

    public int getPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder) {
        int ret = mKeySecurity.GetPartialSeed(unique_id, seed_index, public_key, byteArrayHolder);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int getPartialSeed_v2(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code_pubkey, ByteArrayHolder aes_key, ByteArrayHolder out_seed) {
        int ret = mKeySecurity.GetPartialSeed_v2(unique_id, seed_index, enc_verify_code_pubkey, aes_key, out_seed);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int combineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3) {
        int ret = mKeySecurity.CombineSeeds(unique_id, seed_1, seed_2, seed_3);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int combineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1, ByteArrayHolder enc_seed_2, ByteArrayHolder enc_seed_3) {
        int ret = mKeySecurity.CombineSeeds_v2(unique_id, enc_seed_1, enc_seed_2, enc_seed_3);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int getTZIDHash(ByteArrayHolder ext_idhash) {
        int ret = mKeySecurity.GetTZIDHash(ext_idhash);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int showVerificationCode(long unique_id, byte seed_index, String name, String phone_num, String phone_model) {
        int ret = mKeySecurity.ShowVerificationCode(unique_id, seed_index, name, phone_num, phone_model);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int enterVerificationCode(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code) {
        int ret = mKeySecurity.EnterVerificationCode( unique_id, seed_index, enc_verify_code);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int getEncAddr(long unique_id, String path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature) {
        int ret = mKeySecurity.GetEncAddr( unique_id, path, ext_addr, ext_encaddr, ext_encaddr_signature);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int setERC20BGColor(Color[] colors) {
        int ret = mKeySecurity.SetERC20BGColor(colors);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }


    public int setEnvironment(int env) {
        int ret = mKeySecurity.SetEnvironment(env);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int isRooted() {
        int ret = mKeySecurity.IsRooted();
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int readTzDataSet(int dataSet, ByteArrayHolder data) {
        int ret = mKeySecurity.ReadTzDataSet( dataSet, data);
        if( ret < RESULT.SUCCESS)
            ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int writeTzDataSet(int dataSet, ByteArrayHolder data, ByteArrayHolder signature) {
        int ret = mKeySecurity.WriteTzDataSet( dataSet, data, signature);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int setKeyboardType(long unique_id, int nType) {
        int ret = mKeySecurity.SetKeyboardType( unique_id, nType);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    public int changePIN_v2(long unique_id, int nType) {
        int ret = mKeySecurity.ChangePIN_v2( unique_id, nType);
        ResultChecker.Diagnostic(mContext, ret);
        return ret;
    }

    /**
     * Java callback function to show UI  JNI --> java
     * @param cls class name of activity
     * @param context
     * @param holder in / out parameters
     * @return true success
     */
    public static synchronized boolean doShowUIActivity(Class<?> cls, Context context, ParamHolder holder)
    {
        ZKMALog.i(TAG, "doShowUIActivity +++");
        final Result result  = new Result();
        ResultCallback callback = new ResultCallback(Looper.getMainLooper()){
            @Override
            protected void onSuccess(){
                synchronized(result) {
                    result.success = true;
                    ZKMALog.i(TAG, "onSuccess() notifyAll**");
                    result.notifyAll();
                }
            }

            @Override
            protected void onFailed(int errorCode){
                synchronized(result) {
                    result.errCode = errorCode;
                    ZKMALog.i(TAG, "onFailed() notifyAll**");
                    result.notifyAll();
                }
            }

            @Override
            protected void onFailed(String errorMessage){
                synchronized(result) {
                    result.errMessage = errorMessage;
                    ZKMALog.i(TAG, "onFailed() notifyAll**");
                    result.notifyAll();
                }
            }
        };

        BaseResultCallbackAct.sResultCallback = callback;

        // ZKMALog.i(TAG, "Verify input data : " + holder.getIn().get("test"));

        Intent intent = new Intent();
        intent.putExtra("input", holder.getIn());
        intent.setClass(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.getApplicationContext().startActivity(intent);


        ZKMALog.i(TAG, "before lock");
        synchronized(result) {
            try
            {
                ZKMALog.i(TAG, "wait ....");
                result.wait();
            }
            catch(final InterruptedException e)
            {
                //do nothing
                e.printStackTrace();
            }
        }
        ZKMALog.i(TAG, "after lock ");
        // setOutput Data
        holder.setOut(callback.getOutput());
        ZKMALog.i(TAG, "doShowUIActivity ---");
        return result.success;
    }

    // Using the transparent Activity to simulate a Dialog like Activity, since SDK can only get Context of ApplicationContext, not Activity context.
    public static boolean doShowAlertDialog(Context context, int nErrorCode)
    {
        Bundle in = new Bundle();
        ParamHolder holder = new ParamHolder();
        holder.setIn(in);
        in.putInt("errorCode", nErrorCode);
        boolean bRet = KeyAgent.doShowUIActivity(UIErrorDialogAct.class, context, holder);
        return bRet;
    }

    // Using the transparent Activity to simulate a Dialog like Activity, since SDK can only get Context of ApplicationContext, not Activity context.
    public static boolean doShowAlertDialog(Context context, String errorMessage)
    {
        Bundle in = new Bundle();
        ParamHolder holder = new ParamHolder();
        holder.setIn(in);
        in.putString("errorMessage", errorMessage);
        boolean bRet = KeyAgent.doShowUIActivity(UIErrorDialogAct.class, context, holder);
        return bRet;
    }
}