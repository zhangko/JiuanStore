package com.jiuan.oa.android.app.store.ui;

public interface Subject {

    void registerLoginCallback(LoginCallback callback);

    void removeLoginCallback(LoginCallback callback);
}
