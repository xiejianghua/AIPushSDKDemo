package com.blocktree.sdk.ainotificationkit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.blocktree.sdk.aipushkit.AIComponent;
import com.blocktree.sdk.aipushkit.AIPush;
import com.blocktree.sdk.aipushkit.http.BaseCallBack;
import com.blocktree.sdk.aipushkit.mode.AIHttpResult;
import com.blocktree.sdk.aipushkit.mode.AINotificationMessage;
import com.blocktree.sdk.aipushkit.mode.AIWebSocketResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * xiezuofei
 * 2017-09-20 17:30
 * 793169940@qq.com
 * 消息推送
 */
public class AINotification implements AIComponent {
    // 监听组件
    private List<AINotificationListener> listeners=new ArrayList<AINotificationListener>();
    private Handler mHandler;
    //创建单列
    private volatile static AINotification instance;
    public static AINotification getInstance(){
        if(instance==null){
            synchronized(AINotification.class){
                if(instance==null){
                    instance=new AINotification();
                }
            }
        }
        return instance;
    }
    private AINotification(){
        AIPush.getInstance().addComponent(this);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public String getKey() {
        return "AINotification";
    }

    @Override
    public List<String> method() {
        return new ArrayList<String>(Arrays.asList("setAliasAndTags", "registerDeviceToken", "didReceiveAINotification"));
    }

    @Override
    public boolean distributePacket(final AIPush aiPush,final AIWebSocketResult aiWebSocketResult) {
        if (aiWebSocketResult==null||aiWebSocketResult.getBody().length()<1){
            return false;
        }
        AINotificationMessage aiNotificationMessage=new AINotificationMessage(aiWebSocketResult.getBody());
        responsePushResult(aiNotificationMessage.msgId);
        for(AINotificationListener listener:listeners){
            listener.didReceivedAINotification(aiNotificationMessage);
        }
        /*mHandler.post(new Runnable() {
            @Override
            public void run() {

            }
        });*/
        return true;
    }

    @Override
    public void networkDidChanged(AIPush aiPushs) {
        //网络状态变更回调
        for(AINotificationListener listener:listeners){
            listener.didConnectionChangedAINotification(aiPushs.isConnected());
        }
    }
    public void setupWithOption(Context mContext, String appKey, String appSecret, String uuid, String bundleid){
        //添加组件
        AIPush.getInstance().addComponent(this);
        AIPush.getInstance().setupWithOption(mContext,appKey,appSecret,uuid,bundleid);
    }
    /**
     *接收推送结果反馈
     * @param msgId 消息id
     */
    public void responsePushResult(String msgId) {
        Map<String, String> params=new HashMap<String, String>();
        params.put("msgId",msgId);
        params.put("timestamp",System.currentTimeMillis()+"");
        AIPush.getInstance().sendRequest("responsePushResult", params, new BaseCallBack() {
            @Override
            public void  onResult(final AIHttpResult aiHttpResult) {

            }
        });
    }
    /**
     * 设置推送设备别名和分组
     * @param  alias  别名
     * @param  tags   分组数组
     */
    public void setAliasAndTags(String alias,String tags,BaseCallBack baseCallBack) {
        Map<String, String> params=new HashMap<String, String>();
        params.put("alias",alias);
        params.put("tags",tags);
        AIPush.getInstance().sendRequest("setAliasAndTags", params,baseCallBack);
    }

    // 添加监听者，退出监听必须执行removeListener
    // 添加监听后，在监听者要销毁前必须先退出监听组，因为监听者与组件之间是强引用，不然会泄漏内存
    public void addListener(AINotificationListener listener) {
        listeners.add(listener);
    }


    // 移除监听者
    // 在监听者要销毁前必须先退出监听组
    public void removeListener(AINotificationListener listener) {
        listeners.remove(listener);
    }

}
