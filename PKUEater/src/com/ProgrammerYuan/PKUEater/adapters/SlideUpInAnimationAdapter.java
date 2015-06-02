package com.ProgrammerYuan.PKUEater.adapters;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.SingleAnimationAdapter;
import studio.archangel.toolkitv2.util.Util;

/**
 * Created by Michael on 2014/12/3.
 */
public class SlideUpInAnimationAdapter extends SingleAnimationAdapter {
    private static final String TRANSLATION_Y = "translationY";
    int default_height;

    public SlideUpInAnimationAdapter(final BaseAdapter baseAdapter) {
        this(baseAdapter, -1);

    }

    public SlideUpInAnimationAdapter(final BaseAdapter baseAdapter, int item_height) {
        super(baseAdapter);
        if (item_height != -1) {
            default_height = item_height;
        } else {
            default_height = Util.getPX(80);
        }
    }

    @Override

    protected Animator getAnimator(final ViewGroup parent, final View view) {

//        int height = view.getMeasuredHeight() / 2;
        int height = view.getHeight() / 2;

        if (height == 0) {
            height = default_height;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, TRANSLATION_Y, height, 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(1000);
        return animator;
    }

}
