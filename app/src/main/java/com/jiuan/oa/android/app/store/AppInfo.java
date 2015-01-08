package com.jiuan.oa.android.app.store;

import com.google.gson.annotations.SerializedName;

public class AppInfo {

    @SerializedName("packageName")
    private String packageName;

    @SerializedName("versionCode")
    private int versionCode;

    @SerializedName("downloadUrl")
    private String downloadUrl;

    @SerializedName("iconUrl")
    private String iconUrl;

    @SerializedName("appName")
    private String appName;

    @SerializedName("versionName")
    private String versionName;

    @SerializedName("accessCode")
    private AccessCode[] accessCodeArray;

    private int state;

    private long downloadId = -1;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public AccessCode[] getAccessCodeArray() {
        return accessCodeArray;
    }

    public void setAccessCodeArray(AccessCode[] accessCodeArray) {
        this.accessCodeArray = accessCodeArray;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }
}
