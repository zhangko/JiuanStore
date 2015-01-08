package com.jiuan.oa.android.app.store;

import android.content.Context;

import com.jiuan.oa.android.library.http.OAClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

public class JiuanStoreClient {

    private static final String PATH = "https://raw.githubusercontent.com/BoxuanJia/"+StoreUrl.PATH+"/master/JiuanStoreAppList";

    public static RequestHandle requestAppList(Context context, JsonHttpResponseHandler responseHandler) {
        return OAClient.getInstance().post(context, PATH, null, responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
