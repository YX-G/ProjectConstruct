package com.gyx.projectconstruct.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * 需要使用Fragment的Activity的基类
 * Created by zhangdroid on 2016/2/25.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindowFeature();
        setScreenOrientation();


        super.onCreate(savedInstanceState);
        if (getLayoutResId() > 0) {
            setContentView(getLayoutResId());
            ButterKnife.bind(this);
        }

        initViewsAndVariables();
        addListeners();
        doRegister();
    }

    /**
     * 设置无标题栏等属性
     */
    protected void setWindowFeature() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 设置屏幕方向
     */
    protected void setScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    /**
     * 获得布局文件资源id
     *
     * @return R.layout.xx
     */
    protected abstract int getLayoutResId();

    protected abstract void initViewsAndVariables();

    protected abstract void addListeners();

    /**
     * 注册/初始化receiver或第三方库
     */
    protected void doRegister() {
    }

    /**
     * 注销/释放相关资源
     */
    protected void unregister() {

    }

}
