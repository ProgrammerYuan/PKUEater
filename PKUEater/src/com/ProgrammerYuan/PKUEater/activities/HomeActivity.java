package com.ProgrammerYuan.PKUEater.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;

public class HomeActivity extends Activity {

	TextView eatBtn;
	RelativeLayout main;
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_home);
		eatBtn = (TextView)findViewById(R.id.act_home_eat_btn);
		main = (RelativeLayout) findViewById(R.id.act_home_main);
		eatBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
//				Intent intent = new Intent(HomeActivity.this,RecommendationActivity.class);
				Animator anim = ViewAnimationUtils.createCircularReveal(main, (int)(eatBtn.getX() + eatBtn.getWidth()/2),(int)(eatBtn.getY() + eatBtn.getHeight()/2),1000,0);
				anim.setDuration(1500);
				anim.setInterpolator(new DecelerateInterpolator());
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationCancel(Animator animation) {
						super.onAnimationCancel(animation);
					}

					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						main.setVisibility(View.INVISIBLE);
						Intent intent = new Intent(HomeActivity.this,RecommendationActivity.class);
//						ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeActivity.this,R.anim.act_alpha_in,R.anim.act_alpha_out);
						startActivity(intent);
						finish();
						overridePendingTransition(R.anim.act_alpha_in,R.anim.act_alpha_out);
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
						super.onAnimationRepeat(animation);
					}

					@Override
					public void onAnimationStart(Animator animation) {
						super.onAnimationStart(animation);
					}

					@Override
					public void onAnimationPause(Animator animation) {
						super.onAnimationPause(animation);
					}

					@Override
					public void onAnimationResume(Animator animation) {
						super.onAnimationResume(animation);
					}
				});
				anim.start();
			}

		});

	}
}
