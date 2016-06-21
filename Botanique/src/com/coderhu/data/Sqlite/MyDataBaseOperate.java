package com.coderhu.data.Sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.mapapi.map.InfoWindow;
import com.coderhu.botanique.common.Info;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDataBaseOperate {
	SQLiteDatabase database = null;
	private String Type; // 设置类型
	private String location_chinaname; // 设置中文名
	private String floristics_chinaname; 
	private String Englishname;
	private String Anothername;
	private String Scientificname;
	private String Scholarname;
	private String Orignalplace;
	private String Behavior;
	private String FeatureUse;
	private String Image;
	private double Latitude; // 精度
	private double Longitude; // 纬度

	public MyDataBaseOperate(SQLiteDatabase database) {
		// helper = new MyDatabaseHelper(context);
		this.database = database;
	}

	public List<Info> readStringData(String info_table,String coordinate_table, String[] columns,
			String value, String selection, String[] selectionArgs) {
		List<Info> list = new ArrayList<Info>();

		Cursor info_cursor = null;
		Cursor coordinate_cursor = null;

		try {
			
			coordinate_cursor = database.query(false, coordinate_table, columns, selection,
				    selectionArgs, null, null, null, null);
		  
			
    		while (coordinate_cursor.moveToNext()) {
			   Info info = new Info();
				info.setLatitude(coordinate_cursor.getDouble(coordinate_cursor.getColumnIndex("Latitude")));
				info.setLongitude(coordinate_cursor.getDouble(coordinate_cursor.getColumnIndex("Longitude")));
				info.setChinaname(coordinate_cursor.getString(coordinate_cursor.getColumnIndex("Chinaname")));
				location_chinaname = coordinate_cursor.getString(coordinate_cursor.getColumnIndex("Chinaname"));
				
				info_cursor = database.query(false, info_table, columns, selection,
						    selectionArgs, null, null, null, null);
			    
							
				while (info_cursor.moveToNext() ) {			
					if(location_chinaname.equals(info_cursor.getString(info_cursor.getColumnIndex("Chinaname")))){
						info.setType(info_cursor.getString(info_cursor.getColumnIndex("Type")));			
						info.setEnglishname(info_cursor.getString(info_cursor.getColumnIndex("Englishname")));
						info.setAnothername(info_cursor.getString(info_cursor.getColumnIndex("Anothername")));
						info.setScientificname(info_cursor.getString(info_cursor.getColumnIndex("Scientificname")));
						info.setScholarname(info_cursor.getString(info_cursor.getColumnIndex("Scholarname")));
						info.setBehavior(info_cursor.getString(info_cursor.getColumnIndex("Behavior")));
						info.setFeatureUse(info_cursor.getString(info_cursor.getColumnIndex("FeatureUse")));
						info.setOrignalplace(info_cursor.getString(info_cursor.getColumnIndex("Orignalplace")));				
						info.setImage(info_cursor.getString(info_cursor.getColumnIndex("Image")));
					}
					
				}
				
				list.add(info);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (info_cursor != null)
				info_cursor.close();
			if (coordinate_cursor != null)
				coordinate_cursor.close();
			
		}
		return list;
	}
}
