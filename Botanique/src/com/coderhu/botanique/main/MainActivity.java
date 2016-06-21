package com.coderhu.botanique.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.tabmenu.TabMenuChangeListener;
import com.coderhu.botanique.tabmenu.TabMapFragment;
import com.coderhu.data.Sqlite.MyDataBaseOperate;
import com.coderhu.data.Sqlite.MyDatabaseHelper;
import com.coderhu.data.Sqlite.SQLdm;




import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
//import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
	public RadioGroup mTabMenu;	
	private MainApplication  myApp;
	private MyDatabaseHelper helper = null;
	private  MyDataBaseOperate dbOperate;	
	List<Info> list = new ArrayList<Info>();	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// 配置Tab选项卡按钮
		mTabMenu = (RadioGroup) findViewById(R.id.tab_menu);
		TabMenuChangeListener listener = new TabMenuChangeListener(this);
		mTabMenu.setOnCheckedChangeListener(listener);
		myApp = (MainApplication)this.getApplication();	
        
				
		
		// 初始化第一个Tab选项卡
		if (findViewById(R.id.fragment_container) != null) {
			TabMapFragment _MapFragment = new TabMapFragment();
				myApp = (MainApplication)this.getApplication();
			myApp.setmTabMapFragment(_MapFragment);
			myApp.getmTabMapFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, _MapFragment).commit();

		}
		 
		//helper = new MyDatabaseHelper(getBaseContext());
		//SQLiteDatabase database = helper.getWritableDatabase();
		 //打开数据库输出流  
       SQLdm s = new SQLdm();  
        SQLiteDatabase db =s.openDatabase(getApplicationContext());  
		Log.i("sisi","openDatabase已经开始运行");
		dbOperate = new MyDataBaseOperate(db);	
        list = dbOperate.readStringData("botanique",null, null, null, null);
        Log.i("readStringData", list.toString());

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
