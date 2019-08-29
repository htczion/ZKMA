package com.htc.htcwalletsdk.Protect;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.htc.htcwalletsdk.BuildConfig;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.R;
import com.htc.htcwalletsdk.Security.Core.KeyAgent;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * All generic error filter and check at here
 * Created by hawk_wei on 2018/10/25.
 */

public class SdkProtector {
    private static final String TAG = "SdkProtector";

    /**
     * if you don't want caller call them on main thread.
     */
    public static void throwIfOnMainThread(boolean bAvoidUiThread)
    {
        if(bAvoidUiThread == true) {
            if (Looper.myLooper() == Looper.getMainLooper())
            {
                throw new IllegalStateException("Must not be invoked from the main thread.");
            }
        }
    }

    /**
     * if you don't want caller call this API.
     */
    public static void throwIfApiNotSupport(){

        switch(BuildConfig.PARTNER_ID){
            case 0:  // Htc_internal
                break;
            default: // For customize SDK by flavor, Htc_partner1 , Htc_partner2
                ZKMALog.e(TAG,"throwIfApiNotSupport  BuildConfig.PARTNER_ID="+ BuildConfig.PARTNER_ID);
                throw new java.lang.UnsupportedOperationException();
        }
    }

    /**
     * If caller did not initialized SDK or failed.
     */
    public static void throwIfSdkNotInitial(boolean bInitSdk){

        ZKMALog.d(TAG,"enableApiByFlavor  BuildConfig.PARTNER_ID="+ BuildConfig.PARTNER_ID);
        if( bInitSdk == true ) {
            return;
        }
        else {
            ZKMALog.e(TAG, "ERROR! throwIfSdkNotInitial bInitSdk=" + bInitSdk);
            Thread.dumpStack();
            // throw new RuntimeException();
        }
    }



