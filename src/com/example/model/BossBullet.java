package com.example.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.beatplane.R;
/*BOSS�ӵ�*/
public class BossBullet extends Bullet{
	private Bitmap bullet; 		 // �ӵ���ͼƬ
	public BossBullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.harm = 1;
	}
	// ��ʼ������
	@Override
	public void initial(int arg0,float arg1,float arg2){
		isAlive = true;
		speed = -20;	
		object_x = arg1 - object_width / 2;
		object_y = arg2 - object_height;
	}
	// ��ʼ��ͼƬ��Դ��
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		bullet = BitmapFactory.decodeResource(resources, R.drawable.bossbullet);
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
		if (object_y <= screen_height) {
			object_y -= speed;
		} else {
			isAlive = false;
		}
	}
	// �����ײ�ķ���
	@Override
	public boolean isCollide(GameObject obj) {	
		if (object_x <= obj.getObject_x()
				&& object_x + object_width -10<= obj.getObject_x()) {
			return false;
		}
		// ����1λ�ھ���2���Ҳ�
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x+10 ) {
			return false;
		}
		// ����1λ�ھ���2���Ϸ�
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height -10 <= obj.getObject_y()) {
			return false;
		}
		// ����1λ�ھ���2���·�
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y +10) {
			return false;
		}
		isAlive = false;
		return true;
	}
}

