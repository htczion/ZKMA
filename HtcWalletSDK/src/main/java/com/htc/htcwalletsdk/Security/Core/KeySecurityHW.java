package com.htc.htcwalletsdk.Security.Core;

import android.content.Context;
import android.graphics.Color;

import com.htc.htcwalletsdk.Export.IJavaCallbackListener;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.GlobalVariable;
import com.htc.htcwalletsdk.Native.IJniCallbackListener;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Protect.ResultChecker;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;
import com.htc.htcwalletsdk.Utils.GenericUtils;
import com.htc.htcwalletsdk.Utils.ZKMALog;
import com.htc.wallettzservice.EncAddr;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.htc.htcwalletsdk.Utils.GenericUtils.bytesToHex;


/**
 * 1:1 convert TEEKHelper API to App interface
 * Created by hawk_wei on 2018/9/10.
 */

public class KeySecurityHW implements IKeySecurity {
    private static final String TAG = "KeySecurityHW";
    public long mUnique_id;
    private TEEKHelper mTEEKHelper;
    private Context mContext;

    public KeySecurityHW(Context context) {
        mContext = context;
        mTEEKHelper = TEEKHelper.getInstance(context);
    }

    public boolean isLoadSuccess() {
        boolean ret = mTEEKHelper.isLoadSuccess();
        ZKMALog.d(TAG,"isLoadSuccess ret="+ret);
        return ret;
    }

    public String GetApiVersion() { // HW_WALLET API version
        ZKMALog.d(TAG, "GetApiVersion");
        String TzApiVersion = Integer.toHexString(mTEEKHelper.TzApiVersion());
        ZKMALog.d(TAG, "GetTzApiVersion = " + TzApiVersion);
        String ApiVersion = mTEEKHelper.getServiceVersion()+"."+TzApiVersion;
        return ApiVersion;
    }

    public void SetCallBackListener(IJniCallbackListener listener) {
        ZKMALog.d(TAG, "SetCallBackListener IJniCallbackListener");
        // TODO: implement callback
    }

    public void SetCallBackListener(IJavaCallbackListener listener) {
        ZKMALog.d(TAG, "SetCallBackListener IJavaCallbackListener");
        // TODO: implement callback
    }

    public void SetSdkProtectorListener(ISdkProtector listener) {
        ZKMALog.d(TAG, "SetCallBackListener IJavaCallbackListener");
        ResultChecker.setSdkProtectorListener(listener);
    }

    public int SetEnvironment(int env) {
        return mTEEKHelper.setEnvironment((byte)env);
    }

    public long Register(String walletName, String sha256) {
        byte[] b = sha256.getBytes(); // Also: getBytes(Charset.forName("UTF-8")); getBytes(StandardCharsets.UTF_8);
        mUnique_id = mTEEKHelper.register(walletName, b, b.length);
        return mUnique_id;
    }

    public int Unregister(String wallet_name, String sha256, long unique_id)  {
        byte[] b = sha256.getBytes(); // Also: getBytes(Charset.forName("UTF-8")); getBytes(StandardCharsets.UTF_8);
        mTEEKHelper.unRegister(wallet_name, b, b.length, unique_id);
        return RESULT.SUCCESS;
    }

    public int IsSeedExists(long unique_id) {
        return mTEEKHelper.isSeedExists(unique_id);
    }

    public int CreateSeed(long unique_id) {
        return mTEEKHelper.createSeed(unique_id);
    }

    public int RestoreSeed(long unique_id) {
        return mTEEKHelper.restoreSeed(unique_id);
    }

    public int ShowSeed(long unique_id) {
        return mTEEKHelper.showSeed(unique_id);
    }

    public int ClearSeed(long unique_id) {
        return mTEEKHelper.clearSeed(unique_id);
    }

    public int ChangePIN(long unique_id) {
        return mTEEKHelper.changePIN(unique_id);
    }

    public int ConfirmPIN(long unique_id, int resId) {
        return mTEEKHelper.confirmPIN(unique_id, resId);
    }

    public PublicKeyHolder GetPublicKey(long unique_id, String path, PublicKeyHolder publicKeyHolder) {
        publicKeyHolder.setKey(mTEEKHelper. getPublicKey(unique_id, path));
        return publicKeyHolder;
    }

