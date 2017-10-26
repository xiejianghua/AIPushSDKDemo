package com.blocktree.sdk.aipushkit.mode;

import com.blocktree.sdk.aipushkit.sugarorm.SugarRecord;
import com.blocktree.sdk.aipushkit.sugarorm.annotation.Unique;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 群组对象
 */
public class AIGroup extends SugarRecord {
    @Unique
    private String groupkey="";//群组id
    private String groupType="";//群组类型
    private String groupName="";//群组名称
    private String groupDesc="";//群组简介
    private String groupNotice="";//群组公告
    private String groupAvatarUrl="";//群组头像
    private AIGroupMember owner=new AIGroupMember();//群主信息
    private String createTime="";//创建时间
    private String lastInfoTime="";//群组最后一次信息变更时间
    private String lastMsgTime="";//群组内最后发消息的时间
    private String memberNum="";//当前成员数量
    private String maxMemberNum="";//最大成员数量
    private String applyJoinOption="";//0：任何人都可加入，1：需群主审核通过，2：禁止任何人加入

    public String getGroupkey() {
        return groupkey;
    }

    public void setGroupkey(String groupkey) {
        this.groupkey = groupkey;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGroupNotice() {
        return groupNotice;
    }

    public void setGroupNotice(String groupNotice) {
        this.groupNotice = groupNotice;
    }

    public String getGroupAvatarUrl() {
        return groupAvatarUrl;
    }

    public void setGroupAvatarUrl(String groupAvatarUrl) {
        this.groupAvatarUrl = groupAvatarUrl;
    }

    public AIGroupMember getOwner() {
        return owner;
    }

    public void setOwner(AIGroupMember owner) {
        this.owner = owner;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastInfoTime() {
        return lastInfoTime;
    }

    public void setLastInfoTime(String lastInfoTime) {
        this.lastInfoTime = lastInfoTime;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(String memberNum) {
        this.memberNum = memberNum;
    }

    public String getMaxMemberNum() {
        return maxMemberNum;
    }

    public void setMaxMemberNum(String maxMemberNum) {
        this.maxMemberNum = maxMemberNum;
    }

    public String getApplyJoinOption() {
        return applyJoinOption;
    }

    public void setApplyJoinOption(String applyJoinOption) {
        this.applyJoinOption = applyJoinOption;
    }

    @Override
    public String toString() {
        return "AIGroup{" +
                "groupkey='" + groupkey + '\'' +
                ", groupType='" + groupType + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", groupNotice='" + groupNotice + '\'' +
                ", groupAvatarUrl='" + groupAvatarUrl + '\'' +
                ", owner=" + owner +
                ", createTime='" + createTime + '\'' +
                ", lastInfoTime='" + lastInfoTime + '\'' +
                ", lastMsgTime='" + lastMsgTime + '\'' +
                ", memberNum='" + memberNum + '\'' +
                ", maxMemberNum='" + maxMemberNum + '\'' +
                ", applyJoinOption='" + applyJoinOption + '\'' +
                '}';
    }
}
