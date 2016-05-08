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


}
