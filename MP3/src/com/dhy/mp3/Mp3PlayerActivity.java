package com.dhy.mp3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dhy.bean.LrcContent;
import com.dhy.bean.MP3;
import com.dhy.utils.DataUtils;
import com.dhy.utils.LrcProcess;
import com.dhy.utils.LycirView;

public class Mp3PlayerActivity extends Activity implements OnClickListener {

	private boolean isStop = false;
	private String BASE_PATH = Environment.getExternalStorageDirectory()
			.getPath() + "/mp3/";
	private int position = 0;
	private List<MP3> mp3s;
	private SeekBar seek = null;
	private TextView mp3title, mp3singer, playtime, lasttime;
	private ImageView start;

	private MediaPlayer mp;

	private Timer timer = new Timer();

	/*
	 * 歌词处理
	 */
	private LrcProcess mLrcProcess; // 歌词处理
	private List<LrcContent> lrcList = new ArrayList<LrcContent>(); // 存放歌词列表对象
	private int index = 0; // 歌词检索值
	private static LycirView lrcView;

	boolean stopThread = false;

	private class VerifyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (mp.isPlaying()) {
				Message msg = new Message();
				msg.what = 100 * mp.getCurrentPosition() / mp.getDuration();
				handler.sendMessage(msg);
			}
		}
	};

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			seek.setProgress(msg.what);
			SimpleDateFormat sdf = new SimpleDateFormat("mm:ss",
					Locale.getDefault());
			playtime.setText(sdf.format(new Date(mp.getCurrentPosition())));
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mp3player);
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		initMP();
		// showLrc();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.pause();
	}

	@Override
	protected void onDestroy() {
		stopThread = true;
		super.onDestroy();
		mp.stop();
		mp.release();
		timer.cancel();
	}

	private void initView() {
		findViewById(R.id.previous).setOnClickListener(this);
		start = (ImageView) findViewById(R.id.start);
		start.setOnClickListener(this);
		findViewById(R.id.stop).setOnClickListener(this);
		findViewById(R.id.next).setOnClickListener(this);
		seek = (SeekBar) findViewById(R.id.seekbar);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImpl());

		mp3title = (TextView) findViewById(R.id.mp3title);
		mp3singer = (TextView) findViewById(R.id.mp3singer);

		playtime = (TextView) findViewById(R.id.playtime);
		lasttime = (TextView) findViewById(R.id.lasttime);

		// lrcView = (LycirView)findViewById(R.id.lrc); //歌词视图
	}

	// 初始化MediaPlayer
	private void initMP() {
		// 获得歌曲列表
		mp3s = DataUtils.getAllList();
		position = getIntent().getIntExtra("position", 0);
		try {
			mp = new MediaPlayer();
			mp.setDataSource(BASE_PATH + mp3s.get(position).getMp3name());

			mp.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
					// 设置歌名、歌手
					String name = mp3s.get(position).getMp3name()
							.replace(".mp3", "");
					mp3title.setText(name);
					mp3singer.setText(mp3s.get(position).getMp3singer());
					// 初始化进度条
					seek.setProgress(0);
					// 修改播放按钮
					if (mp.isPlaying()) {
						start.setImageResource(R.drawable.pause);
					}
					SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale
							.getDefault());
					playtime.setText(sdf.format(new Date(mp
							.getCurrentPosition())));
					lasttime.setText(sdf.format(new Date(mp.getDuration())));
				}
			});

			mp.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.stop();
					mp.reset();
					if (position == mp3s.size() - 1) {
						position = 0;
					} else {
						position++;
					}
					try {
						mp.setDataSource(BASE_PATH
								+ mp3s.get(position).getMp3name());
						mp.prepare();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			mp.prepareAsync();
			// 定时器
			timer.schedule(new VerifyTimerTask(), 0, 500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 进度条监听器
	private class OnSeekBarChangeListenerImpl implements
			SeekBar.OnSeekBarChangeListener {

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// Mp3PlayerActivity.this.text.append("正在拖动，当前值："+seekBar.getProgress());
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			// Mp3PlayerActivity.this.text.append("开始拖动，当前值："+seekBar.getProgress());
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
			// Mp3PlayerActivity.this.text.append("停止拖动，当前值："+seekBar.getProgress());
			mp.seekTo(seekBar.getProgress() * mp.getDuration() / 100);
			// mp.start();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.previous: // 上一首
			mp.stop();
			mp.reset();
			if (position == 0) {
				position = mp3s.size() - 1;
			} else {
				position--;
			}
			try {
				mp.setDataSource(BASE_PATH + mp3s.get(position).getMp3name());
				mp.prepare();
				// showLrc();
			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		case R.id.start: // 开始/暂停
			if (mp.isPlaying()) {
				mp.pause();
				start.setImageResource(R.drawable.start);
			} else if (!isStop) {
				mp.start();
				start.setImageResource(R.drawable.pause);
			} else {
				try {
					mp.setDataSource(BASE_PATH
							+ mp3s.get(position).getMp3name());
					mp.prepare();

				} catch (Exception e) {
					e.printStackTrace();
				}
				isStop = false;
			}
			// showLrc();
			break;
		case R.id.stop: // 停止
			mp.stop();
			mp.reset();
			seek.setProgress(0);
			start.setImageResource(R.drawable.start);
			isStop = true;
			// showLrc();
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
				mp.setDataSource(BASE_PATH + mp3s.get(position).getMp3name());
				mp.prepare();
				// showLrc();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/*
	 * 显示歌词
	 */
	public void showLrc() {
		mLrcProcess = new LrcProcess();
		// 读取歌词文件
		mLrcProcess
				.readLRC(mp3s.get(position).getMp3name().replace(".mp3", ""));
		// 传回处理后的歌词文件
		lrcList = mLrcProcess.getLrcList();
		Mp3PlayerActivity.lrcView.setmLrcList(lrcList);
		// 切换带动画显示歌词
		Mp3PlayerActivity.lrcView.setAnimation(AnimationUtils.loadAnimation(
				Mp3PlayerActivity.this, R.anim.alpha_z));
		handler.post(mRunnable); // 启动线程
	}

	Runnable mRunnable = new Runnable() {
		public void run() {
			if (!stopThread) {
				Mp3PlayerActivity.lrcView.setIndex(lrcIndex());
				Mp3PlayerActivity.lrcView.invalidate();
				handler.postDelayed(mRunnable, 100); // 定时更新
			}
		};
	};

	/**
	 * 根据时间获取歌词显示的索引值
	 * 
	 * @return
	 */
	public int lrcIndex() {
		int currentTime = 0;
		int duration = 0;
		if (mp.isPlaying()) {
			currentTime = mp.getCurrentPosition();
			duration = mp.getDuration();
		}
		if (currentTime < duration) {
			for (int i = 0; i < lrcList.size(); i++) {
				if (i < lrcList.size() - 1) {
					if (currentTime < lrcList.get(i).getLrcTime() && i == 0) { // 还没开始
						index = i;
					}
					if (currentTime > lrcList.get(i).getLrcTime()
							&& currentTime < lrcList.get(i + 1).getLrcTime()) {
						index = i; // 歌曲在当前歌词和下一句歌词中间
					}
				}
				if (i == lrcList.size() - 1
						&& currentTime > lrcList.get(i).getLrcTime()) { // 歌曲放完，并且歌词在最后一句
					index = i;
				}
			}
		}
		return index;
	}
}
