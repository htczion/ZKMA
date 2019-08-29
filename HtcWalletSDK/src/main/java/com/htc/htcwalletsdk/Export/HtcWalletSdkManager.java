package com.htc.htcwalletsdk.Export;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.WorkerThread;

import com.htc.htcwalletsdk.BuildConfig;
import com.htc.htcwalletsdk.GlobalVariable;
import com.htc.htcwalletsdk.Native.IJniCallbackListener;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Protect.SdkProtector;
import com.htc.htcwalletsdk.Security.Core.KeyAgent;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;
import com.htc.htcwalletsdk.Utils.DebugMode;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * ZKMA exported APIs
 *
 * @version 3.4.6
 *
 * @since   2018/09/21
 *
 * @comment
 *   version control, modify since and version every SDK release
 *   ID.__.__  : major version, arch changed or target release, ex: architecture changed, or DVT to PVT ...
 *   __.ID.__  : minor version, interface changed, ROM update required, ex: exported API, targetSdk, java interface ...
 *   __.__.ID  : sub-minor version, self-changed,  ex: internal method, field, .so changed ...
 *   __.__.__a : alpha
 *   __.__.__b : beta
 *
 *  You should increase minor version if any modification made in this file.
 *
 * Created by hawk_wei
 */

public class HtcWalletSdkManager implements ISdkExportKeyAgentApis {
    static final String TAG = "HtcWalletSdkManager";
    private KeyAgent mKeyAgent;
    private boolean bAvoidUiThread = true; // To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.

    private static class SingletonHolder {
        // static initial, JVM protected thread-safe
        private static boolean bInit = false;
        private static HtcWalletSdkManager mInstance = new HtcWalletSdkManager();
        public static ExportFields mExportFields = new ExportFields();
    }

    /**
     * Singleton for each process can only get a handle of this SDK manager
     * @return the only instance of Wallet Key Agent
     */
    public static HtcWalletSdkManager getInstance(){
        return SingletonHolder.mInstance;
    }

    private HtcWalletSdkManager(){
        ZKMALog.i(TAG, "HtcWalletSdkManager +++");
        DebugMode.CheckDebugMode();
        mKeyAgent = new KeyAgent(SingletonHolder.mInstance);
        ZKMALog.i(TAG, "HtcWalletSdkManager ---");
    }

    /**
     * init and allocate SDK resources
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param context the caller context
     * @return API execute result, 0 is success
     * @exception IllegalStateException if API called in UI thread.
     */
    @WorkerThread
    public int init(Context context) {
        int ret;
        ZKMALog.i(TAG, "init +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        if(SingletonHolder.bInit == false) {
            ret = mKeyAgent.init(context);
            if(ret == RESULT.SUCCESS)
                SingletonHolder.bInit = true;
        } else {
            ZKMALog.i(TAG, "init again, so do nothing!");
            ret = RESULT.SUCCESS;
        }
        ZKMALog.i(TAG, "init ---  ret="+ret);
        return ret;
    }

