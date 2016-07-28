package com.gyx.projectconstruct.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by GaoLiangChao on 2016/4/22.
 */
public class Tools {
    /**
     * 获取版本号
     * @param mContext
     * @return
     */
    public static int getAppVersionNumber(Context mContext) {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 1;
    }
}
