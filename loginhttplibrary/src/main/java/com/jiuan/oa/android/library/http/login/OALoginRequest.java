package com.jiuan.oa.android.library.http.login;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import com.jiuan.oa.android.library.http.OARequest;

import java.lang.reflect.Modifier;

public class OALoginRequest extends OARequest {

    private static final String PATH_OA_LOGIN = "/MobileOfficeAutomationAPI/LoginWeb.ashx";

    public static final String PATH = PATH_ROOT + PATH_OA_LOGIN;

    public static final String PATH_TEST = PATH_ROOT_TEST + PATH_OA_LOGIN;

    private String account;

    private String password;

    @Override
    public String getContent() {
        OALoginRequestBody obj = new OALoginRequestBody();
        obj.setAccount(account);
        obj.setPassword(password);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        return gson.toJson(obj);
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private class OALoginRequestBody {

        @SerializedName("LogonName")
        private String account;

        @SerializedName("PassWord")
        private String password;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

}
