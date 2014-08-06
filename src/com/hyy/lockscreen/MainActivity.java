package com.hyy.lockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	private DevicePolicyManager policyManager;
	private ComponentName componentName;
	private static final int MY_REQUEST_CODE = 9999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		componentName = new ComponentName(this, AdminReceiver.class);

		// 判断是否有锁屏权限，若有则立即锁屏并结束自己，若没有则获取权限
		if (policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			finish();
		} else {
			activeManage();
		}

		// 放在最后
		setContentView(R.layout.activity_main);

	}

	// 获取权限
	private void activeManage() {
		// TODO Auto-generated method stub
		// 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// 权限列表
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

		// 描述(additional explanation)
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"激活后才能使用锁屏功能哦亲^^");

		startActivityForResult(intent, MY_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 获取权限成功，立即锁屏并finish自己，否则继续获取权限
		if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			policyManager.lockNow();
			finish();
		} else {
			activeManage();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
