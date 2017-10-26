package com.blocktree.sdk.aipushkit.mode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * WebSocket请求返回的数据
 */
public class AIWebSocketResult {
    private String type="";//传输类型，1：请求，2：响应
    private String protocol="";//协议类型，1：客户端接口协议，2：服务端接口协议
    private String ver="";//api的版本号
    private String method="";//方法名，对应上面的接口方法定义
    private String seq="";//请求序号，客户端/服务端发送请求时，会标记序号，客户端/服务端返回数据时也标记序号，用于连接中的设备，配对哪个任务的请求,格式：c_[seq]：客户端发起的s_[seq]：服务端发起的
    private String body="";//
    public String toJson(String type,String protocol,String ver,String method,String seq,JSONObject body){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",type);
            jsonObject.put("protocol",protocol);
            jsonObject.put("ver",ver);
            jsonObject.put("method",method);
            jsonObject.put("seq",seq);
            jsonObject.put("body",body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public String toJson(String type, String protocol, String ver, String method, String seq, Map<String, String> params){
        JSONObject jsonObject=new JSONObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = null;
            if (entry.getValue() == null) {
                value = "";
            } else {
                value = entry.getValue();
            }
            try {
                jsonObject.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toJson(type,protocol,ver,method,seq,jsonObject);
    }
    public void jsonToString(String json){
        try {
            JSONObject jsonObject=new JSONObject(json);
            if(!jsonObject.isNull("type")){
                type=jsonObject.getString("type");
            }
            if(!jsonObject.isNull("protocol")){
                protocol=jsonObject.getString("protocol");
            }
            if(!jsonObject.isNull("ver")){
                ver=jsonObject.getString("ver");
            }
            if(!jsonObject.isNull("method")){
                method=jsonObject.getString("method");
            }
            if(!jsonObject.isNull("seq")){
                seq=jsonObject.getString("seq");
            }
            if(!jsonObject.isNull("body")){
                body=jsonObject.getString("body");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return "type=" + type
                + " protocol=" + protocol
                + " ver=" + ver
                + " method=" + method
                + " seq=" + seq
                + " body=" + body
                +" | ";
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
