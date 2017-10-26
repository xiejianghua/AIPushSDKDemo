package com.blocktree.sdk.aipushkit.http;

import android.os.Handler;
import android.os.Looper;

import com.blocktree.sdk.aipushkit.AIConstants;
import com.blocktree.sdk.aipushkit.mode.AIAttachment;
import com.blocktree.sdk.aipushkit.mode.AIHttpResult;
import com.blocktree.sdk.aipushkit.mode.AIMsg;
import com.blocktree.sdk.aipushkit.mode.AIUserInfo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * xiezuofei
 * 2017-09-17 17:50
 * 793169940@qq.com
 * 网络请求
 */
public class AIHttpMethods {
    //超时时间60s
    public static final int DEFAULT_TIMEOUT = 60;
    private OkHttpClient mHttpClient;
    private Handler mHandler;

    //创建单列
    private volatile static AIHttpMethods instance;
    public static AIHttpMethods getInstance(){
        if(instance==null){
            synchronized(AIHttpMethods.class){
                if(instance==null){
                    instance=new AIHttpMethods();
                }
            }
        }
        return instance;
    }
    private AIHttpMethods(){
        init();
    }
    //初始化
    private void init(){
        mHttpClient = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = mHttpClient.newBuilder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(loggingInterceptor);
        mHttpClient = builder.build();

        //Looper.getMainLooper()  获取主线程的消息队列
        mHandler = new Handler(Looper.getMainLooper());
    }
    /**
     * 发送请求
     * @param aihttpMethod 请求发送
     * @param url 链接
     * @param params 参数
     * @param baseCallBack 回调
     */
    public void sendRequest(AIConstants.AIHTTPMethod aihttpMethod, String url, Map<String, String> params, final BaseCallBack baseCallBack) {
        final Request request = buildRequest(aihttpMethod,url,params);
        mHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                callBackFail(baseCallBack,call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    AIHttpResult aiHttpResult=new AIHttpResult();
                    aiHttpResult.jsonToString(json);
                    //此时请求结果在子线程里面，如何把结果回调到主线程里？
                    callBackSuccess(baseCallBack,call,response, aiHttpResult);
                } else {
                    callBackError(baseCallBack,call,response, response.code());
                }
            }
        });
    }
    /**
     * 获取Request对象
     * @param aihttpMethod 请求发送
     * @param url 链接
     * @param params 参数
     */
    private Request buildRequest(AIConstants.AIHTTPMethod aihttpMethod, String url, Map<String, String> params) {
        //获取辅助类对象
        Request.Builder builder = new Request.Builder();
        //如果是post
        if (aihttpMethod == AIConstants.AIHTTPMethod.post) {
            builder.url(url);
            RequestBody body = buildFormData(params);
            builder.post(body);
        } else{
            builder.url(url);
            builder.get();
        }
        //返回请求对象
        return builder.build();
    }

    /**
     * 主要用于构建请求参数
     *
     * @param params
     * @return ResponseBody
     */
    private RequestBody buildFormData(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        FormBody.Builder builder = new FormBody.Builder();
       for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null) {
              value = "";
            } else {
               value = entry.getValue().toString();
            }
            builder.add(key, value);
       }
       return builder.build();
    }
    //主要用于子线程和主线程进行通讯
    private void callBackSuccess(final BaseCallBack baseCallBack, final Call call , final Response response, final AIHttpResult aiHttpResult){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onResult(aiHttpResult);
            }
        });
    }


    private void callBackError(final BaseCallBack baseCallBack, final Call call, final Response response, final int code){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AIHttpResult aiHttpResult=new AIHttpResult();
                AIMsg aiMsg=new AIMsg();
                aiMsg.setCode(AIConstants.AIApiResultCode.responseError.getCode());
                aiMsg.setMessage("APIResponseError");
                aiHttpResult.setResMsg(aiMsg);
                baseCallBack.onResult(aiHttpResult);
            }
        });
    }

    private void callBackFail(final BaseCallBack baseCallBack, final Call call, final IOException e){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AIHttpResult aiHttpResult=new AIHttpResult();
                AIMsg aiMsg=new AIMsg();
                aiMsg.setCode(AIConstants.AIApiResultCode.responseError.getCode());
                aiMsg.setMessage("APIResponseError");
                aiHttpResult.setResMsg(aiMsg);
                baseCallBack.onResult(aiHttpResult);
            }
        });
    }







    /**
     * 设备注册
     * @param uuid 设备唯一标识符
     * @param bundleid  项目的包唯一路径
     * @param apnsToken 苹果设备推送token，android没有
     * @param deviceOs  设备系统
     * @param deviceModel 设备型号
     *
     * @return regid uuid唯一设备只生成唯一的regid，可以使用此id对单个设备发送消息
     */
    public void registerDevice(String uuid,String bundleid,String apnsToken,String deviceOs,String deviceModel){

    }

    /**
     * @建立长连接
     * @param uuid 唯一标识
     * @param timestamp 时间戳，防止重复连接
     */
    public void bindServer(String uuid,String timestamp){

    }

    /**
     * 设备设置APNS的deviceToken
     * @param apnsToken 苹果设备推送token，android没有
     * @param uuid 唯一标识
     */
    public void registerDeviceToken(String apnsToken,String uuid){

    }



    /**
     * 登录用户
     * @param userid 用户在平台的唯一标识
     * @param usersig 用户签名，由应用服务端使用appsecret签名用户信息得到
     *
     * @return  expiredTime 过期时间，应用请在过期时间前重新登录
     * @return  AIUserInfo userInfo  个人信息对象
     */
    public void login(String userid,String usersig){

    }

    /**
     * 登出用户
     */
    public void logout(String userid,String usersig){

    }
    /**
     * 获取个人信息
     *
     * @return  AIUserInfo userInfo  个人信息对象
     */
    public void getUserInfo(){

    }
    /**
     * 修改个人信息
     * @param aiUserInfo
     *
     */
    public void setUserInfo(AIUserInfo aiUserInfo){

    }



    /**
     * 设备设置别名和标签组
     * @param uuid 唯一标识
     * @param alias 别名
     * @param tags 标签分组，['sd','dd','rtt']
     */
    public void setAliasAndTags(String uuid,String alias,String tags){

    }

    /**
     * 接收推送结果反馈
     * @param uuid 设备唯一识别号
     * @param msgid 消息id
     * @param timestamp 到达时间戳
     *
     * @return
     */
    public void responsePushResult(String uuid,String msgid,String timestamp){

    }


    /**
     * 确认消息已阅
     * @param msgSeq 已读的消息序号，对该序号之前的所有消息标记为已读
     * @param objectid 会话对象id
     *
     * @return AIUserInfo userInfo 个人信息
     */
    public void setReadMessage(String msgSeq,String objectid){

    }

    /**
     * 发送消息
     * @param objectids [String]好友id数组,json数组字符串，数组大于1代表群发给多个好友
     * @param messageType 消息类型：1：文本，2：语音，3：图片，4：视频
     * @param content 文本内容，如果带有附件，可以为空
     * @param attachment 附件，多媒体需要
     *
     * @return msgid 消息对象id
     */
    public void sendMessage(String objectids, String messageType, String content, AIAttachment attachment){

    }
    /**
     * 获取离线消息
     * @param objectid 会话对象的id，不传入时，获取全部会话的离线消息
     *
     * @return [AIMessage] aimessages 消息数组
     */
    public void getMessages(String objectid){

    }


    /**
     * 获取用户好友列表
     *
     * @return [AIRelationship]	friends	好友数组
     */
    public void getFriends(){

    }

    /**
     * 查找好友数据
     * @param username 用户在平台的用户名，唯一标识
     *
     * @return AIRelationship friend	好友对象
     */
    public void searchNewFriend(String username){

    }

    /**
     * 请求添加好友
     * @param friendid 对方的id
     * @param addWording 请求消息
     * @param friendNote 好友备注
     *
     */
    public void requestNewFriend(String friendid,String addWording,String friendNote){

    }

    /**
     * 处理好友申请
     * @param friendid 对方的id
     * @param relationStatus 是	1：同意，2：拒绝，
     * @param message 拒绝消息
     *
     */
    public void responseNewFriend(String friendid,String relationStatus,String message){

    }

    /**
     * 获取好友请求添加记录
     *
     * @return [AIRelationship]	friendRequsets	好友添加请求记录
     */
    public void getNewFriendRequests(){

    }


    /**
     * 上传附件
     * @param key	文件名
     * @param mimeType 附件文件格式，如："image/jpeg"，"text/plain"
     * @param the_file  文件数据，表单的File类型
     *
     * @return AIAttachment	attachment	附件信息
     */
    public void uploadAttachment(String key,String mimeType,String the_file){


    }



}
