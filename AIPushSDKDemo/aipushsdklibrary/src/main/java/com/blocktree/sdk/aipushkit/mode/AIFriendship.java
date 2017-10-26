package com.blocktree.sdk.aipushkit.mode;

/**
 * xiezuofei
 * 2017-09-20 13:20
 * 793169940@qq.com
 * 好友关系对象
 */
public class AIFriendship{
    private String relationType="";//好友关系，0：无，1：好友，2：黑名单
    private String addWording="";//加好友附言
    private String addSource="";//来源，手机号搜索，通讯录等等
    private String friendNote="";//备注
    private String addType="";//发送类型：0：主动请求加好友，1：被动请求加好友
    private String relationStatus="";//关系状态： 0：等待确认，1：已通过，2：拒绝
    private AIUserInfo friendInfo=new AIUserInfo();//详细信息 查看AIUserInfo
    @Override
    public String toString() {
        return "relationType=" + relationType
                + " addWording=" + addWording
                + " addSource=" + addSource
                + " friendNote=" + friendNote
                + " addType=" + addType
                + " relationStatus=" + relationStatus
                + " friendInfo=" + friendInfo
                +" | ";
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getAddWording() {
        return addWording;
    }

    public void setAddWording(String addWording) {
        this.addWording = addWording;
    }

    public String getAddSource() {
        return addSource;
    }

    public void setAddSource(String addSource) {
        this.addSource = addSource;
    }

    public String getFriendNote() {
        return friendNote;
    }

    public void setFriendNote(String friendNote) {
        this.friendNote = friendNote;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }

    public AIUserInfo getFriendInfo() {
        return friendInfo;
    }

    public void setFriendInfo(AIUserInfo friendInfo) {
        this.friendInfo = friendInfo;
    }
}
