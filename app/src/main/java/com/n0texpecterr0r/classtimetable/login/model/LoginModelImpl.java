package com.n0texpecterr0r.classtimetable.login.model;

import com.n0texpecterr0r.classtimetable.login.presenter.OnLoginListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author Created by Nullptr
 * @date 2018/8/10 15:02
 * @describe 登录Model实现
 */
public class LoginModelImpl implements LoginModel {

    private static final String LOGIN_URL =
            "http://authserver.gdut.edu.cn/authserver/login?service=http%3A%2F%2Fjxfw.gdut.edu.cn%2Fnew%2FssoLogin";
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0";

    private ExecutorService mExecutorService;
    private final OnLoginListener mLoginListener;

    public LoginModelImpl(OnLoginListener loginListener){
        mLoginListener = loginListener;
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void login(final String username, final String password) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                /*
                 * 第一次请求
                 */
                Connection connection = Jsoup.connect(LOGIN_URL);
                connection.header("User-Agent", USER_AGENT);
                Response response = null;
                try {
                    response = connection.execute();
                } catch (IOException e) {
                    mLoginListener.onFail();
                }
                // 转为Dom树
                Document document = Jsoup.parse(response.body());
                // 获取form表单
                List<Element> elementList = document.select("form");

                // 获取Cookie及表单属性
                Map<String, String> datas = new HashMap<>();
                for (Element element : elementList.get(0).getAllElements()) {
                    if (element.attr("name").equals("username")){
                        // 设置用户名
                        element.attr("value",username);
                    }
                    if (element.attr("name").equals("password")){
                        // 设置密码
                        element.attr("value",password);
                    }
                    if (element.attr("name").length() > 0) {
                        // 排除空值表单属性
                        datas.put(element.attr("name"), element.attr("value"));
                    }
                }

                /*
                 * 第二次请求
                 */
                Connection scdConnection = Jsoup.connect(LOGIN_URL);
                scdConnection.header("User-Agent",USER_AGENT);
                // 设置Cookie并且post上面的数据
                Response loginResponse = null;
                try {
                    loginResponse = scdConnection
                            .ignoreContentType(true)
                            .followRedirects(true)
                            .method(Method.POST)
                            .data(datas)
                            .cookies(response.cookies())
                            .execute();
                } catch (IOException e) {
                    mLoginListener.onFail();
                }
                if (loginResponse!=null) {
                    Document reallyDocument = Jsoup.parse(loginResponse.body());
                    if (reallyDocument.title().equals("教学管理系统")) {
                        // 登录成功的cookie信息，可以存在本地，之后只需一次登录
                        Map<String, String> cookies = loginResponse.cookies();
                        mLoginListener.onSuccess(cookies);
                    } else {
                        // 登录失败
                        mLoginListener.onFail();
                    }
                }else{
                    mLoginListener.onFail();
                }
            }
        });
    }

}
