package com.example.model;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.example.beatplane.R;
/*子弹物品类*/
public class BulletGoods extends GameGoods{
	public BulletGoods(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
	}
	// 初始化图片资源的
	@Override
	protected void initBitmap() {
		// TODO Auto-generated method stub
		bmp = BitmapFactory.decodeResource(resources, R.drawable.bullet_goods);
		object_width = bmp.getWidth();			
		object_height = bmp.getHeight();	
	}
}

