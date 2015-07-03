package com.jiuan.oa.android.app.store.ui.widget;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;


import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.adapter.NoticeListAdapter;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeClient;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponse;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeResponseHandler;
import com.jiuan.oa.android.app.store.noticehttplibrary.ReturnValueResponse;
import com.jiuan.oa.android.app.store.ui.NoticeActivity;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.ui.login.LoginActivity;


import org.apache.http.Header;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by ZhangKong on 2015/6/29.
 */
public class NoticeFragMent  extends Fragment implements SwipeRefreshLayout.OnRefreshListener,AbsListView.OnScrollListener{

    private NoticeListAdapter adapter;

    private ListView listView;

    private final int NUMBER_IN＿ONEPAGE = 12;

    private int mpage;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int leftSize;

    private TextView lastItem;

    private List<NoticeResponse>  listTotalNotice = new ArrayList<NoticeResponse>();

    private  boolean isFirst = true;

    protected NoticeResponseHandler mHandler = new NoticeResponseHandler(getActivity()){
        @Override
        public void onOASuccess(String value) {

            Gson gson = new Gson();
            ReturnValueResponse returnValueResponse = gson.fromJson(value,ReturnValueResponse.class);
            Log.d("MSG","  " + returnValueResponse.getNoticeList());
            leftSize = returnValueResponse.getLastSize();
            Log.d("MSG","  " +  leftSize);
            listTotalNotice = gson.fromJson(returnValueResponse.getNoticeList().toString(), new TypeToken<List<NoticeResponse>>() {
            }.getType());

            Log.d("MSG","一共有公告：" + listTotalNotice.size());
            Log.d("MSG",listTotalNotice.get(0).getTitleName());

            if(isFirst){

                adapter = new NoticeListAdapter(getActivity(),R.layout.layout_notice_list,listTotalNotice);
                listView = (ListView)getActivity().findViewById(R.id.noticelist);
                listView.setAdapter(adapter);
            }else {
                adapter.addItem(listTotalNotice);
            }

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState){
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:

                            if(view.getLastVisiblePosition() == (view.getCount() - 1)){
                                if(leftSize != 0){
                                  String times =  listTotalNotice.get(listTotalNotice.size() - 1).getTimeStamp();
                                    isFirst = false;
                                    requstData(times);
                                }else {
                                    Toast.makeText(getActivity(),"已全部加载",Toast.LENGTH_SHORT).show();
                                }

                            }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String titleID =listTotalNotice.get(position).getTitleID();
                    Intent  intent = new Intent(getActivity(), NoticeActivity.class);
                    intent.putExtra("titleID",titleID);
                    startActivity(intent);
                    Log.d("MSG","开始显示ListView");


                }
            });

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
        @Override
    public void onFinish(){
            super.onFinish();
            swipeRefreshLayout.setRefreshing(false);
           swipeRefreshLayout.setEnabled(true);

        }

     };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mpage = 1;
        requstData();



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_notice_swiperefresh, null);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1,
                R.color.swiperefresh_color2, R.color.swiperefresh_color3,
                R.color.swiperefresh_color4);

        lastItem = (TextView)view.findViewById(R.id.lastItem);
        Log.d("MSG","开始设置listview");
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }


    @Override
    public void onRefresh() {

        adapter.clear();
       listTotalNotice.clear();
        isFirst = true;
        mpage = 1;
        requstData();


    }

    private void requstData(){
        SharedPreferences preferences=getActivity().getSharedPreferences(LoginActivity.LOGIN_PREFERENCES,getActivity().MODE_PRIVATE);
        String oaResponse=preferences.getString("OALoginResponse","");
        Log.d("MSG",""+oaResponse);
        Gson gson=new Gson();
        OALoginResponse info=gson.fromJson(oaResponse,OALoginResponse.class);
        String accesskey=info.getAccessKey();
        String userid=info.getUserID();
        Log.d("MSG","accesskey:"+accesskey);
        Log.d("MSG","userid:"+userid);

        long time=System.currentTimeMillis();

        String timeStamp=Long.toString(time);
        String subtimeStamp=timeStamp.substring(0,timeStamp.length()-1);
        Log.d("MSG","   "+subtimeStamp);
        Log.d("MSG","  "+timeStamp);


        NoticeClient.requestNoticeList(getActivity(),info.getAccount(),info.getUserID(),info.getAccessKey(),subtimeStamp,mpage*NUMBER_IN＿ONEPAGE,mHandler);

    }

    private void  requstData(String timestamp){
        SharedPreferences preferences=getActivity().getSharedPreferences(LoginActivity.LOGIN_PREFERENCES,getActivity().MODE_PRIVATE);
        String oaResponse=preferences.getString("OALoginResponse","");
        Log.d("MSG",""+oaResponse);
        Gson gson=new Gson();
        OALoginResponse info=gson.fromJson(oaResponse,OALoginResponse.class);
        String accesskey=info.getAccessKey();
        String userid=info.getUserID();
        Log.d("MSG","accesskey:"+accesskey);
        Log.d("MSG","userid:"+userid);
        NoticeClient.requestNoticeList(getActivity(),info.getAccount(),info.getUserID(),info.getAccessKey(),timestamp,mpage*NUMBER_IN＿ONEPAGE,mHandler);

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(lastItem) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (scrollEnd) {
            ++mpage;
            requstData();
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private String getCurrentTime(){
        long time=System.currentTimeMillis();
        String timeStamp=Long.toString(time);
        String subtimeStamp=timeStamp.substring(0,timeStamp.length()-1);
        return subtimeStamp;
    }
}
