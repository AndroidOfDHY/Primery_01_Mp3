package com.dhy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import com.dhy.bean.MP3;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

/*
 * 读取 歌曲列表 的实用工具类
 */

public final class DataUtils {

	private static List<MP3> allList = new ArrayList<MP3>();

	// 为外界提供获取数据列表的接口
	public static List<MP3> getAllList() {
		return allList;
	}

	public static void initData() {
		try {
			// 通过外部文件的文件名获取输入流
			InputStream inputStream = getInputStreamFromSDcard("Mp3player.xml");

			// 获取 xml 解析器
			XmlPullParser xmlParse = Xml.newPullParser();

			// 设置解析器输入流及编码格式
			xmlParse.setInput(inputStream, "utf-8");

			// 对xml数据流进行解析
			allList = ParseXml(xmlParse);
		} catch (Exception e) {
			Log.d("TAG", e.toString());
		}
	}

	/**
	 * 判断sd卡是否挂载在手机上
	 * 
	 * @param 无
	 * @return true 已挂载
	 */
	public static boolean hasSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return true;
		return false;
	}

	/**
	 * 读取SDcard xml文件
	 * 
	 * @param fileName
	 * @return 返回xml文件的inputStream
	 */
	public static InputStream getInputStreamFromSDcard(String fileName)
			throws Exception {
		// 设置path路径
		String path = Environment.getExternalStorageDirectory().toString()
				+ "/mp3/xml/";
		File xmlFlie = new File(path + fileName);
		InputStream inputStream = new FileInputStream(xmlFlie);
		return inputStream;
	}

	/**
	 * 解析SDcard xml文件
	 * 
	 * @param parser
	 * @return 返回MP3歌曲信息数组
	 */
	public static List<MP3> ParseXml(XmlPullParser parser) throws Exception {
		List<MP3> dataList = null;
		MP3 data = null;

		// 开始解析事件:得到第一个事件类型
		int eventType = parser.getEventType();

		// 处理事件，不碰到文档结束就一直处理
		while (eventType != XmlPullParser.END_DOCUMENT) {
			// 因为定义了一堆静态常量，所以这里可以用switch
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				// 不做任何操作或初开始化数据
				dataList = new ArrayList<MP3>();
				break;

			case XmlPullParser.START_TAG:
				// 解析XML节点数据
				// 获取当前标签名字
				String tagName = parser.getName();

				if (tagName.equals("resource")) {
					data = new MP3();
				} else if (tagName.equals("id")) {
					data.setId(Integer.parseInt(parser.nextText()));
				} else if (tagName.equals("mp3.name")) {
					data.setMp3name(parser.nextText());
				} else if (tagName.equals("mp3.size")) {
					data.setMp3size(Long.parseLong(parser.nextText()));
				} else if (tagName.equals("mp3.singer")) {
					data.setMp3singer(parser.nextText());
				} else if (tagName.equals("lrc.name")) {
					data.setLrcname(parser.nextText());
				} else if (tagName.equals("lrc.size")) {
					data.setLrcsize(Long.parseLong(parser.nextText()));
				}
				break;

			case XmlPullParser.END_TAG:
				// 单节点完成，可往集合里边添加新的数据
				if ("resource".equals(parser.getName())) {
					dataList.add(data);
					data = null;
				}
				break;
			case XmlPullParser.END_DOCUMENT:
				break;
			}

			// 别忘了用next方法处理下一个事件，忘了的结果就成死循环
			eventType = parser.next();
		}
		return dataList;  // 返回数据列表
	}
}
