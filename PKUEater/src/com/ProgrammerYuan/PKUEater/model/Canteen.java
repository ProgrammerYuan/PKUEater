package com.ProgrammerYuan.PKUEater.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mac on 15/5/31.
 */
public class Canteen implements Serializable {
	private String name;
	private String startTime,endTime;
	private String image_url;
	private String location;
	private float crowdedness;
	private ArrayList<Dish> dishes;

	public Canteen(){

	}
}
