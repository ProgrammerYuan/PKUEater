package com.ProgrammerYuan.PKUEater.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import studio.archangel.toolkitv2.AngelActivity;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

/**
 * Created by mac on 15/5/31.
 */
public class DishRecommendationActivity extends AngelActivity {

	AngelActionBar aab;
	TextView refreshBtn;
	ImageView iv_like1,iv_like2,iv_like3;
	boolean like1 = false,like2 = false,like3 = false;
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
		aab.getLeftButton().setVisibility(View.INVISIBLE);
		bar.setCustomView(aab);
		aab.setTitleText(title);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_dish_recommendation);
		setupActionBar("菜品推荐");
		refreshBtn = (TextView) findViewById(R.id.act_dish_recommendation_refresh_btn);
		refreshBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});
		iv_like1 = (ImageView)findViewById(R.id.act_dish_recommendation_card1_like);
		iv_like2 = (ImageView)findViewById(R.id.act_dish_recommendation_card2_like);
		iv_like3 = (ImageView)findViewById(R.id.act_dish_recommendation_card3_like);
		iv_like1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				like1 = !like1;
				iv_like1.setImageResource(like1 ? R.drawable.icon_like : R.drawable.icon_not_like);
			}
		});
		iv_like2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				like2 = !like2;
				iv_like2.setImageResource(like2 ? R.drawable.icon_like : R.drawable.icon_not_like);
			}
		});
		iv_like3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				like3 = !like3;
				iv_like3.setImageResource(like3 ? R.drawable.icon_like : R.drawable.icon_not_like);
			}
		});
	}
}