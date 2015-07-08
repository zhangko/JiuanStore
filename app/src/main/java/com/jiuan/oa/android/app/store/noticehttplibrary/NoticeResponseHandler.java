package com.jiuan.oa.android.app.store.noticehttplibrary;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jiuan.oa.android.library.http.OAHttpResponseHandler;

/**
 * Created by ZhangKong on 2015/6/26.
 */
public class NoticeResponseHandler extends OAHttpResponseHandler {
    /**
     * AccessKey错误
     */
    public static final String FAILURE_210_1 = "210.1";

    /**
     * AccessKey过期
     */
    public static final String FAILURE_210_2 = "210.2";

    /**
     * 上传数据不完整，或格式不正确
     */
    public static final String FAILURE_230 = "230";

    @Override
    public void onOAFailure(String msg) {
        if (msg.equals(FAILURE_210_1)) {
            onInfoFailure("AccessKey错误");
        } else if (msg.equals(FAILURE_210_2)) {
            onInfoFailure("AccessKey过期");
        } else if (msg.equals(FAILURE_230)) {
            onInfoFailure("上传数据不完整，或格式不正确");
        }
    }

    public void onInfoFailure(String value) {
        Log.d("MSG",value);

    }
}
