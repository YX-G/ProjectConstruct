package com.gyx.projectconstruct.image;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.gyx.projectconstruct.R;
import com.lidroid.xutils.BitmapUtils;

public class ImageLoader {
    private static BitmapUtils loader;

    public static BitmapUtils getInstance(Context ctx) {
	if (loader == null) {
	    synchronized (BitmapUtils.class) {
		if (loader == null) {
		    loader = new BitmapUtils(ctx, Environment
			    .getExternalStorageDirectory().getAbsolutePath()
			    + File.separator + "hosma", 10, 200);
		    }
	    }
	}
    loader.configDefaultConnectTimeout(60*1000*60*24);
    loader.configDefaultReadTimeout(60*1000*60*24);
	loader.configDiskCacheEnabled(true);
/*	loader.configDefaultLoadFailedImage(R.drawable.default_img);
	loader.configDefaultLoadingImage(R.drawable.default_img);*/


	return loader;
    }
}