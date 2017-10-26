package com.blocktree.sdk.aipushkit.mode;

import com.blocktree.sdk.aipushkit.sugarorm.SugarRecord;

/**
 * xiezuofei
 * 2017-09-20 13:50
 * 793169940@qq.com
 * 群组对象
 */
public class AIGroupMember extends SugarRecord {
    private String memberid="";//群组成员id
    private String memberName="";//成员在群的名称
    private String role="";//成员角色，0：普通，1：管理员，2：群主
    private String joinTime="";//入群时间
    private String lastSendMsgTime="";//最后发送消息的时间
    private AIUserInfo userInfo=new AIUserInfo();//成员的个人信息

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public String getLastSendMsgTime() {
        return lastSendMsgTime;
    }

    public void setLastSendMsgTime(String lastSendMsgTime) {
        this.lastSendMsgTime = lastSendMsgTime;
    }

    public AIUserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(AIUserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "AIGroupMember{" +
                "memberid='" + memberid + '\'' +
                ", memberName='" + memberName + '\'' +
                ", role='" + role + '\'' +
                ", joinTime='" + joinTime + '\'' +
                ", lastSendMsgTime='" + lastSendMsgTime + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
