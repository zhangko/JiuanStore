package com.jiuan.oa.android.library.protocol.login;

public interface LoginProtocol {

    public static final String ACTION_LOGIN_ACTIVITY = "com.jiuan.oa.android.library.ui.login.LoginActivity.intent.action.LOGIN";

    public static final String CATEGORY_LOGIN_ACTIVITY = "android.intent.category.DEFAULT";

    public static final String ACTION_LOGIN_SUCCESS = "com.jiuan.oa.android.library.protocol.login.intent.action.LOGIN_SUCCESS";

    public static final String ACTION_EXIT = "com.jiuan.oa.android.library.protocol.login.intent.action.EXIT";

    public static final String PERMISSIONS_LOGIN = "com.jiuan.oa.android.library.protocol.login.permission.BROADCAST";

    /** 登录类型 */
    public static final String LOGIN_TYPE = "login_type";

    /** 测试服务器 */
    public static final String IS_TEST = "is_test";

    /**
     * 开启登录页,如果已经存在人员信息,直接返回人员信息.
     */
    public static final int REQUEST_LOGIN = 0;

    /**
     * 注销,清除人员信息.
     */
    public static final int REQUEST_LOGOUT = 1;

    /**
     * 注销,清除人员信息,然后开启登录页.
     */
    public static final int REQUEST_LOGOUT_AND_LOGIN = 2;

}
