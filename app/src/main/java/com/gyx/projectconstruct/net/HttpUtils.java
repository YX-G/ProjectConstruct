package com.gyx.projectconstruct.net;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gyx.projectconstruct.utils.FileCopyUtils;
import com.gyx.projectconstruct.utils.LogUtils;
import com.gyx.projectconstruct.utils.MD5Utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 用于发送和取消请求的网络工具类
 * Created by GaoLiangChao on 2016/4/22.
 */
public class HttpUtils{
    /**
     * 过滤重复请求，保存当前正在消息队列中执行的request，其中key为requestCode.
     */
    private static final HashMap<Integer, Request> mInFlightRequests = new HashMap<>();
    /**
     * 请求队列
     */
    private static final RequestQueue mRequestQueue = HttpCore.getRequestQueue();
    /**
     * log日志的TAG
     */
    private static final String TAG = "HTTP";
    /**
     *
     */
    private static final String URL = "";

    public static final String Module1 = "content/list.from";

    public static final String Module2 = "content/text.from";
    public static final String Module3 = "img/list.from";
    public static final String Module4 = "img/text.from";
    /**
     * 以get方式发送gsonRequest请求，默认缓存请求结果
     * @param url       请求地址
     * @param params    GET请求参数，拼接在url后面，可以为null
     * @param requestCode   请求码，每次请求对于一个requestCode，作为该请求的唯一标识
     * @param listener  处理响应的监听器
     * @return  Request对象，方便链式编程，比如说接着设置Tag
     */
    public static Request get(String url, Map<String, Object> params, final int requestCode, final ResponseListener listener) {
        return request(Request.Method.GET, URL+url, params, requestCode, listener, true);
    }


    /**
     * 以get方式发送gsonRequest请求
     *
     * @param url       请求地址
     * @param params    GET请求参数，拼接在url后面，可以为null
     * @param requestCode   请求码，每次请求对于一个requestCode，作为该请求的唯一标识
     * @param listener  处理响应的监听器
     * @param isCache     是否需要缓存本次响应的结果,没有网络时会使用本地缓存
     */
    public static Request get(String url, Map<String, Object> params, final int requestCode, final ResponseListener listener, boolean isCache) {
        return request(Request.Method.GET, URL+url, params, requestCode, listener, isCache);
    }

    /**
     * 发送post方式的GsonRequest请求，默认缓存请求结果
     *
     * @param url         请求地址
     * @param params      请求参数，可以为null
     * @param requestCode 请求码 每次请求对应一个code作为改Request的唯一标识
     * @param listener    处理响应的监听器
     */
    public static Request post(String url, Map<String, Object> params, final int requestCode, final ResponseListener listener) {
        return request(Request.Method.POST, URL+url, params, requestCode, listener, true);
    }


    /**
     * 发送post方式的GsonRequest请求
     *
     * @param url         请求地址
     * @param params      请求参数，可以为null
     * @param requestCode 请求码 每次请求对应一个code作为改Request的唯一标识
     * @param listener    处理响应的监听器
     * @param isCache     是否需要缓存本次响应的结果,没有网络时会使用本地缓存
     */
    public static Request post(String url, Map<String, Object> params,  final int requestCode, final ResponseListener listener, boolean isCache) {
        return request(Request.Method.POST, URL+url, params, requestCode, listener, isCache);
    }


    /**
     * 发送gsonRequest请求
     * @param method        请求方式
     * @param url           请求地址
     * @param params        请求参数，可以为null
     * @param requestCode   请求码
     * @param listener      处理响应的监听器
     * @param isCache       是否需要缓冲本次响应结果
     * @return
     */
    private static Request request(int method, String url, Map<String, Object> params,  int requestCode, ResponseListener listener, boolean isCache) {
        Request request = mInFlightRequests.get(requestCode);
        if (request == null) {
            request = makeGsonRequest(method, url , params,  requestCode, listener, isCache);
            //首先尝试解析本地缓存供界面显示，然后再发起网络请求
            tryLoadCacheResponse(request, requestCode, listener);
            LogUtils.d("Handle request by network!");
            return addRequest(request, requestCode);
        } else {
            LogUtils.d("Hi guy,the request (RequestCode is " + requestCode + ")  is already in-flight , So Ignore!");
            return request;
        }
    }

