package com.dhy.mp3;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.dhy.bean.MP3;
import com.dhy.utils.DataUtils;

public class Mp3PlayerActivity extends Activity implements OnClickListener {

	private TextView mp3title, mp3singer, playtime, lasttime;
	private ImageView ivstart, ivprevious, ivnext, ivstop;
	private MediaPlayer mp;
	private boolean isStop = true, isPause = false;
	private String Path;
	private int position = 0;
	private List<MP3> mp3s;
	private SimpleDateFormat sdf;
	// private Handler handler;
	private Timer timer;
	private SeekBar seekbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mp3player);
		initView();
		initMP();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mp.stop();
		mp.release();
		mp = null;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.pause();
	}

	private void initView() {
		ivstart = (ImageView) findViewById(R.id.start);
		ivprevious = (ImageView) findViewById(R.id.previous);
		ivnext = (ImageView) findViewById(R.id.next);
		ivstop = (ImageView) findViewById(R.id.stop);
		mp3title = (TextView) findViewById(R.id.mp3title);
		mp3singer = (TextView) findViewById(R.id.mp3singer);
		playtime = (TextView) findViewById(R.id.playtime);
		lasttime = (TextView) findViewById(R.id.lasttime);
		timer = new Timer();
		ivstart.setOnClickListener(this);
		ivprevious.setOnClickListener(this);
		ivstop.setOnClickListener(this);
		ivnext.setOnClickListener(this);
		sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());// 设置时间显示格式
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
		setTimerTask();
	}

	private void setTimerTask() {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		}, 10, 1000); /* 表示10毫秒之後，每隔1000毫秒執行一次 */
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			seekbar.setProgress(mp.getCurrentPosition());
			playtime.setText(sdf.format(new Date(mp.getCurrentPosition())));
		}// 实现消息传递
	};// 这里要加分号，handler是个变量不是方法

	private void initMP() {
		Path = Environment.getExternalStorageDirectory().getPath() + "/mp3/";
		mp3s = DataUtils.getAllList();
		position = getIntent().getIntExtra("position", 0);
		try {
			mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(Path + mp3s.get(position).getMp3name());
			mp.prepare();
			seekbar.setMax(mp.getDuration());
			mp.start();
			mp3title.setText(mp3s.get(position).getMp3name()
					.replace(".mp3", ""));
			mp3singer.setText(mp3s.get(position).getMp3singer());
			playtime.setText(sdf.format(new Date(mp.getCurrentPosition())));// 当前的播放位置
			lasttime.setText(sdf.format(new Date(mp.getDuration())));// 总共的播放时间
		} catch (Exception e) {
			// Toast.makeText(getApplicationContext(),
			// "没有找到此歌曲",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
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
				seekbar.setMax(mp.getDuration());
				mp.start();
				mp3title.setText(mp3s.get(position).getMp3name()
						.replace(".mp3", ""));
				mp3singer.setText(mp3s.get(position).getMp3singer());
				playtime.setText(sdf.format(new Date(mp.getCurrentPosition())));
				lasttime.setText(sdf.format(new Date(mp.getDuration())));
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
					mp3title.setText(mp3s.get(position).getMp3name()
							.replace(".mp3", ""));
					mp3singer.setText(mp3s.get(position).getMp3singer());
					playtime.setText(sdf.format(new Date(mp
							.getCurrentPosition())));
					lasttime.setText(sdf.format(new Date(mp.getDuration())));
					ivstart.setImageResource(R.drawable.start);
					isStop = false;
					isPause = false;
				} else if (isStop) {
					try {
						mp.setDataSource(Path + mp3s.get(position).getMp3name());
						mp.prepare();
						seekbar.setMax(mp.getDuration());
						mp.start();
						mp3title.setText(mp3s.get(position).getMp3name()
								.replace(".mp3", ""));
						mp3singer.setText(mp3s.get(position).getMp3singer());
						playtime.setText(sdf.format(new Date(mp
								.getCurrentPosition())));
						lasttime.setText(sdf.format(new Date(mp.getDuration())));
						isStop = false;
						isPause = false;
					} catch (Exception e) {
						// mp3title.setText("播放失败");
						e.printStackTrace();
					}
				}
			} else {
				// mp3title.setText("mp为空");
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
				seekbar.setMax(mp.getDuration());
				mp.start();
				mp3title.setText(mp3s.get(position).getMp3name()
						.replace(".mp3", ""));
				mp3singer.setText(mp3s.get(position).getMp3singer());
				playtime.setText(sdf.format(new Date(mp.getCurrentPosition())));
				lasttime.setText(sdf.format(new Date(mp.getDuration())));
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

	class SeekBarChangeEvent implements OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			mp.seekTo(seekBar.getProgress());
		}
	}

}