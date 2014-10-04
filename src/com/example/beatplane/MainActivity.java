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

		// 初始化应用的发布 ID 和密钥，以及设置测试模式
		AdManager.getInstance(this).init("38252639a7a1e8f6",
				"12baac5adff77963", false);
		// 插屏广告
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

	// 显示游戏的主界面
	public void toMainView() {
		if (mainView == null) {
			mainView = new MainView(this, sounds);
		}
		setContentView(mainView);
		readyView = null;
		endView = null;
	}

	// 显示菜单
	public void toReadyView() {
		if (readyView == null) {
			readyView = new ReadyView(this, sounds);
			setContentView(readyView);
			addADView();
		}
		mainView = null;
		endView = null;

	}

	// 显示游戏结束的界面
	public void toEndView(int score) {
		if (endView == null) {
			endView = new EndView(this, sounds);
			endView.setScore(score);
		}
		setContentView(endView);
		mainView = null;
	}

	// 结束游戏
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

	// 关于我们
	public void aboutGame() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setIcon(R.drawable.icon36x36);
		builder.setTitle("关于我们");
		builder.setMessage("模仿微信平台的打飞机游戏，玩法简单，没有次数限制,适合消磨时间!如果有疑问或建议可以联系开发者QQ:1487598532 ╮(╯▽╰)╭");
		builder.create().show();

	}
	
	// 加入Banner广告
	public void addADView() {
		// 实例化 LayoutParams（重要）
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);

		// 设置广告条的悬浮位置
		layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT; // 这里示例为右下角
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 调用 Activity 的 addContentView 函数
		this.addContentView(adView, layoutParams);
	}

	// getter和setter方法
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
