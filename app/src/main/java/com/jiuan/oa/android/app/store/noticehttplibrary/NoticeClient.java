package com.jiuan.oa.android.app.store.noticehttplibrary;

import android.content.Context;
import android.util.Log;

import com.jiuan.oa.android.library.http.HeadInfo;
import com.jiuan.oa.android.library.http.OAClient;
import com.loopj.android.http.RequestHandle;

/**
 * Created by ZhangKong on 2015/6/26.
 */
public class NoticeClient {
    public NoticeClient(){

    }
    public static RequestHandle requestNoticeList(Context context, String account, String userID, String accessKey,String timestamp,int pagesize,NoticeResponseHandler responseHandler) {
        return request(context, account, userID, accessKey,timestamp,pagesize, responseHandler,NoticeRequest.PATH_NOTICE_LIST);
    }

    public static RequestHandle requestNoticeContent(Context context, String account, String userID, String accessKey,String titleID, NoticeResponseHandler responseHandler) {
        return request(context, account, userID, accessKey,titleID, responseHandler,NoticeRequest.PATH_NOTICE_CONTENT);
    }

    public static RequestHandle requestToken(Context context,String account,String userID,String accessKey,String token,String employeeid,int phonetype,NoticeResponseHandler responseHandler){
        return request(context,account,userID,accessKey,token,employeeid,phonetype,responseHandler,NoticeRequest.PATH_NOTICE_PUSH);
    }
    public static RequestHandle request(Context context, String account, String userID, String accessKey,String timestamp,int pagesize, NoticeResponseHandler responseHandler, String path) {
        NoticeRequest helper = new NoticeRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);
        helper.setPagesize(pagesize);
        helper.setTimestamp(timestamp);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        Log.d("MSG",helper.getPathWithHeadInfo(path));
        Log.d("MSG",helper.getRequestParams().toString());
        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle request(Context context, String account, String userID, String accessKey,String titleid, NoticeResponseHandler responseHandler, String path){
        NoticeContentRequest helper = new NoticeContentRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(account);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);
        helper.setOatitleid(titleid);

        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        Log.d("MSG",helper.getPathWithHeadInfo(path));
        Log.d("MSG",helper.getRequestParams().toString());
        return client.post(context, helper.getPathWithHeadInfo(path), helper.getRequestParams(), responseHandler);
    }

    public static RequestHandle request(Context context,String accout,String userID,String accessKey,String token ,String employeeid,int phonetype,NoticeResponseHandler responseHandler,String path){
        InfoTokenRequest helper = new InfoTokenRequest();
        HeadInfo.Builder builder = new HeadInfo.Builder(context).account(accout);
        helper.setHeadInfo(builder.build());
        helper.setUserID(userID);
        helper.setAccessKey(accessKey);
        helper.setToken(token);
        helper.setEmployeeid(employeeid);
        helper.setPhonetype(phonetype);
        OAClient client = OAClient.getInstance();
        client.setSSLSocketFactory();
        return  client.post(context,helper.getPathWithHeadInfo(path), helper.getRequestParams(),responseHandler);
    }
    public static void cancelRequests(Context context, boolean mayInterruptIfRunning) {
        OAClient.getInstance().cancelRequests(context, mayInterruptIfRunning);

    }
}
