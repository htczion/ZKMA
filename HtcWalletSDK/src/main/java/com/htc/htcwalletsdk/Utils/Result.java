package com.htc.htcwalletsdk.Utils;

/**
 * Created by shihshi-mac on 2018/9/25.
 * A wrapper class to response result
 */
public class Result{
    public boolean success;
    public int errCode;
    public String errMessage;


    @Override
    public String toString(){
        return "Result{" +
                "success = " + success +
                ", errCode = " + errCode +
                ", errMessage = " + errMessage +
                '}';
    }
}