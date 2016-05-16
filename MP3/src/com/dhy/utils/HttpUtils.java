package com.dhy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @Descrpiton进行http请求的工具类
 */
public class HttpUtils {
	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;

	public static HttpEntity getEntity(String uri, List<NameValuePair> params,
			int method) throws ClientProtocolException, IOException {
		HttpEntity entity = null;
		// 创建客户端对象
		HttpClient client = new DefaultHttpClient();
		// 声明请求对象
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:// 采用httpGet
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append("?");
				for (NameValuePair pair : params) {
					sb.append(pair.getName()).append("=")
							.append(pair.getValue()).append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			// 实例化请求对象HttpGet
			request = new HttpGet(sb.toString());
			break;
		case METHOD_POST:// 采用httpPost
			// 实例化请求对象HttpPost
			request = new HttpPost(uri);
			// 准备或封装请求对象
			UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params,
					"utf-8");
			// 将请求实体添加到请求对象中
			((HttpPost) request).setEntity(reqEntity);
			break;
		}
		// 执行请求
		HttpResponse response = client.execute(request);
		// 判断响应码
		if (response.getStatusLine().getStatusCode() == 200) {
			entity = response.getEntity();
		}
		return entity;
	}

	public static InputStream getInputStream(HttpEntity entity)
			throws IllegalStateException, IOException {
		if (entity != null) {
			return entity.getContent();
		}
		return null;
	}
}
