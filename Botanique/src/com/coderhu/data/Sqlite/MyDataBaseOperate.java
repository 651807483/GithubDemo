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
	private String Chinaname; // 设置中文名
	private String Englishname;
	private String Anothername;
	private String Scientificname;
	private String Scholarname;
	private String Orignalplace;
	private String Behavior;
	private String FeatureUse;
	private String Image;
	private double Latitude; // 精度
	private double longitude; // 纬度

	public MyDataBaseOperate(SQLiteDatabase database) {
		// helper = new MyDatabaseHelper(context);
		this.database = database;
	}

	public List<Info> readStringData(String table, String[] columns,
			String value, String selection, String[] selectionArgs) {
		List<Info> list = new ArrayList<Info>();
		Cursor cursor = null;
		try {
			cursor = database.query(false, table, columns, selection,
					selectionArgs, null, null, null, null);
			//int count = cursor.getCount();
			//int pos = cursor.getPosition();
			int cols_len = cursor.getColumnCount();
			while (cursor.moveToNext()) {
				Info info = new Info();
				for (int i = 0; i < cols_len; i++) {
					String cols_name = cursor.getColumnName(i);
					if (i == 10 || i == 11) {
						double cols_value = cursor.getDouble(cursor
								.getColumnIndex(cols_name));

						if (cols_name.equals("Latitude")) {
							info.setLatitude(cols_value);
							Latitude = info.getLatitude();
						}

						if (cols_name.equals("longitude")) {
							info.setLongitude(cols_value);
							longitude = info.getLongitude();
						}
					} else {
						String cols_value = cursor.getString(cursor
								.getColumnIndex(cols_name));

						if (cols_name.equals("Type")) {
							info.setType(cols_value);
							Type = info.getType();
						}

						if (cols_name.equals("Chinaname")) {
							info.setChinaname(cols_value);
							Chinaname = info.getChinaname();
						}

						if (cols_name.equals("Englishname")) {
							info.setEnglishname(cols_value);
							Englishname = info.getEnglishname();
						}

						if (cols_name.equals("Anothername")) {
							info.setAnothername(cols_value);
							Anothername = info.getAnothername();
						}

						if (cols_name.equals("Scientificname")) {
							info.setScientificname(cols_value);
							Scientificname = info.getScientificname();
						}

						if (cols_name.equals("Scholarname")) {
							info.setScholarname(cols_value);
							Scholarname = info.getScholarname();
						}

						if (cols_name.equals("Orignalplace")) {
							info.setOrignalplace(cols_value);
							Orignalplace = info.getOrignalplace();
						}

						if (cols_name.equals("Behavior")) {
							info.setBehavior(cols_value);
							Behavior = info.getBehavior();
						}
						if (cols_name.equals("FeatureUse")) {
							info.setFeatureUse(cols_value);
							FeatureUse = info.getFeatureUse();
						}

						if (cols_name.equals("Image")) {
							info.setImage(cols_value);
							Image = info.getImage();
						}
					}
				}
				// list.add(new Info(Type, Chinaname, Englishname, Anothername,
				// Scientificname, Scholarname, Orignalplace, Behavior,
				// FeatureUse, Latitude, longitude, Image));
				list.add(info);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return list;
	}
}
