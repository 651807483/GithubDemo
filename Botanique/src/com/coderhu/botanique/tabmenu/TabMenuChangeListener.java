package com.coderhu.botanique.tabmenu;

import java.util.ArrayList;

import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.tabmenu.TabMapFragment;
import com.coderhu.botanique.tabmenu.TabNearbyFragment;

import android.R.integer;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TabMenuChangeListener implements OnCheckedChangeListener {
	FragmentActivity context;
	MainApplication myApp;
	
	
	public TabMenuChangeListener(FragmentActivity context)
	{
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId)
	{ 
		myApp = (MainApplication)context.getApplication();
		FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
		
		
		//ActionBar mActionBar = context.getActionBar();
		switch (checkedId)
		{
			case R.id.tab_map:
				if (myApp.getCurrentCheckedId() == checkedId) {
					return;
				}
				TabMapFragment _MapFragment = new TabMapFragment();	
				myApp.setmTabMapFragment(_MapFragment);
				// 加入Fragment回退栈的标记		
				transaction.replace(R.id.fragment_container, _MapFragment, "_MapFragment");				
				//transaction.addToBackStack("_MapFragment");
		     
				break;
            
			
			case R.id.tab_nearby:
				if (myApp.getCurrentCheckedId() == checkedId) {
					return;
				}
				TabNearbyFragment _NearbyFragment = new TabNearbyFragment();
				transaction.replace(R.id.fragment_container, _NearbyFragment, "_NearbyFragment");
				
				
			    // 加入Fragment回退栈的标记				
				//transaction.replace(R.id.fragment_container, _NearbyFragment, "_NearbyFragment");
				//transaction.addToBackStack("_MapFragment2");
				Log.isLoggable("checkId2", checkedId);
				break;
				
			
				
			case R.id.tab_my:
				TabMyFragment _MyFragment = new TabMyFragment();
				transaction.replace(R.id.fragment_container, _MyFragment, "_MyFragment");
				break;

			default:
				
				break;
			
		}
	
	transaction.commit();
	myApp.setCurrentCheckedId(checkedId);
	}


	
	
}
