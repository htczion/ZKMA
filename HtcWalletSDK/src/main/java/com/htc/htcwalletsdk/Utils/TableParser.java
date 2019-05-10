package com.htc.htcwalletsdk.Utils;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Map;

public class TableParser {

    static final String TAG = "TableParser";
    static public final int TABLE_ERC20_ERC721 = 20721;

    Map<String, String> mJsonMap, mJsonMap2;


    String mTableSignature;
    String mTableVersion;
    String mValue;
    String mTableArray;


    public class Key {
        public static final String table_data = "data";
        public static final String table_signature = "signature";
        public static final String table_data_version = "version";
        public static final String table_data_array = "array";
    }

    public Map<String, String>  ParserJsonString(String jsonString) {
        try {
            return JsonParser.JsonStrToMap(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object parser(int dataSet, String key, String jsonStringFromFile) {

        byte[] byteData = null;
        String strDecodedByteData = null;

        if(dataSet == TABLE_ERC20_ERC721) {
            mJsonMap = ParserJsonString(jsonStringFromFile);
            mValue =mJsonMap.get(key);
            ZKMALog.i(TAG, "key = " + key);
            if(mValue != null && mValue.length() != 0) {
                ZKMALog.i(TAG, "mValue.length() = " + mValue.length());
            }

            if(key.equals("signature")) {
                byteData = Base64.getDecoder().decode(mValue);
                ZKMALog.d(TAG, "BASE64 decoded Signature = " + new String(byteData, Charset.forName("UTF-8")));
            } else if (key.equals("data")) {
                byteData = Base64.getDecoder().decode(mValue);
                ZKMALog.d(TAG, "BASE64 decoded data = " + new String(byteData, Charset.forName("UTF-8")));
            } else if (key.equals("version")) {
                mValue = mJsonMap.get(Key.table_data);
                byteData = Base64.getDecoder().decode(mValue);
                strDecodedByteData = new String(byteData, Charset.forName("UTF-8"));
                mJsonMap2 = ParserJsonString(strDecodedByteData);
                mTableVersion = mJsonMap2.get(key);
                return mTableVersion;
            } else if (key.equals("array")) {
                mValue = mJsonMap.get(Key.table_data);
                byteData = Base64.getDecoder().decode(mValue);
                strDecodedByteData = new String(byteData, Charset.forName("UTF-8"));
                mJsonMap2 = ParserJsonString(strDecodedByteData);
                mTableArray = mJsonMap2.get(key);
                byteData = mTableArray.getBytes(Charset.forName("UTF-8"));
            } else {
                ZKMALog.e(TAG, "TableParser can't parse it!");
            }
        }
        ZKMALog.d(TAG, "key = " + key + " \nmValue=" + mValue +"  \nbyteData="+ byteData);
        return byteData;
    }


}
