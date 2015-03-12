package com.jiuan.oa.android.app.store.ui;

import com.jiuan.oa.android.library.http.OAServer;

public class MainActivity extends BaseMainActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @Override
    protected int serverType() {
        return OAServer.BLOOMSKY;
    }
}
