package com.htc.htcwalletsdk.Security.Trusted;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.htc.htcwalletsdk.CONSTANT;
import com.htc.htcwalletsdk.Export.RESULT;
import com.htc.htcwalletsdk.Utils.ZKMALog;

/**
 * check ACCESS_ZION permission by package name
 *
 */
public final class AppPermissionChecker {
    private static final String TAG = "AppPermissionChecker";
    private final PackageManager packageManager;

    public AppPermissionChecker(Context context) {
        this.packageManager = context.getPackageManager();
    }

    public int isPermissionGranted(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            if (ZKMALog.isLoggable(TAG, 5)) {
                ZKMALog.w(TAG, "null or empty package name; do not trust");
            }

            return RESULT.E_ZKMS_PACKAGE_NOT_FOUND;
        } else {
            PackageInfo info;
            try {
                info = this.packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            } catch (PackageManager.NameNotFoundException var6) {
                if (ZKMALog.isLoggable(TAG, 5)) {
                    ZKMALog.w(TAG, "package not found (" + packageName + "); do not trust");
                }

                return RESULT.E_ZKMS_PACKAGE_NOT_FOUND;
            }

            if (info.requestedPermissions != null && info.requestedPermissions.length > 1 ) {
                    for( int i=0; i<info.requestedPermissions.length; i++) {
                        if(info.requestedPermissions[i].equals(CONSTANT.PERMISSION_ACCESS_ZION)) {
                            ZKMALog.d(TAG, "Found " + info.requestedPermissions[i] + " permission !!!");

                            // Loop each <uses-permission> tag to retrieve the permission flag
                                final String requestedPerm = info.requestedPermissions[i];
                                // Retrieve the protection level for each requested permission
                                int protLevel;
                                try {
                                    protLevel = this.packageManager.getPermissionInfo(requestedPerm, 0).protectionLevel;
                                } catch (PackageManager.NameNotFoundException e) {
                                    Log.w(TAG, "Unknown permission: " + requestedPerm, e);
                                    continue;
                                }
                                final boolean system = protLevel == android.content.pm.PermissionInfo.PROTECTION_SIGNATURE;
                                final boolean dangerous = protLevel == android.content.pm.PermissionInfo.PROTECTION_DANGEROUS;
                                final boolean granted = (info.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0;
                                if(granted == true)
                                {
                                    ZKMALog.i(TAG, "Permission " + CONSTANT.PERMISSION_ACCESS_ZION + " has been GRANTED !!!");
                                    return RESULT.SUCCESS;
                                }
                        }
                        // else
                        //     ZKMALog.w(TAG, "Found " + info.requestedPermissions[i] + " permission");
                    }
                    return RESULT.E_ZKMS_PERMISSION_NOT_GRANTED;
            } else {
                if (ZKMALog.isLoggable(TAG, 5)) {
                    ZKMALog.w(TAG, "Permission " + CONSTANT.PERMISSION_ACCESS_ZION + " not found in package (" + packageName + "); do not trust");
                }

                return RESULT.E_ZKMS_PERMISSION_NOT_GRANTED;
            }
        }
    }

}
