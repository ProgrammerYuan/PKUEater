package studio.archangel.toolkitv2.interfaces;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.internal.OnScrollChangedListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.Util;

/**
 * 适用于PullToRefreshScrollView的滚动距离监听器
 * Created by Michael on 2014/10/14.
 *
 * @see com.handmark.pulltorefresh.library.PullToRefreshScrollView
 */
public class OnScrollChangedListenerFadeImplForPullToRefresh implements OnScrollChangedListener {
    View header;
    Drawable drawable;
    Drawable drawable2;
    int min_alpha;
    ActionBar bar;
    View view;
    TextView tv;

    /**
     * 初始化
     *
     * @param v           需要改变背景的任意控件
     * @param b           ActionBar
     * @param view_header Activity中最上方的控件。它的距离将会被用来调节actionbar的透明度
     * @param d1          {@code b} 的背景
     * @param d2          {@code b} 的背景
     * @param min         {@code b} 透明度的最小值
     */
    public OnScrollChangedListenerFadeImplForPullToRefresh(View v, ActionBar b, View view_header, Drawable d1, Drawable d2, int min) {
        this(v, b, view_header, d1, d2, min, null);
    }

    public OnScrollChangedListenerFadeImplForPullToRefresh(View v, ActionBar b, View view_header, Drawable d1, Drawable d2, int min, TextView text) {
        header = view_header;
        drawable = d1;
        drawable2 = d2;
        min_alpha = min;
        drawable.setAlpha(min);
        drawable2.setAlpha(min);
        view = v;
        tv = text;
        bar = b;
        bar.setBackgroundDrawable(drawable);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable2);
        } else {
            view.setBackground(drawable2);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            drawable.setCallback(mDrawableCallback);
            drawable2.setCallback(mDrawableCallback2);
        }
    }
//    public OnScrollChangedListenerFadeForPullToRefresh(ActionBar b, View view_header, Drawable d, int min) {
//        header = view_header;
//        drawable = d;
//        min_alpha = min;
//        drawable.setAlpha(min);
//        bar = b;
//        bar.setBackgroundDrawable(drawable);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            drawable.setCallback(mDrawableCallback);
//        }
//    }

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

    private Drawable.Callback mDrawableCallback2 = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(who);
            } else {
                if (view == null) {
                    Logger.out("====================");
                    Logger.out("view为空");
                    Logger.out("====================");
                }
                if (who == null) {
                    Logger.out("====================");
                    Logger.out("who为空");
                    Logger.out("====================");
                }
                view.setBackground(who);
            }
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
        }
    };

    /**
     * 当绑定的ScrollView滚动时，更新Actionbar的透明度
     *
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
        drawable2.setAlpha(newAlpha);
        if(drawable instanceof ColorDrawable && tv!=null){
            ColorDrawable cd=(ColorDrawable)drawable;
            tv.setTextColor(Util.getReversedColor(cd.getColor()));
        }
    }
}
