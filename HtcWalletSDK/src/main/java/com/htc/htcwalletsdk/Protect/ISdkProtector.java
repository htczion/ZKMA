package com.htc.htcwalletsdk.Protect;

/**
 * Created by hawk_wei on 2018/10/25.
 */

public interface ISdkProtector {
    public enum NotifyType {
        ALARMEVENT,
        UIEVENT,
        APIEVENT,
        RESERVED1,
        RESERVED2,
        RESERVED3,
        RESERVED4,
        RESERVED5,
        RESERVED6,
        RESERVED7,
        RESERVED8,
        CUSTOMEVENT1,
        CUSTOMEVENT2,
        CUSTOMEVENT3,
        CUSTOMEVENT4,
    }

    int onErrorFeedback(int errorcode, int param1, String param2); // always callback to listener
    int onNotify(NotifyType event, int iData, String strData, byte[] byteData); // callback by demand
    // int onScreenProtector(int errorcode, int param1, String param2);
}
