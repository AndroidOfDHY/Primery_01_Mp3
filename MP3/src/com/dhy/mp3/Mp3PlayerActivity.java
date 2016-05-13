package com.dhy.mp3;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Mp3PlayerActivity extends Activity {

	private TextView mp3title;
	private ImageView ivstart, ivprevious, ivnext, ivstop;
	private MediaPlayer mp;
	private boolean isStop = true, isPause = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mp3player);
		
		initView();
	}

	private void initView() {
		ivstart = (ImageView) findViewById(R.id.start);
		ivprevious = (ImageView) findViewById(R.id.previous);
		ivnext = (ImageView) findViewById(R.id.next);
		ivstop = (ImageView) findViewById(R.id.stop);
		mp3title = (TextView) findViewById(R.id.mp3title);
		
		ivstart.setOnClickListener(new startOnClickListener());
		ivprevious.setOnClickListener(new previousOnClickListener());
		ivnext.setOnClickListener(new nextOnClickListener());
		ivstop.setOnClickListener(new stopOnClickListener());
	}

	class startOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mp3title.setText("开始播放");
		}
	}

	class previousOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mp3title.setText("上一首歌");
		}
	}

	class nextOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mp3title.setText("下一首歌");
		}
	}

	class stopOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			mp3title.setText("停止播放");
		}
	}
}