package com.n0texpecterr0r.classtimetable.table.presenter;

import com.n0texpecterr0r.classtimetable.table.bean.Course;
import java.util.List;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 14:35
 * @describe 课表信息回调接口
 */
public interface OnTableListener {

    /**
     * 成功获取课表信息回调
     * @param courseList 课程List
     */
    void onSuccess(List<Course> courseList);

    /**
     * 获取失败回调
     */
    void onFail();
}
