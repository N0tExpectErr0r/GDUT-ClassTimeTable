package com.n0texpecterr0r.classtimetable.ui;

import static android.graphics.Color.rgb;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.n0texpecterr0r.classtimetable.R;
import com.n0texpecterr0r.classtimetable.table.bean.Course;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 16:47
 * @describe 课程表控件
 */
public class CourseTableView extends LinearLayout {

    // 背景颜色数组
    public static int colors[] = {
            R.color.course01, R.color.course02,
            R.color.course03, R.color.course04,
            R.color.course05, R.color.course06,
            R.color.course07, R.color.course08,
            R.color.course09, R.color.course10,
            R.color.course11, R.color.course12,
            R.color.course13, R.color.course14,
            R.color.course15, R.color.course16,
            R.color.course17, R.color.course18,
            R.color.course19, R.color.course20};

    // 最大节数
    private final static int MAXNUM = 12;
    // 星期数
    private final static int WEEKNUM = 7;
    // 单个课程的高度
    private final static int COURSE_HEIGHT = 60;
    // 线条宽度
    private final static int LINE_WIDTH = 2;
    // 数字部分宽度
    private final static int NUM_WIDTH = 20;
    // 星期部分高度
    private final static int WEEK_NAME_HEIGHT = 30;
    // 第一行的星期显示
    private LinearLayout mWeekLayout;
    // 课程显示
    private LinearLayout mCourseLayout;
    // 星期数组
    private String[] mWeekNames = new String[]{"一", "二", "三", "四", "五", "六", "七"};
    // 数据源
    private List<Course> mCourseList = new ArrayList<>();
    // 颜色对应课程名数组
    private String[] colorStr = new String[20];
    // 颜色的num，用于保存当前到第几个颜色
    private int colornum = 0;
    // 当前星期 默认为全部
    private int mCurrentWeek = 0;
    // 空白处点击事件
    private OnBlankCLickListener mBlankListener;
    // 课程点击事件
    private OnCourseClickListener mCourseListener;

    public CourseTableView(Context context) {
        super(context);
    }

