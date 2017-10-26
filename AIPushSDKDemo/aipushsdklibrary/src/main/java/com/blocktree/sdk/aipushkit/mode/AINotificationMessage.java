package com.blocktree.sdk.aipushkit.mode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 通知消息对象
 */
public class AINotificationMessage {
    public String alert="";
    public String sound="";
    public String badge="";
    public String msgId="";
    public JSONArray extras=new JSONArray();
    public AINotificationMessage(){

    }
    public AINotificationMessage(String json){
        jsonToString(json);
    }
    public void jsonToString(String json){
        try {
            JSONObject jsonObject=new JSONObject(json);
            if(!jsonObject.isNull("alert")){
                alert=jsonObject.getString("alert");
            }
            if(!jsonObject.isNull("sound")){
                sound=jsonObject.getString("sound");
            }
            if(!jsonObject.isNull("badge")){
                badge=jsonObject.getString("badge");
            }
            if(!jsonObject.isNull("msgId")){
                msgId=jsonObject.getString("msgId");
            }
            if(!jsonObject.isNull("extras")){
                extras=jsonObject.getJSONArray("extras");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "alert=" + alert
                + " sound=" + sound
                + " msgId=" + msgId
                + " badge=" + badge
                + " extras=" + extras
                +" | ";
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public JSONArray getExtras() {
        return extras;
    }

    public void setExtras(JSONArray extras) {
        this.extras = extras;
    }
}
