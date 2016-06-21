package com.coderhu.botanique.tabmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.coderhu.botanique.common.Info;
import com.coderhu.botanique.main.MainActivity;
import com.coderhu.botanique.main.MainApplication;
import com.coderhu.botanique.main.R;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RadioGroup;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class TabNearbyFragment extends Fragment  {

	MainApplication myApp;
	Activity context;
	private LatLng mCurrentLatLng = null;  //��ȡ��ǰλ��
	List<Info> mCurrentlist = null;
	List<String> title_list = new ArrayList<String>();
	ExpandableListView nearbyListView = null;
	TabMapFragment mTabMapFragment;
	List<Info> select_Botanique = new ArrayList<Info>();
	FragmentActivity fragmentActivity;
	
	RadioGroup mTabMenu;

	/**
	 * ����һ����Ŀ����
	 */
	List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
	/**
	 * �������, �Ա���ʾ���б���
	 */
	List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	context = this.getActivity();
    	myApp = (MainApplication)(context.getApplication());
    	mCurrentlist = myApp.getmCurrent_list();
    	mCurrentLatLng = myApp.getmCurrentlLatLng();
    	mTabMapFragment = myApp.getmTabMapFragment();
    	fragmentActivity = getActivity();

        mTabMenu = (RadioGroup)context.findViewById(R.id.tab_menu);
    	
  
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_nearby, container, false);
		nearbyListView = (ExpandableListView)view.findViewById(R.id.nearbyList);
	    //myApp.setSelection(false);
		if ((mCurrentlist.size()) != 0) {
			setListData(); 
		}else {
			
			Toast.makeText(context, "��Ǹ������������ֲ��", Toast.LENGTH_SHORT).show();
		}
		

		return view;
	}
	
	/** 
     * �����б����� 
     */ 
	private void setListData() {
		// TODO Auto-generated method stub
	    int compare_amount = 0;
	    int j = 0;
	
		
	    //��ȡһ������
	
	 	title_list.add((mCurrentlist.get(0)).getChinaname());

		for (int i = 1; i < mCurrentlist.size(); i++) {
			
			for (j = 0; j < title_list.size(); j++) {
				
				if ((mCurrentlist.get(i)).getChinaname().equals(title_list.get(j))) {
					compare_amount = 0;
					break;
				}else {
					compare_amount++;
				}		
				
			}if(compare_amount == j) {
				
				title_list.add((mCurrentlist.get(i)).getChinaname());
				compare_amount = 0;
				Log.i("siis", (mCurrentlist.get(i)).getChinaname());
				
			}
					
		}
		
	  //����һ������
		for (int k = 0; k < title_list.size(); k++) {
			
			Map<String, String> title_name = new HashMap<String, String>();
			title_name.put("group", title_list.get(k));
			groups.add(title_name);
			
		}
		
		//���ö�����������
		for (int k = 0; k < title_list.size(); k++) {
			int amount = 1;
			List<Map<String, String>> child = new ArrayList<Map<String,String>>();
			for (int i = 0; i < mCurrentlist.size(); i++) {	
				
				Map<String, String> title_content = new HashMap<String,String>();
				if ((mCurrentlist.get(i)).getChinaname().equals(title_list.get(k))) {
					LatLng mCurrentlist_LatLng = new LatLng((mCurrentlist.get(i)).getLatitude(), (mCurrentlist.get(i)).getLongitude());
					title_content.put("child_name",  String.valueOf(amount++) + "." + mCurrentlist.get(i).getChinaname());
					double distance = DistanceUtil.getDistance(mCurrentlist_LatLng,mCurrentLatLng);
					title_content.put("child_distance", String.valueOf((int)distance) + "��");
					title_content.put("location", mCurrentlist_LatLng.toString());	
					title_content.put("chinaname", mCurrentlist.get(i).getChinaname());
					child.add(title_content);
					
				}
			}
			childs.add(child);
		}
		 /** 
         * ����ExpandableList��Adapter���� ����: 1.������ 2.һ������ 3.һ����ʽ�ļ� 4. һ����Ŀ��ֵ 
         * 5.һ����ʾ�ؼ��� 6. �������� 7. ������ʽ 8.������Ŀ��ֵ 9.������ʾ�ؼ��� 
         *  
         */  
		SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
				context, groups, R.layout.groups, new String[] {"group"}, new int[] {R.id.textGroup}, 
				childs, R.layout.childs, new String[] {"child_name","child_distance"} , new int[] {R.id.nameChild,R.id.distanceChild});
		 // �����б� 
		nearbyListView.setAdapter(sela);
	    nearbyListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				String  location = childs.get(groupPosition).get(childPosition).get("location");
				String  Chinaname = childs.get(groupPosition).get(childPosition).get("chinaname");
				
				for (int i = 0; i < mCurrentlist.size(); i++) {
					LatLng latLng = new LatLng((mCurrentlist.get(i)).getLatitude(), (mCurrentlist.get(i)).getLongitude());
					String latString = latLng.toString();
					if ((location.equals(latString)) &&  (Chinaname.equals(mCurrentlist.get(i).getChinaname()))) {
						select_Botanique.add(mCurrentlist.get(i));
						boolean isSelection = true;
						myApp.setSelect_Botanique(select_Botanique);
						myApp.setSelection(isSelection);
//						FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
//						//TabMapFragment _MapFragment = new TabMapFragment();	
//						transaction.replace(R.id.fragment_container, mTabMapFragment);
//						transaction.commit();
						int nearbyId = R.id.tab_map;
						mTabMenu.check(nearbyId);
						
						
						break;
					}
					
				}
			
//			
//				Toast.makeText(
//						context,
//						"��ѡ����"
//								+ groups.get(groupPosition).toString()
//								+ "�ӱ��"
//								+ childs.get(groupPosition).get(childPosition)
//										.toString(), Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}


    
	

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}




}
