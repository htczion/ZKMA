package com.htc.wallettzservice;

import android.os.Parcel;
import android.os.Parcelable;

public class EncAddr implements Parcelable {
    private byte[] mExtAddr;
    private byte[] mExtEncaddr;
    private byte[] mExtEncaddrSignature;

    public byte[] getExtAddr() {
        return mExtAddr;
    }

    public void setExtAddr(byte[] extAddr) {
        mExtAddr = extAddr;
    }

    public byte[] getExtEncaddr() {
        return mExtEncaddr;
    }

    public void setExtEncaddr(byte[] extEncaddr) {
        mExtEncaddr = extEncaddr;
    }

    public byte[] getExtEncaddrSignature() {
        return mExtEncaddrSignature;
    }

    public void setExtEncaddrSignature(byte[] extEncaddrSignature) {
        mExtEncaddrSignature = extEncaddrSignature;
    }


    public EncAddr() {
    }

    private EncAddr(Parcel in) {
        mExtAddr = in.createByteArray();
        mExtEncaddr = in.createByteArray();
        mExtEncaddrSignature = in.createByteArray();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeByteArray(mExtAddr);
        out.writeByteArray(mExtEncaddr);
        out.writeByteArray(mExtEncaddrSignature);

    }

    public static final Parcelable.Creator<EncAddr> CREATOR =
            new Parcelable.Creator<EncAddr>() {
                public EncAddr createFromParcel(Parcel in) {
                    return new EncAddr(in);
                }

                public EncAddr[] newArray(int size) {
                    return new EncAddr[size];
                }
            };
}

