package com.coderhu.botanique.baidumap;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import com.coderhu.botanique.mapapi.overlay.WalkingRouteOverlay;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;


public class MyBaiduMapRoutePlan {
	RoutePlanSearch mRoutePlanSearch = null;
	Drawable location_normal;
	Activity  mContext;
	BaiduMap mBaiduMap;
	public boolean useDefaultIcon = false;
	PlanNode st = null;
	PlanNode ed = null;
	MainApplication myApp;
	Info info;
	LatLng endLatLng = null;
	static double mdistance;
    RouteLine route = null;

	
	public  MyBaiduMapRoutePlan() {
		
	}
	
    public MyBaiduMapRoutePlan(Activity mContext,BaiduMap mBaiduMap) {
		// TODO Auto-generated constructor stub
   		this.mContext = mContext;
   		this.mBaiduMap = mBaiduMap;
	}
	

	public void walkingRoutePlan() {
		// TODO Auto-generated method stub
		route = null;
				
		myApp = (MainApplication)mContext.getApplication();
		mBaiduMap.removeMarkerClickListener(myApp.getmTabMapFragment().markerClickListener);
		mBaiduMap.clear();
		info = myApp.getInfo();
		endLatLng = new LatLng(info.getLatitude(), info.getLongitude());
		//��ȡ��ǰ��λ����
		st = PlanNode.withLocation(myApp.getmCurrentlLatLng());
		ed = PlanNode.withLocation(endLatLng);
	   
		mRoutePlanSearch = RoutePlanSearch.newInstance();
		mRoutePlanSearch.setOnGetRoutePlanResultListener(listener);
		mRoutePlanSearch.walkingSearch(new WalkingRoutePlanOption().from(st).to(ed));
	
		
	}
	
	OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() { 
			 
		// ��ȡ������·�滮���
        public void onGetWalkingRouteResult(WalkingRouteResult result) {  
           if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {  
               Toast.makeText(mContext, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();  
            }  
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {  
                //���յ��;�����ַ����壬ͨ�����½ӿڻ�ȡ�����ѯ��Ϣ  
                //result.getSuggestAddrInfo()  
                return;  
            }  
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {  
            	WalkingRouteOverlay mWalkingRouteOverlay = new MyWalkingRouteOverlay(mBaiduMap);  
                mBaiduMap.setOnMarkerClickListener(mWalkingRouteOverlay);  
                route =  result.getRouteLines().get(0);
                mWalkingRouteOverlay.setData(result.getRouteLines().get(0));  
                mWalkingRouteOverlay.addToMap();  
                mWalkingRouteOverlay.zoomToSpan();  

            }          
            
            
        }  
        public void onGetTransitRouteResult(TransitRouteResult result) {  
			// ��ȡ��������·���滮���
		    } 
        public void onGetDrivingRouteResult(DrivingRouteResult result) {  
            //   
        }
		@Override
		public void onGetBikingRouteResult(BikingRouteResult arg0) {
			// TODO Auto-generated method stub
			
		}  
       
    };
    
      //����������֮�����
	    public static double getDistance(LatLng p1LL,LatLng p2LL) {
	    	 return mdistance;
	    }
            

	
    
   // �Զ�������յ� ͼ��
    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
            }
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            if (useDefaultIcon) {
                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
            }
            return null;
        }
    }


}
