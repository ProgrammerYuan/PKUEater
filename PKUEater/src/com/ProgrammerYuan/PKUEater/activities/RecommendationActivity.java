package com.ProgrammerYuan.PKUEater.activities;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import studio.archangel.toolkitv2.util.Logger;

/**
 * Created by mac on 15/5/28.
 */
public class RecommendationActivity extends Activity {
	RelativeLayout main;
	TextView eatBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_recommendation);
		main = (RelativeLayout) findViewById(R.id.act_home_main);
		main.setVisibility(View.INVISIBLE);
		eatBtn = (TextView) findViewById(R.id.act_home_eat_btn);
		new Handler().postDelayed(new Thread() {
			@Override
			public void run() {
				main.setVisibility(View.VISIBLE);
				Animator anim = ViewAnimationUtils.createCircularReveal(main, 300, 300, 0, 1000);
				anim.setDuration(1500);
				anim.setInterpolator(new AccelerateInterpolator());
				anim.start();
			}
		}, 200);
		Logger.out(getWindow().getEnterTransition() == null);
		getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
			@Override
			public void onTransitionStart(Transition transition) {

			}

			@Override
			public void onTransitionEnd(Transition transition) {

			}

			@Override
			public void onTransitionCancel(Transition transition) {

			}

			@Override
			public void onTransitionPause(Transition transition) {

			}

			@Override
			public void onTransitionResume(Transition transition) {

			}
		});
		Logger.out("what");
	}
}