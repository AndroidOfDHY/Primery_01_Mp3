package com.dhy.utils;

import java.util.ArrayList;
import java.util.List;

import com.dhy.bean.LrcContent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义绘画歌词，产生滚动效果
 */
public class LycirView extends TextView {

	private float width; // 歌词视图宽度
	private float height; // 歌词视图高度
	private Paint currentPaint; // 当前画笔对象
	private Paint notCurrentPaint; // 非当前画笔对象
	private float textHeight = 25; // 文本高度
	private float textSize = 18; // 文本大小
	private int index = 0; // list集合下标

	private List<LrcContent> mLrcList = new ArrayList<LrcContent>();

	public void setmLrcList(List<LrcContent> mLrcList) {
		this.mLrcList = mLrcList;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public LycirView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LycirView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LycirView(Context context) {
		super(context);
		init();
	}

	private void init() {
		setFocusable(true); // 设置可对焦

		// 高亮部分
		currentPaint = new Paint();
		currentPaint.setAntiAlias(true); // 设置抗锯齿，让文字美观饱满
		currentPaint.setTextAlign(Paint.Align.CENTER);// 设置文本对齐方式

		// 非高亮部分
		notCurrentPaint = new Paint();
		notCurrentPaint.setAntiAlias(true);
		notCurrentPaint.setTextAlign(Paint.Align.CENTER);
	}

	/**
	 * 绘画歌词
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (canvas == null) {
			return;
		}

		currentPaint.setColor(Color.argb(210, 251, 248, 29));
		notCurrentPaint.setColor(Color.argb(140, 255, 255, 255));

		currentPaint.setTextSize(24);
		currentPaint.setTypeface(Typeface.SERIF); // 系统自带的一种字体

		notCurrentPaint.setTextSize(textSize);
		notCurrentPaint.setTypeface(Typeface.DEFAULT);
		try {
			setText("");
			canvas.drawText(mLrcList.get(index).getLrcStr(), width / 2,
					height / 2, currentPaint);

			float tempY = height / 2;
			// 画出本句之前的句子
			for (int i = index - 1; i >= 0; i--) {
				// 向上推移
				tempY = tempY - textHeight;
				canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY,
						notCurrentPaint);
			}
			tempY = height / 2;
			// 画出本句之后的句子
			for (int i = index + 1; i < mLrcList.size(); i++) {
				// 往下推移
				tempY = tempY + textHeight;
				canvas.drawText(mLrcList.get(i).getLrcStr(), width / 2, tempY,
						notCurrentPaint);
			}
		} catch (Exception e) {
			setText("...木有歌词，赶紧去下载...");
		}
	}

	/**
	 * 当view大小改变的时候调用的方法
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.width = w;
		this.height = h;
	}

}
