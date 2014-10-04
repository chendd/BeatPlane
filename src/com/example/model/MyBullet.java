package com.example.model;

import com.example.beatplane.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
/*��ҷɻ����ӵ���*/
public class MyBullet extends Bullet{
	private Bitmap bullet; 		 // �ӵ���ͼƬ
	public MyBullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.harm = 1;
	}
	// ��ʼ������
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		speed = 100;	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}
	// ��ʼ��ͼƬ��Դ��
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
		object_width = bullet.getWidth();
		object_height = bullet.getHeight();
	}
	// ����Ļ�ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if (isAlive) {
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width,object_y + object_height);
			canvas.drawBitmap(bullet, object_x, object_y, paint);
			canvas.restore();
			logic();
		}
	}
	// �ͷ���Դ�ķ���
	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(!bullet.isRecycled()){
			bullet.recycle();
		}
	}
	// ������߼�����
	@Override
	public void logic() {
		if (object_y >= 0) {
			object_y -= speed;
		} else {
			isAlive = false;
		}
	}
	// �����ײ�ķ���
	@Override
	public boolean isCollide(GameObject obj) {	
		return super.isCollide(obj);
	}
}
