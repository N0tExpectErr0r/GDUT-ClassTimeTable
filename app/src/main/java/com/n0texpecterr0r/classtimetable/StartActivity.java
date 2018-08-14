package com.n0texpecterr0r.classtimetable;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import com.n0texpecterr0r.classtimetable.login.view.LoginActivity;
import com.n0texpecterr0r.classtimetable.table.view.TableActivity;

public class StartActivity extends AppCompatActivity {
    class TranstationHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 获取SharedPreferences
            SharedPreferences cookiePref = getSharedPreferences("cookies", MODE_PRIVATE);
            if (cookiePref.getAll().size() == 0 || cookiePref == null) {
                // 如果没有缓存cookie，打开登录界面
                LoginActivity.actionStart(StartActivity.this);
                finish();
            } else {
                // 如果已经缓存cookie，则打开课程表界面
                TableActivity.actionStart(StartActivity.this);
                finish();
            }
        }
    }
    private TranstationHandler mHandler = new TranstationHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mHandler.sendEmptyMessageDelayed(1,600);
    }
}
