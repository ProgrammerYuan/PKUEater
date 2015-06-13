package com.ProgrammerYuan.PKUEater.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.model.Canteen;
import com.ProgrammerYuan.PKUEater.utils.EaterDB;
import com.ProgrammerYuan.PKUEater.utils.Net;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.http.client.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import studio.archangel.toolkitv2.AngelActivity;
import studio.archangel.toolkitv2.dialogs.LoadingDialog;
import studio.archangel.toolkitv2.interfaces.NetCallBack;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.image.ImageProvider;
import studio.archangel.toolkitv2.util.text.Notifier;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
				Intent intent = new Intent(CanteenRecommendationActivity.this, DishRecommendationActivity.class);
				intent.putExtra("canteen_id", index);
				startActivity(intent);
			}
		});
		anotherBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(index + 1 >= canteens.size()) {
					Notifier.showNormalMsg(CanteenRecommendationActivity.this,"没有更多推荐食堂了");
					return;
				}
				final ObjectAnimator anim = ObjectAnimator.ofFloat(ll_wrapper, "alpha", 1f, 0f);
				final ObjectAnimator anim2 = ObjectAnimator.ofFloat(ll_wrapper, "alpha", 0f, 1f);
				anim.setDuration(300);
				anim.addListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						super.onAnimationEnd(animation);
						anim2.start();
						fillData(++index);
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
				Intent intent = new Intent(CanteenRecommendationActivity.this, CanteenDetailActivity.class);
				startActivity(intent);
			}
		});
		ll_wrapper = (LinearLayout)findViewById(R.id.act_canteen_recommendation_card_wrapper);
		tv_name = (TextView)findViewById(R.id.act_canteen_recommendation_card_name);
		tv_dishes = (TextView)findViewById(R.id.act_canteen_recommendation_card_dishes);
		iv_avatar = (ImageView)findViewById(R.id.act_canteen_recommendation_card_image);

		canteens = EaterDB.getCanteens();
		if(canteens.size() > 0) fillData(0);
		getCanteenRecommendation();
	}

	private void getCanteenRecommendation(){
		final HttpUtils http = Net.getClient(this);
		final LinkedHashMap<String,String> para = new LinkedHashMap<>();
		Logger.out(Net.url_get_canteen_recommendation);
		http.send(HttpRequest.HttpMethod.GET, Net.url_get_canteen_recommendation, Net.getParam(HttpRequest.HttpMethod.GET,para), new NetCallBack() {

			@Override
			public void onStart(){
				dialog = new LoadingDialog(CanteenRecommendationActivity.this,R.color.main_2);
			}

			@Override
			public void onSuccess(final int status, final String json, JSONObject request_param) {
				Logger.out("JSON:" + json);
				if(dialog == null) return;
				Logger.out(json);
				dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialogInterface) {
						if(status == 0) {
							try {
								JSONArray ja = new JSONArray(json);
								if(ja.length() <= 0){
									Notifier.showNormalMsg(CanteenRecommendationActivity.this,"目前没有推荐餐厅");
									return;
								}
								canteens.clear();
								for(int i = 0;i<ja.length();i++){
									JSONObject jo = ja.getJSONObject(i);
									Canteen canteen = new Canteen(jo);
									canteens.add(canteen);
									EaterDB.saveEntry(canteen);
								}
								fillData(0);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {

						}
					}
				});
				dialog.dismiss();
			}

			@Override
			public void onFailure(HttpException error, String msg, JSONObject request_param){
				dialog.dismiss();
				Logger.out(error.getExceptionCode() + "/" + msg);
			}
		});
	}

	private void fillData(int index) {
		try {
			Canteen c = canteens.get(index);
			tv_name.setText(c.getName());
			tv_dishes.setText(c.getDishesTitle());
			ImageProvider.display(iv_avatar,c.getImageUrl());
		}  catch (IndexOutOfBoundsException e){
//			Notifier.showNormalMsg();
			e.printStackTrace();
		}
	}
}
