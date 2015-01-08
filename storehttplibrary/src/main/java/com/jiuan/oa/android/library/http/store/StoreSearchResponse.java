package com.jiuan.oa.android.library.http.store;

import com.google.gson.annotations.SerializedName;

public class StoreSearchResponse {

    @SerializedName("Result") private int result;

    @SerializedName("ResultMessage") private String resultMessage;

    @SerializedName("ReturnValue") private StoreSearchBody[] returnValue;

    @SerializedName("QueueNum") private long queueNum;

    @SerializedName("TS") private long timestamp;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public StoreSearchBody[] getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(StoreSearchBody[] returnValue) {
        this.returnValue = returnValue;
    }

    public long getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(long queueNum) {
        this.queueNum = queueNum;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
