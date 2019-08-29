package com.htc.htcwalletsdk.Security.Core;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Utils.ZKMALog;
import com.htc.wallettzservice.EncAddr;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * A Helper for access the method was provided by service
 */
public class TEEKHelper {

    private static final String TAG = "ZKMALog";

    private static TEEKHelper sInst;

    private Class mClass;
    private Object mManager;
    private Method METHOD_APIVERSION;
    private Method METHOD_REGISTER;
    public Method METHOD_REGISTEREX;
    private Method METHOD_UNREGISTER;
    private Method METHOD_CREATESEED;
    private Method METHOD_RESTORESEED;
    private Method METHOD_SHOWSEED;
    private Method METHOD_CLEARSEED;
    private Method METHOD_ISSEEDEXISTS;
    private Method METHOD_ISTAMPERED;
    private Method METHOD_GETPUBLICKEY;
    private Method METHOD_SIGNTRANSACTION;
    private Method METHOD_CLEARDATA; // platform key required
    private Method METHOD_GETPARTIALSEED; // HMS key required
    private Method METHOD_COMBINESEEDS; // HMS key required
    private Method METHOD_CHANGEPIN;
    private Method METHOD_CONFIRMPIN;
    private Method METHOD_GETSERVICEVERSION;
    private Method METHOD_GETTZIDHASH;
    // FW update
    private Method METHOD_SHOWVERIFICATIONCODE; // HMS key required
    private Method METHOD_GETPARTIALSEEDv2; // HMS key required
    private Method METHOD_ENTERVERIFICATIONCODE; // HMS key required
    private Method METHOD_GETENCADDR; // HMS key required
    private Method METHOD_COMBINESEEDSv2; // HMS key required
    private Method METHOD_SETERC20BGCOLOR;
    private Method METHOD_SETENVIRONMENT; // HMS key required
    // FW update
    private Method METHOD_READTZDATASET; // SERVER key required
    private Method METHOD_WRITETZDATASET; // SERVER key required
    private Method METHOD_SETKEYBOARDTYPE;
    private Method METHOD_CHANGEPINv2;
    // FW update
    private Method METHOD_GETEXTPUBLICKEY;

    private final Context mContext;
    private boolean mLoadSuccess = false;

    synchronized public static TEEKHelper getInstance(Context context) {
        if (sInst == null) {
            sInst = new TEEKHelper(context);
        }
        return sInst;
    }

    private TEEKHelper(Context context) {
        mContext = context.getApplicationContext();

        init();
    }

