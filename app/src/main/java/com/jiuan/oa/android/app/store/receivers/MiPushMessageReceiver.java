package com.jiuan.oa.android.app.store.receivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.gson.Gson;
import com.jiuan.oa.android.app.store.JiuanStoreApplication;
import com.jiuan.oa.android.app.store.R;
import com.jiuan.oa.android.app.store.noticehttplibrary.MiPushResponse;
import com.jiuan.oa.android.app.store.noticehttplibrary.NoticeTitleResponse;
import com.jiuan.oa.android.app.store.ui.NoticeActivity;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;



import java.util.List;

/**
 * Created by ZhangKong on 2015/7/6.
 */
public class MiPushMessageReceiver extends PushMessageReceiver {
    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {

        String contenttext = "";
        int type = 0;
        String title = "有了新公告";

        String message = miPushMessage.getContent();
        Log.d("接收到的消息是",message);
        Gson gson = new Gson();
        MiPushResponse miPushResponse = gson.fromJson(message,MiPushResponse.class);

        Log.d("消息内容",miPushResponse.getMessage());
        Log.d("消息类型","" + miPushResponse.getType());
        Log.d("公告Content","" + miPushResponse.getContent());

        NoticeTitleResponse noticeTitleResponse = gson.fromJson(miPushResponse.getContent(),NoticeTitleResponse.class);
        Log.d("公告ID",noticeTitleResponse.getTitleID());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_play_store);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder  mbuilder = new NotificationCompat.Builder(context);
        mbuilder.setContentTitle(title).setContentText(contenttext).setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher_play_store).setLargeIcon(bitmap);
        mbuilder.setAutoCancel(true);
        mbuilder.setTicker("考勤异常消息");

        Intent resultintent = new Intent(context, NoticeActivity.class);
        resultintent.putExtra("titleID",noticeTitleResponse.getTitleID());
        //添加判断是从哪个地方跳转到公告栏的参数
        resultintent.putExtra("Type",2);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NoticeActivity.class);
        stackBuilder.addNextIntent(resultintent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(pendingIntent);

        Notification note = mbuilder.build();
        note.defaults |= Notification.DEFAULT_VIBRATE;
        note.defaults |= Notification.DEFAULT_SOUND;
        notificationManager.notify(0,note);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage miPushCommandMessage) {
        Log.d("onCommandResult", "onCommandResult");
        String command =miPushCommandMessage.getCommand();
        List<String> arguments = miPushCommandMessage.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        //   String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log = "";
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (miPushCommandMessage.getResultCode() == ErrorCode.SUCCESS) {
                log = cmdArg1;
            } else {
                log = miPushCommandMessage.getReason();
            }
        }
        Log.v("注册小米推送",log);

        JiuanStoreApplication.saveToken(context, log);
    }
}
