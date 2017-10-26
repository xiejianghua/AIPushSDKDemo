package com.blocktree.sdk.aipushkit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.blocktree.sdk.aipushkit.common.SharedPreferences;
import com.blocktree.sdk.aipushkit.common.SystemConfig;
import com.blocktree.sdk.aipushkit.http.AIHttpMethods;
import com.blocktree.sdk.aipushkit.http.AIWebSocket;
import com.blocktree.sdk.aipushkit.http.BaseCallBack;
import com.blocktree.sdk.aipushkit.mode.AIHttpResult;
import com.blocktree.sdk.aipushkit.mode.AIJsonResult;
import com.blocktree.sdk.aipushkit.mode.AIMsg;
import com.blocktree.sdk.aipushkit.mode.AIWebSocketResult;
import com.blocktree.sdk.aipushkit.service.GrayService;
import com.blocktree.sdk.aipushkit.sugarorm.SugarContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * xiezuofei
 * 2017-09-19 13:30
 * 793169940@qq.com
 *
 */
public class AIPush {
    // API网络环境
    private static AIConstants.AIAPIServerType apiType= AIConstants.AIAPIServerType.pro;

    public static AIConstants.AIAPIServerType getApiType() {
        return apiType;
    }
    public static String getUserkey() {
        return userkey;
    }
    // 设备系统
    private static final AIConstants.AIDeviceOS deviceOs= AIConstants.AIDeviceOS.android;
    //请求方式
    private static final AIConstants.AIHTTPMethod httpMethod= AIConstants.AIHTTPMethod.post;
    // 应用KEY
    private static  String appKey = "";
    // 用户公钥hash（base64编码成字符串），代表全网唯一。
    private static  String userkey = "";
    // 应用密钥
    private static  String appSecret = "";

    private Context mContext;
    public Context getmContext() {
        return mContext;
    }

    private String uuid="";
    private String bundleid="";
    private String registrationId="";
    private static Handler mHandler;

    //回调
    private static Map<String,BaseCallBack> backMap=new HashMap<String,BaseCallBack>();

    // 业务组件数组
    private static List<AIComponent> components=new ArrayList<AIComponent>();

