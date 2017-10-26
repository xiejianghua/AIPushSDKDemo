package com.blocktree.sdk.aipushkit.receiver;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.IBinder;

import com.blocktree.sdk.aipushkit.AILog;
import com.blocktree.sdk.aipushkit.http.AIWebSocket;

/**
 * xiezuofei
 * 2017-10-16 17:50
 * 793169940@qq.com
 * 接收广播
 */
public class WakeReceiver extends BroadcastReceiver {

    private final static String TAG = WakeReceiver.class.getSimpleName();
    private final static int WAKE_SERVICE_ID = -1111;
    /**
     * 灰色保活手段唤醒广播的action
     */
    public final static String GRAY_WAKE_ACTION = "com.blocktree.sdk.aipushkit.gray";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (GRAY_WAKE_ACTION.equals(action)|| ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            AILog.e(TAG, "wake !! wake !! ");
            Intent wakeIntent = new Intent(context, WakeNotifyService.class);
            context.startService(wakeIntent);
        }
    }

    /**
     * 用于其他进程来唤醒UI进程用的Service
     */
    public static class WakeNotifyService extends Service {

        @Override
        public void onCreate() {
            AILog.e(TAG, "WakeNotifyService->onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            AILog.e(TAG, "WakeNotifyService->onStartCommand");
            if (Build.VERSION.SDK_INT < 18) {
                startForeground(WAKE_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
            } else {
                Intent innerIntent = new Intent(this, WakeGrayInnerService.class);
                startService(innerIntent);
                startForeground(WAKE_SERVICE_ID, new Notification());
            }
            AIWebSocket.getInstance().bindServer();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            AILog.e(TAG, "WakeNotifyService->onDestroy");
            super.onDestroy();
        }

    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class WakeGrayInnerService extends Service {

        @Override
        public void onCreate() {
            AILog.e(TAG, "InnerService -> onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            AILog.e(TAG, "InnerService -> onStartCommand");
            startForeground(WAKE_SERVICE_ID, new Notification());
            //stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            AILog.e(TAG, "InnerService -> onDestroy");
            super.onDestroy();
        }
    }
}
