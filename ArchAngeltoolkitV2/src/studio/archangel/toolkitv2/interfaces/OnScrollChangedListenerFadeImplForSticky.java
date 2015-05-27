package studio.archangel.toolkitv2.interfaces;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ScrollView;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;

/**
 * Created by Michael on 2014/10/14.
 *
 * @see OnScrollChangedListenerFadeImplForPullToRefresh
 */
public class OnScrollChangedListenerFadeImplForSticky implements StickyScrollView.OnScrollChangedListener {
    View header;
    Drawable drawable;
    int min_alpha;
    ActionBar bar;

    public OnScrollChangedListenerFadeImplForSticky(ActionBar b, View view_header, Drawable d, int min) {
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

//onCreate...


    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        int headerHeight = header.getHeight() - bar.getHeight();
        float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
        int newAlpha = (int) (ratio * 255);
        newAlpha = Math.max(newAlpha, min_alpha);
        drawable.setAlpha(newAlpha);
    }
}
