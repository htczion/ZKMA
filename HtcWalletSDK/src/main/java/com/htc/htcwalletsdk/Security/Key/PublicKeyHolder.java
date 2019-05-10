package com.htc.htcwalletsdk.Security.Key;

/**
 * Created by hawk_wei on 2018/9/10.
 */

public class PublicKeyHolder extends KeyBase {

    public String getKey() {
        return this.mPublicKey;
    }

    public String getKeyPath() {
        return this.mKeyPath;
    }


    public void setKey(PublicKeyHolder key) {
        this.mPublicKey = key.mPublicKey;
        this.mKeyPath   = key.mKeyPath;
    }


    public void setKey(String publicKey) {
        this.mPublicKey = publicKey;
    }


    public void setKeyPath(String keyPath) {
        this.mKeyPath   = keyPath;
    }
}
