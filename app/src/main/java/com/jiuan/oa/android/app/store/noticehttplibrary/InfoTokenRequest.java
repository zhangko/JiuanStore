package com.jiuan.oa.android.app.store.noticehttplibrary;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.jiuan.oa.android.library.http.OARequest;

import java.lang.reflect.Modifier;

/**
 * Created by ZhangKong on 2015/7/6.
 */
public class InfoTokenRequest extends OARequest {

    // 添加发送小米推送token的地址
    public static final String Token = "https://oa.jiuan.com:4433/MobileOfficeAutomationAPI/RegisteredToken.ashx";
    //推送测试地址
    public static final String Token_Test = "https://192.168.1.24/MobileOfficeAutomationAPI/RegisteredToken.ashx";
    //推送调试地址
    public static final String Token_Debug = "https://192.168.1.23/MobileOfficeAutomationAPI/RegisteredToken.ashx";

    private String userID;

    private String accessKey;

    private String timestamp;
    //添加小米推送需要的参数，包括token，employeeid，phonetype；
    private String token;

    private String employeeid;


    private int phonetype;

    @Override
    public String getContent() {
        InfoRequestBody obj = new InfoRequestBody();
        obj.setUserID(userID);
        obj.setAccessKey(accessKey);
        obj.setPhonetype(phonetype);
        obj.setEmployeeid(employeeid);
        obj.setToken(token);
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
    public String getToken(){return token;}

    public void setToken(String token){this.token = token;}

    public String getEmployeeid(){return  employeeid;}

    public void setEmployeeid(String employeeid){this.employeeid = employeeid;}

    public int getPhonetype(){return phonetype;}

    public void setPhonetype(int phonetype){this.phonetype = phonetype;}

    private class InfoRequestBody {

        @SerializedName("OAUserID")
        private String userID;

        @SerializedName("AccessKey")
        private String accessKey;

        @SerializedName("Token")
        private String token;

        @SerializedName("EmployeeID")
        private String employeeid;

        @SerializedName("PhoneType")
        private int phonetype;



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

        public String getEmployeeid() {
            return employeeid;
        }

        public void setEmployeeid(String employeeid) {
            this.employeeid = employeeid;
        }

        public void setPhonetype(int phonetype) {
            this.phonetype = phonetype;
        }

        public int getPhonetype() {
            return phonetype;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}
