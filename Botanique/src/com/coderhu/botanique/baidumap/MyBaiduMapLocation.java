package com.coderhu.botanique.baidumap;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.tabmenu.TabMapFragment;



public class MyBaiduMapLocation{
	ImageButton requestLocButton;
	MapView mMapView;
	BaiduMap mBaiduMap;
	boolean isFirstLoc = true; // �Ƿ��״ζ�λ
	LocationClient mLocationClient;
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker = null;
	Drawable location_compass;
	Drawable location_follow;
	Drawable location_normal;
	Activity context;
    MainApplication myApp;
    public BDLocationListener myListener = new MyLocationListener();

    
    
    
    
   
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
	
	
	//������
  
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
     * ���˶�λ
     */
	public void location(){
		myApp = (MainApplication) context.getApplication(); //��ȡApplication����	
		//myApp.getmTabMapFragment();
        mCurrentMode = LocationMode.NORMAL;
       // requestLocButton.setText("��ͨ");
        OnClickListener btnClickListener = new OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                       // requestLocButton.setText("����");
                    	requestLocButton.setImageDrawable(location_follow); 
                        mCurrentMode = LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                       mCurrentMode, true, mCurrentMarker));
                        
                        myApp.getmTabMapFragment().addInfosOverlay(Info.infos);
                       
					break;
                   case COMPASS:
                       // requestLocButton.setText("��ͨ");
                	    requestLocButton.setImageDrawable(location_normal); 
                        mCurrentMode = LocationMode.NORMAL;
                          mBaiduMap
                               .setMyLocationConfigeration(new MyLocationConfiguration(
                                    mCurrentMode, true, mCurrentMarker));
                         myApp.getmTabMapFragment().addInfosOverlay(Info.infos);
                        break;
                   case FOLLOWING:
                     //  requestLocButton.setText("����");
                	   requestLocButton.setImageDrawable(location_compass); 
                       mCurrentMode = LocationMode.COMPASS;
                        mBaiduMap
                               .setMyLocationConfigeration(new MyLocationConfiguration(
                                       mCurrentMode, true, mCurrentMarker));
                       myApp.getmTabMapFragment().addInfosOverlay(Info.infos);
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);	
    	// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// ��λ��ʼ��
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
    }
    /**
	 * ��λSDK��������
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
		
			// map view ���ٺ��ڴ����½��յ�λ��
			if (location == null || mMapView == null) {
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
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
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// ��λ������ÿСʱ
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// ��λ����
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// ��λ��
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps��λ�ɹ�");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //��Ӫ����Ϣ
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("���綨λ�ɹ�");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
                sb.append("\ndescribe : ");
                sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
            }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
                List<Poi> list = location.getPoiList();// POI����
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
   
		

