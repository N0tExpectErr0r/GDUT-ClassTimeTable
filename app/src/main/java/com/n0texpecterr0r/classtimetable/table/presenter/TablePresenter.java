package com.n0texpecterr0r.classtimetable.table.presenter;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 14:34
 * @describe 课程表Presenter
 */
public interface TablePresenter {

    /**
     * 获取课表数据
     *
     * @param semesterId 要获取课表数据的学期Id
     */
    void getTableData(String semesterId);

    /**
     * 获取课表数据
     *
     * @param semesterId 学期id
     * @param week 星期数
     */
    void getTableData(final String semesterId, String week);

    /**
     * 清空课表数据
     */
    void clearTable();
}
