package com.example.kldemo;

import com.klsdk.common.ApiListenerInfo;
import com.klsdk.common.ExitListener;
import com.klsdk.common.InitListener;
import com.klsdk.common.KLSDK;
import com.klsdk.common.UserApiListenerInfo;
import com.klsdk.model.LoginMessageinfo;
import com.klsdk.model.PaymentInfo;
import com.test.kl.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public Button mBinit;
	public Button mBlogin;
	public Button mBdata;
	public Button mBpay;
	public Button mBexit;
	public Button mBaccount;
	public Button mPa;
	public int appid = 100000;
	public String appkey="123456";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		KLSDK.onCreate(this);
		//切换账号、登出回调
		KLSDK.setUserListener(new UserApiListenerInfo(){

			@Override
			public void onLogout(Object obj) {
				// TODO Auto-generated method stub
				super.onLogout(obj);
				// 切换账号，处理自己的逻辑，比如重新登录，进行选服进入游戏
				Log.i("kl","切换账号");
			}});
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBinit = (Button) findViewById(R.id.initbt);
		mBinit.setOnClickListener(this);
		mBlogin = (Button) findViewById(R.id.loginbt);
		mBlogin.setOnClickListener(this);
		mBdata = (Button) findViewById(R.id.databt);
		mBdata.setOnClickListener(this);
		mBpay = (Button) findViewById(R.id.paybt);
		mBpay.setOnClickListener(this);
		mBexit = (Button) findViewById(R.id.exitbt);
		mBexit.setOnClickListener(this);
		mBaccount = (Button)findViewById(R.id.account);
		mBaccount.setOnClickListener(this);
		mPa = (Button)findViewById(R.id.paybt1);
		mPa.setOnClickListener(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		KLSDK.onRestart(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		KLSDK.onResume(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		KLSDK.onNewIntent(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		KLSDK.onPause(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		KLSDK.onStop(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		KLSDK.onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		KLSDK.onDestroy(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		if (id == R.id.initbt) {
			// 初始化
			KLSDK.initInterface(MainActivity.this, appid, appkey, new InitListener() {
				
				@Override
				public void fail(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void Success(String arg0) {
					// TODO Auto-generated method stub
					Log.i("kl",arg0);
					
				}
			});
		} else if (id == R.id.loginbt) {
			// 登录
			KLSDK.login(MainActivity.this, appid, appkey, new ApiListenerInfo(){

				@Override
				public void onSuccess(Object obj) {
					// TODO Auto-generated method stub
					super.onSuccess(obj);
					if (obj != null) {
						LoginMessageinfo data = (LoginMessageinfo) obj;
						String result = data.getResult();
						String msg = data.getMsg();
						String gametoken = data.getGametoken();
						String time = data.getTime();
						String uid = data.getUid();
						String sessid = data.getSessid();
						Log.i("kl", "登录结果:" + result + "|msg" + msg
								+ "|gametoken" + gametoken + "" + "|time"
								+ time + "|uid" + uid + "|sessid" + sessid);
					}
				}});

		} else if (id == R.id.databt) {
			// 提交角色信息根据场景分别调用
			/**
			 * 额外信息
			 * 
			 * @param context
			 *            上下文
			 * @param scene_Id
			 *            场景 分别为进入服务器(enterServer)、玩家创建用户角色(createRole)、玩家升级(levelUp)
			 * @param roleId
			 *            角色id
			 * @param roleName
			 *            角色名
			 * @param roleLevel
			 *            角色等级
			 * @param zoneId
			 *            当前登录的游戏区服id
			 * @param zoneName
			 *            当前游戏区服名称
			 * @param balance
			 *            游戏币余额
			 * @param Vip
			 *            当前用户vip等级
			 * @param partyName
			 *            当前用户所属帮派
			 * @param roleCTime
			 *            单位为秒，创建角色的时间
			 * @param roleLevelMTime
			 *            单位为秒，角色等级变化时间
			 */
			//进入服务器
			KLSDK.setExtData(this, "enterServer", "11", "考拉", "99", "1", "1区",
					"80", "8", "逍遥", "21322220", "54456588");
		} else if(id==R.id.account){
			//游戏中存在登出或者切换帐号的按钮，则可在点击按钮时进行登出接口调用，在登出回调中进行重新登录等操作
			KLSDK.switchAccount();
		}else if (id == R.id.paybt) {
			// 充值
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setAppid(appid);
			paymentInfo.setAppKey(appkey);
			paymentInfo.setAgent("");
			paymentInfo.setAmount("1");
			paymentInfo.setBillno("");
			paymentInfo.setExtrainfo("");
			paymentInfo.setSubject("元宝");
			paymentInfo.setIstest("1");
			paymentInfo.setRoleid("1111");
			paymentInfo.setRolename("kl");
			paymentInfo.setRolelevel("100");
			paymentInfo.setServerid("8888");

			paymentInfo.setUid("");
			KLSDK.payment(this, paymentInfo, new ApiListenerInfo() {

				@Override
				public void onSuccess(Object obj) {
					// TODO Auto-generated method stub
					super.onSuccess(obj);
					if (obj != null) {
						// LoginMessageInfo login=(LoginMessageInfo) obj;
						Log.i("kl", "充值界面关闭" + obj.toString());
					}

				}

			});

		} else if (id == R.id.paybt1) {
			// 充值
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setAppid(appid);
			paymentInfo.setAppKey(appkey);
			paymentInfo.setAgent("");
			paymentInfo.setAmount("60");
			paymentInfo.setBillno("");
			paymentInfo.setExtrainfo("");
			paymentInfo.setSubject("元宝");
			paymentInfo.setIstest("1");
			paymentInfo.setRoleid("1111");
			paymentInfo.setRolename("kl");
			paymentInfo.setRolelevel("100");
			paymentInfo.setServerid("8888");

			paymentInfo.setUid("");
			KLSDK.payment(this, paymentInfo, new ApiListenerInfo() {

				@Override
				public void onSuccess(Object obj) {
					// TODO Auto-generated method stub
					super.onSuccess(obj);
					if (obj != null) {
						// LoginMessageInfo login=(LoginMessageInfo) obj;
						Log.i("kl", "充值界面关闭" + obj.toString());
					}

				}

			});

		} else if (id == R.id.exitbt) {
			// 退出
			KLSDK.exit(this, new ExitListener() {

				@Override
				public void fail(String msg) {
					// TODO Auto-generated method stub

				}

				@Override
				public void ExitSuccess(String msg) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			});
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			KLSDK.exit(MainActivity.this, new ExitListener() {
				
				@Override
				public void fail(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void ExitSuccess(String arg0) {
					// TODO Auto-generated method stub
					
					
					System.exit(0);
				}
			});
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
