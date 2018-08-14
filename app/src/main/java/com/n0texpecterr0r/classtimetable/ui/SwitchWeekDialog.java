package com.n0texpecterr0r.classtimetable.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import com.n0texpecterr0r.classtimetable.utils.ToastUtil;

/**
 * @author Created by Nullptr
 * @date 2018/8/14 11:10
 * @describe 选择周次Dialog
 */
public class SwitchWeekDialog extends AlertDialog.Builder {

    private String[] mItems = new String[25];
    private OnChooseListener mChooseListener;

    public SwitchWeekDialog(@NonNull Context context, int currentItem) {
        super(context);
        setTitle("选择周次");
        for (int i = 0; i <= 24; i++) {
            mItems[i] = "第" + (i + 1) + "周";
        }
        setSingleChoiceItems(mItems, currentItem-1, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                if (mChooseListener != null) {
                    mChooseListener.onChoose(position);
                } else {
                    ToastUtil.show(getContext(), mItems[position]);
                }
            }
        });
    }

    /**
     * 设置选中接口
     */
    public void setOnChooseListener(OnChooseListener chooseListener) {
        mChooseListener = chooseListener;
    }


    public interface OnChooseListener {

        /**
         * 选中监听
         * @param position 选中的position
         */
        void onChoose(int position);
    }
}
