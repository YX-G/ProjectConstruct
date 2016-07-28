package com.gyx.projectconstruct.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by zhangdroid on 2016/2/25.
 */
public abstract class BaseFragment extends Fragment {
    public View mRootView;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doRegister();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {

            mRootView = inflater.inflate(getLayoutResId(), container, false);
        }

        ButterKnife.bind(this, mRootView);
        initViewsAndVariables();
        addListeners();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
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


    public void reStr(String s) {

    }
}
