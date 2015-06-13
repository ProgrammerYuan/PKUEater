package com.ProgrammerYuan.PKUEater.model;

import android.database.Cursor;
import com.ProgrammerYuan.PKUEater.R;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by mac on 15/5/31.
 */
public class Dish extends DBEntry implements Serializable {

	private int id,canteen_id,like_count;;
	private String name,intro,image_url;
	private boolean isSystem;
	private double price;
	private float rating;
	public int pic_index,pic_resource;

	public Dish(int canteen_id,JSONObject jo){
		this(jo);
		this.canteen_id = canteen_id;
	}

	public Dish(JSONObject jo){
		if(jo == null) return;
		id = jo.optInt("id");
		name = jo.optString("name");
		price = jo.optDouble("price");
		intro = jo.optString("description");
		image_url = jo.optString("image");
	}

	public Dish(Cursor c){
		if(c == null) return;
		id = c.getInt(c.getColumnIndex("id"));
		canteen_id = c.getInt(c.getColumnIndex("canteen_id"));
		name = c.getString(c.getColumnIndex("name"));
		price = c.getDouble(c.getColumnIndex("price"));
		intro = c.getString(c.getColumnIndex("intro"));
		image_url = c.getString(c.getColumnIndex("image"));
	}

	public Dish(String name,String intro,int pic_index,float rating){
		this(name,intro,pic_index);
		this.rating = rating;
	}

	public Dish(String name,String intro,int pic_index){
		this.name = name;
		this.intro = intro;
		this.pic_index = pic_index;
		switch (pic_index){
			case 0:
				pic_resource = R.drawable.xiangguo;
				break;
			case 1:
				pic_resource = R.drawable.liji;
				break;
			case 2:
				pic_resource = R.drawable.muxurou;
				break;
		}
	}

	public String getName(){
		return name;
	}

	public String getIntro(){
		return intro;
	}

	public float getRating() {
		return rating;
	}

	@Override
	public String getDeletingSql() {
		return null;
	}

	@Override
	public String getSavingSql() {
		return "replace into `dishes` (`id`,`canteen_id`,`name`,`image`,`intro`,`price`) values (" + String.valueOf(id) + "," + canteen_id + ",'" + name + "','" + image_url + "','" + intro + "'," + String.valueOf(price) + ")";
	}

	@Override
	public String getCreatingTableSql() {
		return "create table if not exists `dishes`(" +
				"`id` int primary key," +
				"`canteen_id` int" +
				"`name` varchar(255)," +
				"`image` varchar(255)," +
				"`intro`varchar(255)" +
				"`price` double" +
				");";
	}
}
