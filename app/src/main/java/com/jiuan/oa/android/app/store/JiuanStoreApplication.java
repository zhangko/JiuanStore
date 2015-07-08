package com.jiuan.oa.android.app.store;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

public class JiuanStoreApplication extends Application {

    /**
     * 创建小米推送服务
     */

    public final static String TOKEN_PREFERENCE = "token_request";

    public static final String APP_ID = "2882303761517355512";

    //public static final String APP_ID = "2882303761517355893";

    public static final String APP_KEY = "5731735594512";

   // public static final String APP_KEY = "5141735586893";

    private static final String TAG = "小米推送";

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        registertoken();
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        /*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();*/

        ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(context);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    //注册小米推送token
    private void registertoken(){
        Log.d("InfoApplication", "onCreate");
        //初始化push推送服务
        if(shouldInit()) {
            Log.d("InfoApplication","shouldInit");
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
            // MiPushClient.registerPush(this, APP_ID_TEST, APP_KEY_TEST);
        }
        //打开Log
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    /**
     * 保存登陆人员的token值
     */
    public static void saveToken(Context context,String token){
        Log.v("注册小米推送的token", "成功" + token);
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putString("token",token);
        prefsEditor.commit();


    }

    public static String LoadToken(Context context){
        SharedPreferences preferences = context.getSharedPreferences(TOKEN_PREFERENCE,Context.MODE_PRIVATE);
        //  SharedPreferences.Editor prefsEditor = preferences.edit();
        String token = preferences.getString("token","");
        return token;


    }
}
