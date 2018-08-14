package com.n0texpecterr0r.classtimetable.table.model;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 15:47
 * @describe 课程表Model接口
 */
public interface TableModel {

    /**
     * 获取课表数据
     * @param semesterId 要获取课表数据的学期Id
     */
    void getTableData(String semesterId);

    /**
     * 获取课表数据
     * @param semesterId 学期id
     * @param week 星期数
     */
    void getTableData(final String semesterId,String week);
}
