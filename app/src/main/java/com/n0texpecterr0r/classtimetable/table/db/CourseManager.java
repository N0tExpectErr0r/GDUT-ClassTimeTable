package com.n0texpecterr0r.classtimetable.table.db;

import com.n0texpecterr0r.classtimetable.ContextApplication;
import com.n0texpecterr0r.classtimetable.db.CourseDao;
import com.n0texpecterr0r.classtimetable.table.bean.Course;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 14:44
 * @describe 课表信息管理类
 */
public class CourseManager {
    private static CourseManager sInstance;
    private CourseDao mCourseDao;

    public CourseManager() {
        mCourseDao = ContextApplication.getDaoSession().getCourseDao();
    }

    public static CourseManager getInstance() {
        if (sInstance == null) {
            synchronized (CourseManager.class) {
                if (sInstance == null) {
                    sInstance = new CourseManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 插入Course
     *
     * @param course 要插入的course
     */
    public void insertCourse(Course course) {
        mCourseDao.insert(course);
    }

    /**
     * 插入Course集合
     * @param courseList Course集合
     */
    public void insertCourseList(List<Course> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            return;
        }
        mCourseDao.insertInTx(courseList);
    }

    /**
     * 删除Course
     * @param course 删除的Course
     */
    public void deleteCourse(Course course){
        mCourseDao.delete(course);
    }

    /**
     * 更新Course
     * @param course
     */
    public void updateCourse(Course course){
        mCourseDao.update(course);
    }

    /**
     * 查询Course List
     * @return Course List
     */
    public List<Course> queryCourseList(){
        QueryBuilder<Course> query = mCourseDao.queryBuilder();
        return query.list();
    }

    /**
     * 用星期数查询课表
     */
    public List<Course> queryCourseList(String week) {
        QueryBuilder<Course> query = mCourseDao.queryBuilder();
        List<Course> oriList = queryCourseList();
        List<Course> resultList = new ArrayList<>();
        for (Course course : oriList) {
            if (course.getWeekList().contains(week)){
                resultList.add(course);
            }
        }
        return resultList;
    }

    /**
     * 清空课程表
     */
    public void clearCourseList(){
        mCourseDao.deleteAll();
    }

}
