package com.gyx.projectconstruct.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.gyx.projectconstruct.utils.FileCopyUtils;
import com.gyx.projectconstruct.utils.MD5Utils;


import java.util.Map;

/**
 * 自定义Request, 通过gson把json格式的response解析成bean对象，另外请求自带缓存功能
 * Created by GaoLiangChao on 2016/4/26.
 */
public class GsonRequest<T> extends Request<T> {
    private final Map<String, Object> params;
    private final Response.Listener<T> listener;
    private boolean isCache;

    Priority mPriority;

    public void setPriority(Priority priority) {
        mPriority = priority;
    }

    @Override
    public Priority getPriority() {
        // If you didn't use the setPriority method,
        // the priority is automatically set to NORMAL
        return mPriority != null ? mPriority : Priority.NORMAL;
    }


//    /**
//     * 初始化
//     *
//     * @param method        请求方式
//     * @param url           请求地址
//     * @param params        请求参数，可以为null
//     * @param clazz         Clazz类型，用于GSON解析json字符串封装数据
//     * @param listener      处理响应的监听器
//     * @param errorListener 处理错误信息的监听器
//     * @param isCache       是否缓存
//     */
//    public GsonRequest(int method, String url, Map<String, String> params, Class<? extends T> clazz,
//                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isCache) {
//        super(method, url, errorListener);
//        this.clazz = clazz;
//        this.params = params;
//        this.listener = listener;
//        this.isCache = isCache;
//    }
    /**
     * 初始化
     *
     * @param method        请求方式
     * @param url           请求地址
     * @param params        请求参数，可以为null
     * @param listener      处理响应的监听器
     * @param errorListener 处理错误信息的监听器
     * @param isCache       是否缓存
     */
    public GsonRequest(int method, String url, Map<String, Object> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isCache) {
        super(method, url, errorListener);
        this.params = params;
        this.listener = listener;
        this.isCache = isCache;
    }
//    public Class<? extends T> getClazz() {
//        return clazz;
//    }




    /**
     * 重写分发响应的方法
     * @param response
     */
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    /**
     * 重写解析网络响应的方法
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            //得到返回的数据
            String json = new String(
                    response.data);
            if (isCache) {

                FileCopyUtils.fileSave(json, MD5Utils.encode(getUrl()));
            }
//            缓存对象
           T result = (T) json;
//            result = JSON.parseObject(json, clazz);    //解析json
//            if (isCache) {
//                //如果解析成功，并且需要缓存则将json字符串缓存到本地
//                FileCopyUtils.fileSave(response.data, MD5Utils.encode(getUrl()));
//            }
            return Response.success(
                    result,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }

    }
}