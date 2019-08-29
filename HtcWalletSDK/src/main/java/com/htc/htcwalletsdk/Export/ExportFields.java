package com.htc.htcwalletsdk.Export;

/**
 * Exported Fields for any SDK user
 */

public class ExportFields {
    private final String TAG = "ExportFields";

    /**
     * true: hw security, false: sw security
     */
    public static boolean bTZ_support = false;

    public static boolean bShowErrorDialog = true;

    /**
     * A record for SDK version history
     * @return Version string = HW wallet:     0.SERVICE_VERSION.TZAPI_VERSION
     *                          SW wallet:     1.JNILIB_VERSION .SWLIB_VERSION
     *                          For example:  "0.12AC.012ABCDE"
     */
    String[][][] History = {
            //   SDK  ,  SERVICE ,     TZAPI
            {{"1.0.1"},{"0x0001"},{"0x01000000"}},
            {{"1.1.0"},{"0x0003"},{"0x01010001"}}, // support more APIs
            {{"1.2.0"},{"0x0004"},{"0x01010005"}}, // CL#1059723, enterVerificationCode with seed_index param
            {{"1.2.1"},{"0x0004"},{"0x01010006"}}, // adjust TZ basic error code from 0 to -300
            {{"1.2.3"},{"0x0005"},{"0x0101000e"}}, // TZ CL#1062477
            {{"1.2.5"},{"0x0005"},{"0x01010011"}}, // support signMessage for ETH
            {{"1.2.7"},{"0x0005"},{"0x01010017"}}, // fix secure UI issue
            {{"2.0.1"},{"0x0005"},{"0x01010031"}}, // support signMultipleTransaction
            {{"2.0.2"},{"0x0005"},{"0x01010038"}}, // QSU4 update for social key
            {{"2.2.2"},{"0x0005"},{"0x01010038"}}, // QSU4 update for social key
            {{"3.4.0"},{"0x0005"},{"0x0101004c"}}, // QSU6 update for setKeyboardType
    };

    /**
     * current SDK based on this SERVICE version(HEX)
     */
    public static int minServiceVer = 0x0005;

    /**
     * current SDK based on this TZAPI SDK version(HEX)
     */
    public static int minTzApiVer = 0x0101004c;

}
