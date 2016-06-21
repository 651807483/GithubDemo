package com.coderhu.botanique.tabmenu;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.baidu.lbsapi.auth.i;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;

import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.map.r;
import com.coderhu.botanique.baidumap.MyBaiduMapLocation;

import com.coderhu.botanique.baidumap.MyBaiduMapRoutePlan;
import com.coderhu.botanique.baidumap.MyOrientationListener;
import com.coderhu.botanique.common.BotaniqueInfo;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainActivity;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.main.R.drawable;

import com.coderhu.botanique.tabmenu.TabMapFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.R.integer;
import android.R.layout;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.usage.UsageEvents.Event;
import android.content.Context;
import android.content.Intent;
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
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TabMapFragment extends Fragment   {
  
	MapView mMapView;
	BaiduMap mBaiduMap;
	public LocationClient mLocationClient = null;
	public ImageButton requestLocButton,show_btn,map_switch_btn;
	Drawable location_compass;
	Drawable location_follow;
	Drawable location_normal;
	Drawable map_swith_button_mDrawable,map_swith_button_sDrawable;
	Button mbutton; // Marker����¼�
	Activity context;
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);// �����ǽ�ͼ��ת��Ϊ����
	private RelativeLayout mMarkerInfoLy;
	SelectPopupWindow  menuWindow;    //��������
	private View mMenuView;
	BotaniqueInfo botaniqueInfo;
	MainApplication myApp;
	Activity mainActivity;
	Info info;
    static int press_amount = 0;  //������ʾ��ť�������
    static int isMapSwitch = 0;  //��ͼ�л���־
	List<Info> list = null;
	private MyOrientationListener myOrientationListener;
	boolean ispress = false;
	boolean isSelection = false;
	List<Info> selection_Botanique = new ArrayList<Info>();
    Button search_btn = null;
    EditText searchView = null;
    Drawable right,searchDrawable;
    String text;
    String text_default;
    int length;
   
   
	public TabMapFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this.getActivity();
		// //��ȡApplication����
		myApp = (MainApplication)(context.getApplication());
		mainActivity = myApp.getMainActivity();
		list = myApp.getList();
		
		//mActionBar = context.getActionBar();
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
		
		//��ʾ������Χ�ڵ�ֲ��
		show_btn = (ImageButton)_View.findViewById(R.id.show_btn);   
		show_btn.setOnClickListener(show_listener);
		myApp.setmCurrent_list(new ArrayList<Info>());	
		
		/*
		 * ����ֲ��
		 */
		searchView = (EditText)_View.findViewById(R.id.search_view);	
		searchDrawable = getResources().getDrawable(R.drawable.search_btn); 
		searchDrawable.setBounds(0, 0, searchDrawable.getMinimumWidth(), searchDrawable.getMinimumHeight());  		
		searchView.setCompoundDrawables(searchDrawable, null, null, null);
	    //����edittext�����ݵı仯
		searchView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				length = s.length();	
				if (length > 0) {
					 setClearIcon(true); 
				}else {
					setClearIcon(false);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});

	
		//�������
		searchView.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub	
				
		     //����ұ�û��ͼƬ�����ٴ���
            if (right == null){
                return false;
            }

            //������ǰ����¼������ٴ���
            if (event.getAction() != MotionEvent.ACTION_UP){
            	 return false;
            }
            if (event.getX() > searchView.getWidth()
                    - searchView.getPaddingRight()
                    - right.getIntrinsicWidth()){
            	searchView.setText("");
            	mBaiduMap.clear();
            }
				return false;
			}
		});
	
		 
		search_btn = (Button)_View.findViewById(R.id.btn_search);
		search_btn.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�ر������
			    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);     
			    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);  
				mBaiduMap.clear();
				text = searchView.getText().toString();
				text_default = searchView.getHint().toString();
				List<Info> search_list = new ArrayList<Info>();				
					for (int i = 0; i < list.size(); i++) {
						if (TextUtils.isEmpty(text)) {
							if (text_default.equals(list.get(i).getChinaname())) {
								search_list.add(list.get(i));
								searchView.setText(text_default);
							}
							
						}else {
							if (text.equals(list.get(i).getChinaname())) {
								search_list.add(list.get(i));
								
							}
						}		
						
					}
					
					if(search_list.size() == 0){
						
							Toast.makeText(context, "�����ڣ�����������", Toast.LENGTH_SHORT).show();
						
					}else {
						addInfosOverlay(search_list);
						//searchView.clearFocus();
					     
					}
				
				
				
			}
		});	
		
       //�л�Ϊ���ǵ�ͼ��ť
		map_switch_btn = (ImageButton)_View.findViewById(R.id.map_switch_btn);   
		map_switch_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (isMapSwitch) {
				case 0:
					 mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
					 map_switch_btn.setImageDrawable(getResources().getDrawable(R.drawable.map_switch_button_s));
			
					 isMapSwitch++;
					break;
				case 1:
					mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);			    
				    map_switch_btn.setImageDrawable(getResources().getDrawable(R.drawable.map_switch_button_m));
				    isMapSwitch = 0;
				    break;
				default:
					break;
				}		
				
			}
		});
		
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
			
		//���򴫸���
		myOrientationListener = new MyOrientationListener(context);
		myApp.setMyOrientationListener(myOrientationListener);
		mMyBaiduMapLocation.location();
		Log.i("siis", "location�Ѿ�ִ��");
	    //mCurrenLatLng = myApp.getmCurrentlLatLng();//��ȡ��ǰ��λ����
		//���listview
		isSelection = myApp.isSelection();
		selection_Botanique = myApp.getSelect_Botanique();
		if (isSelection) {
			addInfosOverlay(selection_Botanique);
			isSelection = false;
			myApp.setSelection(isSelection);
			//selection_Botanique.clear();
		}
		return _View;
		
	}
	
    //�������ͼ��
	 protected void setClearIcon(boolean isText) {
		// TODO Auto-generated method stub
		Drawable deleteDrawable = getResources().getDrawable(R.drawable.delete_btn); 
		deleteDrawable.setBounds(0, 0, deleteDrawable.getMinimumWidth(), deleteDrawable.getMinimumHeight()); 
	    right = isText ? deleteDrawable : null;
		searchView.setCompoundDrawables(searchDrawable, null, right, null);
	}

	//��ʾһ����Χ��ֲ��ͼ��
	private OnClickListener show_listener = new OnClickListener() {
		   
	    LatLng database_LatLng = null;  //���ݿ��о�γ��
	    double distance;  //��������
	    //List<Info> mCurrent_list = new ArrayList<Info>();  //���Ϸ�Χ�ڵ�����ֲ������������У���ȫ�ֱ��������������ļ�������	
	    boolean isLocation = false;  //�Ƿ�λ�ɹ�
	    private LatLng mCurrenLatLng ; //��ȡ��ǰ��λ����
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			List<Info> mCurrent_list = new ArrayList<Info>();  //���Ϸ�Χ�ڵ�����ֲ�� �����ڷ���������Ǿֲ�����������������Ϻ���ͷ��ڴ�
			isLocation = myApp.getisLocation();
			ispress = true;
			if(isLocation = true) {
				
				mCurrenLatLng = myApp.getmCurrentlLatLng();//��ȡ��ǰ��λ����
				if(press_amount <= 2) {

				  switch (press_amount) {
					case 0:
						Toast.makeText(context, "����20�׷�Χ��ֲ��", Toast.LENGTH_SHORT).show();
						mBaiduMap.clear();  //���֮ǰ�ĸ�����
						mCurrent_list.clear();  //��������ڲ���ֵ
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 1000) {
								
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
							}
					    }
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
						
						press_amount++;
						break;
					case 1:
						Toast.makeText(context, "����50�׷�Χ��ֲ��", Toast.LENGTH_SHORT).show();
					
						mBaiduMap.clear();  //���֮ǰ�ĸ�����
						mCurrent_list.clear();  //��������ڲ���ֵ
						
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 600) {
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
							}
					    }	
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
						 press_amount++;
						break;
					case 2:
						Toast.makeText(context, "����100�׷�Χ��ֲ��", Toast.LENGTH_SHORT).show();
						mBaiduMap.clear();  //���֮ǰ�ĸ�����
						mCurrent_list.clear();  //��������ڲ���ֵ
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 500) {
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
							}
					    }	
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
		                 press_amount = 0;  //���óɾ�̬��������̬����������˵��Ͳ��ܸı�ֵ�����ܸı�ֵ�����г����� ��ӵ�е�ֵ�ǿɱ�� ���������ᱣ�����µ�ֵ��˵�侲̬������Ϊ���������ź����ĵ��ú��˳��������仯�����ϴε��ú�����ʱ��������Ǹ���̬��������ĳ��ֵ�Ļ����´κ�������ʱ�����ֵ���ֲ���
						break;

					default:
						break;
					}
				}
			}else {
				Toast.makeText(context, "����λ�ɹ����ٵ��������ǰ��Χ��ť", Toast.LENGTH_SHORT).show();
			}			
		}
	};
	
	
	// ��Ӹ�����
 
	public void addInfosOverlay(List<Info> show_list) {
		mBaiduMap.clear();		
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		for (Info info : show_list) {
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
		mBaiduMap.setOnMarkerClickListener(markerClickListener);
	}
	public OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			if (marker == null) {
				Log.i("sisi", "markerΪ�գ���");
				return true;
			}
		    info = (Info) marker.getExtraInfo().get("info");
		    myApp.setInfo(info);
			InfoWindow mInfoWindow;
			mbutton = new Button(getActivity()); // ÿ�ζ���Ҫ���������ڴ�
			mbutton.setBackgroundResource(R.drawable.popup);
			mbutton.setText(info.getChinaname());
			String Chinaname = info.getChinaname();
			botaniqueInfo = new BotaniqueInfo();
			OnInfoWindowClickListener dialog_listener = null;
			dialog_listener = new OnInfoWindowClickListener() {
				public void onInfoWindowClick() {
					// ����InfoWindow
					//marker.remove();
					//mBaiduMap.hideInfoWindow();
					mBaiduMap.hideInfoWindow();
					menuWindow = new SelectPopupWindow(context,itemsOnClick);			
					menuWindow.showAtLocation(context.findViewById(R.id.bmapView), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
					
				}
			};
			LatLng ll = marker.getPosition();
			mInfoWindow = new InfoWindow(BitmapDescriptorFactory
					.fromView(mbutton), ll, -47, dialog_listener);
			mBaiduMap.showInfoWindow(mInfoWindow);			    
			return true;
		}
	};
	 //Ϊ��������ʵ�ּ�����
    private OnClickListener  itemsOnClick = new OnClickListener(){

		public void onClick(View v) {
			menuWindow.dismiss();  //���ٵ�����
			switch (v.getId()) {
			case R.id.btn_pop_info:
				 Intent it = new Intent();   //����һ���µ�intent����
				 it.setClass(getActivity(), BotaniqueInfo.class);  //����Intent��Դ��ַ��Ŀ���ַ			
				 startActivity(it);  //����startActivity����������ͼ��ϵͳ
				break;
			case R.id.btn_pop_navigate:	
				MyBaiduMapRoutePlan myBaiduMapRoutePlan = new MyBaiduMapRoutePlan(context,mBaiduMap);
				myBaiduMapRoutePlan.walkingRoutePlan();	
				break;
//			case R.id.go_btn: 	
//						
//				break;
			default:
				break;
			}							
		}  	
    };
    

    
	private class SelectPopupWindow extends PopupWindow {
		Button btn_pop_info,btn_pop_navigate;
//		ImageButton btn_go;
//		TextView place_text;
		public SelectPopupWindow(Activity context,OnClickListener itemsOnClick) {
			super(context);
			
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mMenuView = inflater.inflate(R.layout.popwindow_dialog, null);
			btn_pop_info = (Button) mMenuView.findViewById(R.id.btn_pop_info);
			btn_pop_navigate = (Button) mMenuView.findViewById(R.id.btn_pop_navigate);
//			btn_go = (ImageButton) mMenuView.findViewById(R.id.go_btn);
//			place_text = (TextView)mMenuView.findViewById(R.id.place);
			//place_text.setText(info.getChinaname());
			//btn_go.setOnClickListener(itemsOnClick);
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
		myOrientationListener.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;		
		super.onDestroy();
		// ���� bitmap ��Դ
		//bdA.recycle();   //���ܵ����ڴ�й¶
		Log.i("sisi", "onDestroy is excuted");

	}

}
