package com.example.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.example.beatplane.R;
/*�ӵ���Ʒ��*/
public class BulletGoods extends GameGoods{
	public BulletGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}
	// ��ʼ��ͼƬ��Դ��
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}

