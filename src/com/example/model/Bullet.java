package com.example.model;

import android.content.res.Resources;
import android.graphics.Canvas;
/*�ӵ�����*/
public class Bullet extends GameObject{
	protected int harm;
	public Bullet(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
	}
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	// �����ײ�ķ���
	@Override
	public boolean isCollide(GameObject obj) {	
		// ����1λ�ھ���2�����
		if (object_x <= obj.getObject_x()
				&& object_x + object_width <= obj.getObject_x()) {
			return false;
		}
		// ����1λ�ھ���2���Ҳ�
		else if (obj.getObject_x() <= object_x
				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
			return false;
		}
		// ����1λ�ھ���2���Ϸ�
		else if (object_y <= obj.getObject_y()
				&& object_y + object_height <= obj.getObject_y()) {
			return false;
		}
		// ����1λ�ھ���2���·�
		else if (obj.getObject_y() <= object_y
				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
			if(obj instanceof SmallPlane){
				if(object_y - speed < obj.getObject_y()){
					isAlive = false;
					return true;
				}
			}
			else
				return false;
		}
		isAlive = false;
		return true;
	}
	//getter��setter����
	public int getHarm() {
		// TODO Auto-generated method stub
		return harm;
	}
	public void setHarm(int harm) {
		// TODO Auto-generated method stub
		this.harm = harm;
	}
}
