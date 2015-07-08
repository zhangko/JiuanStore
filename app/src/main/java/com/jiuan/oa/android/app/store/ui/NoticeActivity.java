package com.jiuan.oa.android.app.store.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeClient;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponse;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponseHandler;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.ui.login.LoginActivity;

import org.apache.http.Header;


/**
 * Created by ZhangKong on 2015/6/26.
 */
public class NoticeActivity extends BaseActivity {

    private int INTENT_TYPE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_noticeactivity);
        SharedPreferences preferences = getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, MODE_PRIVATE);
        String oaResponse = preferences.getString("OALoginResponse","");
        Log.d("MSG", "" + oaResponse);
        Gson gson = new Gson();
        OALoginResponse info = gson.fromJson(oaResponse,OALoginResponse.class);
        String accesskey = info.getAccessKey();
        String userid = info.getUserID();
        String titleID = getIntent().getStringExtra("titleID");
        INTENT_TYPE = getIntent().getIntExtra("Type",0);
        Log.d("MSG","accesskey:" + accesskey);
        Log.d("MSG","userid:" + userid);

        NoticeClient.requestNoticeContent(this,info.getAccount(),userid,accesskey,titleID, new NoticeResponseHandler(){
            @Override
            public void onOASuccess(String value) {

                Log.d("onOASuccess", value);
                Gson gson = new Gson();

                NoticeResponse noticeResponse  = gson.fromJson(value.toString(),NoticeResponse.class);

                Log.d("MSG",noticeResponse.getContest());
                TextView titleText = (TextView)findViewById(R.id.notice_title);
                TextView contentText = (TextView)findViewById(R.id.notice_content);

                 String content = noticeResponse.getContest();
                String content_final = content.replace("\\n","\n");
                titleText.setText(noticeResponse.getTitleName());
                contentText.setText(content_final);


            }
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

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("onFailureMSG", responseString);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("MSG","进入了返回按键");
                if(INTENT_TYPE == 1){
                    this.finish();
                }
                if(INTENT_TYPE == 2){
                    Intent intent = new Intent(NoticeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