    private void init() {
        try {
            mClass = Class.forName("com.htc.wallettzservice.HtcWalletTZManager");
            final Constructor c = mClass.getDeclaredConstructor(new Class[]{Context.class});
            mManager = c.newInstance(mContext);
            ZKMALog.d(TAG, "new instance:" + mManager);
        } catch (Exception e) {
            Log.e(TAG, "load class failed!!", e);
        }

        ZKMALog.d(TAG, "enumerate all methods ++");
        for (Method method : mClass.getDeclaredMethods()) {
            if(method != null) {
                String strMethod = method.toString();
                ZKMALog.d(TAG, "method:" + method);


                // Versions:  {{"1.1.0"},{"0x0001"},{"0x01000000"}}
                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.apiVersion()"))
                        METHOD_APIVERSION      = mClass.getMethod("apiVersion");
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "apiVersion API check failed!", e);
                }

                try {
                    if(strMethod.equals("public long com.htc.wallettzservice.HtcWalletTZManager.register(java.lang.String,byte[],int)"))
                        METHOD_REGISTER        = mClass.getMethod("register",String.class, byte[].class, int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "register API check failed!", e);
                }

                try {
                    if(strMethod.equals("public long com.htc.wallettzservice.HtcWalletTZManager.registerEx(java.lang.String,byte[],int,java.lang.String)"))
                        METHOD_REGISTEREX        = mClass.getMethod("registerEx",String.class, byte[].class, int.class, String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "registerEx API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.unRegister(java.lang.String,byte[],int,long)"))
                        METHOD_UNREGISTER      = mClass.getMethod("unRegister",String.class, byte[].class, int.class, long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "unRegister API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.createSeed(long)"))
                        METHOD_CREATESEED      = mClass.getMethod("createSeed",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "createSeed API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.restoreSeed(long)"))
                        METHOD_RESTORESEED     = mClass.getMethod("restoreSeed",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "restoreSeed API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.showSeed(long)"))
                        METHOD_SHOWSEED        = mClass.getMethod("showSeed",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "showSeed API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.clearSeed(long)"))
                        METHOD_CLEARSEED       = mClass.getMethod("clearSeed",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "clearSeed API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.isSeedExists(long)"))
                        METHOD_ISSEEDEXISTS    = mClass.getMethod("isSeedExists",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "isSeedExists API check failed!", e);
                }

                try {
                    if(strMethod.equals("public java.lang.String com.htc.wallettzservice.HtcWalletTZManager.getPublicKey(long,java.lang.String)"))
                        METHOD_GETPUBLICKEY    = mClass.getMethod("getPublicKey",long.class, String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getPublicKey API check failed!", e);
                }

                try {
                    if(strMethod.equals("public java.lang.String com.htc.wallettzservice.HtcWalletTZManager.getExtPublicKey(long,java.lang.String)"))
                        METHOD_GETEXTPUBLICKEY    = mClass.getMethod("getExtPublicKey",long.class, String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getExtPublicKey API check failed!", e);
                }

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.signTransaction(long,int,float,java.lang.String)"))
                        METHOD_SIGNTRANSACTION = mClass.getMethod("signTransaction",long.class, int.class, float.class, String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "signTransaction API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.clearData()"))
                        METHOD_CLEARDATA       = mClass.getMethod("clearData");
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "clearData API check failed!", e);
                }

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.getPartialSeed(long,byte,byte[],long)"))
                        METHOD_GETPARTIALSEED  = mClass.getMethod("getPartialSeed", long.class, byte.class, byte[].class, long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getPartialSeed API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.combineSeeds(long,byte[],long,byte[],long,byte[],long)"))
                        METHOD_COMBINESEEDS    = mClass.getMethod("combineSeeds", long.class, byte[].class, long.class, byte[].class, long.class, byte[].class, long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "combineSeeds API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.changePin(long)"))
                        METHOD_CHANGEPIN       = mClass.getMethod("changePin",long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "changePin API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.confirmPin(long,int)"))
                        METHOD_CONFIRMPIN      = mClass.getMethod("confirmPin",long.class, int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "confirmPin API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.isTampered()"))
                        METHOD_ISTAMPERED      = mClass.getMethod("isTampered");
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "isTampered API check failed!", e);
                }

                try {
                    if(strMethod.equals("public java.lang.String com.htc.wallettzservice.HtcWalletTZManager.getServiceVersion()"))
                        METHOD_GETSERVICEVERSION      = mClass.getMethod("getServiceVersion");
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getServiceVersion API check failed!", e);
                }

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.getTZIDHash()"))
                        METHOD_GETTZIDHASH = mClass.getMethod("getTZIDHash");
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getTZIDHash API check failed!", e);
                }

                // Versions:  {{"1.2.0"},{"0x0004"},{"0x01010005"}}
                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.showVerificationCode(long,byte,java.lang.String,java.lang.String,java.lang.String)"))
                        METHOD_SHOWVERIFICATIONCODE = mClass.getMethod("showVerificationCode",long.class,byte.class,String.class,String.class,String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "showVerificationCode API check failed!", e);
                }

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.getPartialSeedv2(long,byte,byte[],long,byte[],long)"))
                        METHOD_GETPARTIALSEEDv2 = mClass.getMethod("getPartialSeedv2", long.class, byte.class, byte[].class, long.class, byte[].class, long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getPartialSeedv2 API check failed!", e);
                }

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.enterVerificationCode(long,byte)"))
                        METHOD_ENTERVERIFICATIONCODE = mClass.getMethod("enterVerificationCode", long.class, byte.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "enterVerificationCode API check failed!", e);
                }

                try {
                    if(strMethod.equals("public com.htc.wallettzservice.EncAddr com.htc.wallettzservice.HtcWalletTZManager.getEncAddr(long,java.lang.String)"))
                        METHOD_GETENCADDR = mClass.getMethod("getEncAddr", long.class, String.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "getEncAddr API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.combineSeedsv2(long,byte[],long,byte[],long,byte[],long)"))
                        METHOD_COMBINESEEDSv2 = mClass.getMethod("combineSeedsv2", long.class, byte[].class, long.class, byte[].class, long.class, byte[].class, long.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "combineSeedsv2 API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.setERC20BGColor(android.graphics.Color[])"))
                        METHOD_SETERC20BGCOLOR = mClass.getMethod("setERC20BGColor", android.graphics.Color[].class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "setERC20BGColor API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.setEnvironment(byte)"))
                        METHOD_SETENVIRONMENT = mClass.getMethod("setEnvironment", byte.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "setEnvironment API check failed!", e);
                }
                // Versions:  {{"2.2.0"},{"0x0005"},{"0x010100??"}}

                try {
                    if(strMethod.equals("public byte[] com.htc.wallettzservice.HtcWalletTZManager.readTzDataSet(int)"))
                        METHOD_READTZDATASET = mClass.getMethod("readTzDataSet", int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "readTzDataSet API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.writeTzDataSet(int,byte[],int,byte[],int)"))
                        METHOD_WRITETZDATASET = mClass.getMethod("writeTzDataSet", int.class, byte[].class, int.class, byte[].class, int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "writeTzDataSet API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.setKeyboardType(long,int)"))
                        METHOD_SETKEYBOARDTYPE      = mClass.getMethod("setKeyboardType",long.class, int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "setKeyboardType API check failed!", e);
                }

                try {
                    if(strMethod.equals("public int com.htc.wallettzservice.HtcWalletTZManager.changePinv2(long,int)"))
                        METHOD_CHANGEPINv2      = mClass.getMethod("changePinv2",long.class, int.class);
                } catch (NoSuchMethodException e) {
                    Log.e(TAG, "changePinv2 API check failed!", e);
                }

            }
            else {
                ZKMALog.e(TAG, "get some method failed!!");
            }
        }
        ZKMALog.d(TAG, "enumerate all methods --");
        mLoadSuccess = true;
    }

    public boolean isLoadSuccess() {
        return mLoadSuccess;
    }

    public int TzApiVersion() {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "TzApiVersion +");
            ret = METHOD_APIVERSION.invoke(mManager);
            ZKMALog.i(TAG, "TzApiVersion - " + String.format("0x%08X", ret));
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public long register(String walletName, byte[] array, int arrayLength){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.w(TAG, "register +");
            ret = METHOD_REGISTER.invoke(mManager,walletName, array, arrayLength);
            ZKMALog.w(TAG, "register -");
            return (long)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return RESULT.UNKNOWN;
    }

    public long registerEx(String walletName, byte[] array, int arrayLength, String extra){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.w(TAG, "registerEx +");
            ret = METHOD_REGISTEREX.invoke(mManager,walletName, array, arrayLength, extra);
            ZKMALog.w(TAG, "registerEx -");
            return (long)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return RESULT.UNKNOWN;
    }


    public int unRegister(String walletName, byte[] array, int arrayLength, long id){
        Object ret = RESULT.UNKNOWN;
        try {
            ZKMALog.w(TAG, "unRegister +");
            METHOD_UNREGISTER.invoke(mManager,walletName, array, arrayLength, id);
            ZKMALog.w(TAG, "unRegister -");
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return RESULT.UNKNOWN;
    }

    public int isSeedExists(long id) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "isSeedExists +");
            ret = METHOD_ISSEEDEXISTS.invoke(mManager,id);
            ZKMALog.i(TAG, "isSeedExists - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public int createSeed(long id){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "createSeed +");
            ret = METHOD_CREATESEED.invoke(mManager,id);
            ZKMALog.i(TAG, "createSeed - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }
    public int restoreSeed(long id){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "restoreSeed +");
            ret = METHOD_RESTORESEED.invoke(mManager,id);
            ZKMALog.i(TAG, "restoreSeed - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }
    public int showSeed(long id){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "showSeed +");
            ret = METHOD_SHOWSEED.invoke(mManager,id);
            ZKMALog.i(TAG, "showSeed - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }
    public int clearSeed(long id){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.w(TAG, "clearSeed +");
            ret = METHOD_CLEARSEED.invoke(mManager,id);
            ZKMALog.w(TAG, "clearSeed - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public String getPublicKey(long id, String path){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "getPublicKey +");
            ret = METHOD_GETPUBLICKEY.invoke(mManager, id, path);
            ZKMALog.i(TAG, "getPublicKey -");
            return (String)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public String getExtPublicKey(long id, String path){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "getExtPublicKey +");
            ret = METHOD_GETEXTPUBLICKEY.invoke(mManager, id, path);
            ZKMALog.i(TAG, "getExtPublicKey -");
            return (String)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public byte[] signTransaction(long unique_id,int coin_type, float rates, String data){
        Object ret = null;

        try {
            ZKMALog.i(TAG, "signTransaction +");
            ret = METHOD_SIGNTRANSACTION.invoke(mManager, unique_id, coin_type, rates, data);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "signTransaction -");
            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public byte[] signMultipleTransaction(long unique_id,int coin_type, float rates, String data){
        Object ret = null;

        try {
            ZKMALog.i(TAG, "signMultipleTransaction +");
            ret = METHOD_SIGNTRANSACTION.invoke(mManager, unique_id, coin_type, rates, data);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "signMultipleTransaction -");
            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int clearData() {  // platform key required
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.w(TAG, "clearData +");
            ret = METHOD_CLEARDATA.invoke(mManager);
            ZKMALog.w(TAG, "clearData  - " +ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    // HMS key required
    public byte[] getPartialSeed(long junique_id, byte jseed_index, byte[] public_key_der, long der_len) {
        Object ret = null;

        try {
            ZKMALog.i(TAG, "getPartialSeed +");
            ret = METHOD_GETPARTIALSEED.invoke(mManager, junique_id, jseed_index, public_key_der, der_len);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "getPartialSeed -");
            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int combineSeeds(long junique_id, byte[] jseed_1, long jseed_1_size, byte[] jseed_2, long jseed_2_size, byte[] jseed_3, long jseed_3_size) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "combineSeeds +");
            ret = METHOD_COMBINESEEDS.invoke(mManager, junique_id, jseed_1, jseed_1_size, jseed_2, jseed_2_size, jseed_3, jseed_3_size);
            ZKMALog.i(TAG, "combineSeeds - "+ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public byte[] getTZIDHash() {
        final Object ret;

        try {
            ZKMALog.i(TAG, "getTZIDHash +");
            ret = METHOD_GETTZIDHASH.invoke(mManager);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "getTZIDHash -");

            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public EncAddr getEncAddr(long unique_id, String path){ // HMS key required
        final Object ret;

        try {
            ZKMALog.i(TAG, "getEncAddr +");
            ret = METHOD_GETENCADDR.invoke(mManager, unique_id, path);
            ZKMALog.i(TAG, "getEncAddr -");

            return (EncAddr)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int isRooted() {
        Object ret = RESULT.UNKNOWN;
        try {
            ZKMALog.i(TAG, "isTempered +");
            ret = METHOD_ISTAMPERED.invoke(mManager);
            ZKMALog.i(TAG, "isTempered - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public int changePIN(long id){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "changePin +");
            ret = METHOD_CHANGEPIN.invoke(mManager,id);
            ZKMALog.i(TAG, "changePin - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public int confirmPIN(long id, int resId){
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "confirmPin + resId="+resId);
            ret = METHOD_CONFIRMPIN.invoke(mManager,id, resId);
            ZKMALog.i(TAG, "confirmPin - " + ret + " resId=" + resId + " " + ret);
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public String getServiceVersion() {
        Object ret = null;

        try {
            ZKMALog.i(TAG, "getServiceVersion +");
            ret = METHOD_GETSERVICEVERSION.invoke(mManager);
            ZKMALog.i(TAG, "getServiceVersion - " + ret);
            return (String)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int showVerificationCode(long unique_id, byte seed_index, String name, String phone_num, String phone_model) {
        Object ret = RESULT.UNKNOWN;
        try {
            ZKMALog.i(TAG, "showVerificationCode +");
            ret = METHOD_SHOWVERIFICATIONCODE.invoke(mManager,unique_id,seed_index,name,phone_num,phone_model);
            ZKMALog.i(TAG, "showVerificationCode - " + ret );
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public byte[] enterVerificationCode(long unique_id, byte seed_index) {
        Object ret = null;

        try {
            ZKMALog.i(TAG, "enterVerificationCode +");
            ret = METHOD_ENTERVERIFICATIONCODE.invoke(mManager,unique_id,seed_index);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "enterVerificationCode -");
            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public byte[] getPartialSeed_v2(long unique_id, byte jseed_index, byte[] enc_verify_code_pubkey_arr, long enc_verify_code_pubkey_arr_len, byte[] aes_key_arr, long aes_key_arr_len) {
        Object ret = null;

        try {
            ZKMALog.i(TAG, "getPartialSeedv2 +");
            ret = METHOD_GETPARTIALSEEDv2.invoke(mManager,unique_id,jseed_index,enc_verify_code_pubkey_arr,enc_verify_code_pubkey_arr_len,aes_key_arr,aes_key_arr_len);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "getPartialSeedv2 - ");
            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int combineSeeds_v2(long junique_id, byte[] jseed_1, long jseed_1_size, byte[] jseed_2, long jseed_2_size, byte[] jseed_3, long jseed_3_size) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "combineSeeds_v2 +");
            ret = METHOD_COMBINESEEDSv2.invoke(mManager, junique_id, jseed_1, jseed_1_size, jseed_2, jseed_2_size, jseed_3, jseed_3_size);
            ZKMALog.i(TAG, "combineSeeds_v2 - "+ ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;

    }

    public int setERC20BGColor(Color[] colors){
        Object ret;

        try {
            ZKMALog.i(TAG, "setERC20BGColor +");
            ret = METHOD_SETERC20BGCOLOR.invoke(mManager,new Object[]{colors});
            ZKMALog.i(TAG, "setERC20BGColor - " + ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return RESULT.UNKNOWN;
    }

    public int setEnvironment(byte env) { // HMS key required
        Object ret; // to prevent downgrade casting failed.

        try {
            ZKMALog.i(TAG, "setEnvironment + env=" + env);
            ret = METHOD_SETENVIRONMENT.invoke(mManager, env);
            ZKMALog.i(TAG, "setEnvironment - " + ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return RESULT.UNKNOWN;
    }

    public byte[] readTzDataSet(int dataSet) {
        Object ret = null;

        try {
            ZKMALog.i(TAG, "readTzDataSet +");
            ret = METHOD_READTZDATASET.invoke(mManager, dataSet);
            ZKMALog.byteArray(TAG, (byte[])ret);
            ZKMALog.i(TAG, "readTzDataSet -");

            return (byte[])ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return null;
    }

    public int writeTzDataSet(int dataSet, byte[] data, byte[] signature) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "writeTzDataSet +");
            ret = METHOD_WRITETZDATASET.invoke(mManager, dataSet, data, data.length, signature, signature.length);
            ZKMALog.i(TAG, "writeTzDataSet - " + ret);

            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public int setKeyboardType(long unique_id, int nType) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "setKeyboardType + nType="+nType);
            ret = METHOD_SETKEYBOARDTYPE.invoke(mManager,unique_id, nType);
            ZKMALog.i(TAG, "setKeyboardType - " + ret);
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

    public int changePIN_v2(long unique_id, int nType) {
        Object ret = RESULT.UNKNOWN;

        try {
            ZKMALog.i(TAG, "changePIN_v2 + nType="+nType);
            ret = METHOD_CHANGEPINv2.invoke(mManager,unique_id, nType);
            ZKMALog.i(TAG, "changePIN_v2 - " + ret);
            return (int)ret;
        } catch (Exception e) {
            Log.e(TAG, "" + e, e);
        }
        return (int)ret;
    }

}