package com.dhy.mp3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Mp3ListActivity extends Activity {
	private String[] data = { "多情的月光", "假如爱有天意", "年轮" };
	private ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mp3item);
		
		//接收数据
		Intent intent = getIntent();
		String data = intent.getStringExtra("extra_data");
		//验证
		Log.d("TAG", data);
	}

	// 按back键是启动
	@Override
	public void onBackPressed() {
		Intent intent = getIntent();
		intent.putExtra("data_return", "Hello, StartActivity");
		setResult(RESULT_OK,intent);
		finish(); // 终止Activity的执行
	}
	
	


}
