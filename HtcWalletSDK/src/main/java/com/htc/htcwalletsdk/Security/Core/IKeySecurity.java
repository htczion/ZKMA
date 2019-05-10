package com.htc.htcwalletsdk.Security.Core;

import android.graphics.Color;

import com.htc.htcwalletsdk.Export.IJavaCallbackListener;
import com.htc.htcwalletsdk.Native.IJniCallbackListener;
import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Protect.ISdkProtector;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;

/**
 * Created by hawk_wei on 2018/9/10.
 */

public interface IKeySecurity {
    String GetApiVersion();
    void SetCallBackListener(IJniCallbackListener listener);
    void SetCallBackListener(IJavaCallbackListener listener);
    void SetSdkProtectorListener(ISdkProtector listener);
    int SetEnvironment(int env);
    long Register(String walletName, String sha256);
    int IsSeedExists(long unique_id);
    int CreateSeed(long unique_id);
    int RestoreSeed(long unique_id);
    int ShowSeed(long unique_id);
    int ClearSeed(long unique_id);
    int ChangePIN(long unique_id);
    int ConfirmPIN(long unique_id, int resId);
    PublicKeyHolder GetPublicKey(long unique_id, String path, PublicKeyHolder publicKeyHolder);
    int SignTransaction(long unique_id, int coin_type, float rates, String strJson, ByteArrayHolder byteArrayHolder);
    int signMultipleTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder[] byteArrayHolderArray);
    // int GetPartialSeed(long unique_id, int seed_index, String public_key, StringBuilder out_seed); // 2018/09/21 E-Mail from Hank_Chiu
    byte[] GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key); // Ood Interface
    int GetPartialSeed(long unique_id, byte seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder);
    int GetPartialSeed_v2(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code_pubkey, ByteArrayHolder aes_key, ByteArrayHolder out_seed); // 11/12 ticket#46
    int CombineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3); // 2018/09/21 E-Mail from Hank_Chiu
    int CombineSeeds_v2(long unique_id, ByteArrayHolder enc_seed_1, ByteArrayHolder enc_seed_2, ByteArrayHolder enc_seed_3); // TEAMS: Social Key wiki
    int ShowVerificationCode(long unique_id, byte seed_index, String name, String phone_num, String phone_model); // 11/12 ticket#46
    int EnterVerificationCode(long unique_id, byte seed_index, ByteArrayHolder enc_verify_code); // TEAMS: Social Key wiki
    int GetTZIDHash(ByteArrayHolder ext_idhash); // 2018/10/04 E-Mail from David.SC_Chen
    int GetEncAddr(long unique_id, String path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature);
    int Unregister(String wallet_name, String sha256, long unique_id);
    int SetERC20BGColor(Color[] colors);
    int ClearTzData();
    int IsRooted();
    int ReadTzDataSet(int dataSet, ByteArrayHolder data);
    int WriteTzDataSet(int dataSet, ByteArrayHolder data, ByteArrayHolder signature);
    int SetKeyboardType(long unique_id, int nType);
    int ChangePIN_v2(long unique_id, int nType);

}
