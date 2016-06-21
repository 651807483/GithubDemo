package com.coderhu.botanique.common;

import com.baidu.mapapi.map.Marker;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BotaniqueInfo extends Activity {
	Marker marker = null;
    TextView Chinaname = null;
    TextView othername_info = null;
    TextView Englishname_info = null;
    TextView scientificname_info = null;
    TextView scholarname_info = null;
    TextView type_info = null;
    TextView orignalplace_info = null;
    TextView behavior_info = null;
    TextView featureUse_info = null;
    Info info = null;
    MainApplication myApp;
    ActionBar mActionBar = null;
	ImageButton back_btn = null;
    public BotaniqueInfo() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.scrollview_info);	
		mActionBar = getActionBar();
		 // 返回箭头（默认不显示）  
		mActionBar.setDisplayHomeAsUpEnabled(false);  
       // 左侧图标点击事件使能  
		mActionBar.setHomeButtonEnabled(true);  
       // 使左上角图标(系统)是否显示  
		mActionBar.setDisplayShowHomeEnabled(false);  
       // 显示标题  
		mActionBar.setDisplayShowTitleEnabled(false);  
       //显示自定义视图  
		getActionBar().setDisplayShowCustomEnabled(true);  
		View actionbarLayout = LayoutInflater.from(this).inflate(  
               R.layout.actionbar_info, null);  
       	mActionBar.setCustomView(actionbarLayout);  
       	back_btn = (ImageButton)actionbarLayout.findViewById(R.id.actionbar_back);
       	//返回键点击事件
       	back_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		myApp = (MainApplication)this.getApplication();	
		info = myApp.getInfo();
		Chinaname = (TextView)this.findViewById(R.id.chinaname_title);
		othername_info = (TextView)this.findViewById(R.id.othername_info);
		Englishname_info = (TextView)this.findViewById(R.id.Englishname_info);
		scientificname_info = (TextView)this.findViewById(R.id.scientificname_info);
		scholarname_info = (TextView)this.findViewById(R.id.scholarname_info);
		type_info = (TextView)this.findViewById(R.id.type_info);
		orignalplace_info = (TextView)this.findViewById(R.id.orignalplace_info);
		behavior_info = (TextView)this.findViewById(R.id.behavior_info);
		featureUse_info = (TextView)this.findViewById(R.id.featureUse_info);
		
		Chinaname.setText(info.getChinaname());
		othername_info.setText(info.getAnothername());
		Englishname_info.setText(info.getEnglishname());
		scientificname_info.setText(info.getScientificname());
		scholarname_info.setText(info.getScholarname());
		type_info.setText(info.getType());
		orignalplace_info.setText(info.getOrignalplace());
		behavior_info.setText(info.getBehavior());
		featureUse_info.setText(info.getFeatureUse());
		
	 
	}



   
}
