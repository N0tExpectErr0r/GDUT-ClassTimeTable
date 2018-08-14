package com.n0texpecterr0r.classtimetable.table.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.n0texpecterr0r.classtimetable.R;
import com.n0texpecterr0r.classtimetable.table.bean.Course;
import com.n0texpecterr0r.classtimetable.login.view.LoginActivity;
import com.n0texpecterr0r.classtimetable.table.presenter.TablePresenter;
import com.n0texpecterr0r.classtimetable.table.presenter.TablePresenterImpl;
import com.n0texpecterr0r.classtimetable.ui.CourseTableView;
import com.n0texpecterr0r.classtimetable.ui.CourseTableView.OnCourseClickListener;
import com.n0texpecterr0r.classtimetable.utils.ToastUtil;
import java.util.List;
import java.util.Map;

public class TableActivity extends AppCompatActivity implements TableView, OnCourseClickListener {
    private Map<String,String> mCookies;
    private TablePresenter mTablePresenter;
    private CourseTableView mCtvTable;

    public static void actionStart(Context context){
        Intent intent = new Intent(context,TableActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        mCtvTable = findViewById(R.id.ctv_table);
        // 初始化cookies
        SharedPreferences cookiePref = getSharedPreferences("cookies",MODE_PRIVATE);
        mCookies = (Map<String, String>) cookiePref.getAll();
        // 加载数据
        mTablePresenter = new TablePresenterImpl(this,mCookies);
        mTablePresenter.getTableData("201801");
        // 课表控件初始化
        mCtvTable.setOnCourseClickListener(this);
    }

    /**
     * 注销当前账号
     */
    private void logout() {
        SharedPreferences.Editor cookieEditor = getSharedPreferences("cookies",MODE_PRIVATE).edit();
        cookieEditor.clear();
        cookieEditor.commit();
        mTablePresenter.clearTable();
        LoginActivity.actionStart(this);
        finish();
    }

    @Override
    public void onSuccess(final List<Course> courseList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCtvTable.setCourseList(courseList);
                mCtvTable.setCurrentWeek(2);
            }
        });
    }

    @Override
    public void onFail() {
        ToastUtil.show(this,"网络出现错误，请检查网络设置");
    }

    @Override
    public void onCourseClick(Course course) {
        ToastUtil.show(this,course.getName()+"@"+course.getClassroom());
    }
}
