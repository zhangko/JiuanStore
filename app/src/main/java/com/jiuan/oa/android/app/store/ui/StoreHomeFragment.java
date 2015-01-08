package com.jiuan.oa.android.app.store.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiuan.oa.android.app.store.AppInfo;
import com.jiuan.oa.android.app.store.JiuanStoreClient;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.ui.widget.MultiSwipeRefreshLayout;
import com.jiuan.oa.android.app.store.ui.widget.StoreHomeAdapter;
import com.jiuan.oa.android.app.store.util.LogUtils;
import com.jiuan.oa.android.library.access.OAAllAccessResponse;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.ui.login.LoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class StoreHomeFragment extends LoginCallbackFragment implements AccessCallback, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = LogUtils.makeLogTag(StoreHomeFragment.class);

    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecycler;

    private StoreHomeAdapter mAdapter;

    private AppInfo[] appInfoArray;

    private OAAllAccessResponse[] accessArray;

    private boolean appListComplete;

    private boolean accessListComplete;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_storehome, container, false);

        // Retrieve the SwipeRefreshLayout and GridView instances
        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshLayout.setSwipeableChildren(R.id.recycler);

        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecycler.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // specify an adapter (see also next example)
        mAdapter = new StoreHomeAdapter(getActivity());
        mRecycler.setAdapter(mAdapter);
        mAdapter.registerReceiver();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        // SwipeRefreshLayout indicator does not appear when the setRefreshing(true) is called before the SwipeRefreshLayout.onMeasure()
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 判断是否可以拿到人员的信息,如果可以拿到,调用自身的登录成功方法
        SharedPreferences prefLogin = getActivity().getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefLogin.getString("OALoginResponse", "");
        OALoginResponse info = gson.fromJson(json, OALoginResponse.class);
        if (info != null) {
            onLoginSuccess(info);
        }
        // 判断是否可以拿到权限,如果可以拿到,调用自身的获取权限成功方法
        SharedPreferences mPreferences = getActivity().getSharedPreferences(BaseMainActivity.MAIN_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = mPreferences.getString("OAAllAccessResponse[]", "");
        OAAllAccessResponse[] info2 = gson2.fromJson(json2, OAAllAccessResponse[].class);
        if (info2 != null) {
            onAccessSuccess(info2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.unregisterReceiver();
    }

    @Override
    public void onLoginSuccess(OALoginResponse oaLoginResponse) {
        // 如果未来有正式获取商城列表的接口,在这里调用请求商城列表的服务,并发送人员的相关数据.
        getAppInfoListNetwork();
    }

    @Override
    public void Logout() {
        appInfoArray = null;
        appListComplete = false;
        accessArray = null;
        accessListComplete = false;
        mAdapter.clearAppList();
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAccessSuccess(OAAllAccessResponse[] oaAllAccessResponse) {
        accessArray = oaAllAccessResponse;
        accessListComplete = true;
        checkAllComplete();
    }

    @Override
    public void onRefresh() {
        getAppInfoListNetwork();
    }

    /**
     * 获取网络列表
     */
    private void getAppInfoListNetwork() {
        appInfoArray = null;
        appListComplete = false;
        mAdapter.clearAppList();
        mAdapter.notifyDataSetChanged();
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
        JiuanStoreClient.requestAppList(getActivity(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                appInfoArray = gson.fromJson(response.toString(), AppInfo[].class);
                appListComplete = true;
                checkAllComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                appListComplete = true;
                checkAllComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(getActivity(), getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                appListComplete = true;
                checkAllComplete();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), getString(R.string.time_out), Toast.LENGTH_SHORT).show();
                appListComplete = true;
                checkAllComplete();
            }
        });
    }

    /**
     * 检查是否全部完成
     * 加载商店以及获取权限
     */
    private void checkAllComplete() {
        if (appListComplete && accessListComplete) {
            mAdapter.clearAppList();
            if (appInfoArray != null) {
                for (AppInfo appInfo : appInfoArray) {
                    if (appInfo.getAccessCodeArray() == null) {
                        mAdapter.addAppInfo(appInfo);
                    } else {
                        inner:
                        for (int i = 0; i < appInfo.getAccessCodeArray().length; i++) {
                            for (OAAllAccessResponse access : accessArray) {
                                if (access.getResourceCode().equals(appInfo.getAccessCodeArray()[i].getCode())) {
                                    mAdapter.addAppInfo(appInfo);
                                    break inner;
                                }
                            }
                        }
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
            // Stop the refreshing indicator
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

}

