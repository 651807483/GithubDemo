package com.coderhu.botanique.main;

import com.baidu.mapapi.SDKInitializer;
import com.coderhu.botanique.tabmenu.TabMapFragment;

import android.app.Activity;
import android.app.Application;
import android.widget.Button;

public class MainApplication extends Application {
	private Activity context;
	TabMapFragment mTabMapFragment;




	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(getApplicationContext());
		
	  
	}


	

	public TabMapFragment getmTabMapFragment() {
		return mTabMapFragment;
	}



	public void setmTabMapFragment(TabMapFragment mTabMapFragment) {
		this.mTabMapFragment = mTabMapFragment;
	}
	



	 
}
