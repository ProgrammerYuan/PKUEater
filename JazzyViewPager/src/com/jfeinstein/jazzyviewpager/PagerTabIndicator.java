package com.jfeinstein.jazzyviewpager;

import android.view.View;

/**
 * Created by Michael on 2014/11/4.
 */
public class PagerTabIndicator implements OnPagerScrollListener {
    View normal, cover;
    public String tag = "";

    public PagerTabIndicator(View n, View c) {
        normal = n;
        cover = c;
    }


    @Override
    public void onScroll(float offset) {
        if (cover != null) {
            cover.setAlpha(1 - offset);
        }
    }

}
