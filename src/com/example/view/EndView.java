package com.example.view;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.beatplane.MainActivity;
import com.example.beatplane.R;
import com.example.util.ConstantUtil;
import com.example.util.GameSoundPool;
/*��Ϸ����ʱ��ʾ�Ľ���*/
public class EndView extends BaseView{
	private int score;
	private int high_score;
	private float button_x;
	private float button_y;
	private float button_y2;
	private float strwid;
	private float strhei;
	private boolean isBtChange;				// ��ťͼƬ�ı�ı��
	private boolean isBtChange2;
	private String startGame = "������ս";	// ��ť������
	private String exitGame = "���ز˵�";
	private Bitmap button;					// ��ťͼƬ
	private Bitmap button2;					// ��ťͼƬ
	private Bitmap background;				// ����ͼƬ
	private Rect rect;						// �������ֵ�����
	private MainActivity mainActivity;
	public EndView(Context context,GameSoundPool sounds) {
		super(context,sounds);
		// TODO Auto-generated constructor stub
		this.mainActivity = (MainActivity)context;
		paint.setColor(Color.rgb(80, 80, 80));
		paint.setTextSize(40);
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
		high_score = getScore();
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
		if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
			float x = event.getX();
			float y = event.getY();
			//�жϵ�һ����ť�Ƿ񱻰���
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				sounds.playSound(7, 0,true);
				isBtChange = true;
				drawSelf();
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			//�жϵڶ�����ť�Ƿ񱻰���
			else if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				sounds.playSound(7, 0,true);
				isBtChange2 = true;
				drawSelf();
				threadFlag = false;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_READY_VIEW);
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE){
			float x = event.getX();
			float y = event.getY();
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y && y < button_y + button.getHeight())
			{
				isBtChange = true;
			}
			else{
				isBtChange = false;
			}
			if(x > button_x && x < button_x + button.getWidth() 
					&& y > button_y2 && y < button_y2 + button.getHeight())
			{
				isBtChange2 = true;
			}
			else{
				isBtChange2 = false;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP){
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
		button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();
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
		if (!button.isRecycled()) {
			button.recycle();
		}
		if (!button2.isRecycled()) {
			button2.recycle();
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
			//���ذ�Χ�����ַ�������С��һ��Rect����     

			paint.getTextBounds(startGame, 0, startGame.length(), rect);    		   
			canvas.drawText(startGame, screen_width/2 - strwid/2, button_y + button.getHeight()/2 + strhei/2, paint);
			canvas.drawText(exitGame, screen_width/2 - strwid/2, button_y2 + button.getHeight()/2 + strhei/2, paint);
			float textlong = paint.measureText("�����ܷ�:"+String.valueOf(score));
			canvas.drawText("�����ܷ�:"+String.valueOf(score), screen_width/2 - textlong/2, screen_height/2 - 100, paint);
			
			if (saveScore(score)) {
				float textlong1 = paint.measureText("�Ƽ�¼��!");
				canvas.drawText("�Ƽ�¼��!", screen_width/2 - textlong1/2, screen_height/2 - 50, paint);
			}else {
				float textlong2 = paint.measureText("��ʷ���:"+high_score);
				canvas.drawText("��ʷ���:"+high_score, screen_width/2 - textlong2/2, screen_height/2 - 50, paint);
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	public boolean saveScore(int score) {
			
		Context ctx = mainActivity;
		
		SharedPreferences sp = ctx.getSharedPreferences("BeatPlaneScore", 0);
				
		// ��ȡ�༭����
		Editor editor = sp.edit();				
		
		if(score > high_score) {
			// ��������
			editor.putInt("high_score", score);	
			editor.commit();
			return true;
		}else {
			return false;
		}
					
	}
	
	public int getScore() {
		
		Context ctx = mainActivity;
		
		SharedPreferences sp = ctx.getSharedPreferences("BeatPlaneScore", 0);
						
		return sp.getInt("high_score", 0);
		
					
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {		
			threadFlag = false;
			mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_READY_VIEW);
			//��ʾ�˰����Ѵ������ٽ���ϵͳ����
			//�Ӷ�������Ϸ�������̨
			Log.v("chendd", "onKey");
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//����back���ذ���
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			threadFlag = false;
			mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_READY_VIEW);
			//��ʾ�˰����Ѵ������ٽ���ϵͳ����
			//�Ӷ�������Ϸ�������̨
			Log.v("chendd", "onKey");
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
	public void setScore(int score) {
		this.score = score;
	}
}