    public int SignTransaction(long unique_id, int coin_type, float rates, String strJson, ByteArrayHolder byteArrayHolder) {
        ZKMALog.d(TAG, "SignTransaction");
        byte[] tz_processed = mTEEKHelper.signTransaction(unique_id, coin_type, rates, strJson); // Ood's SERVICE will new byte[] object in JAVA layer, and GC.
        if( (tz_processed == null) || (tz_processed.length == 0 ) ) {
            return RESULT.E_TEEKM_FAILURE;
        } else if ( tz_processed.length == 4 ) {
            int ret = ByteBuffer.wrap(tz_processed).getInt();
            ZKMALog.i(TAG, "ret = "+ ret);
            return ret;
        } else {
            if(byteArrayHolder.byteArray.length >= tz_processed.length) {
                // System.arraycopy(tz_processed, 0, byteArrayHolder.byteArray, 0, tz_processed.length); // this input buffer length will NOT change, APP should trim the buffer.
                byteArrayHolder.byteArray = Arrays.copyOf(tz_processed, tz_processed.length); // CAUTION! the input buffer length will be change.
                byteArrayHolder.receivedLength = tz_processed.length;
                return RESULT.SUCCESS;
            }
            else {
                ZKMALog.w(TAG, "byteArrayHolder.byteArray.length = " + byteArrayHolder.byteArray.length +" < tz_processed.length="+tz_processed.length+" extend size!");
                byteArrayHolder.extendSize(tz_processed.length);
                byteArrayHolder.byteArray = Arrays.copyOf(tz_processed, tz_processed.length);
                byteArrayHolder.receivedLength = tz_processed.length;
                return RESULT.SUCCESS;
            }
        }
    }


    public int signMultipleTransaction(long unique_id,int coin_type, float rates,String strJson, ByteArrayHolder[] byteArrayHolderArray) {
        ZKMALog.d(TAG, "signMultipleTransaction");
        byte[] tz_processed = mTEEKHelper.signTransaction(unique_id, coin_type, rates, strJson); // Ood's SERVICE will new byte[] object in JAVA layer, and GC.
        if( (tz_processed == null) || (tz_processed.length == 0) ) {
            return RESULT.E_TEEKM_FAILURE;
        } else if ( tz_processed.length == 4 ) {
            int ret = ByteBuffer.wrap(tz_processed).getInt();
            ZKMALog.i(TAG, "ret = "+ ret);
            return ret;
        } else {
            // ZKMALog.d(TAG, "tz_processed=" + bytesToHex(tz_processed));
            ZKMALog.d(TAG, "tz_processed.length=" + tz_processed.length);

            // for trade
            byte[] intArray0 = new byte[4];
            System.arraycopy(tz_processed, 0, intArray0, 0, 4);
            ZKMALog.c(TAG, "intArray0=" + bytesToHex(intArray0));
            int length0 = GenericUtils.bytesToInt(intArray0);
            ZKMALog.d(TAG, "len0=" + length0);
            byte[] data0 = new byte[length0];
            System.arraycopy(tz_processed, 4, data0, 0, length0);
            byteArrayHolderArray[0].byteArray = Arrays.copyOf(data0, length0);
            byteArrayHolderArray[0].receivedLength = length0;
            // for approve
            if((tz_processed.length - (4 + length0)) > 4) {
                byte[] intArray1 = new byte[4];
                System.arraycopy(tz_processed, 4 + length0, intArray1, 0, 4);
                ZKMALog.c(TAG, "intArray1=" + bytesToHex(intArray1));
                int len1 = GenericUtils.bytesToInt(intArray1);
                ZKMALog.d(TAG, "len1=" + len1);
                byte[] data1 = new byte[len1];
                System.arraycopy(tz_processed, 8 + length0, data1, 0, len1);
                byteArrayHolderArray[1].byteArray = Arrays.copyOf(data1, len1);
                byteArrayHolderArray[1].receivedLength = len1;
            }
            return RESULT.SUCCESS;
        }
    }


