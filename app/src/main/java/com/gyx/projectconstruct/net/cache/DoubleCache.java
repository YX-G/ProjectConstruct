package com.gyx.projectconstruct.net.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.LruCache;

import com.gyx.projectconstruct.utils.MD5Utils;
import com.gyx.projectconstruct.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 双缓存类
 * 用于磁盘缓存和内容缓存
 * <p/>
 * Created by GaoLiangChao on 2016/4/22.
 */
public class DoubleCache extends BaseCache {

    //上下文
    private static Context mContext;
    //内存缓存类
    private static LruCache<String, Bitmap> mLruCache;
    //磁盘缓存类
    private static DiskLruCache mDiskLruCache;

    private static final int MAX_DISK_LRU_CACHE_SIZE = 10 * 1024 * 1024;

    /**
     * 初始化方法
     *
     * @param mContext
     */
    public DoubleCache(Context mContext) {
        this.mContext = mContext;
        //初始化内存缓存   大小为最大运行内存的1/8
        mLruCache = new LruCache<String, Bitmap>((int) Runtime.getRuntime().maxMemory() / 8) {
            @Override
            protected int sizeOf(String url, Bitmap bitmap) {
                //计算每个bitmap的大小 用行字节数*高度
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
        //初始化磁盘缓存
        try {
            mDiskLruCache = DiskLruCache.open(getDiskCachePath("bitmaps"), Tools.getAppVersionNumber(mContext), 1, MAX_DISK_LRU_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取Bitmap  先从内存中取 如果没有再从本地取，根据url找值bitmap
     *
     * @param url
     * @return bitmap
     */
    @Override
    public Bitmap getBitmap(String url) {
        if (mLruCache.get(url) != null) {
            return mLruCache.get(url);
        } else {
            String key = MD5Utils.encode(url)+"";
            try {
                if (mDiskLruCache.get(key) != null) {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    Bitmap bitmap = null;
                    if (snapshot != null) {
                        bitmap = BitmapFactory.decodeStream(snapshot.getInputStream(0));
                        //从磁盘缓存取出来之后，证明你用了，也要往内存缓存存一次
                        mLruCache.put(url, bitmap);
                    }
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * 存储bitmap(内存缓存 + 磁盘缓存)
     *
     * @param url
     * @param bitmap
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //首先直接存储LruCache缓存
        mLruCache.put(url, bitmap);
        String key = MD5Utils.encode(url)+"";
        try {
            //判断是否存在DiskLruCache缓存，若没有则存入
            if (mDiskLruCache.get(key) == null) {
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存路径
     *
     * @return
     */
    private File getDiskCachePath(String dirName) {
        String cachePath;
        //判断是否是挂载状态
        if ((Environment.MEDIA_MOUNTED).equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()
                && null != mContext.getExternalCacheDir()) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);
    }
}
