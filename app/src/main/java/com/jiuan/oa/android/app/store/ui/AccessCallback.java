package com.jiuan.oa.android.app.store.ui;

import com.jiuan.oa.android.library.access.OAAllAccessResponse;

public interface AccessCallback extends LoginCallback {

    /**
     * 获取权限成功
     */
    void onAccessSuccess(OAAllAccessResponse[] oaAllAccessResponse);

}
