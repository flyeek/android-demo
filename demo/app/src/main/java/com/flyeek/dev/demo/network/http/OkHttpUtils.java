package com.flyeek.dev.demo.network.http;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by flyeek on 9/17/15.
 */
public class OkHttpUtils {

	private static final OkHttpClient mOkHttpClient = new OkHttpClient();
	private static final String CHARSET_NAME = "UTF-8";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	static {
		mOkHttpClient.setConnectTimeout(15, TimeUnit.SECONDS);
	}

	/**
	 * 异步Get请求数据，不在意回调
	 * @param url 请求链接
	 */
	public static void asyncGetRequest(String url) {
		Request request = OkHttpUtils.makeGetRequest(url);
		OkHttpUtils.enqueue(request);
	}

	/**
	 * 带回调的异步请求Get数据
	 * @param url 请求链接
	 * @param responseCallback 回调
	 */
	public static void asyncGetRequest(String url, Callback responseCallback) {
		Request request = OkHttpUtils.makeGetRequest(url);
		OkHttpUtils.enqueue(request, responseCallback);
	}

	/**
	 * 带回调的异步Post请求数据
	 * @param url 请求链接
	 * @param responseCallback 回调
	 */
	public static void asyncPostRequest(String url, String requestBody, MediaType type, Callback responseCallback) {
		Request request = OkHttpUtils.makePostRequest(url, requestBody, type);
		OkHttpUtils.enqueue(request, responseCallback);
	}

	/**
	 * 开启异步线程访问网络
	 *
	 * @param request 异步请求
	 * @param responseCallback 请求回调
	 */
	private static void enqueue(Request request, Callback responseCallback) {
		mOkHttpClient.newCall(request).enqueue(responseCallback);
	}

	/**
	 * 开启异步线程访问网络, 且不在意返回结果（实现空callback）
	 *
	 * @param request 异步请求
	 */
	private static void enqueue(Request request) {
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
			}


			@Override

			public void onFailure(Request arg0, IOException arg1) {
			}

		});
	}

	/**
	 *
	 * @param url 请求链接
	 * @return Request
	 */
	private static Request makeGetRequest(String url) {
		return new Request.Builder().url(url).build();
	}

	/**
	 *
	 * @param url 请求链接
	 * @param requestBody 请求的内容
	 * @param type content type
	 * @return Request
	 */
	private static Request makePostRequest(String url, String requestBody, MediaType type) {
		RequestBody body = RequestBody.create(type, requestBody);
		return new Request.Builder().url(url).post(body).build();
	}

}
