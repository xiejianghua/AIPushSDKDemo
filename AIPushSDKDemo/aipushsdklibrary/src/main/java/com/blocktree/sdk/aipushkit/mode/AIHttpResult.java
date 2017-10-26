package com.blocktree.sdk.aipushkit.mode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * http请求返回的数据
 */
public class AIHttpResult {
    private AIMsg resMsg = new AIMsg();
    private String datas = "";
    private static Gson gson = new Gson();
    public void jsonToString(String json) {
        try {
            if (json == null || json.length() < 1) {
                return;
            }
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.isNull("resMsg")) {
                resMsg=gson.fromJson(jsonObject.getString("resMsg"),AIMsg.class);
            }
            if (!jsonObject.isNull("datas")) {
                datas = jsonObject.getString("datas");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = gson.fromJson(s, clazz);
        return Arrays.asList(arr);
    }
    public static <T> List<T> stringToArray(String s, Type typeOfT) {
        List<T> arr = gson.fromJson(s, typeOfT);
        return arr;
    }
    public static Object  stringToObject(Object obj, Class clazz) {
      return gson.fromJson(new Gson().toJson(obj),clazz);
    }

    public AIHttpObjectResult sendMessageResult(){
       if(datas==null||datas.length()<1){
           return null;
       }
       return gson.fromJson(datas,AIHttpObjectResult.class);
    }

    public List<AIConversation> getConversations() {
        try {
            JSONObject jsonObject = new JSONObject(datas);
            if (!jsonObject.isNull("conversations")) {
                //List<AIConversation> list_data=stringToArray(jsonObject.getString("conversations"),AIConversation[].class);
                List<AIConversation> list_data=stringToArray(jsonObject.getString("conversations"), new TypeToken<List<AIConversation>>() {}.getType());
                return list_data;
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取聊天信息
    public List<AIChatMessage> getChatMessages() {
        try {
            JSONObject jsonObject = new JSONObject(datas);
            if (!jsonObject.isNull("chatMessages")) {
                //List<AIConversation> list_data=stringToArray(jsonObject.getString("conversations"),AIConversation[].class);
                List<AIChatMessage> list_data=stringToArray(jsonObject.getString("chatMessages"), new TypeToken<List<AIChatMessage>>() {}.getType());
                return list_data;
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取单个会话
    public static AIConversation conversation(String datas) {
        try {
            JSONObject jsonObject = new JSONObject(datas);
            if (!jsonObject.isNull("conversation")) {
                return gson.fromJson(jsonObject.getString("conversation"),AIConversation.class);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取单个消息
    public static AIChatMessage chatMessage(String datas){
        try {
            JSONObject jsonObject = new JSONObject(datas);
            if (!jsonObject.isNull("chatMessage")) {
                return gson.fromJson(jsonObject.getString("chatMessage"),AIChatMessage.class);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "resMsg=" + resMsg
                + " datas=" + datas
                +" | ";
    }
    public AIMsg getResMsg() {
        return resMsg;
    }

    public void setResMsg(AIMsg resMsg) {
        this.resMsg = resMsg;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
