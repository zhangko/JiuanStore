package com.jiuan.oa.android.library.http.login;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 所有和OA相关的系统,统一采用该类接收登录响应.
 */
public class OALoginResponse implements Parcelable {

    @SerializedName("AccessKey") private String accessKey;

    @SerializedName("OAfLoginName") private String account;

    @SerializedName("OAfUserPass") private String password;

    @SerializedName("OAfUserID") private String userID;

    @SerializedName("OAfUserName") private String userName;

    @SerializedName("OAfUserType") private int userType;

    @SerializedName("OAfUserMemoryCode") private String userMnemonicCode;

    @SerializedName("OADepartmentID") private String departmentID;

    @SerializedName("OADepartmentCaption") private String departmentName;

    @SerializedName("OADepartmentCode") private String departmentMnemonicCode;

    @SerializedName("OAfUserFirstCompanyID") private String companyID;

    @SerializedName("OAfUserFirstCompanyCaption") private String companyName;

    @SerializedName("OAfUserFirstCompanyCode") private String companyMnemonicCode;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserMnemonicCode() {
        return userMnemonicCode;
    }

    public void setUserMnemonicCode(String userMnemonicCode) {
        this.userMnemonicCode = userMnemonicCode;
    }

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentMnemonicCode() {
        return departmentMnemonicCode;
    }

    public void setDepartmentMnemonicCode(String departmentMnemonicCode) {
        this.departmentMnemonicCode = departmentMnemonicCode;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyMnemonicCode() {
        return companyMnemonicCode;
    }

    public void setCompanyMnemonicCode(String companyMnemonicCode) {
        this.companyMnemonicCode = companyMnemonicCode;
    }

    public static final Parcelable.Creator<OALoginResponse> CREATOR = new Parcelable.Creator<OALoginResponse>(){

        @Override
        public OALoginResponse createFromParcel(Parcel source) {
            OALoginResponse info = new OALoginResponse();
            info.setAccessKey(source.readString());
            info.setAccount(source.readString());
            info.setPassword(source.readString());
            info.setUserID(source.readString());
            info.setUserName(source.readString());
            info.setUserType(source.readInt());
            info.setUserMnemonicCode(source.readString());
            info.setDepartmentID(source.readString());
            info.setDepartmentName(source.readString());
            info.setDepartmentMnemonicCode(source.readString());
            info.setCompanyID(source.readString());
            info.setCompanyName(source.readString());
            info.setCompanyMnemonicCode(source.readString());
            return info;
        }

        @Override
        public OALoginResponse[] newArray(int size) {
            return new OALoginResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(accessKey);
        dest.writeString(account);
        dest.writeString(password);
        dest.writeString(userID);
        dest.writeString(userName);
        dest.writeInt(userType);
        dest.writeString(userMnemonicCode);
        dest.writeString(departmentID);
        dest.writeString(departmentName);
        dest.writeString(departmentMnemonicCode);
        dest.writeString(companyID);
        dest.writeString(companyName);
        dest.writeString(companyMnemonicCode);
    }
}
