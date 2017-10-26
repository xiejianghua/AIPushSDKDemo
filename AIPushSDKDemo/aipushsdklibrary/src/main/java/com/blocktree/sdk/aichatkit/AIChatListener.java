package com.blocktree.sdk.aichatkit;


import com.blocktree.sdk.aipushkit.mode.AIChatMessage;
import com.blocktree.sdk.aipushkit.mode.AIConversation;
import com.blocktree.sdk.aipushkit.mode.AIFriendship;
import com.blocktree.sdk.aipushkit.mode.AIGroup;

/**
 * xiezuofei
 * 2017-09-21 13:20
 * 793169940@qq.com
 * 即时通信监听器
 */
public interface AIChatListener {
    /**
     * 消息通知监听回调方法
     * @param  message 消息通知数据
     */
    void didReceiveAIChat(AIConversation aiConversation, AIChatMessage message);
    /**
     * 连接服务器状态
     * @param isConnected 是否连接成功
     */
    void didConnectionChangedAIChat(boolean isConnected);


    void didReceiveAIFriendshipChange(AIFriendship friendship, String status);

    void didReceiveAIGroupChange(AIGroup group, String status);
}
