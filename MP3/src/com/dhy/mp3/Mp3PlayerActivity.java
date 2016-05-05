package com.dhy.mp3;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class Mp3PlayerActivity extends Activity {

	private SeekBar seek = null;
	private TextView text = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FrameLayout linearLayout = new FrameLayout(this);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		
		FrameLayout.LayoutParams txtparams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		TextView textview = new TextView(this);
		textview.setLayoutParams(txtparams);
		textview.setText("宁波大红鹰学院");
		textview.setTextSize(20);
		TextView textview1 = new TextView(this);
		textview1.setLayoutParams(txtparams);
		textview1.setText("信息工程学院");
		textview1.setTextSize(20);
		linearLayout.addView(textview, txtparams);
		linearLayout.addView(textview1, txtparams);
		addContentView(linearLayout, params);
		
//		setContentView(R.layout.activity_mp3player);
//		
//		this.seek = (SeekBar) super.findViewById(R.id.seekbar);
//		this.text = (TextView) super.findViewById(R.id.text);
//		this.text.setMovementMethod(ScrollingMovementMethod.getInstance());
//		this.seek.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImpl());
	}
	
	private class OnSeekBarChangeListenerImpl implements SeekBar.OnSeekBarChangeListener {

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			Mp3PlayerActivity.this.text.append("正在拖动，当前值："+seekBar.getProgress());
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Mp3PlayerActivity.this.text.append("开始拖动，当前值："+seekBar.getProgress());
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Mp3PlayerActivity.this.text.append("停止拖动，当前值："+seekBar.getProgress());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mp3_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
