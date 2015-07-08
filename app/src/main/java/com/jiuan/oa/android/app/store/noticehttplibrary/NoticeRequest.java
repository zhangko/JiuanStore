package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.library.http.OARequest;


import java.lang.reflect.Modifier;

/**
 * Created by ZhangKong on 2015/6/26.
 */
public class NoticeRequest extends OARequest {

    public static String PATH_NOTICE_LIST = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/GGBMessageListWeb.ashx";

    public static String PATH_NOTICE_CONTENT = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/GGBInformationWeb.ashx";

    public static String PATH_NOTICE_PUSH = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/RegisteredToken.ashx";

    public static int NUMBER_PER_PAGE = 12;

    @Override
    public String getContent() {
        InfoRequestBody obj = new InfoRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        obj.setPageSize(pagesize);
        obj.setTimeStamp(timestamp);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }
    private String userID;

    private String accessKey;

    private String timestamp;

    private int pagesize;

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

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    private class InfoRequestBody {

        @SerializedName("OAfUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        @SerializedName("TimeStamp")
        private String timeStamp;

        @SerializedName("PageSize")
        private int pageSize;

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

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public int getPageSize() {
            return pageSize;
        }
    }
}
