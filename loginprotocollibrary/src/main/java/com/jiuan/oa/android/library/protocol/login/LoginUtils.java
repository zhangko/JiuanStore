package com.jiuan.oa.android.library.protocol.login;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LoginUtils {

    /**
     * @param type 请求登录类型
     *
     * <p>{@link LoginProtocol#REQUEST_LOGIN}</p>
     * <p>{@link LoginProtocol#REQUEST_LOGOUT}</p>
     * <p>{@link LoginProtocol#REQUEST_LOGOUT_AND_LOGIN}</p>
     *
     * @param isTest 是否为测试
     */
    @Deprecated
    public static void startLoginActivity(Activity activity, int type, boolean isTest) {
        try {
            Intent intent = new Intent(LoginProtocol.ACTION_LOGIN_ACTIVITY);
            intent.addCategory(LoginProtocol.CATEGORY_LOGIN_ACTIVITY);
            Bundle bundle = new Bundle();
            bundle.putInt(LoginProtocol.LOGIN_TYPE, type);
            bundle.putBoolean(LoginProtocol.IS_TEST, isTest);
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, type);
        } catch (ActivityNotFoundException e) {
            Log.d("", "没有安装Jiuan 商店");
            Toast.makeText(activity, "没有安装Jiuan 商店", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    /**
     * @param type 请求登录类型
     *
     * <p>{@link LoginProtocol#REQUEST_LOGIN}</p>
     * <p>{@link LoginProtocol#REQUEST_LOGOUT}</p>
     * <p>{@link LoginProtocol#REQUEST_LOGOUT_AND_LOGIN}</p>
     *
     * @param server 服务器类型
     */
    public static void startLoginActivity(Activity activity, int type, int server) {
        try {
            Intent intent = new Intent(LoginProtocol.ACTION_LOGIN_ACTIVITY);
            intent.addCategory(LoginProtocol.CATEGORY_LOGIN_ACTIVITY);
            Bundle bundle = new Bundle();
            bundle.putInt(LoginProtocol.LOGIN_TYPE, type);
            bundle.putInt(LoginProtocol.SERVER_TYPE, server);
            intent.putExtras(bundle);
            activity.startActivityForResult(intent, type);
        } catch (ActivityNotFoundException e) {
            Log.d("", "没有安装Jiuan 商店");
            Toast.makeText(activity, "没有安装Jiuan 商店", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }
}
