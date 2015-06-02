package com.ProgrammerYuan.PKUEater.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import com.ProgrammerYuan.PKUEater.model.Comment;
import studio.archangel.toolkitv2.adapters.CommonAdapter;
import studio.archangel.toolkitv2.adapters.CommonAdapterViewCache;
import studio.archangel.toolkitv2.interfaces.OnCacheGeneratedListener;

import java.util.List;

/**
 * Created by mac on 15/6/1.
 */
public class CommentAdapter extends CommonAdapter<Comment> {

	RatingBar rating;

	public CommentAdapter(Context context, List<Comment> list){
		this(context,list, R.layout.item_comment);
	}

	public CommentAdapter(Context context, List<Comment> list, int item_layout) {
		super(context, list, item_layout);
		l = new OnCacheGeneratedListener<Comment>() {
			@Override
			public void onCacheGenerated(CommonAdapterViewCache c, Comment dish) {
				rating = (RatingBar)c.getView(R.id.item_comment_rating);
				rating.setRating(dish.getRating());
				c.setViewValue(TextView.class,dish.getString(Comment.TITLE),R.id.item_comment_name);
				c.setViewValue(TextView.class,dish.getString(Comment.CONTENT),R.id.item_comment_intro);
//				c.setViewValue(ImageView.class,dish.pic_resource,R.id.item_dish_image);
			}
		};
	}

	public CommentAdapter(Context context, List<Comment> list, int item_layout, boolean need_cache) {
		super(context, list, item_layout, need_cache);
	}
}
