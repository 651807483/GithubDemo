package com.coderhu.botanique.baidumap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapTouchListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.location.BDLocation;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainActivity;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.tabmenu.TabMapFragment;
import com.coderhu.botanique.baidumap.MyOrientationListener;
import com.coderhu.botanique.baidumap.MyOrientationListener.OnOrientationListener;



public class MyBaiduMapLocation{
	ImageButton requestLocButton;
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true; // 是否首次定位
	LocationClient mLocationClient;
	private LocationMode mCurrentMode = LocationMode.NORMAL;
	BitmapDescriptor mCurrentMarker = null;
	Drawable location_compass;
	Drawable location_follow;
	Drawable location_normal;
	Activity context;
    MainApplication myApp;
    private double mCurrentLantitude;
   	private double mCurrentLongitude;
    LatLng mCurrentlLatLng;
   	private float mCurrentAccracy;
   	private int mXDirection;
    boolean isLocation = false;
   
	private MyOrientationListener myOrientationListener;
    
    
    
    
   
    //private MainApplication myApp;
    /*
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	 setContentView(R.layout.fragment_map); 
    	 mbutton = new Button(getApplicationContext());
    	
    	 //myApp = (MainApplication) getApplication(); 
    	 //button = myApp.getButton();
    	 //Log.i("guoll", "ChangeLabel:"+myApp.getButton());
    }*/
	
	
	//构造器
  
    public MyBaiduMapLocation(ImageButton requestLocButton, MapView mMapView,
			Drawable location_compass, Drawable location_follow,
			Drawable location_normal, BaiduMap mBaiduMap,
			LocationClient mLocationClient,Activity context) {
		// TODO Auto-generated constructor stub
    	this.requestLocButton = requestLocButton;
	    this.mBaiduMap = mBaiduMap;
	    this.mMapView = mMapView;
	    this.mLocationClient = mLocationClient;
	    this.location_compass = location_compass;
	    this.location_follow = location_follow;
	    this.location_normal = location_normal;
	    this.context = context;
	}
   
    
    /*
     * 个人定位
     */
	public void location(){
		myApp = (MainApplication) context.getApplication(); //获取Application对象	
		//myApp.getmTabMapFragment();
        mCurrentMode = LocationMode.NORMAL;
       // requestLocButton.setText("普通");
        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                       // requestLocButton.setText("跟随");
                    	requestLocButton.setImageDrawable(location_follow); 
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                       mCurrentMode, true, mCurrentMarker));
                        
                       // myApp.getmTabMapFragment().addInfosOverlay(myApp.getList());
                       
					break;
                   case COMPASS:
                       // requestLocButton.setText("普通");
                	    requestLocButton.setImageDrawable(location_normal); 
                        mCurrentMode = LocationMode.NORMAL;
                          mBaiduMap
                               .setMyLocationConfigeration(new MyLocationConfiguration(
                                    mCurrentMode, true, mCurrentMarker));
                        // myApp.getmTabMapFragment().addInfosOverlay(myApp.getList());
                        break;
                   case FOLLOWING:
                     //  requestLocButton.setText("罗盘");
                	   requestLocButton.setImageDrawable(location_compass); 
                       mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap
                               .setMyLocationConfigeration(new MyLocationConfiguration(
                                       mCurrentMode, true, mCurrentMarker));
                     //  myApp.getmTabMapFragment().addInfosOverlay(myApp.getList());
                        break;
                    default:
                        break;
                }
            }
        };
        
        
        //触摸地图监听事件
        mBaiduMap.setOnMapTouchListener(new OnMapTouchListener() {
			
			@Override
			public void onTouch(MotionEvent event) {
				// TODO Auto-generated method stub
				mCurrentMode = LocationMode.NORMAL;
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					
					    requestLocButton.setImageDrawable(location_normal); 
							mBaiduMap
							.setMyLocationConfigeration(new MyLocationConfiguration(
									mCurrentMode, true, null));
					
				
			}
		}
		});
        requestLocButton.setOnClickListener(btnClickListener);	
    	// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
     	BDLocationListener myListener = new MyLocationListener();
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();	
		myOrientationListener = myApp.getMyOrientationListener();
		myOrientationListener.start();
		initOritationListener();
	 }
	
	/** 
     * 初始化方向传感器 
     */  
	
	private void initOritationListener()
	{
	
				
		myOrientationListener
				.setOnOrientationListener(new OnOrientationListener()
				{
					@Override
					public void onOrientationChanged(float x)
					{
						mXDirection = (int) x;
					
						MyLocationData locData = new MyLocationData.Builder()
								.accuracy(mCurrentAccracy)								
								.direction(mXDirection)
								.latitude(mCurrentLantitude)
								.longitude(mCurrentLongitude).build();
					
						mBaiduMap.setMyLocationData(locData);
											
						mBaiduMap
                         .setMyLocationConfigeration(new MyLocationConfiguration(
                                 mCurrentMode, true, mCurrentMarker));
					
						

					}
				});
	}
	
    /**
	 * 定位SDK监听函数
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
		
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null) {
				return;
			}
			   // 构造定位数据  
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(mXDirection).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			// 设置定位数据  
			mBaiduMap.setMyLocationData(locData);
			mCurrentAccracy = location.getRadius();  
			mCurrentLantitude = location.getLatitude();  
            mCurrentLongitude = location.getLongitude();
            mCurrentlLatLng = new LatLng(mCurrentLantitude , mCurrentLongitude);
            myApp.setmCurrentlLatLng(mCurrentlLatLng);
            isLocation = true;   //设置定位成功信号
            myApp.setLocation(isLocation);
			// 第一次定位时，将地图位置移动到当前位置  
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(18.0f);
				mBaiduMap.animateMapStatus(MapStatusUpdateFactory
						.newMapStatus(builder.build()));
			}
			//Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                    
		 }
            Log.i("BaiduLocationApiDem", sb.toString());    
		}
		public void onReceivePoi(BDLocation poiLocation) {
		}

	
	}	
	

	  
         
    }
   
		

