package com.jiuan.oa.android.library.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class OAHttpResponseHandler extends JsonHttpResponseHandler {

    // 成功
    private static final int SUCCESS = 1;

    // 失败
    private static final int FAILURE = 2;

    // 错误
    private static final int ERROR = 3;

    // 成功
    private static final String SUCCESS_100 = "100";

    // 服务器内部错误
    private static final String ERROR_500 = "500";

    @Override
    public void onStart() {
        onOAStart();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        OAResponse oaResponse = gson.fromJson(response.toString(), OAResponse.class);

        switch (oaResponse.getResult()) {
            case SUCCESS:
                if (oaResponse.getResultMessage().equals(SUCCESS_100)) {
                    onOASuccess(oaResponse.getReturnValue());
                }
                break;
            case FAILURE:
                onOAFailure(oaResponse.getResultMessage());
                onOAFailure(oaResponse.getResultMessage(), oaResponse.getReturnValue());
                break;
            case ERROR:
                if (oaResponse.getResultMessage().equals(ERROR_500)) {
                    onOAError("服务器内部错误");
                }
                break;
        }
        onOAFinish();
    }

    /**
     * 开始请求网络连接会调用此方法
     */
    public void onOAStart() {

    }

    public void onOASuccess(String value) {
        if (null == value) {
            return;
        }
        Log.d("onOASuccess", value);
    }

    public void onOAFailure(String msg) {
        if (null == msg) {
            return;
        }
        Log.d("onOAFailure", msg);
    }

    /**
     * 当返回错误时,需要对相关错误的内容进行处理时,会调用此方法
     */
    public void onOAFailure(String msg, String value) {

    }

    public void onOAError(String value) {
        if (null == value) {
            return;
        }
        Log.d("onOAError", value);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        onOAExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onOAExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onOAExceptionFinish();
    }

    /**
     * 正常结束会调用此方法
     */
    public void onOAFinish() {

    }

    /**
     * 异常结束会调用此方法
     * 注意:如果重写onFailure相关方法时,请在方法结束处调用此方法.
     */
    public void onOAExceptionFinish() {

    }

    /**
     * 把Json格式的字符串转换成实体类型的方法.
     * 注意:实体类中的变量需要用@SerializedName注释
     */
    public final <T> T get(String value, Class<T> classOfT) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            return gson.fromJson(value, classOfT);
        } catch (JsonSyntaxException exception) {
            Log.d("exception", exception.getMessage());
        }
        return null;
    }
}
