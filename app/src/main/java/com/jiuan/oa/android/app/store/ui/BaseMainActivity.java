package com.jiuan.oa.android.app.store.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.jiuan.oa.android.app.store.BuildConfig;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.ui.widget.NoticeFragMent;
import com.jiuan.oa.android.app.store.util.DownloadUtils;
import com.jiuan.oa.android.app.store.util.LogUtils;
import com.jiuan.oa.android.library.access.OAAccessClient;
import com.jiuan.oa.android.library.access.OAAccessHttpResponseHandler;
import com.jiuan.oa.android.library.access.OAAllAccessResponse;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.protocol.login.LoginProtocol;
import com.jiuan.oa.android.library.ui.login.LoginActivity;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public abstract class BaseMainActivity extends BaseActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, Subject {

    private static final String TAG = LogUtils.makeLogTag(BaseMainActivity.class);

    public static final String MAIN_PREFERENCES = "main_preferences";

    private static final String PREF_IS_LOGIN = "pref_is_login";

    private static final String PREF_IS_ACCESS = "pref_is_access";

    private ArrayList<LoginCallback> callbackList = new ArrayList<LoginCallback>();

    private boolean firstTime = true;

    private int lastSelect = -1;

    protected abstract int serverType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在发行版中,开启收集崩溃信息.
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.layout_activity_main);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        // Set up the drawer.
        drawerFragment.setUp(R.id.navigation_drawer, R.id.toolbar_main, (DrawerLayout) findViewById(R.id.drawer_layout));
        drawerFragment.setSubject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkState();
    }

    @Override
    public void registerLoginCallback(LoginCallback callback) {
        callbackList.add(callback);
    }

    @Override
    public void removeLoginCallback(LoginCallback callback) {
        callbackList.remove(callback);
    }

    private void loginSuccess(OALoginResponse info) {
        for (LoginCallback call : callbackList) {
            call.onLoginSuccess(info);
        }
    }

    private void logout() {
        // 将已登录状态,已获取权限状态,已保存的人员权限信息清空
        SharedPreferences pre = getSharedPreferences(BaseMainActivity.MAIN_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorBaseMain = pre.edit();
        editorBaseMain.clear().apply();
        for (LoginCallback call : callbackList) {
            call.Logout();
        }
    }

    private void accessSuccess(OAAllAccessResponse[] array) {
        for (LoginCallback call : callbackList) {
            if (call instanceof AccessCallback) {
                ((AccessCallback) call).onAccessSuccess(array);
            }
        }
    }

    /**
     * 检查状态
     */
    private void checkState() {
        SharedPreferences mPreferences = getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPreferences.getString("OALoginResponse", "");
        OALoginResponse info = gson.fromJson(json, OALoginResponse.class);
        if (info == null) {
            LogUtils.LOGD(TAG, "注销状态");
            logout();
            startLoginActivityForResult();
        } else {
            SharedPreferences pref = getSharedPreferences(MAIN_PREFERENCES, MODE_PRIVATE);
            boolean isLogin = pref.getBoolean(PREF_IS_LOGIN, false);
            if (!isLogin) {
                LogUtils.LOGD(TAG, "登录成功");
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(PREF_IS_LOGIN, true);
                editor.apply();
                LogUtils.LOGD(TAG, "设置收集个人的崩溃信息(UserID,Name)");
                setPersonalCrash(info);
                loginSuccess(info);
            } else {
                LogUtils.LOGD(TAG, "已登录");
            }
            boolean isAccess = pref.getBoolean(PREF_IS_ACCESS, false);
            if (!isAccess || firstTime) {
                getAccess(info);
            } else {
                LogUtils.LOGD(TAG, "已获取权限");
            }
            checkVersion();
        }
    }

    /**
     * 检查新版本
     */
    private void checkVersion() {
        if (firstTime) {
            LogUtils.LOGD(TAG, "自动检查最新版本");
            firstTime = false;
            DownloadUtils.checkVersion(BaseMainActivity.this, "JiuanStore");
        }
    }

    /**
     * 打开登录界面
     */
    private void startLoginActivityForResult() {
        Intent intent = new Intent(BaseMainActivity.this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(LoginProtocol.SERVER_TYPE, serverType());
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    /**
     * 获得权限
     * 注意:现在的处理逻辑为如果用户的权限变动了,只有注销后重新登录后才可以刷新.
     */
    private void getAccess(OALoginResponse info) {
        Log.d("getAccess", "获取全部权限");
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        OAAccessClient.requestAllAccess(this, info.getAccount(), info.getUserID(), info.getAccessKey(), ts, new OAAccessHttpResponseHandler() {

            @Override
            public void onOASuccess(String value) {
                Log.d("onOASuccess", "获取全部权限");

                SharedPreferences pref = getSharedPreferences(MAIN_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("OAAllAccessResponse[]", value);
                editor.putBoolean(PREF_IS_ACCESS, true);
                editor.apply();
                OAAllAccessResponse[] array = get(value, OAAllAccessResponse[].class);
                accessSuccess(array);
            }

            @Override
            public void onAccessFailure(String msg) {
                Log.d("onAccessFailure", "获取全部权限");
                Toast.makeText(BaseMainActivity.this, msg, Toast.LENGTH_SHORT).show();
                startLoginActivityForResult();
            }

            @Override
            public void onOAError(String value) {
                Log.d("onOAError", "获取全部权限");
                Toast.makeText(BaseMainActivity.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOAExceptionFinish() {
                Log.d("onOAExceptionFinish", "获取全部权限");
                Toast.makeText(BaseMainActivity.this, getString(R.string.time_out), Toast.LENGTH_SHORT).show();
            }
        }, serverType());
    }

    /**
     * 设置反馈崩溃信息时的用户标识和用户名称
     */
    private void setPersonalCrash(OALoginResponse info) {
        if (Fabric.isInitialized()) {
            Crashlytics.setUserIdentifier(info.getUserID());
            Crashlytics.setUserName(info.getUserName());
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (lastSelect == position) {
            return;
        }

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new StoreHomeFragment();
                ((StoreHomeFragment) fragment).setSubject(this);
                break;
            //添加公告栏
            case 1:
               fragment = new NoticeFragMent();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
        lastSelect = position;

    }

    @Override
    public void onSettingsClick() {
        Intent intent = new Intent(BaseMainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
