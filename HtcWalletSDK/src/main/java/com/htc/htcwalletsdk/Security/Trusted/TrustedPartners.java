package com.htc.htcwalletsdk.Security.Trusted;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.text.TextUtils;
import android.util.Log;

import com.htc.htcwalletsdk.Utils.ZKMALog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

/**
 * Stores and validates signing certificate hashes for trusted partner applications.
 *
 * <p>Consumers can use this class to collaborate with other applications installed on an Android
 * system. They can set up one-way or two-way trust by including each others signing certificate
 * hashes in the {@link Set} passed to the constructor.
 */
public final class TrustedPartners {
    private static final String TAG = "TrustedPartners";
    private static final String HASH_ALGORITHM = "SHA256";

    private final Set<String> trustedPartnerCertificateHashes;
    private final PackageManager packageManager;

    public TrustedPartners(Context context, Set<String> trustedPartnerCertificateHashes) {
        packageManager = context.getPackageManager();
        this.trustedPartnerCertificateHashes = trustedPartnerCertificateHashes;
    }

    /**
     * Looks up the package signing certificate for the given package and checks to see if it is in
     * the {@link Set} of trusted signing certificate hashes passed to the constructor.
     *
     * @param packageName The name of the package to check (e.g. com.example.foo)
     * @return true if the package signing certificate matches a trusted certificate, else false
     */
    public boolean isTrustedApplication(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            if (ZKMALog.isLoggable(TAG, Log.WARN)) {
                ZKMALog.w(TAG, "null or empty package name; do not trust");
            }
            return false;
        }
        PackageInfo info;
        try {
            info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (NameNotFoundException e) {
            if (ZKMALog.isLoggable(TAG, Log.WARN)) {
                ZKMALog.w(TAG, "package not found (" + packageName + "); do not trust");
            }
            return false;
        }

        if (info.signatures == null || info.signatures.length != 1) {
            // Applications can trick naive code into trusting them by including multiple signing
            // certificates. We reject any packages that have anything other than one signing certificate.
            if (ZKMALog.isLoggable(TAG, Log.WARN)) {
                ZKMALog.w(TAG, info.signatures.length
                        + " signatures found for package (" + packageName + "); do not trust");
            }
            return false;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(info.signatures[0].toByteArray());
            String certHash = bytesToHex(digest.digest());
            return trustedPartnerCertificateHashes.contains(certHash);
        } catch (NoSuchAlgorithmException e) {
            if (ZKMALog.isLoggable(TAG, Log.ERROR)) {
                ZKMALog.e(TAG, "unable to compute hash using " + HASH_ALGORITHM + "; do not trust");
            }
            return false;
        }
    }

    /**
     * Looks up the content provider for the given authority and checks to see if the application it
     * belongs to has a trusted signing certificate.
     *
     * @param authority The authority of the content provider
     * @return true if the authority is part of a trusted application, else false
     */
    public boolean isTrustedAuthority(String authority) {
        ProviderInfo info = packageManager.resolveContentProvider(authority, 0 /*flags*/);
        if (info == null) {
            if (ZKMALog.isLoggable(TAG, Log.WARN)) {
                ZKMALog.w(TAG, "no provider found for " + authority + "; do not trust");
            }
            return false;
        }
        return isTrustedApplication(info.packageName);
    }

    //convert byte to hex
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}