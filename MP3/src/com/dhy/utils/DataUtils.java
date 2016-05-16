package com.dhy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dhy.bean.MP3;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/*
 * 读取 歌曲列表 的实用工具类
 */

public final class DataUtils {

	private static List<MP3> allList = new ArrayList<MP3>();
	private static Context context;

	public static List<MP3> getAllList() {
		return allList;
	}

	public static Context getContext() {
		return context;
	}

	public static void initData(Context context, String uri) {
		DataUtils.context = context;
		// 通过服务器的文件名获取输入流
		InputStream inputStream = getInputStreamFromServer(uri); // //
		try {
			// 定义工厂 XmlPullParserFactory
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

			// 定义解析器 XmlPullParser
			XmlPullParser parser = factory.newPullParser();

			// 获取xml输入数据
			parser.setInput(inputStream, "utf-8");

			allList = ParseXml(parser);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取远程服务器 xml文件
	 * 
	 * @param fileName
	 * @return 返回xml文件的inputStream
	 */
	public static InputStream getInputStreamFromServer(String uri) {

		HttpEntity entity;
		try {

			entity = HttpUtils.getEntity(uri, null, HttpUtils.METHOD_GET);
			InputStream inputStream = HttpUtils.getInputStream(entity);
			return inputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析 xml文件
	 * 
	 * @param parser
	 * @return 返回MP3歌曲信息数组
	 */
	public static ArrayList<MP3> ParseXml(XmlPullParser parser) {
		ArrayList<MP3> mp3Array = new ArrayList<MP3>();
		MP3 mp3Temp = null;

		try {
			// 开始解析事件:得到第一个事件类型
			int eventType = parser.getEventType();

			// 处理事件，不碰到文档结束就一直处理
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// 因为定义了一堆静态常量，所以这里可以用switch
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					// 不做任何操作或初开始化数据
					break;

				case XmlPullParser.START_TAG:
					// 解析XML节点数据
					// 获取当前标签名字
					String tagName = parser.getName();

					if (tagName.equals("resource")) {
						mp3Temp = new MP3();
					} else if (tagName.equals("id")) {
						mp3Temp.setId(Integer.parseInt(parser.nextText()));
					} else if (tagName.equals("mp3.name")) {
						mp3Temp.setMp3name(parser.nextText());
					} else if (tagName.equals("mp3.size")) {
						mp3Temp.setMp3size(Long.parseLong(parser.nextText()));
					} else if (tagName.equals("mp3.singer")) {
						mp3Temp.setMp3singer(parser.nextText());
					} else if (tagName.equals("lrc.name")) {
						mp3Temp.setLrcname(parser.nextText());
					} else if (tagName.equals("lrc.size")) {
						mp3Temp.setLrcsize(Long.parseLong(parser.nextText()));
					}
					break;

				case XmlPullParser.END_TAG:
					// 单节点完成，可往集合里边添加新的数据
					if ("resource".equals(parser.getName())) {
						mp3Array.add(mp3Temp);
					}
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				}

				// 别忘了用next方法处理下一个事件，忘了的结果就成死循环
				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mp3Array;
	}
}
