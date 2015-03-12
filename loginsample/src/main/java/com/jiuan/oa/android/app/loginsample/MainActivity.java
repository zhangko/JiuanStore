package com.jiuan.oa.android.app.loginsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jiuan.oa.android.library.http.OAServer;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.protocol.login.LoginProtocol;
import com.jiuan.oa.android.library.protocol.login.LoginUtils;

public class MainActivity extends Activity implements View.OnClickListener{

    private OALoginResponse info;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case 0:
                Toast.makeText(this,"登录状态返回",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"注销状态返回",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"注销后登录状态返回",Toast.LENGTH_SHORT).show();
                break;
        }

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            info = bundle.getParcelable("OALoginResponse");
            Log.d("MainActivity", "account "+info.getAccount());
            Log.d("MainActivity", "userID "+info.getUserID());
            Log.d("MainActivity", "accessKey "+info.getAccessKey());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        Button buttonLogin = (Button) findViewById(R.id.login);
        Button buttonLogout = (Button) findViewById(R.id.logout);
        Button buttonLogoutToLogin = (Button) findViewById(R.id.logout_to_login);
        buttonLogin.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);
        buttonLogoutToLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                LoginUtils.startLoginActivity(this, LoginProtocol.REQUEST_LOGIN, OAServer.JIUAN);
                break;
            case R.id.logout:
                LoginUtils.startLoginActivity(this, LoginProtocol.REQUEST_LOGOUT, OAServer.JIUAN);
                break;
            case R.id.logout_to_login:
                LoginUtils.startLoginActivity(this, LoginProtocol.REQUEST_LOGOUT_AND_LOGIN, OAServer.JIUAN);
                break;
        }
    }
}
