package com.ecloud.pulltozoomview;

import android.widget.ScrollView;

/**
 * Created by shonenight on 2014/11/27.
 */
public interface OnScrollChangedListener {
    void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
}
