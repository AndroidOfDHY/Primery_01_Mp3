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

import android.os.Environment;

import com.dhy.bean.MP3;

/**
 * 读取文件的实用工具类
 */
public final class DataUtils {
	private static List<MP3> allList = new ArrayList<MP3>();

	// 为外界提供获取数据列表的接口
	public static List<MP3> getAllList() {
		return allList;
	}

	/**
	 * 判断sd卡是否挂载在手机上
	 * 
	 * @param无
	 * @return true 已挂载
	 */
	public static boolean hasSDCard() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
			return true;
		return false;
	}
	
	/**
	* 读取SDcardxml文件
	* @paramfileName
	* @return返回xml文件的inputStream
	*/
	public static InputStream getInputStreamFromSDcard(String fileName)
	  throws Exception {
	    // 设置path路径
	    String path= Environment.getExternalStorageDirectory().toString() + "/mp3/xml/";
	    File xmlFlie = new File(path + fileName);
	    InputStream inputStream = new FileInputStream(xmlFlie);
	    return inputStream;
	}
}
