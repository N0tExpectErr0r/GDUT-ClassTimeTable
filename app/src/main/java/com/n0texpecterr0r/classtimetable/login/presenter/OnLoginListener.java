package com.n0texpecterr0r.classtimetable.login.presenter;

import java.util.Map;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 11:13
 * @describe 登录回调
 */
public interface OnLoginListener {
    /**
     * 登录成功回调
     * @param cookies 登录成功获得的cookies
     */
    void onSuccess(Map<String,String> cookies);
    /**
     * 登录失败回调
     */
    void onFail();
}
