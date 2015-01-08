package com.jiuan.oa.android.library.http.store;

import com.google.gson.annotations.SerializedName;

public class StoreSearchBody {

    @SerializedName("AppName")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Version")
    private String version;

    @SerializedName("AppIdentifier")
    private String appIdentifier;

    @SerializedName("LogoPath")
    private String logoPath;

    @SerializedName("DownLoadPath")
    private String downLoadPath;

    @SerializedName("Keywords")
    private String[] Keywords;

    @SerializedName("Attributes")
    private int[] attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDownLoadPath() {
        return downLoadPath;
    }

    public void setDownLoadPath(String downLoadPath) {
        this.downLoadPath = downLoadPath;
    }

    public String[] getKeywords() {
        return Keywords;
    }

    public void setKeywords(String[] keywords) {
        Keywords = keywords;
    }

    public int[] getAttributes() {
        return attributes;
    }

    public void setAttributes(int[] attributes) {
        this.attributes = attributes;
    }
}
