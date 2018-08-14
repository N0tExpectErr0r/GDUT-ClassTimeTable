package com.n0texpecterr0r.classtimetable.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Created by Nullptr
 * @date 2018/8/1 10:52
 * @describe Toast工具类
 */
public class ToastUtil {
    private static Toast sToast;

    /**
     * 显示文字
     * @param context Context
     * @param text 对应文字
     */
    public static void show(Context context , String text){

        if (sToast == null){
            sToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else {
            // 如果当前Toast没有消失,直接显示内容
            sToast.setText(text);
        }
        sToast.show();
    }

}
