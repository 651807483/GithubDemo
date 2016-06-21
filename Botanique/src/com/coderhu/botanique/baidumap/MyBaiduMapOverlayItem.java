package com.coderhu.botanique.baidumap;



import java.io.Serializable;
import java.util.ArrayList;

import android.R.integer;

import com.baidu.lbsapi.auth.i;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.coderhu.botanique.main.R;


public class MyBaiduMapOverlayItem {
	  MapView mMapView;
	  BaiduMap mBaiduMap;
	  private Marker mMarkerA;
	  BitmapDescriptor bdA ;
	  private ArrayList<LatLng> mMyLatLngs = new ArrayList<LatLng>();
	 //private 
	// ��ʼ��ȫ�� bitmap ��Ϣ������ʱ��ʱ recycle  
	 // BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);//�����ǽ�ͼ��ת��Ϊ����
			
	//������
	
	public MyBaiduMapOverlayItem(MapView mMapView, BaiduMap mBaiduMap,BitmapDescriptor bdA ) {
		// TODO Auto-generated constructor stub
		this.mMapView = mMapView;
		this.mBaiduMap = mBaiduMap;
		this.bdA = bdA;
	}

	//��Ӹ�����
    public void initOverlay() {
    	// add marker overlay;
    	mMyLatLngs.add(new LatLng(22.5500390000,113.9199270000));
    	mMyLatLngs.add(new LatLng(22.5226080000,113.9422150000));
    	mMyLatLngs.add(new LatLng(22.5473690000,113.9471060000));
    	for(int i = 0;i < mMyLatLngs.size();i++) {
    	 LatLng  llA =  mMyLatLngs.get(i);
    	 //�������ֲ�ͬ���͵ĸ�����
     	  MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
   			.zIndex(9).draggable(true);
     	mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
    	}
    //  LatLng llA = new LatLng(22.5485150000,114.0661120000);
   /*
    //�������ֲ�ͬ���͵ĸ�����
  	  MarkerOptions ooA = new MarkerOptions().position(llA).icon(bdA)
			.zIndex(9).draggable(true);
  	mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));*/
      
      
    }
 
	
}
/*
class MyLatLng implements Serializable
{

	 
	private static final long serialVersionUID = 1L;
	
	private double mLantitude;
	private double mLongtitude;
	
	public MyLatLng(double latitude, double longitude)
	{
		// TODO Auto-generated constructor stub
		mLantitude = latitude;
		mLongtitude = longitude;
	}
	
	public double getLantitude()
	{
		return mLantitude;
	}
	
	public double getLongtitude()
	{
		return mLongtitude;
	}
}*/
