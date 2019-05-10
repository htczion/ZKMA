package com.htc.htcwalletsdk.Utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonParser {
    static final String TAG = "JsonParser";

    public static String[] JsonStrToStrArray(String jsonStr) {
        ZKMALog.d(TAG, "JsonStrToStrArray +++");
        jsonStr = "{key:" + jsonStr + "}";

        try {
            JSONObject jsonObject = (JSONObject) new JSONTokener(jsonStr).nextValue();
            JSONArray jsonArray = jsonObject.getJSONArray("key");

            String[] result = new String[jsonArray.length()];
            for (int i = 0; i < result.length; i++) {
                result[i] = jsonArray.getString(i);
            }
            ZKMALog.d(TAG, "JsonStrToStrArray ---");
            return result;
        } catch (JSONException exception) {
            return new String[0];
        }
    }

    public static Map<String, String> JsonStrToMap(String jsonStr) throws JSONException {
        ZKMALog.d(TAG, "JsonStrToMap +++");
        Map<String, String> jsonMap = new HashMap<String, String>();

        if (TextUtils.isEmpty(jsonStr))
            return jsonMap;

        JSONObject jsonObject = (JSONObject) new JSONTokener(jsonStr).nextValue();
        Iterator<?> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            jsonMap.put(key, jsonObject.getString(key));
        }
        ZKMALog.d(TAG, "JsonStrToMap ---");
        return jsonMap;
    }

    Map<String, String> mJsonMap;
    Map<String, String> mJsonMap2;
    String mMessage;
    String mVersion;
    String mData;
    String[] mMessageArrays;

    public Map<String, String>  ParserJsonString(String jsonString) {
        try {
            return JsonParser.JsonStrToMap(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public class JsonDataKey_signMessage {
        public static final String path = "path";
        public static final String message = "message";
        public static final String message_version = "version";
        public static final String message_data = "data";
    }

    public int ParserJsonEthSignMessage(String jsonStringFromFile) {
        mJsonMap = ParserJsonString(jsonStringFromFile);
        // ZKMALog.d(TAG, "JsonMap=" + mJsonMap.entrySet());
        // ZKMALog.d(TAG, "path=" + mJsonMap.get(JsonDataKey_signMessage.path)); // all value is in our Json are all HEX value, so 14=0x14=20
        mMessage = mJsonMap.get(JsonDataKey_signMessage.message);
        // ZKMALog.d(TAG, "mMessage=" + mMessage);

        mJsonMap2 = ParserJsonString(mMessage);
        // ZKMALog.d(TAG, "mJsonMap2=" + mJsonMap2.entrySet());
        mVersion = mJsonMap2.get(JsonDataKey_signMessage.message_version);
        // ZKMALog.d(TAG, "message_version=" + mVersion);
        mData =mJsonMap2.get(JsonDataKey_signMessage.message_data);
        // ZKMALog.d(TAG, "message_data=" + mData);
        String strMessage = hexToString(mData);
        // ZKMALog.d(TAG, "strMessage=" + strMessage);
        return strMessage.length();
    }


    public static String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < hex.length() - 1; count += 2) {
            String output = hex.substring(count, (count + 2));    //grab the hex in pairs
            int decimal = Integer.parseInt(output, 16);    //convert hex to decimal
            sb.append((char) decimal);    //convert the decimal to character
        }
        return sb.toString();
    }

}