    /**
     * 遍历Map集合元素，构建一个get请求参数字符串
     *
     * @param params    get请求map集合
     * @return          get请求的字符串结构
     */
    private static String buildGetParam(Map<String, Object> params) {
        StringBuilder buffer = new StringBuilder();
        if (params != null) {
            buffer.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue()+"";
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                try {
                    buffer.append(URLEncoder.encode(key, "UTF-8"));
                    buffer.append("=");
                    buffer.append(URLEncoder.encode(value, "UTF-8"));
                    buffer.append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        String str = buffer.toString();
        //去掉最后的&
        if (str.length() > 1 && str.endsWith("&")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    /**
     * 尝试从缓存中读取json数据
     * @param request       请求
     * @param requestCode   请求码
     * @param listener      结果响应监听
     */
    private static void tryLoadCacheResponse(Request request, int requestCode, ResponseListener listener) {
        LogUtils.d("Try to  load cache response first !");
        if (listener != null && request != null) {
            try {
                if (request instanceof GsonRequest) {
                    //如果是GsonRequest，那么解析出本地缓存的json数据为GsonRequest
                    //GsonRequest gr = (GsonRequest) request;
                    //RBResponse response = (RBResponse) FileCopyUtils.readObject(MD5Utils.encode(request.getUrl()));
                    //Object o = FileCopyUtils.readObject(MD5Utils.encode(request.getUrl()));
                    String response = FileCopyUtils.readString(MD5Utils.encode(request.getUrl()));
                    //传给onResponse，让前面的人用缓存数据
                    listener.onGetSuccessResponse(requestCode, response);
                    LogUtils.d("Load cache response success !");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 把请求添加到请求队列（相当于发起了网络请求）
     * @param requestCode   请求码，请求的唯一标识
     * @return  request，方便链式编程
     */
    private static Request addRequest(Request<?> request, int requestCode) {
        if (mRequestQueue != null && request != null) {
            mRequestQueue.add(request);
        }
        mInFlightRequests.put(requestCode, request);
        return request;
    }

    /**
     * 取消网络请求
     * @param tag 请求TAG
     */
    public void cancelRequset(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);//从请求队列中取消对应的任务
        }
        //同时在mInFlightRequests删除保存所有TAG匹配的Request
        Iterator<Map.Entry<Integer, Request>> it = mInFlightRequests.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, Request> entry = it.next();
            Object rTag = entry.getValue().getTag();
            if (rTag != null && rTag.equals(tag)) {
                it.remove();
            }
        }
    }

    /**
     *构建一个Gson请求
     */
    public static Request makeGsonRequest(int method, String url, Map<String, Object> params, int requestCode, ResponseListener listener, boolean isCache ) {
        ResquestListener httpListener = new ResquestListener(listener, requestCode,url,params);
        GsonRequest gsonRequest;
        if (method == Request.Method.GET) {
            url = url + buildGetParam(params);
            gsonRequest = new GsonRequest(method, url, null, httpListener, httpListener, isCache) {
                @Override
                public Map<String, Object> getHeaders() throws AuthFailureError {
                    //设置请求头
                    return generateHeaders();
                }
            };
        } else {
            gsonRequest = new GsonRequest(method, url, params, httpListener, httpListener, isCache) {
                @Override
                public Map<String, Object> getHeaders() throws AuthFailureError {
                    //设置请求头
                    return generateHeaders();
                }

            };
        }


        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(1500,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));//设置重试机制，超时时间，重试次数，重试因子等
        return gsonRequest;
    }
    /**
     * 生成公共Header头信息
     *
     * @return
     */
    private static Map<String, Object> generateHeaders() {
        Map<String, Object> headers = new HashMap<String, Object>();
        //        appkey        软件身份key
        //        udid          手机客户端的唯一标识
        //        os            操作系统名称
        //        osversion     操作系统版本
        //        appversion    APP版本
        //        sourceid      推广ID
        //        ver           通讯协议版本
        //        userid        用户ID
        //        usersession   登陆后得到的用户唯一性标识
        //        unique        激活后得到的设备唯一性标识
        return headers;
    }

    /**
     * 自定义一个响应监听，供UI层调用
     */
    public interface ResponseListener {
        /**
         * 成功获取到服务器响应数据的时候调用
         * @param requestCode   response对应的requestCode
         * @param response      返回的response
         */
         void onGetSuccessResponse(int requestCode, String response);

        /**
         * 网络获取失败，(做一些释放性的操作，比如关闭对话框)
         * @param requestCode   请求码
         * @param error         错误信息
         */
         void onGetErrorResponse(int requestCode, VolleyError error);
    }
    /**
     * 针对Volley两个响应监听的封装，对请求做公共的处理
     */
    private static class ResquestListener implements Response.Listener<String>, Response.ErrorListener {
        //自定义的监听
        private ResponseListener listener;
        //请求码
        private int requestCode;
        //请求的url
        private String url;
        //请求的参数
        private String params;


        public ResquestListener(ResponseListener listener, int requestCode, String url, Map<String, Object> params) {
            this.listener = listener;
            this.requestCode = requestCode;
            this.url = url;
            this.params = buildGetParam(params);
        }



        //成功响应的监听
        @Override
        public void onResponse(String rbResponse) {
            LogUtils.d(TAG,"请求的网址"+url);

            LogUtils.d(TAG, "上传的参数" + params);

            //请求错误，从集合中删除该请求
            mInFlightRequests.remove(requestCode);
//            //执行通用处理，如果是服务器返回的ErrorResponse，直接提示错误信息并返回
//            if ("error".equals(response.getResponse()) && response instanceof ErrorResponse) {
//                ErrorResponse errorResponse = (ErrorResponse) response;
//                MyToast.show(App.application, errorResponse.getError().getText());
//                return;
//            }
            JSONObject jsonObject = JSON.parseObject(rbResponse);
            String reason = jsonObject.getString("reason");
            if (!"Success".equals(reason)) {
                LogUtils.d(TAG, "error_code：" + jsonObject.get("error_code"));
                return;
            }
            LogUtils.d(TAG, "content：" + rbResponse);
            if (listener != null) {
                listener.onGetSuccessResponse(requestCode, rbResponse);
                LogUtils.d(TAG, "请求成功返回的结果：" + rbResponse);
            }
        }

        //失败响应的监听
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            //请求错误，从集合中删除该请求
            mInFlightRequests.remove(requestCode);
            if (listener != null) {
                listener.onGetErrorResponse(requestCode,volleyError);
            }
            LogUtils.d(TAG,"请求的网址"+url);

            LogUtils.d(TAG,"上传的参数"+params);
            String err = "错误码：" + volleyError.networkResponse.statusCode + " 头信息：" + volleyError.networkResponse.headers.toString() + "  body信息：" + volleyError.networkResponse.data + "  抛出异常："
                    + volleyError.getMessage();
            LogUtils.d(TAG,"请求失败：" +err);


        }
    }

}
