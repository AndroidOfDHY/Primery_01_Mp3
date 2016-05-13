/**
 * @author 何广军
 *
 * 2015-8-31
 */
package com.dhy.mp3;

import com.dhy.utils.DataUtils;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

/**
 * 系统启动界面
 */
public class StartActivity extends Activity {
	public static Context context; // 上下文环境变量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);  // 隐藏标题
		setContentView(R.layout.start_layout); // 界面初始化
		context = this;
		init(); // 参数初始化
	}

	private void init() {
		// 判断是否有SD卡
		if(!DataUtils.hasSDCard()){
	       //无：给出提示信息，结束项目；
			Toast.makeText(context, "手机中没有SD卡！", Toast.LENGTH_LONG).show();
			finish();
		}else{
	       //有：读取XML文件。
			DataUtils.initData();
			//验证
			Log.d("TAG",DataUtils.getAllList().toString());
		}
	}

}
