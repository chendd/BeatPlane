package com.example.model;

import java.util.List;


import android.graphics.Canvas;

public interface IMyPlane {
	public float getMiddle_x();
	public void setMiddle_x(float middle_x);
	public float getMiddle_y();
	public void setMiddle_y(float middle_y);
	public boolean isChangeBullet();
	public void setChangeBullet(boolean isChangeBullet);
	//�����ӵ��ķ���
	public void shoot(Canvas canvas,List<EnemyPlane> planes);
	//��ʼ���ӵ��ķ���
	public void initButtle();
	//�����ӵ��ķ���
	public void changeButtle();
}
