package com.coderhu.botanique.main;

import com.coderhu.botanique.baidumap.MyOfflineMapLoad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class OfflineActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline_activity);
		getActionBar().hide();
		setonListener();
	}
	
	private void setonListener() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.offline_map).setOnClickListener(this);
		findViewById(R.id.offline_navi).setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.offline_map:
			Intent it = new Intent(OfflineActivity.this,MyOfflineMapLoad.class);
			startActivity(it);
					
			break;
		case R.id.offline_navi:
			
			break;

		default:
			break;
		}
	}

}
