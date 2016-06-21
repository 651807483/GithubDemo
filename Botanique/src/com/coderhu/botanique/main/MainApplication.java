package com.coderhu.botanique.main;

import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.model.LatLng;
import com.coderhu.botanique.baidumap.MyOrientationListener;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.tabmenu.TabMapFragment;

import android.app.Activity;
import android.app.Application;
import android.widget.Button;

public class MainApplication extends Application {
	
	TabMapFragment mTabMapFragment;
	List<Info> list;  
    Activity mainActivity;
    Info info;
    MyOrientationListener myOrientationListener;
    LatLng mCurrentlLatLng;
    boolean isLocation;
    List<Info> mCurrent_list;
	boolean isSelection;
	List<Info> select_Botanique;
	int currentCheckedId;
	MKOLUpdateElement element;










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




	public List<Info> getList() {
		return list;
	}




	public void setList(List<Info> list) {
		this.list = list;
	}
	
	

	public Activity getMainActivity() {
		return mainActivity;
	}




	public void setMainActivity(Activity mainActivity) {
		this.mainActivity = mainActivity;
	}

	



	public Info getInfo() {
		return info;
	}




	public void setInfo(Info info) {
		this.info = info;
	}

	
	public MyOrientationListener getMyOrientationListener() {
		return myOrientationListener;
	}



	public void setMyOrientationListener(MyOrientationListener myOrientationListener) {
		this.myOrientationListener = myOrientationListener;
	}


	public LatLng getmCurrentlLatLng() {
		return mCurrentlLatLng;
	}




	public void setmCurrentlLatLng(LatLng mCurrentlLatLng) {
		this.mCurrentlLatLng = mCurrentlLatLng;
	}

	
	public boolean getisLocation() {
		return isLocation;
	}




	public void setLocation(boolean isLocation) {
		this.isLocation = isLocation;
	}
	
	
	public List<Info> getmCurrent_list() {
		return mCurrent_list;
	}


	public void setmCurrent_list(List<Info> mCurrent_list) {
		this.mCurrent_list = mCurrent_list;
	}

	public boolean isSelection() {
		return isSelection;
	}




	public void setSelection(boolean isSelection) {
		this.isSelection = isSelection;
	}




	public List<Info> getSelect_Botanique() {
		return select_Botanique;
	}




	public void setSelect_Botanique(List<Info> select_Botanique) {
		this.select_Botanique = select_Botanique;
	}




	public int getCurrentCheckedId() {
		return currentCheckedId;
	}




	public void setCurrentCheckedId(int currentCheckedId) {
		this.currentCheckedId = currentCheckedId;
	}
	public MKOLUpdateElement getElement() {
		return element;
	}




	public void setElement(MKOLUpdateElement element) {
		this.element = element;
	}

}


	 

