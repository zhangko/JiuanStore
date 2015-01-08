package com.jiuan.oa.android.library.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiuan.oa.android.library.http.login.OALoginClient;
import com.jiuan.oa.android.library.http.login.OALoginHttpResponseHandler;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.protocol.login.LoginProtocol;
import com.jiuan.oa.android.library.util.MD5Util;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    /**
     * 配置文件
     * <p>存储登录人员的相关信息.
     */
    public static final String LOGIN_PREFERENCES = "login_preferences";

    private EditText account;

    private EditText password;

    private LoginProgressDialogFragment dialog;

    private boolean isRequesting;

    private boolean isTest = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);

        int type = LoginProtocol.REQUEST_LOGIN;
        if (getIntent().getExtras() != null) {
            type = getIntent().getExtras().getInt(LoginProtocol.LOGIN_TYPE, LoginProtocol.REQUEST_LOGIN);
            isTest = getIntent().getExtras().getBoolean(LoginProtocol.IS_TEST, false);
        }

        if (isTest) {
            setTitle("OA_TEST");
        } else {
            setTitle("OA");
        }

        switch (type) {
            case LoginProtocol.REQUEST_LOGIN:
                Gson gson = new Gson();
                SharedPreferences preferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
                String json = preferences.getString("OALoginResponse", "");
                OALoginResponse info = gson.fromJson(json, OALoginResponse.class);
                if (info != null) { // 已存储
                    sendLoginSuccessBroadcast(info);
                    resultFinish(info);
                }
                break;
            case LoginProtocol.REQUEST_LOGOUT:
                logout();
                resultFinish(null);
                break;
            case LoginProtocol.REQUEST_LOGOUT_AND_LOGIN:
                logout();
                break;
        }

        account = (EditText) findViewById(R.id.login_edittext);
        password = (EditText) findViewById(R.id.login_password_edittext);
        ImageButton button = (ImageButton) findViewById(R.id.login_forget_password_imagebutton);
        button.setOnClickListener(this);
        dialog = new LoginProgressDialogFragment();
        dialog.setOnKeyBackListener(new LoginProgressDialogFragment.onKeyBackListener() {
            @Override
            public void onKeyBack() {
                // 取消请求
                isRequesting = false;
                OALoginClient.cancelRequests(LoginActivity.this, true);
                dialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.login_action_send) {
            if (!isRequesting) {
                login();
            }
            return true;
        }
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            sendExitBroadcast();
            finish();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_forget_password_imagebutton) {
            forgetPassword();
        }
    }

    private void sendLoginSuccessBroadcast(OALoginResponse oaLoginResponse) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("OALoginResponse", oaLoginResponse);
        intent.putExtras(bundle);
        intent.setAction(LoginProtocol.ACTION_LOGIN_SUCCESS);
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        }
        sendOrderedBroadcast(intent, LoginProtocol.PERMISSIONS_LOGIN);
    }

    private void sendExitBroadcast() {
        Intent intent = new Intent();
        intent.setAction(LoginProtocol.ACTION_EXIT);
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        }
        sendOrderedBroadcast(intent, LoginProtocol.PERMISSIONS_LOGIN);
    }

    private void resultFinish(OALoginResponse oaLoginResponse) {
        if (oaLoginResponse != null) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("OALoginResponse", oaLoginResponse);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    private void login() {

        // 帐号不能为空
        if (TextUtils.isEmpty(account.getText().toString())) {
            toast(getResources().getString(R.string.login_input_account));
            return;
        }

        // 密码不能为空
        if (TextUtils.isEmpty(password.getText().toString())) {
            toast(getResources().getString(R.string.login_input_password));
            return;
        }

        OALoginClient.requestLogin(LoginActivity.this, account.getText().toString(), MD5Util.get32MD5Capital(password.getText().toString(), MD5Util.UTF_16LE), new OALoginHttpResponseHandler() {

            @Override
            public void onOAStart() {
                setRequesting(true);
            }

            @Override
            public void onLoginSuccess(OALoginResponse oaLoginResponse) {
                saveOALoginResponse(oaLoginResponse);
                sendLoginSuccessBroadcast(oaLoginResponse);
                resultFinish(oaLoginResponse);
            }

            @Override
            public void onLoginFailure(String msg) {
                toast(msg);
            }

            @Override
            public void onOAError(String msg) {
                toast(msg);
            }

            @Override
            public void onOAFinish() {
                setRequesting(false);
            }

            @Override
            public void onOAExceptionFinish() {
                toast(getString(R.string.login_time_out));
                setRequesting(false);
            }


        }, isTest);
    }

    private void toast(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void setRequesting(boolean requesting) {
        isRequesting = requesting;
        if (isRequesting) {
            // 显示对话框
            dialog.show(getFragmentManager(), "LoginProgressDialogFragment");
        } else {
            // 隐藏对话框
            dialog.dismiss();
        }
    }

    private void saveOALoginResponse(OALoginResponse oaLoginResponse) {
        SharedPreferences preferences = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(oaLoginResponse);
        prefsEditor.putString("OALoginResponse", json);
        prefsEditor.apply();
    }

    private void forgetPassword() {

        // 用户名不能为空
        if (TextUtils.isEmpty(account.getText().toString())) {
            toast(getResources().getString(R.string.login_input_account));
            return;
        }

        String url;
        if (isTest) {
            url = "http://192.168.1.24/";
        } else {
            url = "http://oa.jiuan.com/";
        }

        // 开启浏览器
        Uri webPage = Uri.parse(url + "findPassword.aspx?id=" + account.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
