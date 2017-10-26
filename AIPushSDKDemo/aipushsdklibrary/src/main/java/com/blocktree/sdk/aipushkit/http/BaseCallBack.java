package com.blocktree.sdk.aipushkit.http;

import com.blocktree.sdk.aipushkit.mode.AIHttpResult;

/**
 * xiezuofei
 * 2017-09-17 17:50
 * 793169940@qq.com
 * 定义回调接口
 */
public interface BaseCallBack {
    void onResult(final AIHttpResult aiHttpResult);
}