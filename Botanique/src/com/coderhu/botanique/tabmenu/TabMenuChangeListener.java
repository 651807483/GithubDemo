package com.coderhu.botanique.tabmenu;

import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.tabmenu.TabMapFragment;
import com.coderhu.botanique.tabmenu.TapMapFragment2;  
import android.support.v4.app.FragmentActivity;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
		
		switch (checkedId)
		{
			case R.id.tab_map:
			
				TabMapFragment _MapFragment = new TabMapFragment();	
				myApp.setmTabMapFragment(_MapFragment);
				// 加入Fragment回退栈的标记		
				transaction.replace(R.id.fragment_container, _MapFragment, "_MapFragment");				
				//transaction.addToBackStack("_MapFragment");
				
				break;
            
			
			case R.id.tab_nearby:
				
				TapMapFragment2 _MapFragment2 = new TapMapFragment2();
			    // 加入Fragment回退栈的标记				
				transaction.replace(R.id.fragment_container, _MapFragment2, "_MapFragment2");
				//transaction.addToBackStack("_MapFragment2");
				break;
				/*
			case R.id.tab_addnew:
				TabAddNewFragment _AddNewFragment = TabAddNewFragment.newInstance();
				exchange(_AddNewFragment);
				break;
				
			case R.id.tab_account:
				TabAccountFragment _AccountFragment = TabAccountFragment.newInstance();
				exchange(_AccountFragment);
				break;

			default:
				
				break;
				*/
			 default:
					
					break;
		}
	
	transaction.commit();

	}


	
	
}
