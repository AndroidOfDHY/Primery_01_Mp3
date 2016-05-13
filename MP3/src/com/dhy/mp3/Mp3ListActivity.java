package com.dhy.mp3;

import java.util.List;

import com.dhy.bean.MP3;
import com.dhy.utils.DataUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Mp3ListActivity extends Activity {
	// private String[] data = { "多情的月光", "假如爱有天意", "年轮" };
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
		listView = (ListView) findViewById(android.R.id.list);
		// 创建Adapter
		listAdapter = new MyListAdapter();
		// 建立ListView与Adapter之间的联系
		listView.setAdapter(listAdapter);

	}

	public class MyListAdapter extends BaseAdapter {
		private List<MP3> mAllList; // 需要提供的数据集合

		public MyListAdapter() {
			// 构造方法，用于进行数据初始化
			this.mAllList = DataUtils.getAllList();
		}

		// 程序在加载显示到UI上时就要先读取的，获得的值决定了listview显示多少行
		@Override
		public int getCount() {
			// 在实际应用中，此处的返回值是由从数据集合中查询出来的数据的总条数
			return mAllList.size();
		}

		// 根据ListView所在位置返回View的组装数据
		@Override
		public Object getItem(int position) {
			return mAllList.get(position);
		}

		// 根据ListView位置得到数据源集合中的Id
		@Override
		public long getItemId(int position) {
			return position;
		}

		// 编写adapter最重要的就是重写此方法，此方法也是决定listview界面的样式
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 当convertView不为空的时候直接重新使用convertView从而减少了很多不必要的View的创建

			if (convertView == null) {
				// 使用LayoutInfalter对象来导入布局
				convertView = View.inflate(Mp3ListActivity.this,
						R.layout.mp3item, null);
			}
			// 找到item中的元素，并设置相应的数据
			((TextView) convertView.findViewById(R.id.mp3_item_title))
					.setText(mAllList.get(position).getMp3name());
			((TextView) convertView.findViewById(R.id.mp3_item_artist))
					.setText(mAllList.get(position).getMp3singer());

			// 　返回组装后的item view
			return convertView;
		}

	}

}
