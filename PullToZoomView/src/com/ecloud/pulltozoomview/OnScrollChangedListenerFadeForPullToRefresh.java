package com.ecloud.pulltozoomview;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by shonenight on 2014/11/27.
 */
public class OnScrollChangedListenerFadeForPullToRefresh implements OnScrollChangedListener {
    View header;
    Drawable drawable;
    int min_alpha;
    ActionBar bar;

    /**
     * 初始化
     *
     * @param b           ActionBar
     * @param view_header Activity中最上方的控件。它的距离将会被用来调节actionbar的透明度
     * @param d           {@code b} 的背景
     * @param min         {@code b} 透明度的最小值
     */
    public OnScrollChangedListenerFadeForPullToRefresh(ActionBar b, View view_header, Drawable d, int min) {
        header = view_header;
        drawable = d;
        min_alpha = min;
        drawable.setAlpha(min);
        bar = b;
        bar.setBackgroundDrawable(drawable);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable.setCallback(mDrawableCallback);
        }
    }

    /**
     * 监测Actionbar背景的改变。改变时重设Actionbar的背景。
     */
    private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            bar.setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

    /**当绑定的ScrollView滚动时，更新Actionbar的透明度
     * @param who
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        int headerHeight = header.getHeight() - bar.getHeight();
        float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
        int newAlpha = (int) (ratio * 255);
        newAlpha = Math.max(newAlpha, min_alpha);
        drawable.setAlpha(newAlpha);
    }
}
