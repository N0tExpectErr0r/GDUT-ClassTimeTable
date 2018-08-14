package com.n0texpecterr0r.classtimetable.table.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.n0texpecterr0r.classtimetable.table.bean.Course;
import com.n0texpecterr0r.classtimetable.table.db.CourseManager;
import com.n0texpecterr0r.classtimetable.table.presenter.OnTableListener;
import com.n0texpecterr0r.classtimetable.table.view.TableView;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 15:51
 * @describe 课程表Model实现
 */
public class TableModelImpl implements TableModel {

    private static final String QUERY_TABLE_URL =
            "http://jxfw.gdut.edu.cn/xsgrkbcx!xsAllKbList.action?xnxqdm=";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    private OnTableListener mTableListener;
    private Map<String, String> mCookies;
    private ExecutorService mExecutorService;

    public TableModelImpl(OnTableListener tableListener, Map<String, String> cookies) {
        mCookies = cookies;
        mTableListener = tableListener;
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getTableData(final String semesterId) {
        List<Course> courseList = CourseManager.getInstance().queryCourseList();
        if (courseList.size() == 0) {
            getCourseFromNet(semesterId);
        }else{
            mTableListener.onSuccess(courseList);
        }
    }

    @Override
    public void getTableData(final String semesterId,String week) {
        List<Course> courseList = CourseManager.getInstance().queryCourseList(week);
        mTableListener.onSuccess(courseList);
    }

    private void getCourseFromNet(final String semesterId) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Connection queryConnection = Jsoup.connect(QUERY_TABLE_URL + semesterId);
                queryConnection.header("User-Agent", USER_AGENT);
                Response listResponse = null;
                try {
                    listResponse = queryConnection
                            .ignoreContentType(true)
                            .followRedirects(true)
                            .method(Method.GET)
                            .cookies(mCookies)
                            .execute();
                } catch (IOException e) {
                    mTableListener.onFail();
                    return;
                }
                final Document queryDocument = Jsoup.parse(listResponse.body());
                String scriptStr = queryDocument.select("script[type=text/javascript]").last().data();
                Pattern pattern = Pattern.compile("kbxx = \\[.*\\];");
                Matcher matcher = pattern.matcher(scriptStr);
                String script = null;
                if (matcher.find()) {
                    script = matcher.group(0);
                } else {
                    mTableListener.onFail();
                    return;
                }
                int startIndex = script.indexOf('[');
                int endIndex = script.indexOf(';');
                String json = script.substring(startIndex, endIndex);
                Gson gson = new Gson();
                List<Course> courseList = gson.fromJson(json,
                        new TypeToken<List<Course>>() {}.getType());
                CourseManager.getInstance().insertCourseList(courseList);
                mTableListener.onSuccess(courseList);
            }
        });
    }
}
