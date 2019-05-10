package com.htc.htcwalletsdk.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.htc.htcwalletsdk.Export.RESULT;

/**
 * Created by shihshi-mac on 2018/9/25.
 * UI Activity could use this class to notify working thread that the UI operation has done.
 */
public abstract class ResultCallback extends Handler {

    public ResultCallback(Looper looper){
        super(looper);
    }

    private final static int MSG_SUCCESS = 100;
    private final static int MSG_FAILED = 200;

    Bundle mOutput = new Bundle();

    public void makeSuccess(){
        final Message message = Message.obtain(null, ResultCallback.MSG_SUCCESS, 0, 0);
        final Bundle bundle = new Bundle();
        message.setData(bundle);
        sendMessage(message);
    }

    public void makeFailure(int errorCode){
        final Message message = Message.obtain(null, ResultCallback.MSG_FAILED, errorCode, 0);
        final Bundle bundle = new Bundle();
        message.setData(bundle);
        sendMessage(message);
    }

    public void makeFailure(String errorMessage){
        final Message message = Message.obtain(null, ResultCallback.MSG_FAILED, RESULT.E_SDK_CUSTOM_ERRORMSG, 0);
        final Bundle bundle = new Bundle();
        bundle.putString("errorMessage",errorMessage);
        message.setData(bundle);
        sendMessage(message);
    }

    public Bundle getOutput()
    {
        return mOutput;
    }

    @Override
    public final void handleMessage(Message msg){
        switch(msg.what) {
            case MSG_SUCCESS:
                onSuccess();
                break;
            case MSG_FAILED:
                if(msg.arg1 == RESULT.E_SDK_CUSTOM_ERRORMSG) {
                    Bundle bundle = msg.getData();
                    String errorMessage = bundle.getString("errorMessage");
                    onFailed(errorMessage);
                }
                else
                    onFailed(msg.arg1);
                break;
        }
    }

    protected abstract void onSuccess();

    protected abstract void onFailed(int errorCode);

    protected abstract void onFailed(String errorMessage);
}

