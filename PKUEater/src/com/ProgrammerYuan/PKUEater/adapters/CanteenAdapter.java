package com.ProgrammerYuan.PKUEater.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.activities.CanteenDetailActivity;
import com.ProgrammerYuan.PKUEater.model.Canteen;
import studio.archangel.toolkitv2.adapters.CommonAdapter;
import studio.archangel.toolkitv2.adapters.CommonAdapterViewCache;
import studio.archangel.toolkitv2.interfaces.OnCacheGeneratedListener;

import java.util.List;

/**
 * Created by mac on 15/6/1.
 */
public class CanteenAdapter extends CommonAdapter<Canteen> {

	public CanteenAdapter(Context context,List<Canteen> list){
		this(context,list, R.layout.item_canteen);
	}

	public CanteenAdapter(Context context, List<Canteen> list, int item_layout) {
		super(context, list, item_layout);
		init(context, list);
	}

	private void init(final Context context, List<Canteen> list) {
		l = new OnCacheGeneratedListener<Canteen>() {
			@Override
			public void onCacheGenerated(CommonAdapterViewCache c, Canteen canteen) {
				c.getView(R.id.item_canteen_main).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(context, CanteenDetailActivity.class);
						context.startActivity(intent);
					}
				});
			}
		};
	}

	public CanteenAdapter(Context context, List<Canteen> list, int item_layout, boolean need_cache) {
		super(context, list, item_layout, need_cache);
	}
}
