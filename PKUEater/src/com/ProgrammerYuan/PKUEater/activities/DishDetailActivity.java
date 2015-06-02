package com.ProgrammerYuan.PKUEater.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.adapters.CommentAdapter;
import com.ProgrammerYuan.PKUEater.model.Comment;
import com.ProgrammerYuan.PKUEater.model.Dish;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.util.ArrayList;

/**
 * Created by mac on 15/6/2.
 */
public class DishDetailActivity extends Activity {
	ListView list;
	CommentAdapter adapter;
	ArrayList<Comment> comments;
	AngelActionBar aab;
	TextView tv_dish_name,tv_dish_intro;
	ImageView iv_dish_avatar;
	RatingBar rating;
	Dish dish;
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
				Intent intent = new Intent(DishDetailActivity.this,CommentCanteenActivity.class);
				startActivity(intent);
			}
		});
		bar.setCustomView(aab);
		aab.setTitleText(title);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_dish_detail);
		setupActionBar("菜品详情");
		View v = getLayoutInflater().inflate(R.layout.act_dish_detail_header,null);
		tv_dish_name = (TextView)v.findViewById(R.id.item_dish_name);
		tv_dish_intro = (TextView)v.findViewById(R.id.item_dish_intro);
		iv_dish_avatar = (ImageView)v.findViewById(R.id.item_dish_image);
		rating = (RatingBar)v.findViewById(R.id.item_dish_rating);
		Bundle data = getIntent().getExtras();
		if(data != null){
			dish = (Dish)data.getSerializable("dish");
			tv_dish_name.setText(dish.getName());
			tv_dish_intro.setText(dish.getIntro());
			iv_dish_avatar.setImageResource(dish.pic_resource);
			rating.setRating(dish.getRating());
		} else {
			finish();
			return;
		}
		list = (ListView)findViewById(R.id.act_dish_detail_list);
		list.addHeaderView(v);
		comments = new ArrayList<>();
		comments.add(new Comment("着实不错","好吃好吃",Math.min(5,dish.getRating() + 1)));
		comments.add(new Comment("太便宜了","一股贫民气息",Math.min(5, dish.getRating() == 5 ? 5 : dish.getRating()-1)));
		comments.add(new Comment("有点小辣","总的还算可以",Math.min(5,dish.getRating())));
		adapter = new CommentAdapter(this, comments);
		list.setAdapter(adapter);
	}
}