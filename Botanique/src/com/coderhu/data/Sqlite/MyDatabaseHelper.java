package com.coderhu.data.Sqlite;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.widget.TableLayout;

public class MyDatabaseHelper extends SQLiteOpenHelper {
   //private static String name = "mydb.db";//数据库名称
   //private static int version = 1;   //数据库的版本
     
   private static final int version = 1; //数据库的版本
   private static final String name= "Botanique.db";//数据库名称
   
	public MyDatabaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql ="Create Table if not exists botanique(id integer primary key autoincrement,Type varchar(10),Chinaname text,Englishname text,Anothername text,Scientificname text, Scholarname text,Orignalplace text,Behavior text, FeatureUse text,Latitude double,longitude double,Image varchar(32))"; 
        db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS person");
		onCreate(db);
		System.out.println("upgrade a database");
	}

}
