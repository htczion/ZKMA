package com.htc.htcwalletsdk.Export;

import android.content.Context;
import android.graphics.Color;

import com.htc.htcwalletsdk.Native.Type.ByteArrayHolder;
import com.htc.htcwalletsdk.Security.Key.PublicKeyHolder;

/**
 * APIs export for App by SDK
 * Created by hawk_wei on 2018/9/28.
 */

public interface ISdkExportKeyAgentApis {

    String  getModuleVersion();
    String  getApiVersion();
    int     init(Context context);
    long    register(String wallet_name, String sha256);
    // int     register(String wallet_name, String sha256, LONG_PTR unique_id);

    int     changePIN(long unique_id); // 2018/10/09 for SPEC 18.2.A,B
    int     confirmPIN(long unique_id, int resId);// 2018/10/09 for SPEC 18.2.C

    int     isSeedExists(long unique_id);
    int     createSeed(long unique_id);
    int     clearSeed(long unique_id);
    int     showSeed(long unique_id);
    int     restoreSeed(long unique_id);
    byte[]  getPartialSeed(long unique_id, int seed_index, ByteArrayHolder public_key); // 2018/10/04 E-mail by David.SC_Chen
    int     getPartialSeed(long unique_id, int seed_index, ByteArrayHolder public_key, ByteArrayHolder byteArrayHolder);
    int     getPartialSeed_v2(long unique_id, int seed_index, ByteArrayHolder enc_verify_code_pubkey, ByteArrayHolder aes_key, ByteArrayHolder out_seed); // 11/12 Ticket#46

    int     combineSeeds(long unique_id, ByteArrayHolder seed_1, ByteArrayHolder seed_2, ByteArrayHolder seed_3); // 2018/09/21 E-mail by Hank_Chiu

    // PublicKeyHolder getPublicKey(long unique_id, int coinType); // remove 10/15 E-MAIL from Jeff.CF
    PublicKeyHolder getSendPublicKey(long unique_id, int coinType); // add 10/15 E-MAIL from Jeff.CF
    PublicKeyHolder getSendPublicKey(long unique_id, int coinType, int index);
    PublicKeyHolder getReceivePublicKey(long unique_id, int coinType); // add 10/15 E-MAIL from Jeff.CF
    PublicKeyHolder getReceivePublicKey(long unique_id, int coinType, int index);

    PublicKeyHolder getAccountExtPublicKey(long unique_id, int bips, int coinType, int account);
    PublicKeyHolder getBipExtPublicKey(long unique_id, int bips, int coinType, int account, int change, int index);

    int     signTransaction(long unique_id, int oin_type, float rates, String strJson, ByteArrayHolder byteArrayHolder);
    int     signMultipleTransaction(long unique_id, int coin_type, float rates, String strJson , ByteArrayHolder[] byteArrayHolderArray);
    int     signMessage(long unique_id, int oin_type, String strJson, ByteArrayHolder byteArrayHolder);

    int     getEncAddr(long unique_id, String path, ByteArrayHolder ext_addr, ByteArrayHolder ext_encaddr, ByteArrayHolder ext_encaddr_signature); // 10/16 E-mail by David.SC_Chen
    int     getTZIDHash(ByteArrayHolder ext_idhash);
    int     showVerificationCode(long unique_id, int seed_index, String name, String phone_num, String phone_model);
    int     enterVerificationCode(long unique_id, int seed_index, ByteArrayHolder enc_verify_code);
    int     setERC20BGColor(Color[] colors);
    int     setEnvironment(int env);
    int     deinit();
    int     clearTzData();
    int     getLastError();
}
