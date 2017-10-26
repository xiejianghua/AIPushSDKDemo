package com.blocktree.sdk.aipushkit.mode;

import com.blocktree.sdk.aipushkit.sugarorm.SugarRecord;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Column;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Unique;
import com.blocktree.sdk.aipushkit.sugarorm.helper.NamingHelper;

import java.util.List;

/**
 * xiezuofei
 * 2017-09-20 13:20
 * 793169940@qq.com
 * 聊天消息对象
 */
public class AIChatMessage extends SugarRecord {
    @Unique
    private String msgid = "";//消息id
    @Column(name = "objectkey")
    private String objectkey = "";//会话对象id
    private String msgType = "";//消息类型 1：文本，2：语音，3：图片，4，交易记录，6：多重签名请求
    private String title = "";//标题
    private String content = "";//消息内容
    private String senderkey = "";//发送者id
    private String receiverkey = "";//接收送者id
    private String receiverType = "";//接收者类型，0：单聊，1：群组
    private String receiver = "";//接收送者昵称
    private String sender = "";//发送者昵称
    @Column(name = "sendTime")
    private String sendTime = "";//发送时间
    private String receiveTime = "";//接收时间
    private String isAttachment = "";//是否附件 0：否，1：是
    private String attachmentid = "";//附件id

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getObjectkey() {
        return objectkey;
    }

    public void setObjectkey(String objectkey) {
        this.objectkey = objectkey;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderkey() {
        return senderkey;
    }

    public void setSenderkey(String senderkey) {
        this.senderkey = senderkey;
    }

    public String getReceiverkey() {
        return receiverkey;
    }

    public void setReceiverkey(String receiverkey) {
        this.receiverkey = receiverkey;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(String isAttachment) {
        this.isAttachment = isAttachment;
    }

    public String getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(String attachmentid) {
        this.attachmentid = attachmentid;
    }

    @Override
    public String toString() {
        return "AIChatMessage{" +
                "msgid='" + msgid + '\'' +
                ", objectkey='" + objectkey + '\'' +
                ", msgType='" + msgType + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", senderkey='" + senderkey + '\'' +
                ", receiverkey='" + receiverkey + '\'' +
                ", receiverType='" + receiverType + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sender='" + sender + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                ", isAttachment='" + isAttachment + '\'' +
                ", attachmentid='" + attachmentid + '\'' +
                '}';
    }

    public static void saveOne(AIChatMessage aiChatMessage) {
        if (aiChatMessage != null) {
            AIChatMessage.save(aiChatMessage);
        }
    }
    public static void saveALL(List<AIChatMessage> aiChatMessageList, String objectkey) {
        if (aiChatMessageList != null) {
            for(AIChatMessage aiChatMessage:aiChatMessageList){
                aiChatMessage.setObjectkey(objectkey);
                AIChatMessage.save(aiChatMessage);
            }
        }
    }
    public static List<AIChatMessage> findObjectkey(String objectkey) {
        //DESC  ASC
        return AIConversation.findWithQuery(AIChatMessage.class,
                "SELECT * FROM " + NamingHelper.toTableName(AIChatMessage.class) + " WHERE objectkey = ? ORDER BY sendTime ASC", objectkey);
    }

    public static AIChatMessage findLast(String objectkey) {
        List<AIChatMessage> aiChatMessageList = AIConversation.findWithQuery(AIChatMessage.class,
                "SELECT * FROM " + NamingHelper.toTableName(AIChatMessage.class) + " WHERE objectkey = ? ORDER BY sendTime DESC LIMIT 1", objectkey);
        AIChatMessage aiChatMessage = null;
        if (aiChatMessageList != null && aiChatMessageList.size() > 0) {
            aiChatMessage = aiChatMessageList.get(0);
        }
        return aiChatMessage;
    }
}
