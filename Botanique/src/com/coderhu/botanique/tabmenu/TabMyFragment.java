package com.coderhu.botanique.tabmenu;

import com.coderhu.botanique.main.OfflineActivity;
import com.coderhu.botanique.main.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class TabMyFragment extends Fragment implements OnClickListener{

	Activity context;
	View view;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_my, container, false);
		setonListener();
		return view;
	}
	private void setonListener() {
		view.findViewById(R.id.txt_offLine).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txt_collect:
			
			break;
		case R.id.txt_offLine:
			Intent it = new Intent();
			it.setClass(context, OfflineActivity.class);
		    startActivity(it);
	
			break;
		case R.id.txt_help:
			
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
}
