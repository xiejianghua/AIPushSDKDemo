package com.blocktree.sdk.aipushkit.mode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * xiezuofei
 * 2017-09-22 09:50
 * 793169940@qq.com
 * http请求返回的数据
 */
public class AIJsonResult {
    public static JSONObject jsonToString(String json){
        try {
            if(json==null||json.length()<1){
                return null;
            }
            JSONObject jsonObject=new JSONObject(json);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getRegistrationId(String json){
        JSONObject jsonObject=jsonToString(json);
        String registrationId="";
        if(jsonObject!=null&&!jsonObject.isNull("registrationId")){
            try {
                registrationId = jsonObject.getString("registrationId");
            } catch (JSONException e) {
                registrationId="";
                e.printStackTrace();
            }
        }
        return registrationId;
    }

}
