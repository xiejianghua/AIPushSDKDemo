package com.blocktree.sdk.aipushkit.mode;
/**
 * xiezuofei
 * 2017-09-18 09:50
 * 793169940@qq.com
 * 接口返回消息对象
 */
public class AIMsg {
    private String code="";//返回码，查看全局返回码
    private String message;//消息
    private String method;//调用的接口方法名
    @Override
    public String toString() {
        return "code=" + code
                + " message=" + message
                + " method=" + method
                +" | ";
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


}
