package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.library.http.OARequest;

import java.lang.reflect.Modifier;

/**
 * Created by ZhangKong on 2015/6/29.
 */
public class NoticeContentRequest extends OARequest {

    public static String PATH_NOTICE_LIST = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/GGBMessageListWeb.ashx";

    public static String PATH_NOTICE_CONTENT = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/GGBInformationWeb.ashx";
    @Override
    public String getContent() {
        InfoRequestBody obj = new InfoRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        obj.setOatitleid(oatitleid);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }

    private String userID;

    private String accessKey;

    private String oatitleid;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setOatitleid(String oatitleid) {
        this.oatitleid = oatitleid;
    }

    public String getOatitleid() {
        return oatitleid;
    }

    private class InfoRequestBody {

        @SerializedName("OAfUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        @SerializedName("OAfTitleID")
        private String oatitleid;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getOatitleid() {
            return oatitleid;
        }

        public void setOatitleid(String oatitleid) {
            this.oatitleid = oatitleid;
        }
    }
}
