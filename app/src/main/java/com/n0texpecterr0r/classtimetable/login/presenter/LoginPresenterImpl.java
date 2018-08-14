package com.n0texpecterr0r.classtimetable.login.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import com.n0texpecterr0r.classtimetable.ContextApplication;
import com.n0texpecterr0r.classtimetable.login.model.LoginModel;
import com.n0texpecterr0r.classtimetable.login.model.LoginModelImpl;
import com.n0texpecterr0r.classtimetable.login.view.LoginView;
import java.util.Map;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 11:07
 * @describe 登录Presenter层
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginListener {
    private LoginModel mLoginModel;
    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView){
        mLoginModel = new LoginModelImpl(this);
        mLoginView = loginView;
    }


    @Override
    public void login(String username, String password) {
        mLoginModel.login(username, password);
    }

    @Override
    public void onSuccess(Map<String, String> cookies) {
        // 登录成功，保存cookie
        SharedPreferences.Editor cookieEditor = ContextApplication.
                getContext().getSharedPreferences("cookies", MODE_PRIVATE).edit();
        // 先清空之前的Cookie
        cookieEditor.clear();
        cookieEditor.commit();
        // 保存Cookie
        for (String key : cookies.keySet()) {
            cookieEditor.putString(key, cookies.get(key));
        }
        cookieEditor.commit();

        mLoginView.onSuccess(cookies);
    }

    @Override
    public void onFail() {
        mLoginView.onFail();
    }
}
