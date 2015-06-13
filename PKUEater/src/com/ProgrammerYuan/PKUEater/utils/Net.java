package com.ProgrammerYuan.PKUEater.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import org.json.JSONObject;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.text.Notifier;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

//import com.hisun.phone.core.voice.CCPCall;

public class Net {
    public static String cookie_name = "X-CSRFtoken";
    public static String cookie_value = null;

    public static String root = "http://121.42.57.49:2611";
    public static String url_get_canteen_recommendation;
    public static HttpUtils client;
    static ConnectivityManager cm;

    public static void init() {
        url_get_canteen_recommendation = root + "/halls";
    }

    /**
     * 统一的错误提示信息
     */
    public static void handleErrorCode(Context c, int status, String ret, JSONObject request_param) {
        switch(status){

        }
    }

    public static void handleErrorCode(String s, Context c) {
        Notifier.showNormalMsg(c, s);
    }

    public static HttpUtils getClient(Context c) {

        if (client == null) {
            client = new HttpUtils();
        }

        if (getConnectionState(c) == 1) {
            client.configTimeout(10000);
        } else if (getConnectionState(c) == 0) {
            client.configTimeout(30000);
        } else if (getConnectionState(c) == -1) {
            // 没网
        }
        return client;
    }

    /**
     * 获取网络环境状态
     *
     * @return 1：Wifi，0：2G网 -1：未知
     */
    public static int getConnectionState(Context c) {
        if (cm == null) {
            cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        NetworkInfo activeNetInfo = null;
        try {

            activeNetInfo = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return 1;
            } else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static RequestParams getParam() {
        RequestParams p = new RequestParams("utf-8");
        return p;
    }

    public static RequestParams getParam(HttpMethod method, String key, String value) {
        Logger.out("request parameters:" + key + "->" + value);
        RequestParams p = new RequestParams("utf-8");
        if (method == HttpMethod.GET) {
            p.addQueryStringParameter(key, value);
        } else if (method == HttpMethod.POST) {
            p.addBodyParameter(key, value);
        }

        return p;
    }

    public static RequestParams getParam(HttpMethod method, Map<String, String> map) {
        Logger.out("request parameters:" + map);
        RequestParams p = new RequestParams("utf-8");
        for (Entry<String, String> en : map.entrySet()) {
            if (method == HttpMethod.GET) {
                p.addQueryStringParameter(en.getKey(), en.getValue());
            } else if (method == HttpMethod.POST) {
                String value = en.getValue();
                if (value != null && value.startsWith("[!file]")) {
                    p.addBodyParameter(en.getKey(), new File(value.replace("[!file]", "")));
                } else {
                    p.addBodyParameter(en.getKey(), value);
                }

            }

        }

        return p;
    }

}
