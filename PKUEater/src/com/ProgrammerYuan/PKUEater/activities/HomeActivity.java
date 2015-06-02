package com.ProgrammerYuan.PKUEater.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.Fragments.HomeFragment;
import com.ProgrammerYuan.PKUEater.Fragments.MyCollectionFragment;
import com.ProgrammerYuan.PKUEater.Fragments.SearchFragment;
import com.ProgrammerYuan.PKUEater.R;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

public class HomeActivity extends FragmentActivity {

	TextView eatBtn;
	RelativeLayout main;
	FragmentTabHost host;
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
//        AngelActionBar aab = act.getAngelActionBar();
		if (aab == null) {
			aab = new AngelActionBar(this);
//            act.setAngelActionBar(aab);
		}
		aab.setBackgroundResource(R.color.white);
		aab.getLeftButton().setVisibility(View.INVISIBLE);
		aab.getShadow().setVisibility(View.VISIBLE);
		bar.setCustomView(aab);
		aab.setTitleText(title);
//		View v = getLayoutInflater().inflate(R.layout.actionbar_eater,null);
//		bar.setCustomView(v);
	}
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_home);
		setupActionBar("北大吃货");
		host = (FragmentTabHost) findViewById(android.R.id.tabhost);
		host.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		host.addTab(getTab("推荐", R.drawable.selector_tab_icon_menu2), HomeFragment.class, null);
		host.addTab(getTab("搜索", R.drawable.selector_tab_icon_square2), SearchFragment.class, null);
		host.addTab(getTab("我的收藏", R.drawable.selector_tab_icon_star), MyCollectionFragment.class, null);
		host.getTabWidget().setDividerDrawable(null);

	}
	TabHost.TabSpec getTab(String name, int icon_id) {
		TabHost.TabSpec tab = host.newTabSpec(name);
		setIndicator(tab, name, R.layout.tab_home_v3, icon_id);
		return tab;
	}

	public void setIndicator(TabHost.TabSpec spec, String name, int layout_id, int icon_id) {
		View v = getLayoutInflater().inflate(layout_id, null);
		TextView tv = (TextView) v.findViewById(R.id.tab_text_normal);
		ImageView iv = (ImageView) v.findViewById(R.id.tab_icon_normal);
		TextView noti = (TextView) v.findViewById(R.id.tab_msg_mark);
		iv.setImageResource(icon_id);
		tv.setText(name);
		spec.setIndicator(v);
	}

	public AngelActionBar getAngelActionBar(){
		return aab;
	}
}