    public CourseTableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseTableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 绘制界面
     */
    private void drawTable() {
        setOrientation(VERTICAL);
        mWeekLayout = new LinearLayout(getContext());
        mWeekLayout.setOrientation(HORIZONTAL);

        mCourseLayout = new LinearLayout(getContext());
        mCourseLayout.setOrientation(HORIZONTAL);
        // 表格
        for (int i = 0; i <= WEEKNUM; i++) {
            if (i == 0) {
                // 节数
                TextView tvTime = new TextView(getContext());
                tvTime.setHeight(dpToPx(WEEK_NAME_HEIGHT));
                tvTime.setWidth(dpToPx(NUM_WIDTH));
                mWeekLayout.addView(tvTime);

                // 1-MAXNUM个
                LinearLayout llNum = new LinearLayout(getContext());
                // 计算宽高
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                        dpToPx(NUM_WIDTH), dpToPx(MAXNUM * COURSE_HEIGHT) + MAXNUM * LINE_WIDTH);
                llNum.setLayoutParams(layoutParams);
                llNum.setOrientation(VERTICAL);
                for (int j = 1; j <= MAXNUM; j++) {
                    TextView tvNum = new TextView(getContext());
                    tvNum.setGravity(Gravity.CENTER);
                    tvNum.setTextColor(rgb(21, 21, 21));
                    tvNum.setHeight(dpToPx(COURSE_HEIGHT));
                    tvNum.setWidth(dpToPx(NUM_WIDTH));
                    tvNum.setTextSize(14);
                    tvNum.setText(j + "");
                    // 添加显示课程序号的view
                    llNum.addView(tvNum);
                    // 添加横线
                    llNum.addView(getHorizonalLine());
                }
                mCourseLayout.addView(llNum);
            } else if (i >= 1 && i <= 7) {
                // 添加周一到周日文本
                LinearLayout llTemp = new LinearLayout(getContext());
                llTemp.setOrientation(VERTICAL);
                TextView tvWeekName = new TextView(getContext());
                tvWeekName.setTextColor(rgb(21, 21, 21));
                tvWeekName.setTextSize(14);
                // 计算每周的宽度
                tvWeekName.setWidth((caculateViewWidth() - dpToPx(NUM_WIDTH)) / WEEKNUM);
                tvWeekName.setHeight(dpToPx(WEEK_NAME_HEIGHT));
                tvWeekName.setGravity(Gravity.CENTER);
                tvWeekName.setTextSize(16);
                tvWeekName.setText(mWeekNames[i - 1]);
                llTemp.addView(tvWeekName);
                mWeekLayout.addView(llTemp);
                // 显示日期
                List<Course> weekCourseList = new ArrayList<>();
                // 遍历周一至周日的课表
                for (Course course : mCourseList) {
                    if (course.getDay().equals(i + "")) {
                        weekCourseList.add(course);
                    }
                }
                // 添加
                LinearLayout llCourse = getCourseLayout(weekCourseList, i);
                llCourse.setOrientation(VERTICAL);
                ViewGroup.LayoutParams layoutParams = new
                        ViewGroup.LayoutParams((caculateViewWidth() - dpToPx(NUM_WIDTH)) / WEEKNUM, MATCH_PARENT);
                llCourse.setLayoutParams(layoutParams);
                llCourse.setWeightSum(1);
                mCourseLayout.addView(llCourse);
            }
            // 画竖线
            TextView tvLine = new TextView(getContext());
            tvLine.setHeight(dpToPx(COURSE_HEIGHT * MAXNUM) + MAXNUM * LINE_WIDTH);
            tvLine.setWidth(LINE_WIDTH);
            tvLine.setBackgroundResource(R.color.colorLine);
            mCourseLayout.addView(tvLine);
            mWeekLayout.addView(getVerticalLine());
        }
        addView(mWeekLayout);
        addView(getHorizonalLine());
        addView(mCourseLayout);
        addView(getHorizonalLine());
    }

    /**
     * 获取课程Layout
     *
     * @param courseList 课程列表
     * @param weekNum 星期数
     */
    private LinearLayout getCourseLayout(List<Course> courseList, int weekNum) {
        LinearLayout courseLayout = new LinearLayout(getContext());
        courseLayout.setOrientation(VERTICAL);
        if (courseList.size() <= 0) {
            // 如果全部是空的，全部填满空白
            courseLayout.addView(getBlankView(MAXNUM + 1, weekNum, 0));
        } else {
            for (int i = 0; i < courseList.size(); i++) {
                if (i == 0) {
                    // 添加0到开始数的空白
                    int end = courseList.get(0).getStartNum();
                    courseLayout.addView(getBlankView(end, weekNum, 0));
                    courseLayout.addView(getCourseView(courseList.get(0)));
                } else if (courseList.get(i).getStartNum() > courseList.get(i - 1).getStartNum()) {
                    // 填充
                    courseLayout.addView(
                            getBlankView(courseList.get(i).getStartNum() - courseList.get(i - 1).getEndNum()
                                    , weekNum, courseList.get(i - 1).getEndNum()));
                    courseLayout.addView(getCourseView(courseList.get(i)));
                }
                if (i + 1 == courseList.size()) {
                    courseLayout.addView(getBlankView(MAXNUM - courseList.get(i).getEndNum(), weekNum,
                            courseList.get(i).getEndNum()));
                }
            }
        }
        return courseLayout;
    }

    /**
     * 获取空白View
     *
     * @param endNum 结束数
     * @param weekNum 星期数
     * @param start 开始数
     */
    private View getBlankView(int endNum, final int weekNum, final int start) {
        LinearLayout startLayout = new LinearLayout(getContext());
        startLayout.setOrientation(VERTICAL);
        for (int i = 1; i < endNum; i++) {
            TextView tvTime = new TextView(getContext());
            tvTime.setGravity(Gravity.CENTER);
            tvTime.setHeight(dpToPx(COURSE_HEIGHT));
            tvTime.setWidth(dpToPx(COURSE_HEIGHT));
            startLayout.addView(tvTime);
            startLayout.addView(getHorizonalLine());
            final int num = i;
            // 此处处理点击空白处回调
            tvTime.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBlankListener != null) {

                        mBlankListener.onClickBlank(weekNum, start + num);
                    }
                }
            });
        }
        return startLayout;
    }

    /**
     * 获取课程对应View
     */
    private View getCourseView(final Course course) {
        LinearLayout courseLayout = new LinearLayout(getContext());
        courseLayout.setOrientation(VERTICAL);
        CardView courseView = new CardView(getContext());
        TextView tvCourseName = new TextView(getContext());
        int num = course.getEndNum() - course.getStartNum();
        tvCourseName.setHeight(dpToPx((num + 1) * COURSE_HEIGHT) + num * 2);
        tvCourseName.setTextColor(0xffffffff);
        tvCourseName.setWidth(dpToPx(40));
        tvCourseName.setTextSize(12);
        tvCourseName.setGravity(Gravity.CENTER);
        tvCourseName.setText(course.getName() + "@" + course.getClassroom());
        if (mCurrentWeek == 0 || course.getWeekList().contains(mCurrentWeek + "")) {
            // 如果全部选择或者该课包含当前星期
            courseView.setCardBackgroundColor(getResources().getColor(colors[getColorNum(course.getName())]));
        } else {
            // 否则置为灰色
            courseView.setCardBackgroundColor(getResources().getColor(R.color.colorLine));
        }
        courseView.addView(tvCourseName);
        courseView.setRadius(dpToPx(3));
        if (VERSION.SDK_INT >= 21) {
            courseView.setElevation(0f);
        }
        courseLayout.addView(courseView);
        courseLayout.addView(getHorizonalLine());
        courseLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCourseListener != null) {
                    mCourseListener.onCourseClick(course);
                }
            }
        });
        return courseLayout;
    }

    /**
     * 设置课程列表
     *
     * @param courseList 课程列表
     */
    public void setCourseList(List<Course> courseList) {
        // 对课程列表按起始时间排序
        Collections.sort(courseList, new Comparator<Course>() {
            @Override
            public int compare(Course course1, Course course2) {
                return course1.getStartNum() - course2.getStartNum();
            }
        });
        mCourseList = courseList;
        for (Course course : courseList) {
            getColorName(course.getName());
        }
        removeAllViews();
        drawTable();
        invalidate();
    }

    /**
     * 设置当前周
     *
     * @param currentWeek 新设置的当前周
     */
    public void setCurrentWeek(int currentWeek) {
        mCurrentWeek = currentWeek;
        removeAllViews();
        drawTable();
        invalidate();
    }

    /**
     * 输入课表名循环判断是否数组存在该课表 如果存在输出true并退出循环 如果不存在则存入colorStr[20]数组
     */
    private void getColorName(String name) {
        boolean isRepeat = true;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                isRepeat = true;
                break;
            } else {
                isRepeat = false;
            }
        }
        if (!isRepeat) {
            colorStr[colornum] = name;
            colornum++;
        }
    }

    /**
     * 横线
     */
    private View getHorizonalLine() {
        // TextView可以设置width和height
        TextView weekLine = new TextView(getContext());
        weekLine.setBackgroundColor(getResources().getColor(R.color.colorLine));
        weekLine.setHeight(2);
        weekLine.setWidth(MATCH_PARENT);
        return weekLine;
    }

    /**
     * 竖线
     */
    private View getVerticalLine() {
        TextView verticalLine = new TextView(getContext());
        verticalLine.setBackgroundColor(getResources().getColor(R.color.colorLine));
        verticalLine.setHeight(dpToPx(WEEK_NAME_HEIGHT));
        verticalLine.setWidth((LINE_WIDTH));
        return verticalLine;
    }

    /**
     * 计算View的宽度
     */
    private int caculateViewWidth() {
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取数组中的课程名
     */
    public int getColorNum(String name) {
        int num = 0;
        for (int i = 0; i < 20; i++) {
            if (name.equals(colorStr[i])) {
                num = i;
            }
        }
        return num;
    }

    /**
     * 设置课程点击事件
     * @param onCourseClickListener 课程点击事件
     */
    public void setOnCourseClickListener(OnCourseClickListener onCourseClickListener) {
        mCourseListener = onCourseClickListener;
    }


    public interface OnCourseClickListener {

        /**
         * 课程点击事件
         * @param course 点击的课程
         */
        void onCourseClick(Course course);
    }

    /**
     * 设置空白处点击事件
     */
    public void setOnBlankClickListener(OnBlankCLickListener onBlankClickListener) {
        mBlankListener = onBlankClickListener;
    }


    public interface OnBlankCLickListener {

        /**
         * 空白处点击事件
         * @param weekNum 星期数
         * @param courseNum 节次
         */
        void onClickBlank(int weekNum, int courseNum);
    }

    /**
     * 将dp转换为px
     */
    public int dpToPx(float dpValue) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
