package com.jiuan.oa.android.library.http.store;

import com.jiuan.oa.android.library.http.OAClient;
import com.loopj.android.http.RequestHandle;

import android.content.Context;

public class StoreClient {

    private StoreClient() {

    }

    public static RequestHandle requestSearch(Context context, String searchKeyWords, StoreSearchHttpResponseHandler responseHandler) {
        StoreRequest.Builder builder = new StoreSearchRequest.Builder(context).sc("001cfe2fe7044aa691d4e6eff9bfb56c").sv("6a678cd1b2cf4b269bb04660dc7f3046");
        StoreSearchRequest helper = (StoreSearchRequest) builder.build(new StoreSearchRequest());
        helper.setSearchKeyWords(searchKeyWords);
        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        return client.post(context, StoreSearchRequest.PATH, helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }

}
