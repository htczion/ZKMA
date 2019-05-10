package com.htc.htcwalletsdk.Native;

/**
 * An Interface for JNI callback JAVA functions
 *
 * @version 0.0.4
 *
 * @since   2018/09/11
 *
 * @comment
 *   version control, modify since and version every SDK release
 *   ID.__.__  : major version, arch changed or target release, ex: architecture changed, or DVT to PVT ...
 *   __.ID.__  : minor version, interface changed, ex: exported API, targetSdk, java interface ...
 *   __.__.ID  : sub-minor version, self-changed,  ex: internal method, field, .so changed ...
 *   __.__.__a : alpha
 *   __.__.__b : beta
 *
 * You should increase minor version if any modification made in this file.
 *
 * Created by hawk_wei
 */
public interface IJniCallbackListener {

    /**
     * A test function to verify callback
     * @param fromNative
     */
    void CallbackStatus(String fromNative);

    /**
     * UICreatePin (Page.3 1.1~1.2, Page.7 3.1~3.2)
     * @param flag show error message when non-zero (Page.3 1.2.A)
     * @param pin input: (6bytes), the buffer to get users' pin
     *            output: (6bytes), users' pin
     * @return 0:success, < 0:other errors
     */
    int UICreatePin(int flag, String pin);

    /**
     * UIShowAndVerifyWords (Page.4~5 2.1~2.3)
     * @param words String words (delimited by space)
     *              ex: "borrow visit lizard primary defy marine item about nurse crew guess abuse"
     * @param words_size words size
     * @return 0:success, < 0:other errors
     */
    int UIShowAndVerifyWords(String words, int words_size);

    /**
     * UIRestoreWords (Page.6 1.1)
     * @param words input:
     *              String words
     *              output:
     *              String words (delimited by space)
     * @param words_size words buffer size
     * @return 0:success, < 0:other errors
     */

    int UIRestoreWords(String words, int words_size);

    /**
     * UIVerifyPin
     * @param pin String pin (6bytes), users' pin
     * @return 0:success, < 0:other errors
     */
    int UIVerifyPin(String pin);

    /**
     * UIShowWords
     * @param words String words (delimited by space)
     * @param words_size words buffer size
     * @return 0:success, < 0:other errors
     */
    int UIShowWords(String words, int words_size);

    /**
     * UIResetConfirm
     * @param reset input:
     *              int *reset (1bytes)
     *              output:
     *              int *reset (1bytes), 1 to reset
     * @return 0:success, < 0:other errors
     */
    int UIResetConfirm(int reset);

    /**
     * UITransaction
     * @param coin_type ex: https://github.com/satoshilabs/slips/blob/master/slip-0044.md
     *                  0 BTC
     *                  2 LTC
     *                  60 ETH
     * @param to SEND TO
     * @param fee FEE
     * @param fee_usd fee_usd
     * @param amount AMOUNT
     * @param amount_usd amount_usd
     * @param pin users' pin
     * @return 0:success, < 0:other errors
     */
    int UITransaction(long coin_type, String to, String fee, String fee_usd, String amount, String amount_usd, String pin);
}

