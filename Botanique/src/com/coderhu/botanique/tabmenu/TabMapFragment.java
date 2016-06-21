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
	Button mbutton; // Marker点击事件
	Activity context;
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_marka);// 这里是将图标转化为对象
	private RelativeLayout mMarkerInfoLy;
	SelectPopupWindow  menuWindow;    //弹出窗口
	private View mMenuView;
	BotaniqueInfo botaniqueInfo;
	MainApplication myApp;
	Activity mainActivity;
	Info info;
    static int press_amount = 0;  //计算显示按钮点击次数
    static int isMapSwitch = 0;  //地图切换标志
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
		// //获取Application对象
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
		// 显示地图
		mMapView = (MapView) _View.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();	
		
		//显示附近范围内的植物
		show_btn = (ImageButton)_View.findViewById(R.id.show_btn);   
		show_btn.setOnClickListener(show_listener);
		myApp.setmCurrent_list(new ArrayList<Info>());	
		
		/*
		 * 搜索植物
		 */
		searchView = (EditText)_View.findViewById(R.id.search_view);	
		searchDrawable = getResources().getDrawable(R.drawable.search_btn); 
		searchDrawable.setBounds(0, 0, searchDrawable.getMinimumWidth(), searchDrawable.getMinimumHeight());  		
		searchView.setCompoundDrawables(searchDrawable, null, null, null);
	    //监听edittext中内容的变化
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

	
		//清除内容
		searchView.setOnTouchListener(new OnTouchListener() {			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub	
				
		     //如果右边没有图片，不再处理
            if (right == null){
                return false;
            }

            //如果不是按下事件，不再处理
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
				//关闭软键盘
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
						
							Toast.makeText(context, "不存在，请重新输入", Toast.LENGTH_SHORT).show();
						
					}else {
						addInfosOverlay(search_list);
						//searchView.clearFocus();
					     
					}
				
				
				
			}
		});	
		
       //切换为卫星地图按钮
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
		
		// 定位功能
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
			
		//方向传感器
		myOrientationListener = new MyOrientationListener(context);
		myApp.setMyOrientationListener(myOrientationListener);
		mMyBaiduMapLocation.location();
		Log.i("siis", "location已经执行");
	    //mCurrenLatLng = myApp.getmCurrentlLatLng();//获取当前定位坐标
		//点击listview
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
	
    //设置清除图标
	 protected void setClearIcon(boolean isText) {
		// TODO Auto-generated method stub
		Drawable deleteDrawable = getResources().getDrawable(R.drawable.delete_btn); 
		deleteDrawable.setBounds(0, 0, deleteDrawable.getMinimumWidth(), deleteDrawable.getMinimumHeight()); 
	    right = isText ? deleteDrawable : null;
		searchView.setCompoundDrawables(searchDrawable, null, right, null);
	}

	//显示一定范围内植物图标
	private OnClickListener show_listener = new OnClickListener() {
		   
	    LatLng database_LatLng = null;  //数据库中经纬度
	    double distance;  //两点间距离
	    //List<Info> mCurrent_list = new ArrayList<Info>();  //符合范围内的所有植物，放在匿名类中，是全局变量，整个工程文件都存在	
	    boolean isLocation = false;  //是否定位成功
	    private LatLng mCurrenLatLng ; //获取当前定位坐标
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			List<Info> mCurrent_list = new ArrayList<Info>();  //符合范围内的所有植物 ，放在方法里面就是局部变量，方法调用完毕后会释放内存
			isLocation = myApp.getisLocation();
			ispress = true;
			if(isLocation = true) {
				
				mCurrenLatLng = myApp.getmCurrentlLatLng();//获取当前定位坐标
				if(press_amount <= 2) {

				  switch (press_amount) {
					case 0:
						Toast.makeText(context, "搜索20米范围内植物", Toast.LENGTH_SHORT).show();
						mBaiduMap.clear();  //清除之前的覆盖物
						mCurrent_list.clear();  //清空容器内部的值
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 1000) {
								
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
							}
					    }
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
						
						press_amount++;
						break;
					case 1:
						Toast.makeText(context, "搜索50米范围内植物", Toast.LENGTH_SHORT).show();
					
						mBaiduMap.clear();  //清除之前的覆盖物
						mCurrent_list.clear();  //清空容器内部的值
						
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 600) {
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
							}
					    }	
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
						 press_amount++;
						break;
					case 2:
						Toast.makeText(context, "搜索100米范围内植物", Toast.LENGTH_SHORT).show();
						mBaiduMap.clear();  //清除之前的覆盖物
						mCurrent_list.clear();  //清空容器内部的值
						for(Info database_info : list) {
							database_LatLng = new LatLng(database_info.getLatitude(), database_info.getLongitude());
							distance = DistanceUtil.getDistance(database_LatLng,mCurrenLatLng);
							if(distance <= 500) {
								mCurrent_list.add(database_info);
								
							}else {
								Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
							}
					    }	
						myApp.setmCurrent_list(mCurrent_list);
						addInfosOverlay(mCurrent_list);
		                 press_amount = 0;  //设置成静态变量，静态变量并不是说其就不能改变值，不能改变值的量叫常量。 其拥有的值是可变的 ，而且它会保持最新的值。说其静态，是因为它不会随着函数的调用和退出而发生变化。即上次调用函数的时候，如果我们给静态变量赋予某个值的话，下次函数调用时，这个值保持不变
						break;

					default:
						break;
					}
				}
			}else {
				Toast.makeText(context, "待定位成功后再点击搜索当前范围按钮", Toast.LENGTH_SHORT).show();
			}			
		}
	};
	
	
	// 添加覆盖物
 
	public void addInfosOverlay(List<Info> show_list) {
		mBaiduMap.clear();		
		LatLng latLng = null;
		OverlayOptions overlayOptions = null;
		Marker marker = null;
		for (Info info : show_list) {
			// 位置
			latLng = new LatLng(info.getLatitude(), info.getLongitude());
			// 图标
			overlayOptions = new MarkerOptions().position(latLng).icon(bdA)
					.zIndex(5);
			marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
			Bundle bundle = new Bundle();
			bundle.putSerializable("info", info);
			marker.setExtraInfo(bundle);
		
			
		}
		
		
		// 对Marker的点击事件
		mBaiduMap.setOnMarkerClickListener(markerClickListener);
	}
	public OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {
		
		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			if (marker == null) {
				Log.i("sisi", "marker为空！！");
				return true;
			}
		    info = (Info) marker.getExtraInfo().get("info");
		    myApp.setInfo(info);
			InfoWindow mInfoWindow;
			mbutton = new Button(getActivity()); // 每次都需要给他分配内存
			mbutton.setBackgroundResource(R.drawable.popup);
			mbutton.setText(info.getChinaname());
			String Chinaname = info.getChinaname();
			botaniqueInfo = new BotaniqueInfo();
			OnInfoWindowClickListener dialog_listener = null;
			dialog_listener = new OnInfoWindowClickListener() {
				public void onInfoWindowClick() {
					// 隐藏InfoWindow
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
	 //为弹出窗口实现监听类
    private OnClickListener  itemsOnClick = new OnClickListener(){

		public void onClick(View v) {
			menuWindow.dismiss();  //销毁弹出框
			switch (v.getId()) {
			case R.id.btn_pop_info:
				 Intent it = new Intent();   //创建一个新的intent对象
				 it.setClass(getActivity(), BotaniqueInfo.class);  //设置Intent的源地址和目标地址			
				 startActivity(it);  //调用startActivity方法发送意图给系统
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
    		//设置SelectPicPopupWindow的View
    	    //final PopupWindow popupWindow = new PopupWindow(null, 200, 300);
    		 this.setContentView(mMenuView);
    		//设置SelectPicPopupWindow弹出窗体的宽
     		this.setWidth(LayoutParams.MATCH_PARENT);
     		//设置SelectPicPopupWindow弹出窗体的高
     		this.setHeight(LayoutParams.WRAP_CONTENT);
     		//设置SelectPicPopupWindow弹出窗体可点击
     		this.setFocusable(true);
     		//设置SelectPicPopupWindow弹出窗体动画效果
    		this.setAnimationStyle(R.style.AnimBottom);
    		//实例化一个ColorDrawable颜色为透明
    		ColorDrawable dw = new ColorDrawable(-00000);
    		//设置SelectPicPopupWindow弹出窗体的背景
    		this.setBackgroundDrawable(dw);
    		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
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
		// 退出时销毁定位
		mLocationClient.stop();
		myOrientationListener.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;		
		super.onDestroy();
		// 回收 bitmap 资源
		//bdA.recycle();   //可能导致内存泄露
		Log.i("sisi", "onDestroy is excuted");

	}

}
