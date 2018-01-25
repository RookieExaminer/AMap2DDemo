package com.amap.map2d.demo.util;


import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.amap.api.maps2d.model.Marker;
import com.amap.map2d.demo.R;


/**
 * @author azheng
 * @date 2018/1/24.
 * GitHub：https://git.oschina.net/weiazheng
 * email：wei.azheng@foxmail.com
 * description：窗口
 */


public class WindowDialog {

    private static WindowDialog instance;
    private AlertDialog.Builder builder;

    private WindowDialog() {
    }

    public static WindowDialog getInstance() {
        if (instance == null) {
            synchronized (WindowDialog.class) {
                if (instance == null) {
                    instance = new WindowDialog();
                }
            }
        }
        return instance;
    }

    public void showDialog(Marker marker, Context mContext) {

        builder = new AlertDialog.Builder(mContext, R.style.aMapWindowDialog);
        //判断身份类型
        switch (marker.getObject().toString()) {
            case "1":
                setDog(marker, mContext);
                break;
            case "2":
                setCat(marker, mContext);
                break;
            default:
                break;
        }
    }

    /**
     * 设置小狗的布局
     * @param marker
     * @param mContext
     */
    private void setDog(Marker marker, Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_amap_marker_window_dog, null, false);
        setAlertDialog(view);
        TextView nameDog,ageDog,genderDog;
        nameDog = (TextView) view.findViewById(R.id.text_name_marker_window_dog);
        ageDog = (TextView) view.findViewById(R.id.text_age_marker_window_dog);
        genderDog = (TextView) view.findViewById(R.id.text_gender_marker_window_dog);

        //截取字段
        String age = marker.getSnippet().substring(0, marker.getSnippet().indexOf("#"));
        String gender = marker.getSnippet().replaceAll(age + "#", "");


        nameDog.setText("名字："+marker.getTitle());
        ageDog.setText("年龄："+age+"岁");
        genderDog.setText("性别："+gender+"的");

    }
    /**
     * 设置小猫的布局
     * @param marker
     * @param mContext
     */
    private void setCat(Marker marker, Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_amap_marker_window_cat, null, false);
        setAlertDialog(view);
        TextView nameCat,ageCat,genderCat;
        nameCat = (TextView) view.findViewById(R.id.text_name_marker_window_cat);
        ageCat = (TextView) view.findViewById(R.id.text_age_marker_window_cat);
        genderCat = (TextView) view.findViewById(R.id.text_gender_marker_window_cat);


        //截取字段
        String age = marker.getSnippet().substring(0, marker.getSnippet().indexOf("#"));
        String gender = marker.getSnippet().replaceAll(age + "#", "");

        nameCat.setText("名字："+marker.getTitle());
        ageCat.setText("年龄："+age+"岁");
        genderCat.setText("性别："+gender+"的");
    }

    /**
     * 初始化Dialog
     * @param view
     */
    private void setAlertDialog(View view) {
        final AlertDialog mDialog = builder.create();
        Window mWindow = mDialog.getWindow();
        assert mWindow != null;
        mWindow.setWindowAnimations(R.style.DialogThemeForgetAnim);
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
        mWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams mWindowlayoutParams = mWindow.getAttributes();
        mWindowlayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowlayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindow.setAttributes(mWindowlayoutParams);
        mDialog.show();
        mDialog.setContentView(view);

    }


}
