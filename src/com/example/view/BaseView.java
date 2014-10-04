package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.beatplane.MainActivity;
import com.example.util.ConstantUtil;
import com.example.util.GameSoundPool;

public class BaseView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
	protected int currentFrame;				// ��ǰ����֡
	protected float scalex;					// ����ͼƬ�����ű���
	protected float scaley;
	protected float screen_width;			// ��ͼ�Ŀ��
	protected float screen_height;			// ��ͼ�ĸ߶�
	protected boolean threadFlag;			// �߳����еı��
	protected Paint paint; 					// ���ʶ���
	protected Canvas canvas; 				// ��������
	protected Thread thread; 				// ��ͼ�߳�
	protected SurfaceHolder sfh;
	protected GameSoundPool sounds;
	protected MainActivity mainActivity;
	// ���췽��
	public BaseView(Context context,GameSoundPool sounds) {
		super(context);
		// TODO Auto-generated constructor stub
		this.sounds = sounds;
		this.mainActivity = (MainActivity) context;
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		setFocusable(true);
		setFocusableInTouchMode(true);
		//���ñ�������
		this.setKeepScreenOn(true);
	}
	// ��ͼ�ı�ķ���
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}
	// ��ͼ�����ķ���
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		screen_width = this.getWidth();		//�����ͼ�Ŀ��
		screen_height = this.getHeight();	//�����ͼ�ĸ߶�
		threadFlag = true;
	}
	// ��ͼ���ٵķ���
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		threadFlag = false;
	}
	// ��ʼ��ͼƬ��Դ����
	public void initBitmap() {}
	// �ͷ�ͼƬ��Դ�ķ���
	public void release() {}
	// ��ͼ����
	public void drawSelf() {}
	// �߳����еķ���
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	public void setThreadFlag(boolean threadFlag){
		this.threadFlag = threadFlag;
	}
	
}
