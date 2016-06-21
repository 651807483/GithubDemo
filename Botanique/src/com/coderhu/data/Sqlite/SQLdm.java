package com.coderhu.data.Sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLdm {
	 //���ݿ�洢·��    
    String filePath = "data/data/com.coderhu.botanique.main/tree.db";    
    //���ݿ��ŵ��ļ��� data/data/com.main.jh ����    
    String pathStr = "data/data/com.coderhu.botanique.main";    
        
    SQLiteDatabase database;     
    public  SQLiteDatabase openDatabase(Context context){    
        //System.out.println("filePath:"+filePath);    
    	Log.i("sisi", "filePath:" + filePath);
        File jhPath=new File(filePath);    
            //�鿴���ݿ��ļ��Ƿ����    
            if(jhPath.exists()){    
                Log.i("tree.db", "�������ݿ�");  
                //������ֱ�ӷ��ش򿪵����ݿ�    
                return SQLiteDatabase.openOrCreateDatabase(jhPath, null);    
            }else{    
                //�������ȴ����ļ���    
                File path=new File(pathStr);    
                Log.i("tree.db", "pathStr="+path);  
                if (path.mkdir()){    
                    Log.i("tree.db", "�����ɹ�");   
                }else{    
                    Log.i("tree.db", "����ʧ��");  
                };    
                try {    
                    //�õ���Դ    
                    AssetManager am= context.getAssets();    //��AssertManager�в��ܴ��������� 1MB���ļ�
                    //�õ����ݿ��������    
                    InputStream is=am.open("tree.db");    
                    Log.i("tree.db", is+"");  
                    //�������д��SDcard����      
                    FileOutputStream fos=new FileOutputStream(jhPath);    
                    Log.i("tree.db", "fos="+fos);  
                    Log.i("tree.db", "jhPath="+jhPath);  
                    //����byte����  ����1KBдһ��    
                    byte[] buffer=new byte[1024];    
                    int count = 0;    
                    while((count = is.read(buffer))>0){    
                        Log.i("tree.db", "�õ�");  
                        fos.write(buffer,0,count);    
                    }    
                    //���رվͿ�����    
                    fos.flush();    
                    fos.close();    
                    is.close();    
                } catch (IOException e) {    
                    // TODO Auto-generated catch block    
                    e.printStackTrace();    
                    return null;  
                }    
                //���û��������ݿ�  �����Ѿ�����д��SD�����ˣ�Ȼ����ִ��һ��������� �Ϳ��Է������ݿ���    
                return openDatabase(context);    
             
            }    
    }    
}
