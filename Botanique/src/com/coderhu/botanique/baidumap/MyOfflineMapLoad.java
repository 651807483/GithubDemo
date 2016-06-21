package com.coderhu.botanique.baidumap;

import java.util.ArrayList;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyOfflineMapLoad extends Activity implements MKOfflineMapListener {
	private MKOfflineMap mOffline;
	MKOLUpdateElement element = null;
	TextView update,ratio;
	MainApplication myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.offline_map);
		getActionBar().hide();
		mOffline = new MKOfflineMap();
		// 传入接口事件，离线地图更新会触发该回调
	    mOffline.init(this);
		myApp = (MainApplication) this.getApplication();
		ratio = (TextView)findViewById(R.id.ratio);
		update = (TextView)findViewById(R.id.update);	
		element = mOffline.getUpdateInfo(163);
		if (element == null) {
			ratio.setText("未下载");
		}else {
			if (element.ratio != 100) {
				 ratio.setText("已暂停" + " " + element.ratio + "%");
			}else {
				ratio.setText("完成下载");
			}
		}
	
	   	
	    
	}
	
	/**
	 * 开始下载离线地图
	 * 
	 * @param view
	 */
	public void start(View view) {
		int cityid = 163;  //深圳市的ID	
		mOffline.start(cityid);	
		element = mOffline.getUpdateInfo(163);
		if (element.ratio == 100) {
			Toast.makeText(this, "完成下载", Toast.LENGTH_SHORT)
        	.show();
			return;
		}else {
			Toast.makeText(this, "开始下载离线地图. cityid: " + cityid, Toast.LENGTH_SHORT)
        	.show();
		}
//		if (element == null) {
//			return;
//		}else {
//			  if (element.ratio == 100) {
//					return;
//				}
//		}
		 

	}

	/**
	 * 暂停下载离线地图
	 * 
	 * @param view
	 */
	public void stop(View view) {
		int cityid = 163;
		mOffline.pause(cityid);
		element = mOffline.getUpdateInfo(163);
		if (element == null) {
			return;
		}else {
			if (element.ratio != 100) {
				
				Toast.makeText(this, "暂停下载离线地图. cityid: " + cityid, Toast.LENGTH_SHORT)
						.show();
				ratio.setText("已暂停" + " " + element.ratio + "%");
			}else {
				return;
			}
		}
	
	

		
	}
	@Override
	protected void onPause() {
		int cityid = 163;
		MKOLUpdateElement temp = mOffline.getUpdateInfo(cityid);
		if (temp != null && temp.status == MKOLUpdateElement.DOWNLOADING) {
			mOffline.pause(cityid);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}	@Override
	protected void onDestroy() {
		/**
		 * 退出时，销毁离线地图模块
		 */
		mOffline.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetOfflineMapState(int type, int state) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			 element = mOffline.getUpdateInfo(state);
	     
			// 处理下载进度更新提示
			if (element != null) {			  
				ratio.setText("正在下载" + " " + element.ratio + "%");
				if (element.ratio == 100) {
					ratio.setText("完成下载");
				}
			}
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// 有新离线地图安装
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// 版本更新提示
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }

	}


}
