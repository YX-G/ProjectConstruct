package com.gyx.projectconstruct.net.cache;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader;


/**
 * 缓存类的基类 所有缓存类都要继承该类
 * Created by Glc on 2016/4/9.
 */
public abstract class BaseCache implements ImageLoader.ImageCache {
    @Override
    public abstract Bitmap getBitmap(String url) ;


    @Override
    public abstract void putBitmap(String url, Bitmap bitmap) ;

}
