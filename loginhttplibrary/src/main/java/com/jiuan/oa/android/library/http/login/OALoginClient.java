package com.jiuan.oa.android.library.http.login;

import com.jiuan.oa.android.library.http.HeadInfo;
import com.jiuan.oa.android.library.http.OAClient;
import com.loopj.android.http.RequestHandle;

import android.content.Context;

public class OALoginClient {

    private OALoginClient() {

    }

    public static RequestHandle requestLogin(Context context, String account, String password, OALoginHttpResponseHandler responseHandler, boolean isTest) {
        OALoginRequest helper = new OALoginRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setAccount(account);
        helper.setPassword(password);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();

        String path = OALoginRequest.PATH_TEST;
        if (!isTest) {
            path = OALoginRequest.PATH;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }
}
