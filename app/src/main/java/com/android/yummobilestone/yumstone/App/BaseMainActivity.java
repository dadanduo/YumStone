package com.android.yummobilestone.yumstone.App;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;

import com.lidroid.xutils.util.LogUtils;
import com.lkl.cloudpos.aidl.AidlDeviceService;

import java.util.List;


/*activity服务绑定封装回调
 * 
 * 2017/5/22
 * */
public abstract class BaseMainActivity extends Activity {
	public static final int SHOW_MSG = 0;

	public static final String LKL_SERVICE_ACTION = "lkl_cloudpos_mid_service";
	//设别服务连接桥
	private ServiceConnection conn = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
			LogUtils.d("aidlService服务连接成功");
			if(serviceBinder != null){	//绑定成功
				AidlDeviceService serviceManager = AidlDeviceService.Stub.asInterface(serviceBinder);
				onDeviceConnected(serviceManager);
			}
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			LogUtils.d("AidlService服务断开了");
		}
	};

	//绑定服务
	public void bindService(){

		BaseMainActivity.this.bindService(getExplicitIntent(this,new Intent(LKL_SERVICE_ACTION)),
				conn, Context.BIND_AUTO_CREATE);

		//5.0以后的版本会抛出异常方法已被淘汰
//		Intent intent = new Intent();
//		intent.setAction(LKL_SERVICE_ACTION);
//		intent.setPackage("");
//		boolean flag = bindService(intent, conn, Context.BIND_AUTO_CREATE);
//		if(flag){
//			LogUtils.d("服务绑定成功");
//		}else{
//			LogUtils.d("服务绑定失败");
//		}

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bindService();

		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	this.unbindService(conn);
	LogUtils.d("服务解绑 ");
	}
	/**
	 * 服务连接成功时回调
	 * @param serviceManager
	 * @createtor：Administrator
	 * @date:2015-8-4 上午7:38:08
	 */
	public abstract void onDeviceConnected(AidlDeviceService serviceManager);



	//将隐式启动转换为显式启动,兼容编译sdk5.0以后版本
	public Intent getExplicitIntent(Context context,Intent implicitIntent){
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfos = pm.queryIntentServices(implicitIntent, 0);
		if (resolveInfos == null || resolveInfos.size()!= 1) {
			return null;
		}
		Intent explicitIntent = null;
		ResolveInfo info = resolveInfos.get(0);
		String packageName = info.serviceInfo.packageName;
		String className = info.serviceInfo.name;
		ComponentName component = new ComponentName(packageName,className);
		explicitIntent = new Intent(implicitIntent);
		explicitIntent.setComponent(component);
		return explicitIntent;
	}

}


