package com.jiuan.oa.android.library.http.store;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class StoreSearchHttpResponseHandler extends JsonHttpResponseHandler {

    // 成功
    private static final int SUCCESS = 1;

    // 失败
    private static final int FAILURE = 2;

    // 错误
    private static final int ERROR = 3;

    // 成功码
    private static final String SUCCESS_1000 = "1000";

    // 请求缺少必须参数
    private static final String FAILURE_2001 = "2001";

    // Sc或Sv未授权
    private static final String FAILURE_2002 = "2002";

    // 服务器内部错误
    private static final String ERROR_5000 = "5000";

    @Override
    public void onStart() {
        onStoreStart();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("StoreonSuccess","onSuccess");
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        StoreSearchResponse storeResponse = gson.fromJson(response.toString(), StoreSearchResponse.class);

        switch (storeResponse.getResult()) {
            case SUCCESS:
                if (storeResponse.getResultMessage().equals(SUCCESS_1000)) {
                    onStoreSearchSuccess(storeResponse.getReturnValue());
                }
                break;
            case FAILURE:
                if (storeResponse.getResultMessage().equals(FAILURE_2001)) {
                    onStoreSearchFailure("请求缺少必须参数");
                } else if (storeResponse.getResultMessage().equals(FAILURE_2002)) {
                    onStoreSearchFailure("Sc或Sv未授权");
                }
                break;
            case ERROR:
                if (storeResponse.getResultMessage().equals(ERROR_5000)) {
                    onStoreSearchError("服务器内部错误");
                }
                break;
        }
        onStoreFinish();
    }

    public void onStoreStart() {

    }

    public void onStoreSearchSuccess(StoreSearchBody[] storeSearchBody) {

    }

    public void onStoreSearchFailure(String value) {
        Log.d("onStoreSearchFailure", value);
    }

    public void onStoreSearchError(String value) {
        Log.d("onStoreSearchError", value);
    }

    public void onStoreFinish() {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        onStoreExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onStoreExceptionFinish();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        onStoreExceptionFinish();
    }

    public void onStoreExceptionFinish() {

    }
}
