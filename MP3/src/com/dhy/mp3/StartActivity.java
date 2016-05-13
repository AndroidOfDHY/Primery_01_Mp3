package com.dhy.mp3;

import com.dhy.utils.DataUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

/**
 * 起始欢迎界面
 * 
 * @author 何广军
 * 
 */
public class StartActivity extends Activity {

	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_layout);
		context = this;

		if (!DataUtils.hasSDCard()) {
			Toast.makeText(context, "手机中没有SD卡！", Toast.LENGTH_LONG).show();
			finish();
		} else {
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {
					if (DataUtils.getAllList().size() == 0) {
						DataUtils.initData();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					startActivity(new Intent(context, Mp3ListActivity.class));
					finish();
				}
			}.execute();
		}
	}
}
