package com.blocktree.sdk.ainotificationkit;


import com.blocktree.sdk.aipushkit.mode.AINotificationMessage;

/**
 * xiezuofei
 * 2017-09-20 18:50
 * 793169940@qq.com
 *  消息通知监听器
 */
public interface AINotificationListener {
    /**
     * 消息通知监听回调方法
     * @param  message 消息通知数据
     */
    void didReceivedAINotification(AINotificationMessage message);


    /**
     * 连接服务器状态
     * @param isConnected 是否连接成功
     */
    void didConnectionChangedAINotification(boolean isConnected);
}
