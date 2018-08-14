package com.n0texpecterr0r.classtimetable.table.view;

import com.n0texpecterr0r.classtimetable.table.bean.Course;
import java.util.List;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 15:53
 * @describe 课程表视图接口
 */
public interface TableView {
    void onSuccess(List<Course> courseList);

    void onFail();
}
