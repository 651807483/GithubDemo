package com.coderhu.botanique.tabmenu;

import java.util.ArrayList;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.r;
import com.coderhu.botanique.baidumap.MyBaiduMapLocation;
import com.coderhu.botanique.baidumap.MyBaiduMapOverlayItem;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainActivity;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;

import com.coderhu.botanique.tabmenu.TabMapFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.R.layout;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class TabMapFragment extends Fragment {
	MapView mMapView;
	BaiduMap mBaiduMap;
	public LocationClient mLocationClient = null;
	public ImageButton requestLocButton;
	Drawable location_compass;
	Drawable location_follow;
	Drawable location_normal;
	Button mbutton; // Marker����¼�
	Activity context;
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);// �����ǽ�ͼ��ת��Ϊ����
	private RelativeLayout mMarkerInfoLy;
	SelectPopupWindow  menuWindow;    //��������
	private View mMenuView;
	
	
	
	
	public TabMapFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// context = this.getActivity();
		// myApp = (MainApplication)(context.getApplication());
		// //��ȡApplication����
		// myApp.getmTabMapFragment();
		Log.i("sisi", "onCreate is excuted");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View _View = inflater.inflate(R.layout.fragment_map, container, false);
		requestLocButton = (ImageButton) _View
				.findViewById(R.id.location_button);
		// ��ʾ��ͼ
		mMapView = (MapView) _View.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();	
	    context = this.getActivity();
		// ��λ����
		mLocationClient = new LocationClient(getActivity());
		location_compass = requestLocButton.getResources().getDrawable(
				R.drawable.location_compass);
		location_follow = requestLocButton.getResources().getDrawable(
				R.drawable.location_follow);
		location_normal = requestLocButton.getResources().getDrawable(
				R.drawable.location_normal);
		MyBaiduMapLocation mMyBaiduMapLocation = new MyBaiduMapLocation(
				requestLocButton, mMapView, location_compass, location_follow,
				location_normal, mBaiduMap, mLocationClient, this.getActivity());
		mMyBaiduMapLocation.location();
		
		return _View;

	}

	// ��Ӹ�����

	public void addInfosOverlay(ArrayList<Info> infos) {
		mBaiduMap.clear();
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		for (Info info : infos) {
			// λ��
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			// ͼ��
			overlayOptions = new MarkerOptions().position(latLng).icon(bdA)
					.zIndex(5);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);

		}

		// ��Marker�ĵ���¼�
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			public boolean onMarkerClick(final Marker marker) {
				// ���marker�е�����
				Info info = (Info) marker.getExtraInfo().get("info");
				InfoWindow mInfoWindow;
				mbutton = new Button(getActivity()); // ÿ�ζ���Ҫ���������ڴ�
				mbutton.setBackgroundResource(R.drawable.popup);
				mbutton.setText(info.getName());
				OnInfoWindowClickListener listener = null;
				listener = new OnInfoWindowClickListener() {
					public void onInfoWindowClick() {
						// ����InfoWindow
						mBaiduMap.hideInfoWindow();
						menuWindow = new SelectPopupWindow(context,itemsOnClick);			
						menuWindow.showAtLocation(context.findViewById(R.id.bmapView), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
					}
				};
				LatLng ll = marker.getPosition();
				mInfoWindow = new InfoWindow(BitmapDescriptorFactory
						.fromView(mbutton), ll, -47, listener);
				mBaiduMap.showInfoWindow(mInfoWindow);
				// ������ϸ��Ϣ����Ϊ�ɼ�
				//mMarkerInfoLy.setVisibility(View.VISIBLE);
				//MainActivity.RadioGroup;
				//RadioGroup.setVisibility(View.VISIBLE);
				// �����̼���ϢsΪ��ϸ��Ϣ����������Ϣ
				//popupInfo(mMarkerInfoLy, info);
				//initMapClickEvent();  // ���س��ֵ���ϸ��Ϣ���ֺ�InfoWindow
			    
				return true;
			}
		});
	}
	
	 //Ϊ��������ʵ�ּ�����
    private OnClickListener  itemsOnClick = new OnClickListener(){

		public void onClick(View v) {
			menuWindow.dismiss();  //���ٵ�����
			switch (v.getId()) {
			case R.id.btn_pop_info:
				break;
			case R.id.btn_pop_navigate:				
				break;
			default:
				break;
			}							
		}  	
    };
    
    
	private class SelectPopupWindow extends PopupWindow {
		Button btn_pop_info,btn_pop_navigate;
		public SelectPopupWindow(Activity context,OnClickListener itemsOnClick) {
			super(context);
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mMenuView = inflater.inflate(R.layout.popwindow_dialog, null);
			btn_pop_info = (Button) mMenuView.findViewById(R.id.btn_pop_info);
			btn_pop_navigate = (Button) mMenuView.findViewById(R.id.btn_pop_navigate);
        	btn_pop_info.setOnClickListener(itemsOnClick);
    		btn_pop_navigate.setOnClickListener(itemsOnClick);
    		//����SelectPicPopupWindow��View
    	    //final PopupWindow popupWindow = new PopupWindow(null, 200, 300);
    		 this.setContentView(mMenuView);
    		//����SelectPicPopupWindow��������Ŀ�
     		this.setWidth(LayoutParams.MATCH_PARENT);
     		//����SelectPicPopupWindow��������ĸ�
     		this.setHeight(LayoutParams.WRAP_CONTENT);
     		//����SelectPicPopupWindow��������ɵ��
     		this.setFocusable(true);
     		//����SelectPicPopupWindow�������嶯��Ч��
    		this.setAnimationStyle(R.style.AnimBottom);
    		//ʵ����һ��ColorDrawable��ɫΪ͸��
    		ColorDrawable dw = new ColorDrawable(-00000);
    		//����SelectPicPopupWindow��������ı���
    		this.setBackgroundDrawable(dw);
    		//mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
    		mMenuView.setOnTouchListener(new OnTouchListener() {
    			
    			public boolean onTouch(View v, MotionEvent event) {
    				
    				int height = mMenuView.findViewById(R.id.id_marker_info).getTop();
    				int y=(int) event.getY();
    				if(event.getAction()==MotionEvent.ACTION_DOWN){
    					if(y<height){
    						dismiss();
    					}
    				}				
    				return true;
    			}
    		});
		} 
		
	}
	
	@Override
	public void onPause() {
		// mMapView.onPause();
		super.onPause();
		Log.i("sisi", "onPause is excuted");
	}

	@Override
	public void onResume() {
		// mMapView.onResume();
		super.onResume();
		Log.i("sisi", "onResume is excuted");
	}

	@Override
	public void onDestroy() {
		// �˳�ʱ���ٶ�λ
		mLocationClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;		
		super.onDestroy();
		// ���� bitmap ��Դ
		bdA.recycle();   //���ܵ����ڴ�й¶
		Log.i("sisi", "onDestroy is excuted");

	}

}
