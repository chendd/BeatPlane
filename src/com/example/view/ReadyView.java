package com.example.view;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.example.beatplane.R;
import com.example.util.ConstantUtil;
import com.example.util.GameSoundPool;

public class ReadyView extends BaseView {
	
	private float fly_x;					// ͼƬ������
	private float fly_y;
	private float fly_height;
	private float text_x;
	private float text_y;
	private float button_x;
	private float button_y;
	private float button_y2;
	private float strwid;
	private float strhei;
	private boolean isBtChange;				// ��ťͼƬ�ı�ı��
	private boolean isBtChange2;
	private String startGame = "��ʼ��Ϸ";	// ��ť������
	private String aboutGame = "��������";
	private Bitmap text;					// ����ͼƬ
	private Bitmap button;					// ��ťͼƬ
	private Bitmap button2;					// ��ťͼƬ
	private Bitmap planefly;				// �ɻ����е�ͼƬ
	private Bitmap background;				// ����ͼƬ
	private Rect rect;						// �������ֵ�����
	
	public ReadyView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		// TODO Auto-generated constructor stub
		paint.setTextSize(40);
		paint.setColor(Color.rgb(80, 80, 80));
		paint.setAntiAlias(true);
		rect = new Rect();
		thread = new Thread(this);
        
	}
	
	// ��ͼ�ı�ķ���
		@Override
		public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			super.surfaceChanged(arg0, arg1, arg2, arg3);
		}
		// ��ͼ�����ķ���
		@Override
		public void surfaceCreated(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			super.surfaceCreated(arg0);
			initBitmap(); 
			if(thread.isAlive()){
				thread.start();
			}
			else{
				thread = new Thread(this);
				thread.start();
			}
		}
		// ��ͼ���ٵķ���
		@Override
		public void surfaceDestroyed(SurfaceHolder arg0) {
			// TODO Auto-generated method stub
			super.surfaceDestroyed(arg0);
			release();
		}
		// ��Ӧ�����¼��ķ���
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN
					&& event.getPointerCount() == 1) {
				float x = event.getX();
				float y = event.getY();
				//�жϵ�һ����ť�Ƿ񱻰���
				if (x > button_x && x < button_x + button.getWidth()
						&& y > button_y && y < button_y + button.getHeight()) {
					sounds.playSound(7, 0,true);
					isBtChange = true;
					drawSelf();
					mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
				}
				//�жϵڶ�����ť�Ƿ񱻰���
				else if (x > button_x && x < button_x + button.getWidth()
						&& y > button_y2 && y < button_y2 + button.getHeight()) {
					sounds.playSound(7, 0,true);
					isBtChange2 = true;
					drawSelf();
					mainActivity.getHandler().sendEmptyMessage(ConstantUtil.ABOUT_GAME);
					
				}
				return true;
			} 
			//��Ӧ��Ļ�����ƶ�����Ϣ
			else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				float x = event.getX();
				float y = event.getY();
				if (x > button_x && x < button_x + button.getWidth()
						&& y > button_y && y < button_y + button.getHeight()) {
					isBtChange = true;
				} else {
					isBtChange = false;
				}
				if (x > button_x && x < button_x + button.getWidth()
						&& y > button_y2 && y < button_y2 + button.getHeight()) {
					isBtChange2 = true;
				} else {
					isBtChange2 = false;
				}
				return true;
			} 
			//��Ӧ��ָ�뿪��Ļ����Ϣ
			else if (event.getAction() == MotionEvent.ACTION_UP) {
				isBtChange = false;
				isBtChange2 = false;
				return true;
			}
			return false;
		}
		// ��ʼ��ͼƬ��Դ����
		@Override
		public void initBitmap() {
			// TODO Auto-generated method stub
			background = BitmapFactory.decodeResource(getResources(),R.drawable.bg_01);
			text = BitmapFactory.decodeResource(getResources(), R.drawable.text);
			planefly = BitmapFactory.decodeResource(getResources(), R.drawable.fly);
			button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
			button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
			scalex = screen_width / background.getWidth();
			scaley = screen_height / background.getHeight();
			text_x = screen_width / 2 - text.getWidth() / 2;
			text_y = screen_height / 3 - text.getHeight();
			fly_x = screen_width / 2 - planefly.getWidth() / 2;
			fly_height = planefly.getHeight() / 3;
			fly_y = text_y - fly_height - 20;
			button_x = screen_width / 2 - button.getWidth() / 2;
			button_y = screen_height / 2 + button.getHeight();
			button_y2 = button_y + button.getHeight() + 40;
			// ���ذ�Χ�����ַ�������С��һ��Rect����
			paint.getTextBounds(startGame, 0, startGame.length(), rect);
			strwid = rect.width();
			strhei = rect.height();
		}
		// �ͷ�ͼƬ��Դ�ķ���
		@Override
		public void release() {
			// TODO Auto-generated method stub
			if (!text.isRecycled()) {
				text.recycle();
			}
			if (!button.isRecycled()) {
				button.recycle();
			}
			if (!button2.isRecycled()) {
				button2.recycle();
			}	
			if (!planefly.isRecycled()) {
				planefly.recycle();
			}
			if (!background.isRecycled()) {
				background.recycle();
			}
		}
		// ��ͼ����
		@Override
		public void drawSelf() {
			// TODO Auto-generated method stub
			try {
				canvas = sfh.lockCanvas();
				canvas.drawColor(Color.BLACK); 						// ���Ʊ���ɫ
				canvas.save();
				canvas.scale(scalex, scaley, 0, 0);					// ���㱳��ͼƬ����Ļ�ı���
				canvas.drawBitmap(background, 0, 0, paint); 		// ���Ʊ���ͼ
				canvas.restore();
				canvas.drawBitmap(text, text_x, text_y, paint);		// ��������ͼƬ
				//����ָ������ťʱ�任ͼƬ
				if (isBtChange) {
					canvas.drawBitmap(button2, button_x, button_y, paint);
				} 
				else {
					canvas.drawBitmap(button, button_x, button_y, paint);
				}
				if (isBtChange2) {
					canvas.drawBitmap(button2, button_x, button_y2, paint);
				}
				else {
					canvas.drawBitmap(button, button_x, button_y2, paint);
				}		
				//��ʼ��Ϸ�İ�ť
				canvas.drawText(startGame, screen_width / 2 - strwid / 2, button_y
						+ button.getHeight() / 2 + strhei / 2, paint);
				//�������ǵİ�ť
				canvas.drawText(aboutGame, screen_width / 2 - strwid / 2, button_y2
						+ button.getHeight() / 2 + strhei / 2, paint);
				//�ɻ����еĶ���
				canvas.save();
				canvas.clipRect(fly_x, fly_y, fly_x + planefly.getWidth(), fly_y + fly_height);
				canvas.drawBitmap(planefly, fly_x, fly_y - currentFrame * fly_height,paint);
				currentFrame++;
				if (currentFrame >= 3) {
					currentFrame = 0;
				}
				canvas.restore();
			} catch (Exception err) {
				err.printStackTrace();
			} finally {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			}
		}
		
		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
			//����back���ذ���
			if (keyCode == KeyEvent.KEYCODE_BACK) {		
				threadFlag = false;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
				//��ʾ�˰����Ѵ������ٽ���ϵͳ����
				//�Ӷ�������Ϸ�������̨
				return true;
			}
			
			return super.onKeyDown(keyCode, event);
		}
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			//����back���ذ���
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				threadFlag = false;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
				//��ʾ�˰����Ѵ������ٽ���ϵͳ����
				//�Ӷ�������Ϸ�������̨
				return true;
			}
			
			return super.onKeyDown(keyCode, event);
		}
		
		// �߳����еķ���
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (threadFlag) {
				long startTime = System.currentTimeMillis();
				drawSelf();
				long endTime = System.currentTimeMillis();
				try {
					if (endTime - startTime < 400)
						Thread.sleep(400 - (endTime - startTime));
				} catch (InterruptedException err) {
					err.printStackTrace();
				}
			}
		}

}