    public byte[] GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key) {
        byte[] tz_processed = mTEEKHelper.getPartialSeed(unique_id, seed_index, public_key.byteArray, public_key.byteArray.length );
        if ((tz_processed == null ) || (tz_processed.length == 0)) {
            GlobalVariable.SetErrorCode(RESULT.E_TEEKM_FAILURE);
        }
        else if ( tz_processed.length == 4 ) {
            int result = ByteBuffer.wrap(tz_processed).getInt();
            ResultChecker.Diagnostic(mContext, result);
        }
        return tz_processed;
    }

    public int GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder) {
        int ret = RESULT.UNKNOWN;
        byte[] tz_processed = mTEEKHelper.getPartialSeed(unique_id, seed_index, public_key.byteArray, public_key.byteArray.length );
        if ((tz_processed == null ) || (tz_processed.length == 0)) {
            ret = RESULT.E_TEEKM_FAILURE;
        }
        else if ( tz_processed.length == 4 ) {
           ret = ByteBuffer.wrap(tz_processed).getInt();
       } else {
           if(byteArrayHolder.byteArray.length >= tz_processed.length) {
                byteArrayHolder.byteArray = Arrays.copyOf(tz_processed, tz_processed.length);
                byteArrayHolder.receivedLength = tz_processed.length;
                ret = RESULT.SUCCESS;
            }
            else {
               ZKMALog.e(TAG, "byteArrayHolder.byteArray.length = " + byteArrayHolder.byteArray.length +" ,  tz_processed.length="+tz_processed.length);
                throw new IllegalArgumentException();
            }
        }
        ZKMALog.i(TAG, "ret = "+ ret);
        return ret;
    }
    public int GetPartialSeed_v2(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code_pubkey, ByteArrayHolder aes_key, ByteArrayHolder out_seed) {
        int ret = RESULT.UNKNOWN;
        byte[] tz_processed = mTEEKHelper.getPartialSeed_v2(unique_id, seed_index, enc_verify_code_pubkey.byteArray, enc_verify_code_pubkey.byteArray.length, aes_key.byteArray, aes_key.byteArray.length);
        if ((tz_processed == null ) || (tz_processed.length == 0)) {
            ret = RESULT.E_TEEKM_FAILURE;
        }
        else if ( tz_processed.length == 4 ) {
            ret = ByteBuffer.wrap(tz_processed).getInt();
        } else {
            if(out_seed.byteArray.length >= tz_processed.length) {
                out_seed.byteArray = Arrays.copyOf(tz_processed, tz_processed.length);
                out_seed.receivedLength = tz_processed.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "out_seed.byteArray.length = " + out_seed.byteArray.length +" ,  tz_processed.length="+tz_processed.length);
                throw new IllegalArgumentException();
            }
        }
        ZKMALog.i(TAG, "ret = "+ ret);
        return ret;
    }

    public int ShowVerificationCode(long unique_id, byte seed_index, String name, String phone_num, String phone_model) {
        return mTEEKHelper.showVerificationCode(unique_id, seed_index, name, phone_num, phone_model);
    }

    public int EnterVerificationCode(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code) {
        int ret = RESULT.UNKNOWN;
        byte[] tz_processed = mTEEKHelper.enterVerificationCode(unique_id, seed_index);
        if ((tz_processed == null ) || (tz_processed.length == 0)) {
            ret = RESULT.E_TEEKM_FAILURE;
        }
        else if ( tz_processed.length == 4 ) {
            ret = ByteBuffer.wrap(tz_processed).getInt();
        } else {
            if(enc_verify_code.byteArray.length >= tz_processed.length) {
                enc_verify_code.byteArray = Arrays.copyOf(tz_processed, tz_processed.length);
                enc_verify_code.receivedLength = tz_processed.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "enc_verify_code.byteArray.length = " + enc_verify_code.byteArray.length +" ,  tz_processed.length="+tz_processed.length);
                throw new IllegalArgumentException();
            }
        }
        ZKMALog.i(TAG, "ret = "+ ret);
        return ret;
    }

    public int CombineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3) {
        return mTEEKHelper.combineSeeds(unique_id, seed_1.byteArray, seed_1.byteArray.length, seed_2.byteArray, seed_2.byteArray.length, seed_3.byteArray, seed_3.byteArray.length);
    }

    public int CombineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1, ByteArrayHolder enc_seed_2, ByteArrayHolder enc_seed_3) {
        return mTEEKHelper.combineSeeds_v2(unique_id, enc_seed_1.byteArray, enc_seed_1.byteArray.length, enc_seed_2.byteArray, enc_seed_2.byteArray.length, enc_seed_3.byteArray, enc_seed_3.byteArray.length);
    }

    public int GetTZIDHash(ByteArrayHolder ext_idhash) {
        byte[] tz_processed = mTEEKHelper.getTZIDHash();
        if ( (tz_processed == null ) || (tz_processed.length == 0) ) {
            return RESULT.E_TEEKM_FAILURE;
        } else if ( tz_processed.length == 4 ) {
            int result = ByteBuffer.wrap(tz_processed).getInt();
            return result;
        } else {
            if(ext_idhash.byteArray.length >= tz_processed.length) {
                ext_idhash.byteArray = Arrays.copyOf(tz_processed, tz_processed.length);
                ext_idhash.receivedLength = tz_processed.length;
                return RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "ext_idhash.byteArray.length = " + ext_idhash.byteArray.length +" ,  tz_processed.length="+tz_processed.length);
                throw new IllegalArgumentException();
            }
        }
    }

    public int ClearTzData() {
        return mTEEKHelper.clearData();
    }

    public int IsRooted() {
        return mTEEKHelper.isRooted();
    }

    public int GetEncAddr(long unique_id, String path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature) {
        int ret = RESULT.UNKNOWN;
        EncAddr encAddresses = mTEEKHelper.getEncAddr(unique_id, path);
        byte[] src_ext_addr = encAddresses.getExtAddr();
        byte[] src_ext_encaddr = encAddresses.getExtEncaddr();
        byte[] src_ext_encaddr_signature = encAddresses.getExtEncaddrSignature();

        if ((src_ext_addr == null ) || (src_ext_addr.length == 0)) {
            return RESULT.E_TEEKM_FAILURE;
        } else if ( src_ext_addr.length == 4 ) {
            ret = ByteBuffer.wrap(src_ext_addr).getInt();
            return ret;
        } else {
            // ext_addr
            if(ext_addr.byteArray.length >= src_ext_addr.length) {
                ext_addr.byteArray = Arrays.copyOf(src_ext_addr, src_ext_addr.length);
                ext_addr.receivedLength = src_ext_addr.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "ext_addr.byteArray.length = " + ext_addr.byteArray.length +" ,  src_ext_addr.length="+src_ext_addr.length);
                throw new IllegalArgumentException();
            }
            // ext_encaddr
            if(ext_encaddr.byteArray.length >= src_ext_encaddr.length) {
                ext_encaddr.byteArray = Arrays.copyOf(src_ext_encaddr, src_ext_encaddr.length);
                ext_encaddr.receivedLength = src_ext_encaddr.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "ext_addr.byteArray.length = " + ext_addr.byteArray.length +" ,  src_ext_addr.length="+src_ext_addr.length);
                throw new IllegalArgumentException();
            }
            // ext_encaddr_signature
            if(ext_encaddr_signature.byteArray.length >= src_ext_encaddr_signature.length) {
                ext_encaddr_signature.byteArray = Arrays.copyOf(src_ext_encaddr_signature, src_ext_encaddr_signature.length);
                ext_encaddr_signature.receivedLength = src_ext_encaddr_signature.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "ext_addr.byteArray.length = " + ext_addr.byteArray.length +" ,  src_ext_addr.length="+src_ext_addr.length);
                throw new IllegalArgumentException();
            }
        }
        return ret;
    }

    public int SetERC20BGColor(Color[] colors) {
        return mTEEKHelper.setERC20BGColor(colors);
    }

    public int ReadTzDataSet(int dataSet, ByteArrayHolder data) {

        int ret = RESULT.UNKNOWN;
        byte[] tz_data = mTEEKHelper.readTzDataSet(dataSet);
        if ( (tz_data == null ) || (tz_data.length == 0) ) {
            ret = RESULT.E_TEEKM_FAILURE;
        }
        else if ( tz_data.length == 4 ) { // error
            ZKMALog.e(TAG, "tz_data.length == 4   an ERROR occurred!");
            ret = ByteBuffer.wrap(tz_data).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
            // WORKAROUND
        } else if ( tz_data.length == 8 ) { // value
                ZKMALog.e(TAG, "tz_data.length == 8  ");
                ret = RESULT.SUCCESS;
                data.byteArray = Arrays.copyOf(tz_data, tz_data.length);
                data.receivedLength = tz_data.length;
        } else {
            if(data.byteArray.length >= tz_data.length) {
                data.byteArray = Arrays.copyOf(tz_data, tz_data.length);
                data.receivedLength = tz_data.length;
                ret = RESULT.SUCCESS;
            }
            else {
                ZKMALog.e(TAG, "data.byteArray.length = " + data.byteArray.length +" ,  tz_data.length="+tz_data.length);
                throw new IllegalArgumentException();
            }
        }
        ZKMALog.i(TAG, "ret = "+ ret);
        return ret;
    }

    public int WriteTzDataSet(int dataSet, ByteArrayHolder data, ByteArrayHolder signature) {
        int ret = RESULT.UNKNOWN;
        ret = mTEEKHelper.writeTzDataSet(dataSet, data.byteArray, signature.byteArray);
        if ( (data == null ) || (data.byteArray.length == 0) || (signature.byteArray.length == 0)) {
            ret = RESULT.E_TEEKM_FAILURE;
        } else {
            ZKMALog.e(TAG, "data.byteArray.length = " + data.byteArray.length + "   signature.byteArray.length = " + signature.byteArray.length );
        }
        ZKMALog.i(TAG, "ret = "+ ret);
        return ret;
    }


    public int SetKeyboardType(long unique_id, int nType) {
        return mTEEKHelper.setKeyboardType(unique_id, nType);
    }

    public int ChangePIN_v2(long unique_id, int nType) {
        return mTEEKHelper.changePIN_v2(unique_id, nType);
    }

}
