package com.ProgrammerYuan.PKUEater.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.adapters.DishAdapter;
import com.ProgrammerYuan.PKUEater.model.Dish;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.util.ArrayList;

/**
 * Created by mac on 15/6/1.
 */
public class CanteenDetailActivity extends Activity {

	ListView list;
	DishAdapter adapter;
	ArrayList<Dish> dishes;
	AngelActionBar aab;
	public void setupActionBar(String title) {
		ActionBar bar = getActionBar();
		if (bar == null) {
			return;
		}
		bar.setIcon(getResources().getDrawable(studio.archangel.toolkitv2.R.color.trans));
		bar.setDisplayHomeAsUpEnabled(false);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("");
		if (aab == null) {
			aab = new AngelActionBar(this);
		}
		aab.setBackgroundResource(R.color.white);
		aab.getShadow().setVisibility(View.VISIBLE);
		aab.setRightText("评价");
		aab.getRightButton().getTextView().setTextColor(getResources().getColor(R.color.main_2));
		aab.setRightListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CanteenDetailActivity.this,CommentCanteenActivity.class);
				startActivity(intent);
			}
		});
		bar.setCustomView(aab);
		aab.setTitleText(title);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_canteen_detail);
		setupActionBar("食堂详情");
		View v = getLayoutInflater().inflate(R.layout.act_canteen_detail_header,null);
		list = (ListView)findViewById(R.id.act_canteen_detail_list);
		list.addHeaderView(v);
		dishes = new ArrayList<>();
		dishes.add(new Dish("麻辣香锅", "超辣超好吃", 0));
		dishes.add(new Dish("糖醋里脊", "酸甜可口，鲜嫩多汁", 1));
		dishes.add(new Dish("木须肉","色泽鲜艳有营养",2));
		adapter = new DishAdapter(this,dishes);
		list.setAdapter(adapter);
	}
}