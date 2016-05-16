package com.dhy.mp3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

import com.dhy.utils.Config;
import com.dhy.utils.DataUtils;
/**
 * 起始欢迎界面
 * @author 何广军
 *
 */
public class StartActivity extends Activity {

	public static Context context;
	//private ContentResolver resolver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_layout);
		context = this;
		//构建服务器连接地址
		String uri = Config.SERVICE_IP+"mp3/xml/Mp3player.xml";
		//创建异步任务对象
		MusicAnycTask task = new MusicAnycTask();
		task.execute(uri);
	}
	
	class MusicAnycTask extends AsyncTask<String, Void, Void>{

		@Override
		protected Void doInBackground(String... params) {
			if(DataUtils.getAllList().size()==0) {
				DataUtils.initData(context,params[0]);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			startActivity(new Intent(context, Mp3ListActivity.class));
			finish();
		}
		
	}
}