    //创建单列
    private volatile static AIPush instance;
    public static AIPush getInstance(){
        if(instance==null){
            synchronized(AIPush.class){
                if(instance==null){
                    instance=new AIPush();
                }
            }
        }
        return instance;
    }
    private AIPush(){
        mHandler = new Handler(Looper.getMainLooper());
    }
    public void setupWithOption(Context mContext,String appKey,String appSecret,String uuid,String bundleid){
        this.mContext=mContext;
        this.appKey=appKey;
        this.appSecret=appSecret;
        this.uuid=uuid;
        this.bundleid=bundleid;
        this.registrationId= SharedPreferences.getInstance(mContext).getString("registrationId","");
        SugarContext.init(mContext);
        connectServer();
    }
    public Map<String, String> configAuthParams(){
        String sigtime=System.currentTimeMillis()+"";
        String period=String.valueOf(24*3600);//24小时有效
        userkey= SharedPreferences.getInstance(mContext).getString("userkey","");

        Map<String, String> params=new HashMap<String, String>();
        params.put("uuid",uuid+"");
        params.put("appkey",appKey+"");
        //代理人授权签名（base64编码成字符串），如果没有代理人，不需要传入
        params.put("appsig", AICommon.encryptionHMAC(appKey+sigtime+period,appSecret));
        //用户公钥hash（base64编码成字符串），代表全网唯一
        params.put("userkey",userkey+"");
        //用户授权签名（base64编码成字符串），IM系统验签成功，才能访问用户授权的接口方法
        params.put("usersig", AICommon.encryptionHMAC(userkey+sigtime+period,appSecret));
        //签名的时间戳
        params.put("sigtime",sigtime);
        //签名有效时间，单位秒
        params.put("period",period);
        //随机字符串，长度在32字节内
        params.put("nonce", AICommon.randomUUID()+"");
        return params;
    }
    public String urlParams(Map<String, String> params){
        String params_str="";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = null;
            if (entry.getValue() == null) {
                value = "";
            } else {
                value = entry.getValue().toString();
            }
            if(params_str.equals("")){
                params_str+="?";
            }else{
                params_str+="&";
            }
            params_str+=key+"="+value;
        }
        return params_str;
    }
    /**
     *注册设备
     */
    private void registerDevice() {
        Map<String, String> params=configAuthParams();
        params.put("bundleid",bundleid+"");
        params.put("apnsToken","");
        params.put("deviceOs",deviceOs.getCode());
        params.put("deviceModel","");
        requestByHttp("registerDevice", params, new BaseCallBack() {
            @Override
            public void onResult(AIHttpResult aiHttpResult) {
                AIMsg aiMsg=aiHttpResult.getResMsg();
                if(aiMsg!=null&& AIConstants.AIApiResultCode.success.getCode().equals(aiMsg.getCode())){
                    String result_string= AIJsonResult.getRegistrationId(aiHttpResult.getDatas());
                    SharedPreferences.getInstance(mContext).putString("registrationId",result_string);
                    bindServer();
                }
            }
        });
    }
    /**
     *建立长连接
     */
    private void bindServer() {
        bindAIPushService();
    }


    /**
     *连接服务器
     */
    private void connectServer() {
        //检查是否已经注册，并在本地记录了注册号
        if (registrationId==""){
            //注册设备
            registerDevice();
        } else {
            //已注册的可以建立连接
            bindServer();
        }
    }


    /**
     * 使用http发送请求
     * @param method
     * @param params
     * @param response
     */
    private void requestByHttp(String method,Map<String, String> params,BaseCallBack response){
        AIHttpMethods.getInstance().sendRequest(httpMethod, apiType.httpUrl() + method, params, response);
    }

    /**
     * 使用websocket发送请求
     * @param method
     * @param params
     * @param response
     */
    private void requestByWebsocket(String method,Map<String, String> params,BaseCallBack response){
        AIWebSocketResult aiWebSocketResult=new AIWebSocketResult();
        String seq=getSeq();
        backMap.put(seq,response);
        String websocket_params= aiWebSocketResult.toJson(AIConstants.AIDataType.request.getCode(),apiType.protocolType(),apiType.version(),method,seq,params);
        sendMessage(websocket_params);
    }


    /**
     *调用http接口
     * @param method       接口地址
     * @param params       传入参数
     * @param response     回调处理
     */
    public void sendRequest(String method,Map<String, String> params,BaseCallBack response) {
        AILog.e("接口方法:"+method);
        if("login".equals(method)){
            String username=params.get("username");
            //用户在应用服务的唯一标识
            String userkey_ls= AICommon.shaEncrypt(SystemConfig.APPKEY+username);
            SharedPreferences.getInstance(mContext).putString("userkey",userkey_ls);
        }
        params.putAll(configAuthParams());
        AILog.e("传入参数:"+params);
        String requestProtocol="";
        //如果连接了websocket
        if (isConnected()){
            requestProtocol= AIConstants.APIRequestProtocol.websocket.getCode();
            //使用websocket请求
            requestByWebsocket(method,params,response);
        }else{
            //使用http请求
            requestProtocol= AIConstants.APIRequestProtocol.http.getCode();
            requestByHttp(method,params,response);
        }
        AILog.e("请求方式:"+requestProtocol);
    }
    //处理推送过来的消息
    private static void handleTask(final AIWebSocketResult aiWebSocketResult){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //是否是请求过来的消息
                if (AIConstants.AIDataType.response.getCode().equals(aiWebSocketResult.getType())){
                    BaseCallBack response=backMap.get(aiWebSocketResult.getSeq());
                    AIHttpResult aiHttpResult=new AIHttpResult();
                    aiHttpResult.jsonToString(aiWebSocketResult.getBody());
                    if (response!=null){
                        response.onResult(aiHttpResult);
                    }
                    backMap.remove(aiWebSocketResult.getSeq());
                }else{
                    //查找符合业务的组件，把数据包分发给他
                    for (AIComponent aiComponent:components){
                        //如果组件包含该方法，并且接口版本号一致
                        if (aiComponent.method().contains(aiWebSocketResult.getMethod()) && apiType.version().equals(aiWebSocketResult.getVer())) {
                            //分发数据包给业务组件处理
                            aiComponent.distributePacket(instance,aiWebSocketResult);
                        }

                    }
                }
            }
        });

    }
    //处理推送过来的状态
    private static void handleConnectedTask(final boolean is_connected){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //查找符合业务的组件，把数据包分发给他
                for (AIComponent aiComponent:components){
                    aiComponent.networkDidChanged(instance);

                }
            }
        });

    }
    /**
     * 通过WebSocket发送消息
     * @param str
     * @return
     */
    private boolean sendMessage(String str){
        AILog.e("WebSocket传入参数:",str);
        return AIWebSocket.getInstance().sendMessage(str);
    }
    /**
     *  关闭WebSocket
     *
     * @return
     */
    public boolean closeWebSocket(){
        return AIWebSocket.getInstance().closeWebSocket();

    }
    public boolean isConnected(){
        return  AIWebSocket.getInstance().is_isConnected();
    }
    /**
     *获取序号
     */
    private final Lock lock = new ReentrantLock();
    private String getSeq(){
        lock.lock();
        String timestamp =System.currentTimeMillis()+""+ AICommon.randomUUID();
        lock.unlock();
        return timestamp;
    }
    /**
     *添加组件
     */
    public void addComponent(AIComponent aiComponent) {
        components.add(aiComponent);
    }


    private void bindAIPushService(){
        Intent grayIntent = new Intent(mContext, GrayService.class);
        mContext.startService(grayIntent);
    }
    //接收推送过来的消息
    public static class WebSocketMessageReceiver extends BroadcastReceiver{
        public final static String MESSAGE_ACTION = "com.blocktree.sdk.aipushkit.message";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MESSAGE_ACTION.equals(action)) {
                String message= intent.getStringExtra("message");
                if(message==null||message.length()<1){
                    return;
                }
                AIWebSocketResult aiWebSocketResult=new AIWebSocketResult();
                aiWebSocketResult.jsonToString(message);
                handleTask(aiWebSocketResult);
            }
        }
    }
    //接收推送过来的状态
    public static class WebSocketConnectedReceiver extends BroadcastReceiver{
        public final static String CONNECTED_ACTION = "com.blocktree.sdk.aipushkit.connected";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CONNECTED_ACTION.equals(action)) {
                boolean _isConnected= intent.getBooleanExtra("connected",false);
                handleConnectedTask(_isConnected);
            }
        }
    }
}
