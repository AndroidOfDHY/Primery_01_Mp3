package com.dhy.mp3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dhy.bean.MP3;
import com.dhy.utils.Config;
import com.dhy.utils.DataUtils;
import com.dhy.utils.HttpUtils;

public class Mp3ListActivity extends Activity {
	// private String[] data = { "多情的月光", "假如爱有天意", "年轮" };
	private ListView listView = null;
	public static MyListAdapter listAdapter;

	private String mp3Name;

	// private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
		setContentView(R.layout.activity_mp3list);
		init();
		this.registerForContextMenu(listView);
		// Builder builder = new Builder(this);
		/*
		 * dialog = builder.setIcon(android.R.drawable.ic_dialog_info)
		 * .setTitle("删除").setCancelable(false) .setPositiveButton("确定",
		 * null).create();
		 */
		// getOverflowMenu();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
		menu.setHeaderTitle("操作");
		// 添加上下文菜单
		menu.add(1, 0, 1, "下载");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		if (item.getItemId() == 0) {
			/*
			 * 处理所选中音乐的下载，包括歌词文件
			 */
			// 构建服务器连接地址
			mp3Name = ((MP3) listAdapter.getItem(position)).getMp3name();
			DownloadAsyncTask download = new DownloadAsyncTask();
			download.execute(mp3Name);
		}
		return super.onContextItemSelected(item);
	}

	private void init() {
		listView = (ListView) findViewById(android.R.id.list);
		listAdapter = new MyListAdapter();
		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				System.out.println("---------------");
				// 进行播放处理
				Log.e("TAG", DataUtils.getAllList().get(position) + "");

				Intent intent = new Intent(Mp3ListActivity.this,
						Mp3PlayerActivity.class);
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

	class DownloadAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			// 构建服务器连接地址
			String uri = Config.SERVICE_IP + "mp3/" + params[0];
			// String path =
			// Environment.getExternalStorageDirectory().toString()+ "/mp3";
			System.out.println("uri" + uri);
			try {
				HttpEntity entity = HttpUtils.getEntity(uri, null,
						HttpUtils.METHOD_GET);
				InputStream is = HttpUtils.getInputStream(entity);

				File sdcardDir = Environment.getExternalStorageDirectory();
				File file = new File(sdcardDir + "/mp3/" + params[0]);
				file.createNewFile();
				// 获取指向文件的输出流对象
				FileOutputStream os = new FileOutputStream(file);
				byte[] buffer = new byte[1024 * 4]; // 每次读取字节的缓冲数组
				BufferedInputStream in = new BufferedInputStream(is);
				BufferedOutputStream out = new BufferedOutputStream(os);
				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					// 将读取的1k内容输出到输出流
					out.write(buffer, 0, len);
					// 清理缓存
					out.flush();
				}
				out.close();
				os.close();
				in.close();
				is.close();
				return "下载完成";
			} catch (Exception e) {
				e.printStackTrace();
				return "下载失败";
			}
		}
	}

}
