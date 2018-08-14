package com.n0texpecterr0r.classtimetable.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.n0texpecterr0r.classtimetable.R;
import com.n0texpecterr0r.classtimetable.table.bean.Course;

/**
 * @author Created by Nullptr
 * @date 2018/8/14 20:08
 * @describe 课程详情Dialog
 */
public class CourseDialog extends Dialog {

    private TextView mTvName;
    private TextView mTvLocation;
    private TextView mTvTeacher;
    private TextView mTvTime;
    private TextView mTvWeek;
    private ImageView mIvClose;
    private Course mCourse;

    public CourseDialog(@NonNull Context context, Course course) {
        super(context,R.style.CourseDialogStyle);
        mCourse = course;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_course);
        // 空白处不关闭
        setCanceledOnTouchOutside(false);

        mTvName = findViewById(R.id.course_tv_name);
        mTvLocation = findViewById(R.id.course_tv_location);
        mTvTeacher = findViewById(R.id.course_tv_teacher);
        mTvTime = findViewById(R.id.course_tv_time);
        mTvWeek = findViewById(R.id.course_tv_week);
        mIvClose = findViewById(R.id.course_iv_close);

        mTvName.setText(mCourse.getName());
        if (mCourse.getClassroom().equals("")) {
            mTvLocation.setText("暂无课室安排");
        } else {
            mTvLocation.setText(mCourse.getClassroom());
        }
        if (mCourse.getTeacher().equals("")) {
            mTvTeacher.setText("暂无老师安排");
        }else{
            mTvTeacher.setText(mCourse.getTeacher());
        }
        mTvTime.setText(mCourse.getStartNum()+"-"+mCourse.getEndNum()+"节");
        mTvWeek.setText(mCourse.getStartWeek()+"-"+mCourse.getEndWeek()+"周");
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
