package com.jiuan.oa.android.library.http.login;

import com.jiuan.oa.android.library.http.HeadInfo;
import com.jiuan.oa.android.library.http.OAClient;
import com.jiuan.oa.android.library.http.OAServer;
import com.loopj.android.http.RequestHandle;

import android.content.Context;

public class OALoginClient {

    private OALoginClient() {

    }

    @Deprecated
    public static RequestHandle requestLogin(Context context, String account, String password, OALoginHttpResponseHandler responseHandler, boolean isTest) {
        OALoginRequest helper = new OALoginRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setAccount(account);
        helper.setPassword(password);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();

        String path = OALoginRequest.PATH_JIUAN_TEST;
        if (!isTest) {
            path = OALoginRequest.PATH_JIUAN;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle requestLogin(Context context, String account, String password, OALoginHttpResponseHandler responseHandler, int server) {
        OALoginRequest helper = new OALoginRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setAccount(account);
        helper.setPassword(password);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();

        String path = "";
        switch (server) {
            case OAServer.JIUAN:
                path = OALoginRequest.PATH_JIUAN;
                break;
            case OAServer.JIUAN_TEST:
                path = OALoginRequest.PATH_JIUAN_TEST;
                break;
            case OAServer.BLOOMSKY:
                path = OALoginRequest.PATH_BLOOMSKY;
                break;
        }

        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }

}
