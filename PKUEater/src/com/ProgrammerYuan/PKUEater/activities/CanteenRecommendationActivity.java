package com.ProgrammerYuan.PKUEater.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.model.Canteen;
import studio.archangel.toolkitv2.AngelActivity;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.util.ArrayList;

/**
 * Created by mac on 15/5/31.
 */
public class CanteenRecommendationActivity extends AngelActivity {

	AngelActionBar aab;
	TextView selectBtn,anotherBtn;
	LinearLayout ll_wrapper,ll_card;
	TextView tv_name,tv_dishes;
	ImageView iv_avatar;
	ArrayList<Canteen> canteens;
	int index = 0;
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
//        AngelActionBar aab = act.getAngelActionBar();
		if (aab == null) {
			aab = new AngelActionBar(this);
//            act.setAngelActionBar(aab);
		}
		aab.setBackgroundResource(R.color.white);
		aab.getShadow().setVisibility(View.VISIBLE);
		aab.getLeftButton().setVisibility(View.INVISIBLE);
		bar.setCustomView(aab);
		aab.setTitleText(title);
//		View v = getLayoutInflater().inflate(R.layout.actionbar_eater,null);
//		bar.setCustomView(v);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_canteen_recommendation);
		setupActionBar("食堂推荐");
		selectBtn = (TextView) findViewById(R.id.act_canteen_recommendation_select_btn);
		anotherBtn = (TextView) findViewById(R.id.act_canteen_recommendation_another_btn);
		selectBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CanteenRecommendationActivity.this,DishRecommendationActivity.class);
				intent.putExtra("canteen_id", index);
				startActivity(intent);
			}
		});
		anotherBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final ObjectAnimator anim = ObjectAnimator.ofFloat(ll_wrapper, "alpha", 1f, 0f);
				final ObjectAnimator anim2 = ObjectAnimator.ofFloat(ll_wrapper, "alpha", 0f, 1f);
				anim.setDuration(300);
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						anim2.start();
						fillData();
					}
				});
				anim2.setDuration(500);
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						anim.removeAllListeners();
						anim2.removeAllListeners();
					}
				});
				anim.start();
			}
		});
		ll_card = (LinearLayout)findViewById(R.id.act_canteen_recommendation_card);
		ll_card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(CanteenRecommendationActivity.this,CanteenDetailActivity.class);
				startActivity(intent);
			}
		});
		ll_wrapper = (LinearLayout)findViewById(R.id.act_canteen_recommendation_card_wrapper);
		tv_name = (TextView)findViewById(R.id.act_canteen_recommendation_card_name);
		tv_dishes = (TextView)findViewById(R.id.act_canteen_recommendation_card_dishes);
		iv_avatar = (ImageView)findViewById(R.id.act_canteen_recommendation_card_image);

	}

	private void fillData() {
		for(int i = 0;i<10000;i++){

		}
	}
}
