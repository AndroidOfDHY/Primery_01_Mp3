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

public class Mp3PlayerActivity extends Activity implements OnClickListener {

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
		initMP();
	}

	private void initView() {
		mp3title = (TextView) findViewById(R.id.mp3title);
		ivstart = (ImageView) findViewById(R.id.start);
		ivprevious = (ImageView) findViewById(R.id.previous);
		ivnext = (ImageView) findViewById(R.id.next);
		ivstop = (ImageView) findViewById(R.id.stop);

		ivstart.setOnClickListener(this);
		ivprevious.setOnClickListener(this);
		ivstop.setOnClickListener(this);
		ivnext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.previous: // 上一首
			mp3title.setText("上一首歌");
			break;
		case R.id.start: // 开始/暂停
			mp3title.setText("开始播放");
			if (mp != null) {
				if (isStop) {
					try {
						mp.prepare();
						mp.start();
						isStop = false;
						isPause = false;
					} catch (Exception e) {
						mp3title.setText("播放失败");
						e.printStackTrace();
					}
				} else if (isPause) {
					mp.start();
					isStop = false;
					isPause = false;
				} else if (mp.isPlaying()) {
					mp.pause();
					isStop = false;
					isPause = true;
				}
			} else {
				mp3title.setText("mp为空");
			}
			break;
		case R.id.stop: // 停止
			mp3title.setText("停止播放");
			if (mp != null) {
				mp.stop();
				isStop = true;
				isPause = false;
			}
			break;
		case R.id.next: // 下一首
			mp3title.setText("下一首歌");
			break;
		default:
			break;
		}
	}

	private void initMP() {
		String path = "/sdcard/mp3/hckz.mp3";
		try {
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(path);
		} catch (Exception e) {
			mp3title.setText("播放失败");
			e.printStackTrace();
		}
	}
}