package com.ProgrammerYuan.PKUEater.model;

import com.ProgrammerYuan.PKUEater.R;

import java.io.Serializable;

/**
 * Created by mac on 15/5/31.
 */
public class Dish implements Serializable {

	private int id,canteen_id,like_count;;
	private String name,intro,image_url;
	private boolean isSystem;
	private double price;
	public int pic_index,pic_resource;

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


}
