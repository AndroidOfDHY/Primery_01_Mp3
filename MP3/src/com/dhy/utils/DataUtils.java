/**
 * @author 何广军
 *
 * 2015-9-4
 */
package com.dhy.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.dhy.bean.MP3;

/**
 * 读取文件的实用工具类
 */
public final class DataUtils {
	private static List<MP3> allList = new ArrayList<MP3>();
	
	// 为外界提供获取数据列表的接口
	public static List<MP3> getAllList(){
		return allList;
	}
	
	/**
	 * 判断sd卡是否挂载在手机上
	 * @param 无
	 * @return true 已挂载
	 */
	public static boolean hasSDCard(){
		if (Environment.MEDIA_MOUNTED.equals(
				Environment.getExternalStorageState())){
			return true;
		}
		return false;
	}
	
	/**
	* 读取SDcard xml文件
	* @param fileName
	* @return 返回xml文件的inputStream
	*/
	public static InputStream getInputStreamFromSDCard(String fileName) 
			throws Exception{
		// 设置读取文件的路径
		String path = Environment.getExternalStorageDirectory().toString()
				+"/mp3/xml/";
		File xmlFile = new File(path+fileName);
		InputStream inputStream = new FileInputStream(xmlFile);
		return inputStream;
	}

	// 数据读取总控框架
	public static void initData(){
		try{
			// 通过外部文件的文件名获取输入流
			InputStream inputStream = getInputStreamFromSDCard("Mp3player.xml");
			// 获取XML解析器
			XmlPullParser xmlParser = Xml.newPullParser();
			// 设置解析器输入流及编码格式
			xmlParser.setInput(inputStream, "utf-8");
			
			// 对XML输入流进行解析
			
			
		}catch(Exception e){
			Log.d("TAG",e.toString());
		}
	}

	/*
	 * 解析SDCard xml文件
	 * @param parser
	 * @return MP3歌曲信息列表
	 */
	public static List<MP3> ParseXml(XmlPullParser parser) throws Exception{
		List<MP3> dataList = null; // 歌曲列表
		MP3 data = null; // 歌曲对象
		
		// 开始解析事件 ：得到第一个事件类型
		int eventType = parser.getEventType();
		// 处理事件：不碰到文档结束就一直处理
		while (eventType != XmlPullParser.END_DOCUMENT){
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				//不做任何处理或初始化数据列表
				dataList = new ArrayList<MP3>();
				break;
            case XmlPullParser.START_TAG:
            	// 解析每个XML节点
            	// 获取当前标签名字
				String tagName = parser.getName();
				
				// 根据不同名字做不同的处理
				if(tagName.equalsIgnoreCase("resource")){
					// 对象开始标志，需要新建对象
					data = new MP3();
				}else if (tagName.equalsIgnoreCase("id")){
					data.setId(Integer.parseInt(parser.nextText()));
				}else if (tagName.equalsIgnoreCase("mp3.name")){
					data.setMp3name(parser.nextText());
				}else if (tagName.equalsIgnoreCase("mp3.size")){
					data.setMp3size(Long.parseLong(parser.nextText()));
				}else if (tagName.equalsIgnoreCase("mp3.singer")){
					data.setMp3singer(parser.nextText());
				}else if (tagName.equalsIgnoreCase("lrc.name")){
					data.setLrcname(parser.nextText());
				}else if (tagName.equalsIgnoreCase("lrc.size")){
					data.setLrcsize(Long.parseLong(parser.nextText()));
				}
				break;
            case XmlPullParser.END_TAG:
            	//一个对象数据读取结束，需要加入列表
            	if("resource".equalsIgnoreCase(parser.getName())){
            		dataList.add(data);
            		data = null;
            	}
	            break;
            case XmlPullParser.END_DOCUMENT:
            	//一般不做处理
	
            	break;

			default:
				break;
			}
			eventType = parser.next();// 不要忘记，否则会死循环
		}
		return dataList;  // 返回数据列表
	}
}
