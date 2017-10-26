package com.blocktree.sdk.aipushkit.mode;

import com.blocktree.sdk.aipushkit.sugarorm.SugarRecord;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Unique;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 用户基本信息
 */
public class AIUserInfo extends SugarRecord {
    @Unique
    private String userkey="";//用户ID
    private String username="";//用户名，好友搜索，可以是手机号码，email
    private String nickname="";//可以独立设置，也可以同步自己系统的
    private String avatarUrl="";//头像地址
    private String role="";//自定义角色，数字
    private String msgSettings="0";//消息设置，0：接受，1：不接受
    private String allowType="0";//0：需要经过自己确认才能添加自己为好友；1：允许任何人添加自己为好友；2：不允许任何人添加自己为好友。
    private String extAttr="";//消扩展字段，可以使用json格式
    private String online="";//在线状态，0：不在线，1：在线

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMsgSettings() {
        return msgSettings;
    }

    public void setMsgSettings(String msgSettings) {
        this.msgSettings = msgSettings;
    }

    public String getAllowType() {
        return allowType;
    }

    public void setAllowType(String allowType) {
        this.allowType = allowType;
    }

    public String getExtAttr() {
        return extAttr;
    }

    public void setExtAttr(String extAttr) {
        this.extAttr = extAttr;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "AIUserInfo{" +
                "userkey='" + userkey + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", role='" + role + '\'' +
                ", msgSettings='" + msgSettings + '\'' +
                ", allowType='" + allowType + '\'' +
                ", extAttr='" + extAttr + '\'' +
                ", online='" + online + '\'' +
                '}';
    }
}
