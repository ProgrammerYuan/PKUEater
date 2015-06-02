package com.ProgrammerYuan.PKUEater.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.activities.CanteenRecommendationActivity;
import com.ProgrammerYuan.PKUEater.activities.DishRecommendationActivity;
import com.ProgrammerYuan.PKUEater.activities.HomeActivity;
import com.ProgrammerYuan.PKUEater.adapters.CanteenAdapter;
import com.ProgrammerYuan.PKUEater.adapters.SlideUpInAnimationAdapter;
import com.ProgrammerYuan.PKUEater.model.Canteen;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 15/5/31.
 */
public class SearchFragment extends B2Fragment {

	HomeActivity owner;
	EditText et;
	TextView tv_search;
	ArrayList<Canteen> canteens;
	CanteenAdapter adapter;
	SlideUpInAnimationAdapter slider;
	ListView list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setRealName("HomeFragment");
		if(!onCreateView(inflater,container,savedInstanceState, R.layout.frag_search)){
			et = (EditText)cache.findViewById(R.id.frag_search_input);
			tv_search = (TextView)cache.findViewById(R.id.frag_search_btn);
			tv_search.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					canteens.add(new Canteen());
					adapter.notifyDataSetChanged();
				}
			});
			canteens = new ArrayList<>();
			list = (ListView)cache.findViewById(R.id.frag_search_result_list);
			adapter = new CanteenAdapter(owner,canteens);
			slider = new SlideUpInAnimationAdapter(adapter,80);
			slider.setAbsListView(list);
			list.setAdapter(slider);

		}
		return cache;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		owner = (HomeActivity) activity;}

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
			aab.setTitleText("搜索");
			aab.setMultiButtonEnabed(false);
			aab.getLeftButton().setVisibility(View.GONE);
			aab.getRightButton().setVisibility(View.GONE);
			aab.getRightImageButton().setVisibility(View.GONE);
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
