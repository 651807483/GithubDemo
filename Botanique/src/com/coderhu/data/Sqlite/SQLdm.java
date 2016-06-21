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
	 //数据库存储路径    
    String filePath = "data/data/com.coderhu.botanique.main/tree.db";    
    //数据库存放的文件夹 data/data/com.main.jh 下面    
    String pathStr = "data/data/com.coderhu.botanique.main";    
        
    SQLiteDatabase database;     
    public  SQLiteDatabase openDatabase(Context context){    
        //System.out.println("filePath:"+filePath);    
    	Log.i("sisi", "filePath:" + filePath);
        File jhPath=new File(filePath);    
            //查看数据库文件是否存在    
            if(jhPath.exists()){    
                Log.i("tree.db", "存在数据库");  
                //存在则直接返回打开的数据库    
                return SQLiteDatabase.openOrCreateDatabase(jhPath, null);    
            }else{    
                //不存在先创建文件夹    
                File path=new File(pathStr);    
                Log.i("tree.db", "pathStr="+path);  
                if (path.mkdir()){    
                    Log.i("tree.db", "创建成功");   
                }else{    
                    Log.i("tree.db", "创建失败");  
                };    
                try {    
                    //得到资源    
                    AssetManager am= context.getAssets();    //在AssertManager中不能处理单个超过 1MB的文件
                    //得到数据库的输入流    
                    InputStream is=am.open("tree.db");    
                    Log.i("tree.db", is+"");  
                    //用输出流写到SDcard上面      
                    FileOutputStream fos=new FileOutputStream(jhPath);    
                    Log.i("tree.db", "fos="+fos);  
                    Log.i("tree.db", "jhPath="+jhPath);  
                    //创建byte数组  用于1KB写一次    
                    byte[] buffer=new byte[1024];    
                    int count = 0;    
                    while((count = is.read(buffer))>0){    
                        Log.i("tree.db", "得到");  
                        fos.write(buffer,0,count);    
                    }    
                    //最后关闭就可以了    
                    fos.flush();    
                    fos.close();    
                    is.close();    
                } catch (IOException e) {    
                    // TODO Auto-generated catch block    
                    e.printStackTrace();    
                    return null;  
                }    
                //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了    
                return openDatabase(context);    
             
            }    
    }    
}
