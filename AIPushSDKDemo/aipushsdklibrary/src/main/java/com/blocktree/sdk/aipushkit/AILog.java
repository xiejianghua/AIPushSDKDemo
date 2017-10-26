package com.blocktree.sdk.aipushkit;


import android.util.Log;

/**
 * xiezuofei
 * 2017-09-19 13:30
 * 793169940@qq.com
 *  打印日志
 */
public class AILog {
    public static void i(String content){
        Log.i("AILog_i:",content+"");
    }
    public static void i(String tag,String content){
        Log.i(tag+":",content+"");
    }
    public static void e(String content){
        Log.e("AILog_e:",content+"");
    }
    public static void e(String tag,String content){
        Log.e(tag+":",content+"");
    }
}
