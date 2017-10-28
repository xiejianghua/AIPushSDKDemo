package com.blocktree.sdk.aipushkit.mode;


import com.blocktree.sdk.aipushkit.sugarorm.SugarRecord;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Column;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Unique;
import com.blocktree.sdk.aipushkit.sugarorm.helper.NamingHelper;

import java.util.List;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 会话
 */
public class AIConversation extends SugarRecord {
    @Unique
    @Column(name = "objectkey")
    private String objectkey="";//会话对象id
    private String conversationid="";
    private String conversationType="";//会话类型，0：单聊，1：群组
    private String conversationName="";//会话名称，单聊为对方昵称，群聊为群名称
    private String number="";//会话编号
    private String isTop="";//会话是是否置顶，0：不置顶，1置顶
    private String toptime="";//置顶时间
    @Column(name = "lastSendMsgTime")
    private String lastSendMsgTime="";//最后一次会话时间
    private String lastSendMsgid="";//最近一条消息id
    private String unreadCount="";//消息未阅数
    private AIUserInfo userInfo=new AIUserInfo();//单聊：AIUserInfo，
    private AIGroup groupInfo=new AIGroup();//群聊：model为AIGroup
    private AIChatMessage lastChatMessage=new AIChatMessage();//最后消息对象（可选）

    public String getObjectkey() {
        return objectkey;
    }

    public void setObjectkey(String objectkey) {
        this.objectkey = objectkey;
    }

    public String getConversationid() {
        return conversationid;
    }

    public void setConversationid(String conversationid) {
        this.conversationid = conversationid;
    }

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getToptime() {
        return toptime;
    }

    public void setToptime(String toptime) {
        this.toptime = toptime;
    }

    public String getLastSendMsgTime() {
        return lastSendMsgTime;
    }

    public void setLastSendMsgTime(String lastSendMsgTime) {
        this.lastSendMsgTime = lastSendMsgTime;
    }

    public String getLastSendMsgid() {
        return lastSendMsgid;
    }

    public void setLastSendMsgid(String lastSendMsgid) {
        this.lastSendMsgid = lastSendMsgid;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public AIUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(AIUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public AIGroup getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(AIGroup groupInfo) {
        this.groupInfo = groupInfo;
    }

    public AIChatMessage getLastChatMessage() {
        return lastChatMessage;
    }

    public void setLastChatMessage(AIChatMessage lastChatMessage) {
        this.lastChatMessage = lastChatMessage;
    }

    @Override
    public String toString() {
        return "AIConversation{" +
                "objectkey='" + objectkey + '\'' +
                ", conversationid='" + conversationid + '\'' +
                ", conversationType='" + conversationType + '\'' +
                ", conversationName='" + conversationName + '\'' +
                ", number='" + number + '\'' +
                ", isTop='" + isTop + '\'' +
                ", toptime='" + toptime + '\'' +
                ", lastSendMsgTime='" + lastSendMsgTime + '\'' +
                ", lastSendMsgid='" + lastSendMsgid + '\'' +
                ", unreadCount='" + unreadCount + '\'' +
                ", userInfo=" + userInfo +
                ", groupInfo=" + groupInfo +
                ", lastChatMessage=" + lastChatMessage +
                '}';
    }

    public static void saveOne(AIConversation aiConversation){
        if (aiConversation.getLastChatMessage() != null) {
            AIChatMessage.saveOne(aiConversation.getLastChatMessage());
        }
        if (aiConversation.getUserInfo() != null) {
            AIUserInfo.save(aiConversation.getUserInfo());
        }
        if (aiConversation.getGroupInfo() != null) {
            AIGroup.save(aiConversation.getGroupInfo());
        }
        AIConversation.save(aiConversation);
    }
    public static void saveAll(List<AIConversation> aiConversationList){
        if(aiConversationList==null){
            return;
        }
        for (AIConversation aiConversation:aiConversationList) {
            if (aiConversation.getLastChatMessage() != null) {
                aiConversation.getLastChatMessage().setObjectkey(aiConversation.getObjectkey());
                AIChatMessage.saveOne(aiConversation.getLastChatMessage());
            }
            if (aiConversation.getUserInfo() != null) {
                AIUserInfo.save(aiConversation.getUserInfo());
            }
            if (aiConversation.getGroupInfo() != null) {
                AIGroup.save(aiConversation.getGroupInfo());
            }
            AIConversation.save(aiConversation);
        }
    }
    public static List<AIConversation> selectAll(){
        //DESC  ASC
        return AIConversation.findWithQuery(AIConversation.class,
                "SELECT * FROM " + NamingHelper.toTableName(AIConversation.class) + " ORDER BY lastSendMsgTime DESC");
    }
    public static AIConversation getInfo(String objectkey){
        List<AIConversation> aiConversations = AIConversation.findWithQuery(AIConversation.class,
                "SELECT * FROM " + NamingHelper.toTableName(AIConversation.class) + " WHERE objectkey = ? ", objectkey);
        AIConversation aiConversation = null;
        if (aiConversations != null && aiConversations.size() > 0) {
            aiConversation = aiConversations.get(0);
        }
        return aiConversation;
    }
}
