package com.blocktree.sdk.aipushkit;

import com.blocktree.sdk.aipushkit.mode.AIWebSocketResult;

import java.util.List;

/**
 * xiezuofei
 * 2017-09-20 18:50
 * 793169940@qq.com
 *  SDK业务组件接口，如消息通信组件，即时聊天组件都实现这个接口
 */
public interface AIComponent {

    // 组件的唯一表示名字，用于查找
    String getKey();

    // 组件支持的方法指令组，用于分发业务
    List<String> method();

    // 根据监听器类型，分发数据给监听者处理
    boolean distributePacket(AIPush aiPush, AIWebSocketResult aiWebSocketResult);


    /// 网络状态变更回调
    void networkDidChanged(AIPush aiPushs);
}
