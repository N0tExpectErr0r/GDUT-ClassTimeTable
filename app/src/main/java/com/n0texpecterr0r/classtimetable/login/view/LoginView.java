package com.n0texpecterr0r.classtimetable.login.view;

import java.util.Map;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 15:14
 * @describe 登录界面视图接口
 */
public interface LoginView {
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
