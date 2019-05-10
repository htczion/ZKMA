# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump class_files.txt
-printseeds seeds.txt
-printusage unused.txt
-printmapping mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep class com.htc.htcwalletsdk.Export.**{*;}
-keep class com.htc.htcwalletsdk.Security.Key.PublicKeyHolder{*;}
-keep class com.htc.htcwalletsdk.Utils.GenericUtils{*;}
-keep class com.htc.htcwalletsdk.Native.Type.ByteArrayHolder**{*;}
-keep interface com.htc.htcwalletsdk.Native.IJniCallbackListener**{*;}
-keep interface com.htc.htcwalletsdk.Wallet.Coins.CoinType{*;}
-keep class com.htc.htcwalletsdk.Security.Core.KeySecurityHW{*;}
-keep class com.htc.htcwalletsdk.CONSTANT**{*;}
-keep class com.htc.htcwalletsdk.Security.Core.KeyAgent{*;}
-keep class com.htc.htcwalletsdk.Security.Core.KeySecuritySW{*;}
-keep class com.htc.htcwalletsdk.Utils.LayoutCustodian{*;}
-keep class com.htc.htcwalletsdk.Utils.ParamHolder{*;}
-keep class com.htc.htcwalletsdk.Utils.JsonParser{*;}
-keep class com.htc.htcwalletsdk.Security.Trusted.TrustedPartners{*;}
-keep class com.htc.htcwalletsdk.Native.JniSdkCallbackListenerImpl{*;}
-keep class com.htc.wallettzservice.EncAddr{*;}
-keepattributes Exceptions