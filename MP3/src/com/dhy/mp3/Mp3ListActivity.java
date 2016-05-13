package com.dhy.mp3;

import java.util.List;

import com.dhy.bean.MP3;
import com.dhy.utils.DataUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Mp3ListActivity extends Activity {
	//private String[] data = { "多情的月光", "假如爱有天意", "年轮" };
	private ListView listView = null;
	public static MyListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
		setContentView(R.layout.activity_mp3list);
		init();
	}

	private void init() {
		listView = (ListView) findViewById(android.R.id.list);
		listAdapter = new MyListAdapter();
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

				System.out.println("---");
				// 进行播放处理
				Log.i("TAG", DataUtils.getAllList().get(position) + "");
				
				Intent intent = new Intent(Mp3ListActivity.this, Mp3PlayerActivity.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		});
	}

	// 自定义的适配器类
	public class MyListAdapter extends BaseAdapter {
		private class textViewHolder {
			TextView mp3Name;
			TextView mp3Singer;
		}

		private List<MP3> mAllList;
		
		public MyListAdapter() {
			this.mAllList = DataUtils.getAllList();
		}
		
		@Override
		public int getCount() {
			return mAllList.size();
		}

		@Override
		public Object getItem(int position) {
			return mAllList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			textViewHolder holder = null;
			if (convertView != null) {
				holder = (textViewHolder) convertView.getTag();
			} else {
				convertView = View.inflate(Mp3ListActivity.this,
						R.layout.mp3item, null);
				holder = new textViewHolder();
				holder.mp3Name = (TextView) convertView
						.findViewById(R.id.mp3_item_title);
				holder.mp3Singer = (TextView) convertView
						.findViewById(R.id.mp3_item_artist);
				convertView.setTag(holder);
			}

			MP3 mp3Music = mAllList.get(position);

			if (mp3Music != null) {
				holder.mp3Name.setText(mp3Music.getMp3name());
				holder.mp3Singer.setText(mp3Music.getMp3singer());
			}
			return convertView;
		}
	}

}
