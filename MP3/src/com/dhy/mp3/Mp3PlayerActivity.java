package com.dhy.mp3;

import java.util.List;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dhy.bean.MP3;
import com.dhy.utils.DataUtils;

public class Mp3PlayerActivity extends Activity implements OnClickListener {

	private TextView mp3title;
	private ImageView ivstart, ivprevious, ivnext, ivstop;
	private MediaPlayer mp;
	private boolean isStop = true, isPause = false;
	private String Path = Environment.getExternalStorageDirectory().getPath()
			+ "/mp3/";
	private int position = 0;
	private List<MP3> mp3s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mp3player);
		initView();
		initMP();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.previous: // 上一首
			mp.stop();
			mp.reset();// 进入Idle状态
			if (position == 0) {// 第一首的上一首是最后一首
				position = mp3s.size() - 1;
			} else {
				position--;
			}
			try {
				mp.setDataSource(Path + mp3s.get(position).getMp3name());
				mp.prepare();
				mp.start();
				ivstart.setImageResource(R.drawable.start);
				isStop = false;
				isPause = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.start: // 开始/暂停
			if (mp != null) {
				if (mp.isPlaying()) {
					mp.pause();
					ivstart.setImageResource(R.drawable.pause);
					isStop = false;
					isPause = true;
				} else if (isPause) {
					mp.start();
					ivstart.setImageResource(R.drawable.start);
					isStop = false;
					isPause = false;
				} else if (isStop) {
					try {
						mp.setDataSource(Path + mp3s.get(position).getMp3name());
						mp.prepare();
						mp.start();
						isStop = false;
						isPause = false;
					} catch (Exception e) {
						mp3title.setText("播放失败");
						e.printStackTrace();
					}
				}
			} else {
				mp3title.setText("mp为空");
			}
			break;
		case R.id.stop: // 停止
			mp3title.setText("停止播放");
			mp.stop();
			mp.reset();
			isStop = true;
			isPause = false;
			break;
		case R.id.next: // 下一首
			mp.stop();
			mp.reset();
			if (position == mp3s.size() - 1) {
				position = 0;
			} else {
				position++;
			}
			try {
				mp.setDataSource(Path + mp3s.get(position).getMp3name());
				mp.prepare();
				mp.start();
				ivstart.setImageResource(R.drawable.start);
				isStop = false;
				isPause = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	private void initView() {
		ivstart = (ImageView) findViewById(R.id.start);
		ivprevious = (ImageView) findViewById(R.id.previous);
		ivnext = (ImageView) findViewById(R.id.next);
		ivstop = (ImageView) findViewById(R.id.stop);
		mp3title = (TextView) findViewById(R.id.mp3title);
		ivstart.setOnClickListener(this);
		ivprevious.setOnClickListener(this);
		ivstop.setOnClickListener(this);
		ivnext.setOnClickListener(this);
	}

	private void initMP() {

		mp3s = DataUtils.getAllList();
		position = getIntent().getIntExtra("position", 0);
		try {
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(Path + mp3s.get(position).getMp3name());
			mp.prepare();
			mp.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "没有找到此歌曲",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
}