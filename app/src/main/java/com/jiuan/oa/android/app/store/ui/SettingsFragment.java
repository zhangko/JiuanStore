package com.jiuan.oa.android.app.store.ui;

import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.library.ui.login.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_settings);
        findPreference("logout").setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals("logout")) {
            // 将已保存的人员信息清空
            SharedPreferences preferences = getActivity().getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorLogin = preferences.edit();
            editorLogin.clear().apply();
            // 将已登录状态,已获取权限状态,已保存的人员权限信息清空
            SharedPreferences pre = getActivity().getSharedPreferences(BaseMainActivity.MAIN_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editorBaseMain = pre.edit();
            editorBaseMain.clear().apply();
            // 发送广播
            sendLogoutBroadcast(getActivity());
            getActivity().finish();
            return true;
        }
        return false;
    }

    /**
     * 发送注销广播
     */
    public void sendLogoutBroadcast(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.jiuan.oa.android.app.store.Logout");
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.addFlags(Intent. FLAG_INCLUDE_STOPPED_PACKAGES);
        }
        context.sendOrderedBroadcast(intent, "com.jiuan.oa.android.app.store.permission.BROADCAST");
    }

}
