package studio.archangel.toolkitv2.interfaces;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import studio.archangel.toolkitv2.util.Logger;

/**
 * 网络请求回调接口
 */
public abstract class NetCallBack extends RequestCallBack<String> {
    /**
     * json数据格式错误
     */
    public static final int data_error = -3;

    //    public NetCallBack() {
//        this.rate = 10;
//    }
    @Override
    public void onSuccess(ResponseInfo<String> responseInfo, JSONObject request_param) {
        JSONObject jo;
        try {
            jo = new JSONObject(responseInfo.result);
            printRequestParameters(request_param);
            onSuccess(jo.optInt("ret"), jo.optString("body"), request_param);
        } catch (JSONException e) {
            Logger.out(responseInfo.result);
            e.printStackTrace();
            onFailure(new HttpException(), "数据格式错误", request_param);
        }

    }

    void printRequestParameters(JSONObject request_param) {
        try {
            Logger.out(request_param.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        JSONArray names = request_param.names();
//        if (names == null) {
//            return;
//        }
//        for (int i = 0; i < names.length(); i++) {
//            String name = names.optString(i);
//            Logger.out(name);
//        }
    }
//    public abstract void onFailure(ResponseInfo<String> responseInfo, String msg);

    @Override
    public void onFailure(HttpException error, String msg, JSONObject request_param) {

    }

    public abstract void onSuccess(int status, String json, JSONObject request_param);

}
