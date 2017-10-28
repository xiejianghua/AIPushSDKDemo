package com.blocktree.sdk.aipushkit.http;

import android.content.Context;
import android.content.Intent;

import com.blocktree.sdk.aipushkit.AILog;
import com.blocktree.sdk.aipushkit.AIPush;
import com.blocktree.sdk.aipushkit.common.DeviceUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.ByteString;

/**
 * xiezuofei
 * 2017-09-19 17:20
 * 793169940@qq.com
 * WebSocket
 */
public class AIWebSocket{
    private static boolean _isConnected= false;
    private static WebSocket mWebSocket=null;
    private static Context mContext;
    public boolean is_isConnected() {
        return _isConnected;
    }

    //创建单列
    private volatile static AIWebSocket instance;
    public static AIWebSocket getInstance(){
        if(instance==null){
            synchronized(AIWebSocket.class){
                if(instance==null){
                    instance=new AIWebSocket();
                }
            }
        }
        return instance;
    }
    public void bindServer(){
        /*if(_isConnected&&sendMessage("")){
            return;
        }*/
        if(_isConnected||!DeviceUtils.isNetworkAvailable(mContext)){
            return;
        }
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        String params_str= AIPush.getInstance().urlParams(AIPush.getInstance().configAuthParams());
        Request request = new Request.Builder().url(AIPush.getInstance().getApiType().wsUrl() + "bindServer" + params_str).build();
        mHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                AILog.e("onOpen>>>>>>>",response+"");
                _isConnected=true;
                mWebSocket=webSocket;

                Intent alarmIntent = new Intent();
                alarmIntent.setAction(AIPush.WebSocketConnectedReceiver.CONNECTED_ACTION);
                alarmIntent.putExtra("connected",_isConnected);
                AIPush.getInstance().getmContext().sendBroadcast(alarmIntent);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                AILog.e("onMessage>>>>>>>",text+"");
                Intent alarmIntent = new Intent();
                alarmIntent.setAction(AIPush.WebSocketMessageReceiver.MESSAGE_ACTION);
                alarmIntent.putExtra("message",text);
                AIPush.getInstance().getmContext().sendBroadcast(alarmIntent);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                AILog.e("onClosing>>>>>>>",reason+"");
                _isConnected=false;
                webSocket.close(1000, null);

                Intent alarmIntent = new Intent();
                alarmIntent.setAction(AIPush.WebSocketConnectedReceiver.CONNECTED_ACTION);
                alarmIntent.putExtra("connected",_isConnected);
                AIPush.getInstance().getmContext().sendBroadcast(alarmIntent);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                AILog.e("onFailure>>>>>>>",response+"");
                _isConnected=false;
                bindServer();

                Intent alarmIntent = new Intent();
                alarmIntent.setAction(AIPush.WebSocketConnectedReceiver.CONNECTED_ACTION);
                alarmIntent.putExtra("connected",_isConnected);
                AIPush.getInstance().getmContext().sendBroadcast(alarmIntent);
            }
        });
        mHttpClient.dispatcher().executorService().shutdown();
    }

    /**
     *  关闭WebSocket
     *
     * @return
     */
    public boolean closeWebSocket(){
        _isConnected=false;
        if (mWebSocket!=null){
            return mWebSocket.close(1000,"主动关闭");
        }
        return false;
    }
    /**
    *  发送消息
    *
    * @return
    */
    public boolean sendMessage(String message){
        if (mWebSocket!=null&&DeviceUtils.isNetworkAvailable(mContext)){
            return mWebSocket.send(message);
        }
        return false;
   }
}