package com.gyx.projectconstruct.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 网络请求的核心类 初始化网络队列 以及将请求添加到请求队列中
 * Created by Glc on 2016/4/8.
 */
public class HttpCore {

    private static HttpCore httpCore;
    //请求队列
    private static RequestQueue mRequestQueue ;
    //上下文
    private static Context mContext;
    //图片加载器
    private static ImageLoader imageLoder;
    //默认缓存为二级缓存
    private ImageLoader.ImageCache iamgeCache ;

    //private BaseCache baseCache = new DoubleCache(mContext);
    /**
     * 初始化网络框架 初始化队列 在基类中初始化只初始化一次
     * @param context
     */
    public static void httpInit(Context context) {
        if (null == mRequestQueue) {
            synchronized (HttpCore.class) {
                if (null == mRequestQueue) {
                    httpCore = new HttpCore(context);
                }
            }
        }
    }

    /**
     * 私有构造方法
     * 做请求队列 以及 Imageloader 的初始化
     */
    private HttpCore(Context context) {
        this.mContext = context;

        mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        //imageLoder = new ImageLoader(mRequestQueue,baseCache);


    }

    /**
     * 获取消息队列
     * @return mRequestQueue 消息队列的实例对象
     */
    public static RequestQueue getRequestQueue() {
        if (null == mRequestQueue) {

            throw new IllegalStateException("HttpCore not yet initialized,please initialize it in Application.class   ");
        }else {

            return mRequestQueue;
        }
    }


}
