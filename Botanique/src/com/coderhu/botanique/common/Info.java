package com.coderhu.botanique.common;

import java.io.Serializable;
import java.util.ArrayList;


public class Info implements Serializable{
	private static final long serialVersionUID = -758459502806858414L;  
	private String Type;  //设置类型
	private String Chinaname;  //设置中文名
	private String Englishname;  
	private String Anothername;  
	private String Scientificname;  
	private String Scholarname; 
	private String Orignalplace;  
	private String Behavior; 
	private String FeatureUse;
	private String Image;
    private double Latitude;  //精度 
    private double longitude;  //纬度
    private String name;
   
   
  
    public static ArrayList<Info> infos = new ArrayList<Info>();  
  
    static  
    {  
//        infos.add(new Info(22.5500390000,113.9199270000,  "荷兰花卉小镇" ));  
//        infos.add(new Info(22.5318390000,113.9400990000, "桂庙新村"));  
//        infos.add(new Info(22.5347070000,113.9446610000,  "深大南区运动场" ));  
      
    }  
  public Info () {
	
  }
     
  
    public Info(String Type,String Chinaname,String Englishname,String Anothername,String Scientificname,String Scholarname,String Orignalplace,String Behavior,String FeatureUse,double Latitude,double longitude,String Image)  
    {  
        super();  
        this.Type = Type;
        this.Chinaname = Chinaname; 
        this.Englishname = Englishname;
        this.Anothername = Anothername;
        this.Scientificname = Scientificname;
        this.Scholarname = Scholarname;
        this.Orignalplace = Orignalplace;
        this.Behavior = Behavior;
        this.FeatureUse = FeatureUse;
        this.Image = Image;
        this.Latitude = Latitude;  
        this.longitude = longitude;   
      
    }  
  
    public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public String getChinaname() {
		return Chinaname;
	}


	public void setChinaname(String chinaname) {
		Chinaname = chinaname;
	}


	public String getEnglishname() {
		return Englishname;
	}


	public void setEnglishname(String englishname) {
		Englishname = englishname;
	}


	public String getAnothername() {
		return Anothername;
	}


	public void setAnothername(String anothername) {
		Anothername = anothername;
	}


	public String getScientificname() {
		return Scientificname;
	}


	public void setScientificname(String scientificname) {
		Scientificname = scientificname;
	}


	public String getScholarname() {
		return Scholarname;
	}


	public void setScholarname(String scholarname) {
		Scholarname = scholarname;
	}


	public String getOrignalplace() {
		return Orignalplace;
	}


	public void setOrignalplace(String orignalplace) {
		Orignalplace = orignalplace;
	}


	public String getBehavior() {
		return Behavior;
	}


	public void setBehavior(String behavior) {
		Behavior = behavior;
	}


	public String getFeatureUse() {
		return FeatureUse;
	}


	public void setFeatureUse(String featureUse) {
		FeatureUse = featureUse;
	}


	public double getLatitude()  
    {  
        return Latitude;  
    }  
  
    public void setLatitude(double latitude)  
    {  
        this.Latitude = latitude;  
    }  
  
    public double getLongitude()  
    {  
        return longitude;  
    }  
  
    public void setLongitude(double longitude)  
    {  
        this.longitude = longitude;  
    }  
    public String getImage() {
		return Image;
	}


	public void setImage(String image) {
		Image = image;
	}
   public String getName(){
	   return name;
   }
   
}
