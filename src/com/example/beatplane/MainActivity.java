package com.example.beatplane;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.spot.SpotManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.util.ConstantUtil;
import com.example.util.GameSoundPool;
import com.example.view.EndView;
import com.example.view.MainView;
import com.example.view.ReadyView;

public class MainActivity extends Activity {

	private GameSoundPool sounds;

	private ReadyView readyView;
	private MainView mainView;
	private EndView endView;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == ConstantUtil.TO_MAIN_VIEW) {
				toMainView();
			} else if (msg.what == ConstantUtil.TO_END_VIEW) {
				toEndView(msg.arg1);
			} else if (msg.what == ConstantUtil.END_GAME) {
				endGame();
			} else if (msg.what == ConstantUtil.TO_READY_VIEW) {
				toReadyView();
			} else if (msg.what == ConstantUtil.ABOUT_GAME) {
				aboutGame();
			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// ��ʼ��Ӧ�õķ��� ID ����Կ���Լ����ò���ģʽ
		AdManager.getInstance(this).init("38252639a7a1e8f6",
				"12baac5adff77963", false);
		// �������
//		SpotManager.getInstance(this).loadSpotAds();
//		SpotManager.getInstance(this).setSpotTimeout(4000);
//		SpotManager.getInstance(this).showSpotAds(this);
		
		sounds = new GameSoundPool(this);
		sounds.initGameSound();
		readyView = new ReadyView(this, sounds);
		setContentView(readyView);
		addADView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		endGame();
		return true;
	}

	// ��ʾ��Ϸ��������
	public void toMainView() {
		if (mainView == null) {
			mainView = new MainView(this, sounds);
		}
		setContentView(mainView);
		readyView = null;
		endView = null;
	}

	// ��ʾ�˵�
	public void toReadyView() {
		if (readyView == null) {
			readyView = new ReadyView(this, sounds);
			setContentView(readyView);
			addADView();
		}
		mainView = null;
		endView = null;

	}

	// ��ʾ��Ϸ�����Ľ���
	public void toEndView(int score) {
		if (endView == null) {
			endView = new EndView(this, sounds);
			endView.setScore(score);
		}
		setContentView(endView);
		mainView = null;
	}

	// ������Ϸ
	public void endGame() {
		if (readyView != null) {
			readyView.setThreadFlag(false);
		} else if (mainView != null) {
			mainView.setThreadFlag(false);
		} else if (endView != null) {
			endView.setThreadFlag(false);
		}
		this.finish();
	}

	// ��������
	public void aboutGame() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setIcon(R.drawable.icon36x36);
		builder.setTitle("��������");
		builder.setMessage("ģ��΢��ƽ̨�Ĵ�ɻ���Ϸ���淨�򵥣�û�д�������,�ʺ���ĥʱ��!��������ʻ��������ϵ������QQ:1487598532 �r(�s���t)�q");
		builder.create().show();

	}
	
	// ����Banner���
	public void addADView() {
		// ʵ���� LayoutParams����Ҫ��
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);

		// ���ù����������λ��
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // ����ʾ��Ϊ���½�
		// ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// ���� Activity �� addContentView ����
		this.addContentView(adView, layoutParams);
	}

	// getter��setter����
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
