package com.htc.htcwalletsdk.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class VolleyUtils {
    static public final String TAG = "VolleyUtils";
    static public final String urlContractServer = "https://api-htcexodus.htctouch.com/";
    static public final String eth_erc_table = "eth/ercjson/list/";
    /**
     *    Naming : contractTable
     *    Method : http GET
     *
     *    ex : https://www.htcsense.exodus/contractTable?dataSet=20&version=0
     *
     *   if http request got 200 ok, but JSON body is null, it means no need to update.
     */
    static public void downloadData(Context context, JsonObjectRequest jsonObjectRequest) {
        ZKMALog.d(TAG,"VolleyUtils.downloadData() +++");
        Volley.newRequestQueue(context).add(jsonObjectRequest);
        ZKMALog.d(TAG,"VolleyUtils.downloadData() ---");
    }

}
