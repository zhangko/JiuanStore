package com.jiuan.oa.android.library.access;

import android.content.Context;

import com.jiuan.oa.android.library.http.HeadInfo;
import com.jiuan.oa.android.library.http.OAClient;
import com.loopj.android.http.RequestHandle;

public class OAAccessClient {

    private OAAccessClient() {

    }

    public static RequestHandle requestAllAccess(Context context, String account, String userID, String accessKey, String timestamp, OAAccessHttpResponseHandler responseHandler, boolean isTest) {
        OAAccessRequest helper = buildRequest(context, account, userID, accessKey, timestamp, "0", responseHandler, isTest);
        String path = OAAccessRequest.PATH_TEST_ALL_ACCESS;
        if (!isTest) {
            path = OAAccessRequest.PATH_ALL_ACCESS;
        }
        return buildClient().post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle requestOneAccess(Context context, String account, String userID, String accessKey, String timestamp, String resourceCode, OAAccessHttpResponseHandler responseHandler, boolean isTest) {
        OAAccessRequest helper = buildRequest(context, account, userID, accessKey, timestamp, resourceCode, responseHandler, isTest);
        String path = OAAccessRequest.PATH_TEST_ONE_ACCESS;
        if (!isTest) {
            path = OAAccessRequest.PATH_ONE_ACCESS;
        }
        return buildClient().post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);
    }

    private static OAAccessRequest buildRequest(Context context, String account, String userID, String accessKey, String timestamp, String resourceCode, OAAccessHttpResponseHandler responseHandler, boolean isTest) {
        OAAccessRequest helper = new OAAccessRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);
        helper.setTimestamp(timestamp);
        helper.setResourceCode(resourceCode);
        return helper;
    }

    private static OAClient buildClient() {
        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        return client;
    }

}
