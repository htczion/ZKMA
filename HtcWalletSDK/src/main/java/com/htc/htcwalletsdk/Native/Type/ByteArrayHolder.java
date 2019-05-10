package com.htc.htcwalletsdk.Native.Type;

import android.util.Log;

import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 *
 * Created by hawk_Wei on 2018/10/15.
 */

public class ByteArrayHolder {
    private static final int   DEFAULT_ARRAY_SIZE = 2*1024; // default=2KB
    private static final int   MAX_ARRAY_SIZE = 16*1024; // max=16KB
    public byte[] byteArray;
    public int   receivedLength;

    public ByteArrayHolder(){
        byteArray = new byte[DEFAULT_ARRAY_SIZE];
        receivedLength = 0;
    }

    public void extendSize(int newSize){
        if(newSize > 0 ) {
            if (newSize > MAX_ARRAY_SIZE ) // max=16KB
            {
                ZKMALog.w("ByteArrayHolder","newSize="+newSize+" > "+MAX_ARRAY_SIZE+" , SDK can only support max 16KB now.");
                newSize = MAX_ARRAY_SIZE;
            }
            byteArray = new byte[newSize];
        }
        else {
            ZKMALog.e("ByteArrayHolder","newSize="+newSize+" less than 0, not support!");
            throw new IllegalArgumentException();
        }
        receivedLength = newSize;
    }

}
