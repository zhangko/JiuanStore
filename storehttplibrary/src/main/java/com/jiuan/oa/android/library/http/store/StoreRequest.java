package com.jiuan.oa.android.library.http.store;

import com.jiuan.oa.android.library.util.Installation;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Locale;

public class StoreRequest {

    // Application User SNCode 使用者编号,为了可以随时关闭某个来源
    private String sc;

    // API of Application User Service 使用者对应此接口的编号,为了随时可以关闭某个来源,或者关闭某个来源某个服务
    private String sv;

    // App名称,预先由平台定义好,不随版本变化而变化
    private String appName;

    // App软件版本
    private String appVersion;

    // App每次安装生成的唯一id
    private String appGUID;

    // App所在的操作系统(android)
    private String deviceOS;

    // 手机的名称
    private String deviceName;

    // 手机的唯一编号
    private String deviceID;

    // 手机系统设置的语言
    private String deviceLanguage;

    // 手机系统设置的国家地区
    private String deviceRegion;

    // App传上来的.和云端无关,云端原样返回
    private long queueNum;

    // 推送用的令牌
    private String token;

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppGUID() {
        return appGUID;
    }

    public void setAppGUID(String appGUID) {
        this.appGUID = appGUID;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceLanguage() {
        return deviceLanguage;
    }

    public void setDeviceLanguage(String deviceLanguage) {
        this.deviceLanguage = deviceLanguage;
    }

    public String getDeviceRegion() {
        return deviceRegion;
    }

    public void setDeviceRegion(String deviceRegion) {
        this.deviceRegion = deviceRegion;
    }

    public long getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(long queueNum) {
        this.queueNum = queueNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put("SC", getSc());
        params.put("SV", getSv());
        params.put("AppName", getAppName());
        params.put("AppVersion", getAppVersion());
        params.put("AppGUID", getAppGUID());
        params.put("DeviceOS", getDeviceOS());
        params.put("DeviceName", getDeviceName());
        params.put("DeviceID", getDeviceID());
        params.put("DeviceLanguage", getDeviceLanguage());
        params.put("DeviceRegion", getDeviceRegion());
        params.put("QueueNum", getQueueNum());
        params.put("Token", getToken());
        return params;
    }

    public static final class Builder {

        private String sc;

        private String sv;

        private String appName;

        private String appVersion;

        private String appGUID;

        private String deviceOS;

        private String deviceName;

        private String deviceID;

        private String deviceLanguage;

        private String deviceRegion;

        private long queueNum;

        private String token;

        public Builder(Context context) {
            appName = context.getPackageName();
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                appVersion = ""+packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appGUID = Installation.id(context);
            deviceOS = "android";
            deviceName = Build.MODEL;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceID = telephonyManager.getDeviceId();
            deviceLanguage = Locale.getDefault().getLanguage();
            deviceRegion = Locale.getDefault().getCountry();
            queueNum = 0;
            token = "";
        }

        public Builder sc(String sc) {
            this.sc = sc;
            return this;
        }

        public Builder sv(String sv) {
            this.sv = sv;
            return this;
        }

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder appGUID(String appGUID) {
            this.appGUID = appGUID;
            return this;
        }

        public Builder deviceOS(String deviceOS) {
            this.deviceOS = deviceOS;
            return this;
        }

        public Builder deviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }

        public Builder deviceID(String deviceID) {
            this.deviceID = deviceID;
            return this;
        }

        public Builder deviceLanguage(String deviceLanguage) {
            this.deviceLanguage = deviceLanguage;
            return this;
        }

        public Builder deviceRegion(String deviceRegion) {
            this.deviceRegion = deviceRegion;
            return this;
        }

        public Builder queueNum(long queueNum) {
            this.queueNum = queueNum;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public StoreRequest build(StoreRequest storeRequest) {
            storeRequest.setSc(sc);
            storeRequest.setSv(sv);
            storeRequest.setAppName(appName);
            storeRequest.setAppVersion(appVersion);
            storeRequest.setAppGUID(appGUID);
            storeRequest.setDeviceOS(deviceOS);
            storeRequest.setDeviceName(deviceName);
            storeRequest.setDeviceID(deviceID);
            storeRequest.setDeviceLanguage(deviceLanguage);
            storeRequest.setDeviceRegion(deviceRegion);
            storeRequest.setQueueNum(queueNum);
            storeRequest.setToken(token);
            return storeRequest;
        }

    }
}
