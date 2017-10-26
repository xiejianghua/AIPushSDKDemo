package com.blocktree.sdk.aipushkit.mode;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * http请求返回的数据
 */
public class AIHttpObjectResult {
    private String msgid="";
    private String sendTime="";

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "AIHttpObjectResult{" +
                "msgid='" + msgid + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
