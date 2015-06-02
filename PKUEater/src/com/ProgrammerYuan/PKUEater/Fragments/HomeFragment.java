package com.ProgrammerYuan.PKUEater.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.activities.CanteenRecommendationActivity;
import com.ProgrammerYuan.PKUEater.activities.DishRecommendationActivity;
import com.ProgrammerYuan.PKUEater.activities.HomeActivity;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

/**
 * Created by mac on 15/5/31.
 */
public class HomeFragment extends B2Fragment {
	HomeActivity owner;
	TextView eatBtn;
	TextView eatBtn2;
	int type = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRealName("HomeFragment");
		if(!onCreateView(inflater,container,savedInstanceState, R.layout.frag_home)){
			eatBtn = (TextView)cache.findViewById(R.id.frag_home_eat_btn);
			eatBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(owner, CanteenRecommendationActivity.class);
					owner.startActivity(intent);
				}
			});
			eatBtn2 = (TextView)cache.findViewById(R.id.frag_home_eat_btn2);
			eatBtn2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(owner, DishRecommendationActivity.class);
					startActivity(intent);
				}
			});
		}
		return cache;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//        front = true;
		owner = (HomeActivity) activity;
//        BApplication.instance.showGuideDialog(getActivity(), "more");
	}

	@Override
	public void onDetach() {
		super.onDetach();
//        front = false;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		AngelActionBar aab = owner.getAngelActionBar();
		if (aab != null) {
			aab.setTitleText("北大吃货");
			aab.setMultiButtonEnabed(false);
			aab.getLeftButton().setVisibility(View.GONE);
			aab.getRightButton().setVisibility(View.GONE);
			aab.setRightImage(R.drawable.icon_switch);
			aab.getRightImageButton().setVisibility(View.VISIBLE);
			aab.setRightListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					final boolean temp = type == 0;
					final ObjectAnimator anim = ObjectAnimator.ofFloat(temp ? eatBtn : eatBtn2, "rotationY", 0, 90);
					final ObjectAnimator anim2 = ObjectAnimator.ofFloat(temp ? eatBtn2 : eatBtn, "rotationY", -90, 0);
					type = 1 - type;
					anim.setDuration(300);
//					anim.setInterpolator(new DecelerateInterpolator());
					anim.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationCancel(Animator animation) {
							super.onAnimationCancel(animation);
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							if (temp) {
								eatBtn.setVisibility(View.INVISIBLE);
								eatBtn2.setVisibility(View.VISIBLE);
							} else {
								eatBtn2.setVisibility(View.INVISIBLE);
								eatBtn.setVisibility(View.VISIBLE);
							}

							anim2.start();
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							super.onAnimationRepeat(animation);
						}

						@Override
						public void onAnimationStart(Animator animation) {
							super.onAnimationStart(animation);
						}
					});
					anim2.setDuration(300);
//					anim2.setInterpolator(new AccelerateInterpolator());
					anim2.addListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationCancel(Animator animation) {
							super.onAnimationCancel(animation);
						}

						@Override
						public void onAnimationEnd(Animator animation) {
							super.onAnimationEnd(animation);
							anim.removeAllListeners();
							anim2.removeAllListeners();
						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							super.onAnimationRepeat(animation);
						}

						@Override
						public void onAnimationStart(Animator animation) {
							super.onAnimationStart(animation);
						}
					});
					anim.start();
				}
			});
			aab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// do nothing
				}
			});
			aab.setTitleListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// do nothing
				}
			});
		}
	}
}
