package com.dhy.mp3;

import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class Mp3ListActivity extends Activity {
	//private String[] data = { "多情的月光", "假如爱有天意", "年轮" };
	private ListView listView = null; // 定义全局的ListView
	// 定义自定义的Adapter
	public static MyListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mp3list);
		init();

	}
	
	// 进行初始化
	private void init() {
		// 装载ListView组件
		listView = (ListView)findViewById(android.R.id.list);
		// 创建Adapter
		listAdapter = new MyListAdapter();
		// 建立ListView与Adapter之间的联系
		listView.setAdapter(listAdapter);
		
	}

	public class MyListAdapter extends BaseAdapter{
		
	}


	
	


}
