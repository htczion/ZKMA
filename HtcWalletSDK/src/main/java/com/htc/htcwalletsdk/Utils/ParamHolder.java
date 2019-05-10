package com.htc.htcwalletsdk.Utils;

import android.os.Bundle;

/**
 * Created by shihshi-mac on 2018/9/26.
 */
public class ParamHolder
{
    Bundle mIn = new Bundle();
    Bundle mOut = new Bundle();

    public void setIn(Bundle in)
    {
        mIn = in;
    }

    public Bundle getIn()
    {
        return mIn;
    }

    public void setOut(Bundle out)
    {
        mOut = out;
    }

    public Bundle getOut()
    {
        return mOut;
    }
}
