package com.example.model;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.beatplane.R;
import com.example.factory.GameObjectFactory;
import com.example.view.MainView;
/*��ҷɻ�����*/
public class MyPlane extends GameObject implements IMyPlane{
	private float middle_x;			 // �ɻ�����������
	private float middle_y;
	private long startTime;	 	 	 // ��ʼ��ʱ��
	private long endTime;	 	 	 // ������ʱ��
	private boolean isChangeBullet;  // ��Ǹ������ӵ�
	private Bitmap myplane;			 // �ɻ�����ʱ��ͼƬ
	private Bitmap myplane2;		 // �ɻ���ըʱ��ͼƬ
	private List<Bullet> bullets;	 // �ӵ�������
	private MainView mainView;
	private GameObjectFactory factory;
	public MyPlane(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		initBitmap();
		this.speed = 12;
		isChangeBullet = false;
		factory = new GameObjectFactory();
		bullets = new ArrayList<Bullet>();
		changeButtle();
	}
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}
	// ������Ļ��Ⱥ͸߶�
	@Override
	public void setScreenWH(float screen_width, float screen_height) {
		super.setScreenWH(screen_width, screen_height);
		object_x = screen_width/2 - object_width/2;
		object_y = screen_height - object_height;
		middle_x = object_x + object_width/2;
		middle_y = object_y + object_height/2;
	}
	// ��ʼ��ͼƬ��Դ��
	@Override
	public void initBitmap() {
		// TODO Auto-generated method stub
		myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
		myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
		object_width = myplane.getWidth() / 2; // ���ÿһ֡λͼ�Ŀ�
		object_height = myplane.getHeight(); 	// ���ÿһ֡λͼ�ĸ�
	}
	// ����Ļ�ͼ����
	@Override
	public void drawSelf(Canvas canvas) {
		// TODO Auto-generated method stub
		if(isAlive){
			int x = (int) (currentFrame * object_width); // ��õ�ǰ֡�����λͼ��X����
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
			canvas.drawBitmap(myplane, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 0;
			}
		}
		else{
			int x = (int) (currentFrame * object_width); // ��õ�ǰ֡�����λͼ��Y����
			canvas.save();
			canvas.clipRect(object_x, object_y, object_x + object_width, object_y
					+ object_height);
			canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
			canvas.restore();
			currentFrame++;
			if (currentFrame >= 2) {
				currentFrame = 1;
			}
		}
	}
	// �ͷ���Դ�ķ���
	@Override
	public void release() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){	
			obj.release();
		}
		if(!myplane.isRecycled()){
			myplane.recycle();
		}
		if(!myplane2.isRecycled()){
			myplane2.recycle();
		}
	}
	//�����ӵ�
	@Override
	public void shoot(Canvas canvas,List<EnemyPlane> planes) {
		// TODO Auto-generated method stub	
		//�����ӵ��Ķ���
		for(Bullet obj:bullets){	
			if(obj.isAlive()){		//�ӵ��Ƿ�Ϊ���״̬
				for(EnemyPlane pobj:planes){ //�����л�����
					// �жϵл��Ƿ񱻼����ײ
					if( pobj.isCanCollide()){
						if(obj.isCollide((GameObject)pobj)){			   		   // �����ײ
							 pobj.attacked(obj.getHarm());		   // �л������˺�
							if(pobj.isExplosion()){
								mainView.addGameScore(pobj.getScore());// ��÷���
								if(pobj instanceof SmallPlane){
									mainView.playSound(2);
								}
								else if(pobj instanceof MiddlePlane){
									mainView.playSound(3);
								}
								else if(pobj instanceof BigPlane){
									mainView.playSound(4);
								}
								else{
									mainView.playSound(5);
								}
							}			
							break;
						}
					}
				}
				obj.drawSelf(canvas);					//�����ӵ�
			}
		}
	}
	//��ʼ���ӵ�
	@Override
	public void initButtle() {
		// TODO Auto-generated method stub
		for(Bullet obj:bullets){	
			if(!obj.isAlive()){
				obj.initial(0,middle_x, middle_y);
				break;
			}
		}
	}
	//�����ӵ�
	@Override
	public void changeButtle() {
		// TODO Auto-generated method stub
		bullets.clear();
		if(isChangeBullet){
			for(int i = 0;i < 9;i++){
				MyBullet2 bullet = (MyBullet2) factory.createMyBullet2(resources);
				bullets.add(bullet);
			}
		}
		else{
			for(int i = 0;i < 9;i++){
				MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
				bullets.add(bullet);
			}
		}
	}
	//�ж��ӵ��Ƿ�ʱ
	public void isBulletOverTime(){
		if(isChangeBullet){
			endTime = System.currentTimeMillis();	
			if(endTime - startTime > 10000){
				isChangeBullet = false;
				startTime = 0;
				endTime = 0;
				changeButtle();
			}
		}
	}
	//getter��setter����
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Override
	public boolean isChangeBullet() {
		return isChangeBullet;
	}
	@Override
	public void setChangeBullet(boolean isChangeBullet) {
		this.isChangeBullet = isChangeBullet;
	}
	@Override
	public float getMiddle_x() {
		return middle_x;
	}
	@Override
	public void setMiddle_x(float middle_x) {
		this.middle_x = middle_x;
		this.object_x = middle_x - object_width/2;
	}
	@Override
	public float getMiddle_y() {
		return middle_y;
	}
	@Override
	public void setMiddle_y(float middle_y) {
		this.middle_y = middle_y;
		this.object_y = middle_y - object_height/2;
	}	
}
