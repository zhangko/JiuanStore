package com.jiuan.oa.android.app.store.ui;


import android.support.v4.app.Fragment;

import com.jiuan.oa.android.library.http.OAClient;

public abstract class LoginCallbackFragment extends Fragment implements LoginCallback {

    private Subject subject;

    /**
     * 必须调用
     * 目的是通过Subject把Activity和Fragment解耦
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registerLoginCallback(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subject != null) {
            subject.removeLoginCallback(this);
        }
        cancelRequests();
    }

    /**
     * 取消请求
     */
    private void cancelRequests() {
        OAClient.getInstance().cancelRequests(getActivity(), true);
    }
}
