package com.gyx.projectconstruct.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * 对话框公用基类
 * Created by zhangdroid on 2016/3/29.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    private static final String ARGUMENT_DIALOG_TITLE = "dialog_title";
    private static final String ARGUMENT_DIALOG_MESSAGE = "dialog_message";
    // 确认按钮文字
    private static final String ARGUMENT_DIALOG_POSITIVE = "dialog_positive";
    // 取消按钮文字
    private static final String ARGUMENT_DIALOG_NEGATIVE = "dialog_negative";

    private Bundle mBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    public static Bundle getDialogBundle(String title, String message, String positive, String negative) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_DIALOG_TITLE, title);
        bundle.putString(ARGUMENT_DIALOG_MESSAGE, message);
        bundle.putString(ARGUMENT_DIALOG_POSITIVE, positive);
        bundle.putString(ARGUMENT_DIALOG_NEGATIVE, negative);
        return bundle;
    }

    protected String getDialogTitle() {
        return mBundle == null ? null : mBundle.getString(ARGUMENT_DIALOG_TITLE);
    }

    protected String getDialogMessage() {
        return mBundle == null ? null : mBundle.getString(ARGUMENT_DIALOG_MESSAGE);
    }

    protected String getDialogPositive() {
        return mBundle == null ? null : mBundle.getString(ARGUMENT_DIALOG_POSITIVE);
    }

    protected String getDialogNegative() {
        return mBundle == null ? null : mBundle.getString(ARGUMENT_DIALOG_NEGATIVE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        View view = getActivity().getLayoutInflater().inflate(getLayoutResId(), null);
        setDialogContentView(view);
        alertDialog.setView(view);
        // 去掉dialog背景
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.setCancelable(true);
        return alertDialog;
    }

    /**
     * 自定义dialog的布局文件资源id
     */
    protected abstract int getLayoutResId();

    /**
     * 设置dialog的内容
     *
     * @param view the dialog content view
     */
    protected abstract void setDialogContentView(View view);

}
