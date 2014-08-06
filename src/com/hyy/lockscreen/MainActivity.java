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

		// �ж��Ƿ�������Ȩ�ޣ����������������������Լ�����û�����ȡȨ��
		if (policyManager.isAdminActive(componentName)) {
			policyManager.lockNow();
			finish();
		} else {
			activeManage();
		}

		// �������
		setContentView(R.layout.activity_main);

	}

	// ��ȡȨ��
	private void activeManage() {
		// TODO Auto-generated method stub
		// �����豸����(��ʽIntent) - ��AndroidManifest.xml���趨��Ӧ������
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		// Ȩ���б�
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

		// ����(additional explanation)
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
				"��������ʹ����������Ŷ��^^");

		startActivityForResult(intent, MY_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ��ȡȨ�޳ɹ�������������finish�Լ������������ȡȨ��
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
