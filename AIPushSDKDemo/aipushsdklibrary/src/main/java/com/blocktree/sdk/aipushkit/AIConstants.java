package com.blocktree.sdk.aipushkit;
/**
 * xiezuofei
 * 2017-09-17 17:50
 * 793169940@qq.com
 * 工具类
 */
public class AIConstants {

    public enum AIAPIServerType{
        dev(0),
        test(1),
        pro(2);

        public String symbol() {
            String symbol = "";
            switch (this) {
                case dev:
                    symbol = "dev";
                    break;
                case test:
                    symbol = "test";
                    break;
                case pro:
                    symbol = "pro";
                    break;
            }
            return symbol;
        }
        // 客户端协议
        public String protocolType() {
            return "1";
        }

        public String urlProtocol() {
            String urlProtocol = "";
            switch (this) {
                case dev:
                    urlProtocol = "http://";
                    break;
                case test:
                    urlProtocol = "http://";
                    break;
                case pro:
                    urlProtocol = "https://";
                    break;
            }
            return urlProtocol;
        }
        //版本号
        public String version() {
            String version = "";
            switch (this) {
                case dev:
                    version = "v1";
                    break;
                case test:
                    version = "v1";
                    break;
                case pro:
                    version = "v1";
                    break;
            }
            return version;
        }
        //请求链接
        public String hostDomain() {
            String hostDomain = "";
            switch (this) {
                case dev:
                    hostDomain = "192.168.5.200:8080/api/c/"+version()+"/";
                    break;
                case test:
                    hostDomain = "47.52.133.178:8082/api/c/"+version()+"/";
                    break;
                case pro:
                    hostDomain = "chat.exx.com/api/c/"+version()+"/";
                    break;
            }
            return hostDomain;
        }
        //http链接
        public String httpUrl() {
            return urlProtocol()+hostDomain();
        }
        //websocket的链接地址
        public String wsUrl() {
            String wsUrl="";
            switch (this) {
                case dev:
                    wsUrl ="ws://";
                    break;
                case test:
                    wsUrl = "ws://";
                    break;
                case pro:
                    wsUrl = "wss://";
                    break;
            }
            return wsUrl+hostDomain();
        }
        private int code;
        private AIAPIServerType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

    /**
     API返回结果代码
     */
    public enum AIApiResultCode{
        /**
         *  操作成功（success）
         */
        success("1000"),
        /**
         *  一般错误提示（Error Tips）
         */
        errorTips("1001"),
        /**
         *  内部错误（Internal Error）
         */
        internalError("1002"),
        /**
         *  验证不通过（Validate No Pass）
         */
        validateNoPass("1003"),
        /**
         *  usersig失效（Usersig No Pass）
         */
        usersigNoPass("1004"),

        /**
         *  数据解析错误
         */
        valueDecodeError("20001"),

        /**
         *  接口访问失败（APIResponseError）
         */
        responseError("90000"),

        /**
         *  接口访问超时（APIRequestTimeout）
         */
        requestTimeout("90001");

        private String code;
        private AIApiResultCode(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    /**
     传输类型

     - Request:  发送
     - Response: 返回
     */
    public enum AIDataType{
        //发送
        request("1"),
        //返回
        response("2");

        private String code;
        private AIDataType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     *  推送本地通知类型
     */
    public enum AIPushNotificationType {

        //接收自定义推送（非APNS）
        NetworkDidReceivePush("NetworkDidReceivePush"),
        //接收网络连接成功
        NetworkDidConnected("NetworkDidConnected");

        private String code;
        private AIPushNotificationType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     网络请求协议

     - http:      http
     - websocket: websocket
     */
    public enum APIRequestProtocol{
        http("http"),
        websocket("websocket");

        private String code;
        private APIRequestProtocol(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    // HTTP的请求方法类型
    public enum AIHTTPMethod{
        options("OPTIONS"),
        get("GET"),
        head("HEAD"),
        post("POST"),
        put("PUT"),
        patch("PATCH"),
        delete("DELETE"),
        trace("TRACE"),
        connect("CONNECT");

        private String code;
        private AIHTTPMethod(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    // 设备操作系统
    // - iOS: 苹果
    // - android: 安卓
    // - other: 其他
    public enum AIDeviceOS{
        ios("1"),
        android("2"),
        other("3");

        private String code;
        private AIDeviceOS(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
