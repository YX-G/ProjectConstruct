package com.gyx.projectconstruct.base;

import android.content.Context;
import android.os.Environment;

import com.gyx.projectconstruct.BuildConfig;
import com.gyx.projectconstruct.utils.L;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mContext;// 程序的Context对象
    private UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
    private static CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器  
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理  
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
//            try {
//                Thread.sleep(1000);// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            // 退出程序
            ((BaseApplication) mContext.getApplicationContext()).exit();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(Throwable ex) {
        if (ex == null)
            return false;
        ex.printStackTrace();
        saveCrashInfo2File(ex);
        return true;
    }

    private void saveCrashInfo2File(Throwable ex) {
        String timeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File logFile = new File(Environment.getExternalStorageDirectory() + File.separator + "Error.txt");
        StringWriter sw = new StringWriter();
        sw.append(timeStr).append('\n');
        ex.printStackTrace(new PrintWriter(sw));
        sw.append('\n');
        L.writeFile(logFile, sw.toString());
    }
}
