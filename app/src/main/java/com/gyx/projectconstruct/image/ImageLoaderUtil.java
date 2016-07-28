package com.gyx.projectconstruct.image;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * ImageLoader加载图片工具类，根据不同的需要选择不同的方法，如果重载的方法不够，可以自己添加
 * 
 * @author zhangmeng
 * 
 */
public class ImageLoaderUtil {

	public static void Load(String imgUrl, ImageView imageView, int defaultImageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageId)
				.showImageForEmptyUri(defaultImageId).showImageOnFail(defaultImageId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
	}

	public static void Load(String imgUrl, ImageView imageView) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
	}

	public static void LoadLocal(String imgUrl, ImageView imageView, int defaultImageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageId)
				.showImageForEmptyUri(defaultImageId).showImageOnFail(defaultImageId).cacheInMemory(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
	}

	public static void LoadPhonePic(String imgUrl, ImageView imageView, int defaultImageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageId)
				.showImageForEmptyUri(defaultImageId).showImageOnFail(defaultImageId).cacheInMemory(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoader.getInstance().displayImage("file:/" + imgUrl, imageView, options);
	}

	public static void LoadPhoneCriclePic(String imgUrl, ImageView imageView, int defaultImageId) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageId)
				.showImageForEmptyUri(defaultImageId).showImageOnFail(defaultImageId).cacheInMemory(true)
				.considerExifParams(true).displayer(new CircleBitmapDisplayer()).bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		ImageLoader.getInstance().displayImage("file:/" + imgUrl, imageView, options);
	}
	/**
	 * 加载图片的方法  正在加载、空Uri、请求失败的情况显示默认图片  多加了一个监听
	 * 做内存缓存以及本地缓存
	 * @param imgUrl 图片的URL
	 * @param imageView 加载图片的ImageView对象
	 * @param defaultImageId 默认的图片资源
	 * @param listener 关于加载过程的监听
	 */
	public static void Load(String imgUrl, ImageView imageView, int defaultImageId, ImageLoadingListener listener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(defaultImageId)
				.showImageForEmptyUri(defaultImageId).showImageOnFail(defaultImageId).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoader.getInstance().displayImage(imgUrl, imageView, options, listener);
	}

	public static void Load(String imgUrl, ImageView imageView, ImageLoadingListener listener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		ImageLoader.getInstance().displayImage(imgUrl, imageView, options, listener);
	}

	public static void LoadCirclePic(String imgUrl, ImageView imageView, int defaultpicid) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).showImageOnLoading(defaultpicid).showImageForEmptyUri(defaultpicid)
				.showImageOnFail(defaultpicid).bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new CircleBitmapDisplayer()).build();

		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
	}

	public static void LoadCirclePic(String imgUrl, ImageView imageView,
			SimpleImageLoadingListener simpleImageLoadingListener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).displayer(new CircleBitmapDisplayer())
				.build();

		ImageLoader.getInstance().displayImage(imgUrl, imageView, options, simpleImageLoadingListener);
	}

	public static void LoadCirclePic(String imgUrl, ImageView imageView) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).displayer(new CircleBitmapDisplayer())
				.build();

		ImageLoader.getInstance().displayImage(imgUrl, imageView, options);
	}

	public static void getCirclePic(String imgUrl, ImageSize targetImageSize,ImageLoadingListener loadingListener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
				/*.displayer(new com.hm.hosma.datatask.CircleBitmapDisplayer())*/
				.build();
	         ImageLoader.getInstance().loadImage(imgUrl, targetImageSize, options, loadingListener);

	}


	public static void getCircleBg(String imgUrl,ImageLoadingListener loadingListener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true)
				.considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		ImageLoader.getInstance().loadImage(imgUrl, options, loadingListener);

	}

}
