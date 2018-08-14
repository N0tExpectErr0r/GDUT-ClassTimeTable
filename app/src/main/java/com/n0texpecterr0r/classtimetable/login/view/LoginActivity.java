package com.n0texpecterr0r.classtimetable.login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.n0texpecterr0r.classtimetable.R;
import com.n0texpecterr0r.classtimetable.login.presenter.LoginPresenter;
import com.n0texpecterr0r.classtimetable.login.presenter.LoginPresenterImpl;
import com.n0texpecterr0r.classtimetable.table.view.TableActivity;
import com.n0texpecterr0r.classtimetable.utils.ToastUtil;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;

    private LoginPresenter mLoginPresenter;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtUsername = findViewById(R.id.login_et_username);
        mEtPassword = findViewById(R.id.login_et_password);
        mBtnLogin = findViewById(R.id.login_btn_login);

        mLoginPresenter = new LoginPresenterImpl(this);

        mBtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.login(mEtUsername.getText().toString(),
                        mEtPassword.getText().toString());
            }
        });
    }

    @Override
    public void onSuccess(Map<String, String> cookies) {
        // 登录成功，拿到Cookie
        TableActivity.actionStart(this);
        finish();
    }

    @Override
    public void onFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.show(LoginActivity.this, "登录失败,请重试");
            }
        });
    }
}
