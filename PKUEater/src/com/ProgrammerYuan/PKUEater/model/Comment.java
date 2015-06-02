package com.ProgrammerYuan.PKUEater.model;

import java.io.Serializable;

/**
 * Created by mac on 15/6/3.
 */
public class Comment implements Serializable {

	private String content,title;
	private int id;
	private float rating;

	public static final int TITLE = 0;
	public static final int CONTENT = 1;

	public Comment(String content,String title,float rating){
		this.content = content;
		this.title = title;
		this.rating = rating;
	}

	public String getString(int key){
		String ret = "";
		switch (key){
			case TITLE:
				ret = title;
				break;
			case CONTENT:
				ret = content;
				break;
		}
		return ret;
	}
	public float getRating(){
		return rating;
	}
}
