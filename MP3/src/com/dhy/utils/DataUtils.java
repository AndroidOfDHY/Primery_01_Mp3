/**
 * @author 何广军
 *
 * 2015-9-4
 */
package com.dhy.utils;

import java.util.ArrayList;
import java.util.List;

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

}
