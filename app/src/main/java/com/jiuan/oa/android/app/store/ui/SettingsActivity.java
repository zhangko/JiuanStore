package com.jiuan.oa.android.app.store.ui;

import com.jiuan.oa.android.app.store.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_settings);
        getFragmentManager().beginTransaction().replace(R.id.content, new SettingsFragment()).commit();
    }
}
