package com.jiuan.oa.android.library.access;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.library.http.OARequest;

import java.lang.reflect.Modifier;

public class OAAccessRequest extends OARequest {

    private static final String PATH_OA_ALL_ACCESS = "/MobileOfficeAutomationAPI/GetAuthorResourceWeb.ashx";

    public static final String PATH_JIUAN_ALL_ACCESS = PATH_ROOT_JIUAN + PATH_OA_ALL_ACCESS;

    public static final String PATH_JIUAN_TEST_ALL_ACCESS = PATH_ROOT_JIUAN_TEST + PATH_OA_ALL_ACCESS;

    public static final String PATH_BLOOMSKY_ALL_ACCESS = PATH_ROOT_BLOOMSKY + PATH_OA_ALL_ACCESS;

    private static final String PATH_OA_ONE_ACCESS = "/MobileOfficeAutomationAPI/CheckAuthorResourceWeb.ashx";

    public static final String PATH_JIUAN_ONE_ACCESS = PATH_ROOT_JIUAN + PATH_OA_ONE_ACCESS;

    public static final String PATH_JIUAN_TEST_ONE_ACCESS = PATH_ROOT_JIUAN_TEST + PATH_OA_ONE_ACCESS;

    public static final String PATH_BLOOMSKY_ONE_ACCESS = PATH_ROOT_BLOOMSKY + PATH_OA_ONE_ACCESS;

    private String userID;

    private String accessKey;

    private String timestamp;

    private String resourceCode;

    @Override
    public String getContent() {
        OAAllAccessRequestBody obj = new OAAllAccessRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        obj.setTimestamp(timestamp);
        obj.setResourceCode(resourceCode);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    private class OAAllAccessRequestBody {

        @SerializedName("OAfUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        @SerializedName("DateTime")
        private String timestamp;

        @SerializedName("ResourceCode")
        private String resourceCode;

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

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getResourceCode() {
            return resourceCode;
        }

        public void setResourceCode(String resourceCode) {
            this.resourceCode = resourceCode;
        }
    }
}
