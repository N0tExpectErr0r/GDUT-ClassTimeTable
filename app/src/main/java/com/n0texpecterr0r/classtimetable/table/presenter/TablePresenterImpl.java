package com.n0texpecterr0r.classtimetable.table.presenter;

import com.n0texpecterr0r.classtimetable.table.bean.Course;
import com.n0texpecterr0r.classtimetable.table.db.CourseManager;
import com.n0texpecterr0r.classtimetable.table.model.TableModel;
import com.n0texpecterr0r.classtimetable.table.model.TableModelImpl;
import com.n0texpecterr0r.classtimetable.table.view.TableView;
import java.util.List;
import java.util.Map;

/**
 * @author Created by Nullptr
 * @date 2018/8/13 14:37
 * @describe 课表Presenter实现类
 */
public class TablePresenterImpl implements TablePresenter, OnTableListener {

    private TableModel mTableModel;
    private TableView mTableView;

    public TablePresenterImpl(TableView tableView, Map<String, String> cookies) {
        mTableModel = new TableModelImpl(this, cookies);
        mTableView = tableView;
    }

    @Override
    public void getTableData(String semesterId) {
        mTableModel.getTableData(semesterId);
    }

    @Override
    public void getTableData(String semesterId, String week) {
        mTableModel.getTableData(semesterId,week);
    }

    @Override
    public void clearTable() {
        // 清空课表
        CourseManager.getInstance().clearCourseList();
    }

    @Override
    public void onSuccess(List<Course> courseList) {
        mTableView.onSuccess(courseList);
    }

    @Override
    public void onFail() {
        mTableView.onFail();
    }
}
