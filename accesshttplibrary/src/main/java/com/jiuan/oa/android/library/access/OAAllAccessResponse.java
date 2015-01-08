package com.jiuan.oa.android.library.access;

import com.google.gson.annotations.SerializedName;

public class OAAllAccessResponse {

    @SerializedName("ID")
    private int id;

    @SerializedName("ResourceCode")
    private String resourceCode;

    @SerializedName("ResourceUrl")
    private String resourceID;

    @SerializedName("ResourceName")
    private String resourceName;

    @SerializedName("Description")
    private String resourceDes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceDes() {
        return resourceDes;
    }

    public void setResourceDes(String resourceDes) {
        this.resourceDes = resourceDes;
    }
}