    static public void GenericErrorChecker(final Context context, final int errcode) {
        if(context!= null && errcode!=RESULT.SUCCESS) {

            if((errcode<= RESULT.E_TEEKM_JSON_GENERAL) && (errcode >= RESULT.E_TEEKM_JSON_GENERAL-100)) { // -1000 ~ -1100 is "The provided parameters are not valid." ERROR
                KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type7_error_description),errcode));
            }
            else {
                switch (errcode) {
                    case RESULT.E_SDK_ROM_TZAPI_TOO_OLD:
                    case RESULT.E_SDK_ROM_SERVICE_TOO_OLD:
                    case RESULT.E_ZKMA_TOO_OLD:
                    case RESULT.E_ZKMS_TOO_OLD:
                        ZKMALog.d(TAG, "TZAPI/Service/ZKMA/ZKMS is too old.");
                        break;

                    case RESULT.E_TEEKM_SEED_NOT_FOUND: // not handle, too frequently
                    case RESULT.E_TEEKM_SEED_EXISTS:
                    case RESULT.E_TEEKM_PIN_NOT_FOUND:
                    case RESULT.E_TEEKM_TIME_TIMEOUT:
                    case RESULT.E_TEEKM_SSS_VERIFY_CODE_NOT_MATCH:
                    case RESULT.E_TEEKM_SSS_RE_TRY_COUNT_0:
                    case RESULT.E_TEEKM_VC_CANCEL:
                    case RESULT.E_TEEKM_VC_TIMEOUT:
                    case RESULT.E_TEEKM_PASSCODE_NOT_FOUND: // changing PIN without seed
                    case RESULT.E_TEEKM_DATA_DB_DATASET:
                    case RESULT.E_TEEKM_DATA_DB_RSA_SIGN_VERIFY:
                    case RESULT.E_TEEKM_REGISTER_ALREADY:
                        break;

                    case RESULT.E_TEEKM_CLIENT_TOUCH: // SSD asked not handle
                    case RESULT.E_TEEKM_CLIENT_SCREEN_OFF:
                    case RESULT.E_TEEKM_NO_PROMPT_ERROR_MONKEY:
                        break;

                    case RESULT.E_TEEKM_UI_GENERAL: // just show dbgmsg
                    case RESULT.E_TEEKM_UI_CANCEL:
                    case RESULT.E_TEEKM_UI_BACK:
                    case RESULT.E_TEEKM_UI_EDIT:
                    case RESULT.E_TEEKM_UI_RELATED:
                    case RESULT.E_TEEKM_UI_REJECT:
                        ZKMALog.d(TAG, "GenericErrorChecker BYPASS errorcode = "+ errcode);
                        break;

                    case RESULT.E_TEEKM_TAMPERED: // App handle
                        ZKMALog.d(TAG, "GenericErrorChecker App need to handle this errorcode = "+ errcode);
                        break;

                    case RESULT.TEST_GENERIC_ERROR:
                    case RESULT.E_TEEKM_UNKNOWN_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_generic_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE1_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type1_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE2_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type2_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE3_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type3_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE4_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type4_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE5_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type5_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE6_ERROR:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type6_error_description),errcode));
                        break;
                    case RESULT.TEST_TYPE7_ERROR:
                    case RESULT.E_TEEKM_SIGN_SCRIPT_ADDRESS_DECODE:
                    case RESULT.E_TEEKM_INVALID_ARG:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_type7_error_description),errcode));
                        break;
                    case RESULT.E_SDK_DOWNLOAD_TZTABL:
                    case RESULT.E_TEEKM_JSON_ETH721_DB_DECIAML_NOT_MATCH:
                    case RESULT.E_TEEKM_JSON_ETH721_DB_SYMBOL_NOT_MATCH:
                        KeyAgent.doShowAlertDialog(context, String.format(context.getString(R.string.text_sync_tztable_error),errcode));
                        break;
                    default: // Sdk handle
                        ZKMALog.e(TAG, "GenericErrorChecker Sdk is Handling errorcode="+errcode);
                        KeyAgent.doShowAlertDialog(context, errcode);
                }
            }
        }
    }

    static public int setXXXXListener(Object listener) {
        if(listener==null) {
            ZKMALog.w(TAG, "listener="+listener);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public long register(String wallet_name, String sha256) {
        if(wallet_name==null || sha256==null) {
            ZKMALog.e(TAG, "wallet_name="+wallet_name+"  sha256="+sha256);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int unregister(String wallet_name, String sha256, long unique_id) {
        if(unique_id==0 || wallet_name==null || sha256==null) {
            ZKMALog.e(TAG, "wallet_name="+wallet_name+"  sha256="+sha256+"  unique_id="+unique_id);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int getPartialSeed(long unique_id, int seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder) {
        if(unique_id==0 || public_key==null || byteArrayHolder==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  public_key="+public_key+"  byteArrayHolder="+byteArrayHolder);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int combineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3) {
        if(unique_id==0 || seed_1==null || seed_2==null || seed_3==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  seed_1="+seed_1+"  seed_2="+seed_2+"  seed_3="+seed_3);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int signMessage(long unique_id, int coin_type, String strJson , ByteArrayHolder byteArrayHolder) {
        if(unique_id==0 || strJson==null || byteArrayHolder==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  strJson="+strJson+"  byteArrayHolder="+byteArrayHolder);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else {
            return RESULT.SUCCESS;
        }
    }

    static public int signTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder byteArrayHolder) {
        if(unique_id==0 || strJson==null || byteArrayHolder==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  strJson="+strJson+"  byteArrayHolder="+byteArrayHolder);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else {
            return RESULT.SUCCESS;
        }
    }

    static public int getTZIDHash(ByteArrayHolder ext_idhash) {
        if(ext_idhash==null) {
            ZKMALog.e(TAG, "ext_idhash="+ext_idhash);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int showVerificationCode(long unique_id, int seed_index, String friend_name, String friend_phone_num, String friend_phone_model) {
        if(unique_id==0 || friend_name==null || friend_phone_num==null || friend_phone_model==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  friend_name="+friend_name+"  friend_phone_num="+friend_phone_num+"  friend_phone_model="+friend_phone_model);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int enterVerificationCode(long unique_id, int seed_index, ByteArrayHolder enc_verify_code_with_sig) {
        if(unique_id==0 || enc_verify_code_with_sig==null) {
            ZKMALog.e(TAG, "unique_id="+unique_id+"  enc_verify_code_with_sig="+enc_verify_code_with_sig);
            return RESULT.E_SDK_INVALID_ARG;
        }
        else
            return RESULT.SUCCESS;
    }

    static public int getPartialSeed_v2(long unique_id, int seed_index, ByteArrayHolder enc_verify_code_pubkey_with_sig, ByteArrayHolder enc_aes_key_with_sig, ByteArrayHolder out_encryptedSeed) {
        if (unique_id == 0 || enc_verify_code_pubkey_with_sig == null || enc_aes_key_with_sig == null || out_encryptedSeed == null) {
            ZKMALog.e(TAG, "unique_id=" + unique_id + "  enc_verify_code_pubkey_with_sig=" + enc_verify_code_pubkey_with_sig + "  enc_aes_key_with_sig=" + enc_aes_key_with_sig + "  out_encryptedSeed=" + out_encryptedSeed);
            return RESULT.E_SDK_INVALID_ARG;
        } else
            return RESULT.SUCCESS;
    }

    static public int combineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1_with_sig, ByteArrayHolder enc_seed_2_with_sig, ByteArrayHolder enc_seed_3_with_sig) {
        if (unique_id == 0 || enc_seed_1_with_sig == null || enc_seed_2_with_sig == null || enc_seed_3_with_sig == null) {
            ZKMALog.e(TAG, "unique_id=" + unique_id + "  enc_seed_1_with_sig=" + enc_seed_1_with_sig + "  enc_seed_2_with_sig=" + enc_seed_2_with_sig + "  enc_seed_3_with_sig=" + enc_seed_3_with_sig);
            return RESULT.E_SDK_INVALID_ARG;
        } else
            return RESULT.SUCCESS;
    }

}