    /**
     * set runtime environment for SDK by demand
     *
     * @param env the environment value, 0:SHIP, 1:TEST
     * @return API execute result, 0 is success
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int setEnvironment(int env) {
        ZKMALog.i(TAG, "setEnvironment +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.setEnvironment(env);
        ZKMALog.i(TAG, "setEnvironment ---  ret="+ret);
        return ret;
    }

    /**
     * Assign an Object with IJniCallbackListener interface to get a callback from JNI native layer
     *
     * @param listener An Object implemented IJniCallback to listen the Callback
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @deprecated
     */
    public void setCallBackListener(IJniCallbackListener listener) {
        ZKMALog.i(TAG, "setCallBackListener for JNI +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        mKeyAgent.setCallBackListener(listener);
        ZKMALog.i(TAG, "setCallBackListener for JNI ---");
    }

    /**
     * Assign an Object with IJavaCallbackListener interface to get a callback from JAVA layer
     *
     * @param listener An Object implemented IJavaCallbackListener to listen the Callback
     *
     * @exception UnsupportedOperationException if API is not supported.
     * @deprecated
     */
    public void setCallBackListener(IJavaCallbackListener listener) {
        ZKMALog.i(TAG, "setCallBackListener for JAVA +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        mKeyAgent.setCallBackListener(listener);
        ZKMALog.i(TAG, "setCallBackListener for JAVA---");
    }

    /**
     * Assign an Object with ISdkProtector interface to get a callback if any SDK protection triggered
     *
     * @param listener An Object implemented ISdkProtector to listen the Callback
     *
     * @exception UnsupportedOperationException if API is not supported.
     * @deprecated
     */
    public void setSdkProtectorListener(ISdkProtector listener) {
        ZKMALog.i(TAG, "setSdkProtectorListener +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        mKeyAgent.setSdkProtectorListener(listener);
        ZKMALog.i(TAG, "setSdkProtectorListener ---");
    }

    /**
     * Get current SDK AAR version
     *
     * @return Version string
     */
    public String getModuleVersion() {
        ZKMALog.i(TAG, "GetModuleVersion +++");
        String ret = mKeyAgent.getModuleVersion();
        ZKMALog.i(TAG, "GetModuleVersion ---  vret="+ret);
        return ret;
    }

    /**
     * Get current API version(HEX) provide by SDK.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @return Version string = HW wallet:     0.SERVICE_VERSION.TZAPI_VERSION
     *                          SW wallet:     1.JNILIB_VERSION .SWLIB_VERSION
     *                          For example:  "0.12AC.012ABCDE"
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public String getApiVersion() {
        ZKMALog.i(TAG, "getApiVersion +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        String ret = mKeyAgent.getApiVersion();
        ZKMALog.i(TAG, "getApiVersion ---  vret="+ret);
        return ret;
    }

    /**
     * Generate a unique ID for APP
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param wallet_name wallet APP name, max length is 32 char.
     * @param sha256 wallet APP sha256 generated by device ID (ex: Android ID)
     * @return positive value: unique id, 0 or negative value: fail with error code
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public long register(String wallet_name, String sha256) {
        ZKMALog.i(TAG, "register +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        long ret = mKeyAgent.register(wallet_name, sha256);
        ZKMALog.i(TAG, "register ---");
        return ret;
    }

    /**
     * Get a public Key from SDK for send condition,and if call this API, the index string of path will auto increase always.
     *  In case of BTC/LTC, the path will be m/44'/0'/0'/0/index
     *  In case of ETH, the path will be m/44'/60'/0'/0/0
     *  In case of XLM, the path will be m/44'/148'/0'/0'/0'
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coinType BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @param index an index for path generation
     * @return null: failed, others: success is Account address of coin type.
     *      Note: In case of XLM, Account ID will be returned by PublicKeyHolder, not public key.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getSendPublicKey(long unique_id, int coinType, int index) {
        ZKMALog.i(TAG, "getSendPublicKey idx +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getPublicKey(unique_id, coinType, 0, index); // 0:send,  1:receive
        ZKMALog.i(TAG, "getSendPublicKey idx ---");
        return publicKeyHolder;
    }

    /**
     * Get public Key from SDK for receive condition,and if call this API, the index string of path will auto increase always.
     *  In case of BTC/LTC, the path will be m/44'/0'/0'/1/index
     *  In case of ETH, the path will be m/44'/60'/0'/0/0
     *  In case of XLM, the path will be m/44'/148'/0'/0'/0'
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coinType BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @param index an index for path generation
     * @return null: failed, others: success is Account address of coin type.
     *      Note: In case of XLM, Account ID will be returned by PublicKeyHolder, not public key.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getReceivePublicKey(long unique_id, int coinType, int index) {
        ZKMALog.i(TAG, "getReceivePublicKey idx +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getPublicKey(unique_id, coinType, 1, index); // change=  0:send,  1:receive
        ZKMALog.i(TAG, "getReceivePublicKey idx ---");
        return publicKeyHolder;
    }

    /**
     * Get public Key from SDK for send condition,and if call this API, the index string of path will auto increase always.
     *  In case of BTC/LTC, the path will be m/44'/0'/0'/0/index
     *  In case of ETH, the path will be m/44'/60'/0'/0/0
     *  In case of XLM, the path will be m/44'/148'/0'/0'/0'
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coinType BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @return null: failed, others: success is Account address of coin type.
     *      Note: In case of XLM, Account ID will be returned by PublicKeyHolder, not public key.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getSendPublicKey(long unique_id, int coinType) {
        ZKMALog.i(TAG, "getSendPublicKey +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getPublicKey(unique_id, coinType, 0); // 0:send,  1:receive
        ZKMALog.i(TAG, "getSendPublicKey ---");
        return publicKeyHolder;
    }

    /**
     * Get public Key from SDK for receive condition,and if call this API, the index string of path will auto increase always.
     *  In case of BTC/LTC, the path will be m/44'/0'/0'/1/index
     *  In case of ETH, the path will be m/44'/60'/0'/0/0
     *  In case of XLM, the path will be m/44'/148'/0'/0'/0'
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coinType BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @return null: failed, others: success is Account address of coin type.
     *      Note: In case of XLM, Account ID will be returned by PublicKeyHolder, not public key.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getReceivePublicKey(long unique_id, int coinType) {
        ZKMALog.i(TAG, "getReceivePublicKey +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getPublicKey(unique_id, coinType, 1); // 0:send,  1:receive
        ZKMALog.i(TAG, "getReceivePublicKey ---");
        return publicKeyHolder;
    }

    /**
     * Get Account Extended public Key
     *
     * Address format is BIP44.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param purpose BIP44 is 44.
     * @param coinType BitCoin is 0, LiteCoin is 2, and not support Ethereum.
     * @param account number of account, default is 0.
     * @return null: failed, others: success is Account address of coin type.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getAccountExtPublicKey(long unique_id, int purpose, int coinType, int account) {
        ZKMALog.i(TAG, "getAccountExtendedPublicKey +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getExtPublicKey(unique_id, purpose, coinType, account);
        ZKMALog.i(TAG, "getAccountExtendedPublicKey ---");
        return publicKeyHolder;
    }

    /**
     * Get BIP32 Extended public Key
     *
     * Address format is BIP44.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param purpose BIP44 is 44.
     * @param coinType BitCoin is 0, LiteCoin is 2, and not support Ethereum.
     * @param account number of account, default is 0.
     * @param change 0: send, 1: receive
     * @param index an index for path generation
     * @return null: failed, others: success is Account address of coin type.
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public PublicKeyHolder getBipExtPublicKey(long unique_id, int purpose, int coinType, int account, int change, int index) {
        ZKMALog.i(TAG, "getBipExtPublicKey +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        PublicKeyHolder publicKeyHolder = mKeyAgent.getExtPublicKey(unique_id, purpose, coinType, account, change, index);
        ZKMALog.i(TAG, "getBipExtPublicKey ---");
        return publicKeyHolder;
    }



    /**
     * To check if Seed is already existing.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int isSeedExists(long unique_id) {
        ZKMALog.i(TAG, "IsSeedExists +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.isSeedExists(unique_id);
        ZKMALog.i(TAG, "IsSeedExists ---  ret="+ret);
        return ret;
    }


    /**
     * Create a Seed for Key generation
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int createSeed(long unique_id) {
        ZKMALog.i(TAG, "createSeed +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.createSeed(unique_id);
        ZKMALog.i(TAG, "createSeed ---  ret="+ret);
        return ret;
    }

    /**
     * clear the seed of this unique id which already stored in device
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return  0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int clearSeed(long unique_id) {
        ZKMALog.i(TAG, "clearSeed +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.clearSeed(unique_id);
        ZKMALog.i(TAG, "clearSeed ---  ret="+ret);
        return ret;
    }

    /**
     * show seed of this unique id
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int showSeed(long unique_id) {
        ZKMALog.i(TAG, "showSeed +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.showSeed(unique_id);
        ZKMALog.i(TAG, "showSeed ---  ret="+ret);
        return ret;
    }

    /**
     * restore the seed of the unique_id
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return  0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int restoreSeed(long unique_id) {
        ZKMALog.i(TAG, "restoreSeed +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.restoreSeed(unique_id);
        ZKMALog.i(TAG, "restoreSeed ---  ret="+ret);
        return ret;
    }

    /**
     * Process sign transaction by Coin type with Exchange server
     * After you got transaction result, please de base64 to get original
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param coin_type BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @param rates USD currency rate
     * @param strJson a JSON format data from APP
     * @param byteArrayHolder A holder object( default:2Kb ) for receiving the output tx signed data from KeyAgent
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception IllegalArgumentException if byte array length is not enough.
     */
    @WorkerThread
    public int signTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder byteArrayHolder) {
        ZKMALog.i(TAG, "signTransaction +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.signTransaction(unique_id, coin_type, rates, strJson, byteArrayHolder);
        ZKMALog.i(TAG, "signTransaction ---  ret="+ret);
        return ret;
    }

    /**
     * To prevent hacking, it's better to check if the device rooted or not.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @return 0: not root yet(RESULT.NOT_ROOTED), others: rooted or ,other errors
     */
    @WorkerThread
    public int isRooted() {
        ZKMALog.i(TAG, "isRooted +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.isRooted();
        ZKMALog.i(TAG, "isRooted ---  ret="+ret);
        return ret;
    }


    /**
     * clear all Trust Zone data stored in Trust Zone
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     * @deprecated
     */
    @WorkerThread
    public int clearTzData() {
        ZKMALog.i(TAG, "clearTzData +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = RESULT.UNKNOWN;
        if(BuildConfig.DEBUG)
            ret = mKeyAgent.clearTzData();
        else
            ZKMALog.i(TAG, "API not support!");
        ZKMALog.i(TAG, "clearTzData ---  ret="+ret);
        return ret;
    }

    /**
     * release the Key managed resources
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param wallet_name wallet APP name
     * @param sha256 wallet APP sha256
     * @param unique_id a unique_id generated by register() API.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int unregister(String wallet_name, String sha256, long unique_id) {
        ZKMALog.i(TAG, "unregister +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.unregister(wallet_name, sha256, unique_id);
        // SingletonHolder.mInstance = null;
        // SingletonHolder.bInit = false;
        // mKeyAgent = null;
        ZKMALog.i(TAG, "unregister ---");
        return ret;
    }

    /**
     * release the SDK resources
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int deinit() {
        ZKMALog.i(TAG, "deinit +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.deinit();
        // SingletonHolder.mInstance = null;
        SingletonHolder.bInit = false;
        // mKeyAgent = null;
        ZKMALog.i(TAG, "deinit ---  ret="+ret);
        return ret;
    }

    /**
     * Get partial seed with given index (range: 0-4), the partial seed is split by Shamir secret
     * sharing and encrypted with given public key. Shamir secret sharing should always
     * output same result, which means the partial seed should be identical every time.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param seed_index the index for partial seed, range: 0 - 4 (Max: 5).
     * @param public_key the public key from friend to encrypt the partial seed.
     * @return a byte array with partial seed data
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception IllegalArgumentException if byte array length is not enough.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    @Deprecated
    public byte[] getPartialSeed(long unique_id, int seed_index, ByteArrayHolder public_key) {
        ZKMALog.i(TAG, "getPartialSeed +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        byte[] ret = mKeyAgent.getPartialSeed(unique_id, (byte)seed_index, public_key);
        ZKMALog.i(TAG, "getPartialSeed ---  ret="+ret);
        return ret;
    }

    /**
     * Get partial seed with given index (range: 0-4), the partial seed is split by Shamir secret
     * sharing and encrypted with given public key. Shamir secret sharing should always
     * output same result, which means the partial seed should be identical every time.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param seed_index the index for partial seed, range: 0 - 4 (Max: 5).
     * @param public_key the public key from friend to encrypt the partial seed.
     * @param byteArrayHolder a ByteArrayHolder to receive the partial Seed data
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    @Deprecated
    public int getPartialSeed(long unique_id, int seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder) {
        ZKMALog.i(TAG, "getPartialSeed +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int  ret = mKeyAgent.getPartialSeed(unique_id, (byte)seed_index, public_key, byteArrayHolder);
        ZKMALog.i(TAG, "getPartialSeed ---  ret="+ret);
        return ret;
    }

    /**
     * Combine three partial seeds to restore the private key. The seeds should have no sequence.
     * Which means the caller can provide partial seed in random sequence and private key can still
     * be recovered.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * API Prerequisite:
     * 1. clearSeed(will show secureUI)  == RESULT.SUCCESS
     * 2. do getPartialSeed and decrypt *3 times   != null
     * 3. combineSeeds                   == RESULT.SUCCESS
     *
     * @param unique_id a unique_id generated by register() API.
     * @param seed_1 the 1st partial seed.
     * @param seed_2 the 2nd partial seed.
     * @param seed_3 the 3rd partial seed.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int combineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3) {
        ZKMALog.i(TAG, "combineSeeds +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.combineSeeds(unique_id, seed_1, seed_2, seed_3);
        ZKMALog.i(TAG, "combineSeeds ---  ret="+ret);
        return ret;
    }

    /**
     * Generate an activity for change PIN.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int changePIN(long unique_id) {
        ZKMALog.i(TAG, "changePIN +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.changePIN(unique_id);
        ZKMALog.i(TAG, "changePIN ---  ret="+ret);
        return ret;
    }

    /**
     * Generate an activity for check LAST_ERR_PIN.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param resId SDK will show the corresponding UI by this ID.
     *      0 : "Enter your passcode to unlock your wallet"
     *      1 : "Are you sure to remove this trusted contact?"
     *      2 : "Are you sure you want to sign out?"
     *      3 : "Are you sure to use this account?"
     *      4 : "Passcode required for additional security"
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int confirmPIN(long unique_id, int resId) {
        ZKMALog.i(TAG, "confirmPIN +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.confirmPIN(unique_id, resId);
        ZKMALog.i(TAG, "confirmPIN ---  ret="+ret);
        return ret;
    }


    /**
     * get KeyAgent for keyControl
     *
     * @return KeyAgent
     * @deprecated
     */
    public KeyAgent getKeyAgent(){
        return mKeyAgent;
    }

    /**
     * get all export fields of SDK
     * ex: TZ_support,minServiceVer,minTzApiVer....
     *
     * @return KeyAgent
     */
    public ExportFields getExportFields(){
        return SingletonHolder.mExportFields;
    }

    /**
     * Get the last error code happened
     * @return error code
     */
    public int getLastError(){
        return GlobalVariable.GetErrorCode();
    }



    /////////////////////////////////// FW update required //////////////////////////////////////
    /**
     * get a hashed security device_id for HTC Key Server public key
     * This is for Exchange Server.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param ext_idhash hashed byte array ID from trust zone
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception IllegalArgumentException if byte array length is not enough.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int getTZIDHash(ByteArrayHolder ext_idhash) {
        ZKMALog.i(TAG, "getTZIDHash +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.getTZIDHash(ext_idhash);
        ZKMALog.i(TAG, "getTZIDHash ---  ret="+ret);
        return ret;
    }

    /**
     * For Exchange server, get Encoded Address.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param bip_path BIP path of coin
     * @param ext_addr plain text of coin address
     * @param ext_encaddr secret text of coin address
     * @param ext_encaddr_signature signature of ext_encaddr
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int getEncAddr(long unique_id, String bip_path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature) {
        ZKMALog.i(TAG, "getEncAddr +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.getEncAddr( unique_id, bip_path, ext_addr, ext_encaddr, ext_encaddr_signature);
        ZKMALog.i(TAG, "getEncAddr ---  ret="+ret);
        return ret;
    }


    /**
     * Show verification code, it should be protected by Zion signature (sha256).
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param seed_index the index for partial seed, range: 0 - 4 (Max: 5).
     * @param friend_name friend's name
     * @param friend_phone_num friend's phone number
     * @param friend_phone_model friend's phone model
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int showVerificationCode(long unique_id, int seed_index, String friend_name, String friend_phone_num, String friend_phone_model) {
        ZKMALog.i(TAG, "showVerificationCode +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.showVerificationCode(unique_id, (byte)seed_index, friend_name, friend_phone_num, friend_phone_model);
        ZKMALog.i(TAG, "showVerificationCode ---  ret="+ret);
        return ret;
    }

    /***
     * For social key restore, enter an encrypted verify_code
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param seed_index the index for partial seed, range: 0 - 4 (Max: 5).
     * @param enc_verify_code_with_sig a number code from TZ which byte array encrypted RSA2048
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int enterVerificationCode(long unique_id, int seed_index, ByteArrayHolder enc_verify_code_with_sig) {
        ZKMALog.i(TAG, "enterVerificationCode +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.enterVerificationCode(unique_id, (byte)seed_index , enc_verify_code_with_sig);
        ZKMALog.i(TAG, "enterVerificationCode ---  ret="+ret);
        return ret;
    }

    /**
     * For social key restore, get Partial Seed V2.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param unique_id a unique_id generated by register() API.
     * @param seed_index the index for partial seed, range: 0 - 4 (Max: 5).
     * @param enc_verify_code_pubkey_with_sig the friend's verification code and public key to encrypt the partial seed with signature.
     * @param enc_aes_key_with_sig  AES256 encrypted key from server-side which can be decrypted by TZ with signature.
     * @param out_encryptedSeed RSA2048 Encrypted seed from TZ
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int getPartialSeed_v2(long unique_id, int seed_index, ByteArrayHolder enc_verify_code_pubkey_with_sig, ByteArrayHolder enc_aes_key_with_sig, ByteArrayHolder out_encryptedSeed) {
        ZKMALog.i(TAG, "getPartialSeed_v2 +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.getPartialSeed_v2(unique_id, (byte)seed_index, enc_verify_code_pubkey_with_sig, enc_aes_key_with_sig, out_encryptedSeed);
        ZKMALog.i(TAG, "getPartialSeed_v2 ---  ret="+ret);
        return ret;
    }

    /**
     * For social key restore, combine seeds V2
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * API Prerequisite:
     * 1. clearSeed(will show secureUI)  == RESULT.SUCCESS
     * 2. do POSIX API to get 3 seeds from server , getTZIDhash() is required.
     * 5. combineSeeds_v2                == RESULT.SUCCESS
     *
     * @param unique_id a unique_id generated by register() API.
     * @param enc_seed_1_with_sig the 1st encrypted partial seed with signature.
     * @param enc_seed_2_with_sig the 2nd encrypted partial seed with signature.
     * @param enc_seed_3_with_sig the 3rd encrypted partial seed with signature.
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int combineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1_with_sig, ByteArrayHolder enc_seed_2_with_sig, ByteArrayHolder enc_seed_3_with_sig) {
        ZKMALog.i(TAG, "combineSeeds_v2 +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.combineSeeds_v2(unique_id, enc_seed_1_with_sig, enc_seed_2_with_sig, enc_seed_3_with_sig);
        ZKMALog.i(TAG, "combineSeeds_v2 ---  ret="+ret);
        return ret;
    }

    /**
     * Assign a color table for Ethereum ERC20 background with text generation by system.
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     * 
     * @param colors 1~N color for signTransaction API to set its custom colors
     * @return 0: success and checked pass, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception UnsupportedOperationException if API is not supported.
     */
    @WorkerThread
    public int setERC20BGColor(Color[] colors) {
        ZKMALog.i(TAG, "setERC20BGColor +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.setERC20BGColor(colors);
        ZKMALog.i(TAG, "setERC20BGColor ---  ret="+ret);
        return ret;
    }

    /**
     * Process sign Message by Coin type with Exchange server
     * After you got Message result, please de base64 to get original
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coin_type BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @param strJson a JSON format data from APP
     * @param byteArrayHolder A holder object( default:1Kb ) for receiving the output tx signed data from KeyAgent
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception IllegalArgumentException if byte array length is not enough.
     */
    @WorkerThread
    public int signMessage(long unique_id, int coin_type, String strJson , ByteArrayHolder byteArrayHolder) {
        ZKMALog.i(TAG, "signMessage +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.signMessage(unique_id, coin_type, strJson, byteArrayHolder);
        ZKMALog.i(TAG, "signMessage ---  ret="+ret);
        return ret;
    }

    /**
     * Process sign multiple transactions by Coin type with Exchange server
     * After you got transaction results by byteArrayHolderArray, please de base64 to get original
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param unique_id a unique_id generated by register() API.
     * @param coin_type BitCoin is 0, LiteCoin is 2, Ethereum is 60.
     * @param rates USD currency rate
     * @param strJson a JSON format data for multiple transaction from APP
     *  An example JSON format for multiple transaction as following:
     *      {
     *         "approve": {
     *             ...
     *         },
     *         "trade": {
     *             ...
     *         },
     *         "currency": "USD",
     *         "extra": {
     *             ...
     *         }
     *      }
     * @param byteArrayHolderArray A array holder objects( default:2Kb ) for receiving the output tx signed data from KeyAgent
     * @return 0: success, others: other errors
     *
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     * @exception IllegalArgumentException if byte array length is not enough.
     */
    @WorkerThread
    public int signMultipleTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder[] byteArrayHolderArray) {
        ZKMALog.i(TAG, "signMultipleTransaction +++");
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.signMultipleTransaction(unique_id, coin_type, rates, strJson, byteArrayHolderArray);
        ZKMALog.i(TAG, "signMultipleTransaction ---  ret="+ret);
        return ret;
    }

    /**
     * read a specific data set from TZ
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param dataSet data set number
     * @param data A {@link ByteArrayHolder} for receiving data from TZ
     * @return 0: success and checked pass, others: other errors
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int readTzDataSet(int dataSet, ByteArrayHolder data) {
        ZKMALog.i(TAG, "readTzDataSet +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.readTzDataSet(dataSet, data);
        ZKMALog.i(TAG, "readTzDataSet ---");
        return ret;
    }

    /**
     * write a specific data set to TZ
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param dataSet data set number
     * @param data A {@link ByteArrayHolder} for overwriting data in TZ
     * @param signature A {@link ByteArrayHolder} stored the signature of data for verifying by TZ
     * @return 0: success and checked pass, others: other errors
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int writeTzDataSet(int dataSet, ByteArrayHolder data, ByteArrayHolder signature) {
        ZKMALog.i(TAG, "writeTzDataSet +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.writeTzDataSet(dataSet, data, signature);
        ZKMALog.i(TAG, "writeTzDataSet ---");
        return ret;
    }

    /**
     * set the keyboard type before create seed, restore seed
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param nType Input method type
     *              0: qwertypad
     *              1 : numberpad
     * @return 0: success and checked pass, others: other errors
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     *
     *  Note:
     *    a.setKeyboardType will only effect if seed of paired walletid doesn't exist before
     *    b.invoke setKeyboardType before execute create seed, restore seed.
     */
    @WorkerThread
    public int setKeyboardType(long unique_id, int nType) {
        ZKMALog.i(TAG, "setKeyboardType +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.setKeyboardType(unique_id, nType);
        ZKMALog.i(TAG, "setKeyboardType ---");
        return ret;
    }

    /**
     * ChangePin version 2 supported qwertypad or numberpad
     * Note: To prevent TRUST-ZONE blocked main thread, the caller must call this API at background thread.
     *
     * @param nType is required to indicate the new passcode's keyboard type.
     *              0: qwertypad
     *              1 : numberpad
     * @return 0: success and checked pass, others: other errors
     *
     * @exception UnsupportedOperationException if API not support by SDK.
     * @exception IllegalStateException if API called in UI thread.
     * @exception RuntimeException if SDK not init yet.
     */
    @WorkerThread
    public int changePIN_v2(long unique_id, int nType) {
        ZKMALog.i(TAG, "changePIN_v2 +++");
        SdkProtector.throwIfApiNotSupport();
        SdkProtector.throwIfOnMainThread(bAvoidUiThread);
        SdkProtector.throwIfSdkNotInitial( SingletonHolder.bInit);
        int ret = mKeyAgent.changePIN_v2(unique_id, nType);
        ZKMALog.i(TAG, "changePIN_v2 ---");
        return ret;
    }

}
