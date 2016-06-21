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
		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(getApplicationContext());
		
	  
	}


	

	public TabMapFragment getmTabMapFragment() {
		return mTabMapFragment;
	}



	public void setmTabMapFragment(TabMapFragment mTabMapFragment) {
		this.mTabMapFragment = mTabMapFragment;
	}
	



	 
}
