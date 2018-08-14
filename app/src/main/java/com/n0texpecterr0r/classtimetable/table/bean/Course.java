package com.n0texpecterr0r.classtimetable.table.bean;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 19:15
 * @describe 课程bean
 */
@Entity
public class Course {
    @Id(autoincrement = true)
    private Long id;
    @SerializedName("kcmc")
    private String name;
    @SerializedName("kcbh")
    private String code;
    @SerializedName("jxbmc")
    private String classStr;
    @SerializedName("kcrwdm")
    private String courseId;
    @SerializedName("jcdm2")
    private String sessionStr;
    @SerializedName("zcs")
    private String weekStr;
    @SerializedName("xq")
    private String day;
    @SerializedName("jxcdmcs")
    private String classroom;
    @SerializedName("teaxms")
    private String teacher;

    @Generated(hash = 711502852)
    public Course(Long id, String name, String code, String classStr,
            String courseId, String sessionStr, String weekStr, String day,
            String classroom, String teacher) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.classStr = classStr;
        this.courseId = courseId;
        this.sessionStr = sessionStr;
        this.weekStr = weekStr;
        this.day = day;
        this.classroom = classroom;
        this.teacher = teacher;
    }

    @Generated(hash = 1355838961)
    public Course() {
    }

    public int getStartNum(){
        return Integer.valueOf(getSessionList().get(0));
    }

    public int getEndNum(){
        List<String> sessionList = getSessionList();
        return Integer.valueOf(sessionList.get(sessionList.size()-1));
    }

    public String getStartWeek(){
        List<String> weekList = getWeekList();
        Collections.sort(weekList, new Comparator<String>() {
            @Override
            public int compare(String num1, String num2) {
                return Integer.parseInt(num1)-Integer.parseInt(num2);
            }
        });
        return weekList.get(0);
    }

    public String getEndWeek(){
        List<String> weekList = getWeekList();
        Collections.sort(weekList, new Comparator<String>() {
            @Override
            public int compare(String num1, String num2) {
                return Integer.parseInt(num1)-Integer.parseInt(num2);
            }
        });
        return weekList.get(weekList.size()-1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getClassList() {
        return Arrays.asList(classStr.split(","));
    }

    public void setClassStr(String classStr) {
        this.classStr = classStr;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<String> getSessionList() {
        return Arrays.asList(sessionStr.split(","));
    }

    public void setSessionStr(String sessionStr) {
        this.sessionStr = sessionStr;
    }

    public List<String> getWeekList() {
        return Arrays.asList(weekStr.split(","));
    }

    public void setWeekStr(String weekStr) {
        this.weekStr = weekStr;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassStr() {
        return this.classStr;
    }

    public String getSessionStr() {
        return this.sessionStr;
    }

    public String getWeekStr() {
        return this.weekStr;
    }
}
