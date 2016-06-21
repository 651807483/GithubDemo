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
		// ����ӿ��¼������ߵ�ͼ���»ᴥ���ûص�
	    mOffline.init(this);
		myApp = (MainApplication) this.getApplication();
		ratio = (TextView)findViewById(R.id.ratio);
		update = (TextView)findViewById(R.id.update);	
		element = mOffline.getUpdateInfo(163);
		if (element == null) {
			ratio.setText("δ����");
		}else {
			if (element.ratio != 100) {
				 ratio.setText("����ͣ" + " " + element.ratio + "%");
			}else {
				ratio.setText("�������");
			}
		}
	
	   	
	    
	}
	
	/**
	 * ��ʼ�������ߵ�ͼ
	 * 
	 * @param view
	 */
	public void start(View view) {
		int cityid = 163;  //�����е�ID	
		mOffline.start(cityid);	
		element = mOffline.getUpdateInfo(163);
		if (element.ratio == 100) {
			Toast.makeText(this, "�������", Toast.LENGTH_SHORT)
        	.show();
			return;
		}else {
			Toast.makeText(this, "��ʼ�������ߵ�ͼ. cityid: " + cityid, Toast.LENGTH_SHORT)
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
	 * ��ͣ�������ߵ�ͼ
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
				
				Toast.makeText(this, "��ͣ�������ߵ�ͼ. cityid: " + cityid, Toast.LENGTH_SHORT)
						.show();
				ratio.setText("����ͣ" + " " + element.ratio + "%");
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
		 * �˳�ʱ���������ߵ�ͼģ��
		 */
		mOffline.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetOfflineMapState(int type, int state) {
		switch (type) {
		case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
			 element = mOffline.getUpdateInfo(state);
	     
			// �������ؽ��ȸ�����ʾ
			if (element != null) {			  
				ratio.setText("��������" + " " + element.ratio + "%");
				if (element.ratio == 100) {
					ratio.setText("�������");
				}
			}
		}
			break;
		case MKOfflineMap.TYPE_NEW_OFFLINE:
			// �������ߵ�ͼ��װ
			Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
			break;
		case MKOfflineMap.TYPE_VER_UPDATE:
			// �汾������ʾ
			// MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }

	}


}
