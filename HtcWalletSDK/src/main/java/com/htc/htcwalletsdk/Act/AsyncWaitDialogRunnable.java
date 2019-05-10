package com.htc.htcwalletsdk.Act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.htc.htcwalletsdk.R;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * Custom any dialog for prompt
 * Created by hawk_wei on 2018/11/15.
 */

public class AsyncWaitDialogRunnable implements Runnable {
    private static final String TAG = "AsyncWaitDialogRunnable";
    private Context mContext;
    private int mErrorCode;

    public AsyncWaitDialogRunnable(Context context, int errorcode) {
        mContext = context;
        mErrorCode = errorcode;
    }

    public void run() {
        doShowGeneralFailure(mContext, mErrorCode);
    }

    private void doShowGeneralFailure(final Context context, final int errorCode)
    {
        ZKMALog.i(TAG, "doShowGeneralFailure +++");
        final Object lock = new Object();
        Handler mHandler = new Handler(Looper.getMainLooper());
        mHandler.post(new Runnable() {
            @SuppressLint("StringFormatMatches")
            @Override
            public void run() {
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                //TODO : General failure need to use string.
                //adb.setTitle(R.string.aar_name);
                adb.setMessage(String.format(context.getString(R.string.text_generic_error_description), errorCode));
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        synchronized(lock) {
                            ZKMALog.i(TAG, "onPositiveButton() notifyAll**");
                            lock.notifyAll();
                        }
                    }
                });

                adb.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        synchronized(lock) {
                            ZKMALog.i(TAG, "onCancelButton() notifyAll**");
                            lock.notifyAll();
                        }
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = adb.show();
                /*
                TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
                messageView.setGravity(Gravity.CENTER);
                */
            }
        });
        ZKMALog.i(TAG, "before lock");
        synchronized (lock) {
            try {
                ZKMALog.i(TAG, "wait ....");
                lock.wait();
            } catch (final InterruptedException e) {
                //do nothing
            }
        }
        ZKMALog.i(TAG, "after lock");
        ZKMALog.i(TAG, "doShowGeneralFailure ---");
    }

}