package com.blocktree.sdk.aichatkit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.blocktree.sdk.aipushkit.AIComponent;
import com.blocktree.sdk.aipushkit.AIPush;
import com.blocktree.sdk.aipushkit.http.BaseCallBack;
import com.blocktree.sdk.aipushkit.mode.AIChatMessage;
import com.blocktree.sdk.aipushkit.mode.AIConversation;
import com.blocktree.sdk.aipushkit.mode.AIHttpResult;
import com.blocktree.sdk.aipushkit.mode.AIUserInfo;
import com.blocktree.sdk.aipushkit.mode.AIWebSocketResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * xiezuofei
 * 2017-09-21 13:30
 * 793169940@qq.com
 * 即时通信SDK配置工具
 */
public class AIChat implements AIComponent {
    // 监听组件
    private List<AIChatListener> listeners=new ArrayList<AIChatListener>();
    private Handler mHandler;
    //创建单列
    private volatile static AIChat instance;
    public static AIChat getInstance(){
        if(instance==null){
            synchronized(AIChat.class){
                if(instance==null){
                    instance=new AIChat();
                }
            }
        }
        return instance;
    }
    private AIChat(){
        AIPush.getInstance().addComponent(this);
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public String getKey() {
        return "AIChat";
    }
    /*
    4.6 登录用户 login
    4.7 获取用户好友列表 getFriends
    4.8 查找好友数据 searchNewFriend
    4.9 请求添加好友 requestNewFriend
    4.10 处理好友申请 responseNewFriend
    4.11 获取好友请求添加记录 getNewFriendRequests
    4.12 获取离线消息 getMessages
    4.13 发送消息 sendMessage
    4.14 上传附件 uploadAttachment
    4.15 确认会话已阅 setReadConversation
    4.16 获取个人信息 getUserInfo
    4.17 修改个人信息 setUserInfo
    4.18 登出用户 logout
    4.19 获取TOTP动态验证码 getDynamicCode
    4.20 登录用户并绑定连接（websocket） loginAndBind
    4.21 获取最近会话 getConversations
    4.22 绑定用户与设备 bindUserToDevice
    4.23 结束会话 closeConversation
    4.24 批量获取应用的用户资料 getUsersInApp
    */
    @Override
    public List<String> method() {
        return new
                ArrayList<String>(
                    Arrays.asList(
                            "didReceiveAIChatMessage",
                            "login",
                            "getFriends",
                            "searchNewFriend",
                            "requestNewFriend",
                            "responseNewFriend",
                            "getNewFriendRequests",
                            "getMessages",
                            "sendMessage",
                            "uploadAttachment",
                            "setReadConversation",
                            "getUserInfo",
                            "setUserInfo",
                            "logout",
                            "getDynamicCode",
                            "loginAndBind",
                            "getConversations",
                            "bindUserToDevice",
                            "closeConversation",
                            "getUsersInApp"
                    )
                );
    }

    @Override
    public boolean distributePacket(final AIPush aiPush, final AIWebSocketResult aiWebSocketResult) {
        if (aiWebSocketResult==null||aiWebSocketResult.getBody().length()<1){
            return false;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for(AIChatListener aiChatListener:listeners){
                    //是否是在线消息
                    if(aiWebSocketResult.getMethod().equals("didReceiveAIChatMessage")){
                        AIChatMessage aiChatMessage= AIHttpResult.chatMessage(aiWebSocketResult.getBody());
                        AIConversation aiConversation= AIHttpResult.conversation(aiWebSocketResult.getBody());
                        /*//判断会话是否存在
                        if(aiConversation!=null){
                            if(aiChatMessage!=null){
                                aiConversation.setLastChatMessage(aiChatMessage);
                            }
                            AIConversation.saveOne(aiConversation);
                        }
                        if(aiChatMessage!=null){
                            if(aiConversation!=null){
                                aiChatMessage.setObjectkey(aiConversation.getObjectkey());
                                AIChatMessage.saveOne(aiChatMessage);
                            }
                        }*/
                        aiChatListener.didReceiveAIChat(aiConversation,aiChatMessage);


                    }
                }
            }
        });
        return true;
    }

    @Override
    public void networkDidChanged(AIPush aiPush) {
        //监听服务器状态
        for(AIChatListener aiChatListener:listeners){
            aiChatListener.didConnectionChangedAIChat(aiPush.isConnected());
        }
    }
    public void setupWithOption(Context mContext, String appKey, String appSecret, String uuid, String bundleid){
        //添加组件
        AIPush.getInstance().addComponent(this);
        AIPush.getInstance().setupWithOption(mContext,appKey,appSecret,uuid,bundleid);
    }
    private void sendRequest(String method,Map<String, String> params,BaseCallBack response){
        AIPush.getInstance().sendRequest(method, params, response);
    }
    /**
     *  4.6 登录用户 login
     * @param userkey
     * @param username  用户在应用服务的登录用户名（如：邮件，手机）
     * @param nickname  用户在应用服务的昵称
     * @param avatarUrl 用户头像的url
     * @param role      自定义角色，数字
     */
    public void login(String userkey,String username, String nickname, String avatarUrl, String role, BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("userkey",userkey);
        params.put("username",username);
        params.put("nickname",nickname);
        params.put("avatarUrl",avatarUrl);
        params.put("role",role);
        sendRequest("login", params, baseCallBack);
    }

    /**
     *  4.20 登录用户并绑定连接 （websocket） loginAndBind
     *
     * @param username  用户在应用服务的登录用户名（如：邮件，手机）
     * @param nickname  用户在应用服务的昵称
     * @param avatarUrl 用户头像的url
     * @param role      自定义角色，数字
     */
    public void loginAndBind(String username, String nickname, String avatarUrl, String role, BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("loginAndBind", params, baseCallBack);
    }

    /**
     *  4.22 绑定用户与设备
     */
    public void bindUserToDevice(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("bindUserToDevice", params, baseCallBack);
    }

    /**
     *  4.18 登出用户
     */
    public void logout(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("logout", params, baseCallBack);
    }

    /**
     * 4.16 获取个人信息
     */
    public void getUserInfo(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("getUserInfo", params, baseCallBack);
    }

    /**
     * 4.17 修改个人信息
     */
    public void setUserInfo(AIUserInfo userInfo, BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("setUserInfo", params, baseCallBack);
    }
    /**
     * 4.24 批量获取应用的用户资料
     * @param userkeys	[String] 用户key值，每次限制50个
     * @param role	  角色参数，有应用进行定义
     */
    public void getUsersInApp(String userkeys,String role,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("userkeys",userkeys);
        params.put("role",role);
        sendRequest("getUsersInApp", params, baseCallBack);
    }

    /**
     * 4.7 获取用户好友列表
     */
    public void getFriends(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("getFriends", params, baseCallBack);
    }
    /**
     * 4.8 查找好友数据
     * @param username 用户在平台的用户名，唯一标识
     */
    public void searchNewFriend(String username,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("username",username);
        sendRequest("searchNewFriend", params, baseCallBack);
    }
    /**
     * 4.9 请求添加好友
     * @param friendkey    对方的id
     * @param addWording   请求消息
     * @param friendNote   好友备注
     */
    public void requestNewFriend(String friendkey,String addWording,String friendNote,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("friendkey",friendkey);
        params.put("addWording",addWording);
        params.put("friendNote",friendNote);
        sendRequest("requestNewFriend", params, baseCallBack);
    }
    /**
     * 4.10 处理好友申请
     * @param friendkey       对方的id
     * @param relationStatus  1：同意，2：拒绝，
     * @param message         拒绝消息
     */
    public void responseNewFriend(String friendkey,String relationStatus,String message,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("friendkey",friendkey);
        params.put("relationStatus",relationStatus);
        params.put("message",message);
        sendRequest("responseNewFriend", params, baseCallBack);
    }
    /**
     * 4.11 获取好友请求添加记录
     */
    public void getNewFriendRequests(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("getNewFriendRequests", params, baseCallBack);
    }

    /**
     * 4.12 获取离线消息
     * @param lastMsgid        获取的少于最后一条消息id的消息，如果传空，从最新的消息开始读取
     * @param count            指定获取消息的数量
     * @param objectkey        会话对象key，如果为空则返回全部消息，不论接会话对象
     * @param conversationType 会话类型，0：单聊，1：群组
     */
    public void getMessages(String lastMsgid,String count,String objectkey,String conversationType,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("lastMsgid",lastMsgid);
        params.put("count",count);
        params.put("objectkey",objectkey);
        params.put("conversationType",conversationType);
        sendRequest("getMessages", params, baseCallBack);
    }
    /**
     * 4.13 发送消息
     * @param objectkey     好友或群组的key
     * @param objectType	发送对象类型,，0：单聊，1：群聊
     * @param messageType	消息类型： 1：文本，2：语音，3：图片，4：视频
     * @param content	    文本内容，如果带有附件，可以为空
     * @param attachment	AIAttachment	否	附件，多媒体需要
     */
    public void sendMessage(String objectkey,String objectType,String messageType,String content, String attachment, BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("objectkey",objectkey);
        params.put("objectType",objectType);
        params.put("messageType",messageType);
        params.put("content",content);
        params.put("attachment",attachment);
        sendRequest("sendMessage", params, baseCallBack);
    }

    /**
     * 4.15 确认会话已阅
     * @param conversationid     会话id
     */
    public void setReadConversation(String conversationid,String number,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("conversationid",conversationid);
        params.put("number",number);
        sendRequest("setReadConversation", params, baseCallBack);
    }

    /**
     * 4.23 结束会话
     * @param conversationid     会话id
     */
    public void closeConversation(String conversationid,String number,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("conversationid",conversationid);
        params.put("number",number);
        sendRequest("closeConversation", params, baseCallBack);
    }

    /**
     * 4.21 获取最近会话
     * @param count	限制会话条数，最大50
     */
    public void getConversations(String count,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("count",count);
        sendRequest("getConversations", params, baseCallBack);
    }



    /**
     * 4.14 上传附件 uploadAttachment
     * @param key      文件名
     * @param mimeType 附件文件格式，如：”image/jpeg”，”text/plain”
     * @param the_file 文件数据，表单的File类型
     */
    public void uploadAttachment(String key, String mimeType, String the_file,BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        params.put("key",key);
        params.put("mimeType",mimeType);
        sendRequest("uploadAttachment", params, baseCallBack);
    }
    /**
     * 4.19 获取TOTP动态验证码
     */
    public void getDynamicCode(BaseCallBack baseCallBack){
        Map<String, String> params=new HashMap<String, String>();
        sendRequest("getDynamicCode", params, baseCallBack);
    }






    // 添加监听者，退出监听必须执行removeListener
    // 添加监听后，在监听者要销毁前必须先退出监听组，因为监听者与组件之间是强引用，不然会泄漏内存
    public void addListener(AIChatListener listener) {
        listeners.add(listener);
    }


    // 移除监听者
    // 在监听者要销毁前必须先退出监听组
    public void removeListener(AIChatListener listener) {
        listeners.remove(listener);
    }

}
