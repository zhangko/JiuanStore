package com.jiuan.oa.android.app.store.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.ui.widget.NavigationFooterView;
import com.jiuan.oa.android.app.store.ui.widget.NavigationHeaderView;
import com.jiuan.oa.android.library.http.login.OALoginResponse;
import com.jiuan.oa.android.library.ui.login.LoginActivity;

public class NavigationDrawerFragment extends LoginCallbackFragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {

        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);

        void onSettingsClick();
    }

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Used to store the last screen title.
     */
    private CharSequence mTitle;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationHeaderView mNavigationHeaderView;

    private NavigationAdapter mNavigationAdapter;

    private ListView mDrawerListView;

    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;

    private boolean mFromSavedInstanceState;

    private boolean mUserLearnedDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mNavigationHeaderView = (NavigationHeaderView) view.findViewById(R.id.header_view);

        mDrawerListView = (ListView) view.findViewById(R.id.content);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        NavigationFooterView mNavigationFooterView = new NavigationFooterView(getActivity());
        mNavigationFooterView.setOnClickSettingsListener(new NavigationFooterView.OnClickSettingsListener() {
            @Override
            public void onClickSettings() {
                selectSettings();
            }
        });
        mDrawerListView.addFooterView(mNavigationFooterView);

        //添加公告栏选项

        mNavigationAdapter = new NavigationAdapter(getActivity(), new String[]{
                getString(R.string.navigation_item_store_home),
                getString(R.string.navigation_item_notice)

        });
        mNavigationAdapter.setItemChecked(mCurrentSelectedPosition);
        mDrawerListView.setAdapter(mNavigationAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
        // 获得title
        setTitle(mCurrentSelectedPosition);
        // 判断是否可以拿到人员的信息,如果可以拿到,调用自身的登录成功方法
        SharedPreferences prefLogin = getActivity().getSharedPreferences(LoginActivity.LOGIN_PREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefLogin.getString("OALoginResponse", "");
        OALoginResponse info = gson.fromJson(json, OALoginResponse.class);
        if (info != null) {
            onLoginSuccess(info);
        }
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param toolbarId    The android:id of this toolbar in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, int toolbarId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        // Handle Toolbar
        mToolbar = (Toolbar) getActivity().findViewById(toolbarId);
        if (mToolbar != null) {
            ((ActionBarActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        // 设置标题
        updateTitle(mTitle);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                mToolbar,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // DrawerLayout 默认实现了点击返回键关闭导航栏的功能
        // 但是因为是 Fragment , DrawerLayout 的处理未起作用,需要自己实现点击返回关闭导航栏.
        // 监听返回键
        mDrawerListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
                    mDrawerLayout.closeDrawers();
                    return true;
                }
                return mDrawerListView.onKeyUp(keyCode, event);
            }
        });
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mNavigationAdapter != null) {
            mNavigationAdapter.setItemChecked(position);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }

        setTitle(position);
        updateTitle(mTitle);
    }

    private void setTitle(int position) {
        switch (position) {
            case 0:
                mTitle = getString(R.string.navigation_item_store_home);
                break;
            case 1:
                mTitle = "公告栏";
                break;
        }
    }

    private void updateTitle(CharSequence title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

    private void selectSettings() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onSettingsClick();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
            updateTitle(getActivity().getTitle());
        } else if (mDrawerLayout != null && !mDrawerLayout.isDrawerOpen(mFragmentContainerView)) {
            updateTitle(mTitle);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoginSuccess(OALoginResponse oaLoginResponse) {
        mNavigationHeaderView.setContent(oaLoginResponse.getUserName(), oaLoginResponse.getAccount());
    }

    @Override
    public void Logout() {
        mNavigationHeaderView.setContent("", "");
    }

    private class NavigationAdapter extends BaseAdapter {

        private Context mContext;

        private String[] content;

        private int index;

        public NavigationAdapter(Context context, String[] text) {
            mContext = context;
            content = text;
        }

        @Override
        public int getCount() {
            return content.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.navigation_list_item, null);
                viewHolder.textView = (TextView) convertView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(content[position]);

            if (index == position) {
                viewHolder.textView.setTextColor(getResources().getColor(R.color.theme_primary));
            } else {
                viewHolder.textView.setTextColor(getResources().getColor(R.color.abc_primary_text_material_light));
            }

            return convertView;
        }

        private class ViewHolder {

            TextView textView;
        }

        public void setItemChecked(int index) {
            this.index = index;
        }
    }
}
