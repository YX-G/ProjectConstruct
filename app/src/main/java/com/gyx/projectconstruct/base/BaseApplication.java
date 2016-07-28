package com.gyx.projectconstruct.base;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;


import com.gyx.projectconstruct.aws.CognitoClientManager;
import com.gyx.projectconstruct.aws.DynamoDBManager;
import com.gyx.projectconstruct.net.HttpCore;
import com.gyx.projectconstruct.utils.Log4jConfigure;
import com.gyx.projectconstruct.utils.LogUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatReportStrategy;
import com.tencent.stat.StatService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import butterknife.ButterKnife;


/**
 * Created by aomiya
 */
public class BaseApplication extends Application {
    private static Context appContext;
    private ExecutorService executorService;
    protected final Set<Activity> activityPool = new HashSet<>();
    protected final Set<Activity> frontActivityPool = new HashSet<>();
    protected final Set<String> serviceNamePool = new HashSet<>();
    protected final Set<Toast> mToastSet = new HashSet<>();
    public static Context getAppContext(
    )
    {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        ButterKnife.setDebug(false);
        CognitoClientManager.init(this);
        DynamoDBManager.init();
        HttpCore.httpInit(this);
        StatConfig.init(this);
        initImageLoader();
        CrashHandler crashHandler=CrashHandler.getInstance();
        crashHandler.init(this);
        Log4jConfigure.configure();
        initMTAConfig(true);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public void addActivity(Activity activity) {
        activityPool.add(activity);
    }

    public void addFrontActivity(Activity activity) {
        synchronized (frontActivityPool) {
            frontActivityPool.add(activity);
        }
    }

    public Activity findActivity(ComponentName componentName) {
        synchronized (frontActivityPool) {
            Iterator<Activity> iterator = frontActivityPool.iterator();
            while (iterator.hasNext()) {
                Activity activity = iterator.next();
                if (activity == null) {
                    iterator.remove();
                    continue;
                }
                if (activity.getComponentName().equals(componentName)) {
                    return activity;
                }
            }
            return null;
        }
    }

    public void removeActivity(Activity activity) {
        activityPool.remove(activity);
    }



    public void removeFrontActivity(Activity activity) {
        synchronized (frontActivityPool) {
            frontActivityPool.remove(activity);
        }
    }


    public void addService(String serviceName) {
        serviceNamePool.add(serviceName);
    }


    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .denyCacheImageMultipleSizesInMemory().memoryCache(new WeakMemoryCache())
                .threadPoolSize(4).threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize((int) (Runtime.getRuntime().freeMemory()/3))
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(50)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
                 ImageLoader.getInstance().init(config);
    }

    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
        }
        return false;
    }
    public void exit() {
        for (Activity ac : activityPool) {
            initMTAConfig(true);
            ac.finish();
        }
        for (String serviceName : serviceNamePool) {
            try {
                stopService(new Intent(this, Class.forName(serviceName)));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 根据不同的模式，建议设置的开关状态，可根据实际情况调整，仅供参考。
     *
     * @param isDebugMode
     *            根据调试或发布条件，配置对应的MTA配置
     */
    private void initMTAConfig(boolean isDebugMode) {
        LogUtils.d("isDebugMode:" + isDebugMode);
        if (isDebugMode) { // 调试时建议设置的开关状态
            // 查看MTA日志及上报数据内容
             StatConfig.setDebugEnable(true);
            // 禁用MTA对app未处理异常的捕获，方便开发者调试时，及时获知详细错误信息。
             StatConfig.setAutoExceptionCaught(false);
             StatConfig.setEnableSmartReporting(false);
             Thread.setDefaultUncaughtExceptionHandler(new
             Thread.UncaughtExceptionHandler() {

             @Override
             public void uncaughtException(Thread thread, Throwable ex) {

             }
             });
             //调试时，使用实时发送
             StatConfig.setStatSendStrategy(StatReportStrategy.BATCH);
             // 是否按顺序上报
             StatConfig.setReportEventsByOrder(false);
             // 缓存在内存的buffer日志数量,达到这个数量时会被写入db
             StatConfig.setNumEventsCachedInMemory(30);
             // 缓存在内存的buffer定期写入的周期
             StatConfig.setFlushDBSpaceMS(10 * 1000);
             // 如果用户退出后台，记得调用以下接口，将buffer写入db
             StatService.flushDataToDB(getApplicationContext());

             StatConfig.setEnableSmartReporting(false);
             StatConfig.setSendPeriodMinutes(1);
             StatConfig.setStatSendStrategy(StatReportStrategy.PERIOD);
        } else { // 发布时，建议设置的开关状态，请确保以下开关是否设置合理
            // 禁止MTA打印日志
            StatConfig.setDebugEnable(false);
            // 根据情况，决定是否开启MTA对app未处理异常的捕获
            StatConfig.setAutoExceptionCaught(true);
            // 选择默认的上报策略
            StatConfig.setStatSendStrategy(StatReportStrategy.APP_LAUNCH);
        }
    }
}
