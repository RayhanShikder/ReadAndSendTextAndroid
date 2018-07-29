package com.example.rayhanshikder.readandsendtexts;
import android.util.Log;

import com.loopj.android.http.*;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.StringEntity;

public class HttpUtils {
    private static final String BASE_URL = "https://cadetsisland.com/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, JSONObject postparams, AsyncHttpResponseHandler responseHandler) {
        Log.e("params",postparams.toString());
        try {
            StringEntity entity = new StringEntity(postparams.toString());
            client.post(MainActivity.getAppContext(), getAbsoluteUrl(url), entity, "application/json",responseHandler);
        }
        catch (Exception e)
        {
            Log.e("exception in post",e.toString());
        }
    }

    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
