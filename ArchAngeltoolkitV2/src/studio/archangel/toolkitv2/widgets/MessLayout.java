/**
 *
 */
package studio.archangel.toolkitv2.widgets;

import android.content.Context;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsoluteLayout;
import studio.archangel.toolkitv2.interfaces.Constructable;

import java.util.ArrayList;

/**
 * @author Administrator
 */
@SuppressWarnings("deprecation")
public class MessLayout extends AbsoluteLayout implements Constructable {
    public ArrayList<View> views;
    int spacing_h = 0;
    int spacing_v = 0;

    /**
     * @param context
     * @param attrs
     */
    public MessLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public MessLayout(Context context) {
        super(context);
    }

    public void setData(ArrayList<View> vs, int presetW, int presetH) {
        views = vs;
        int w = presetW;
        if (w == -1) {
            //w = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
            w = this.getWidth();
        }
        int h = presetH;
        if (h == -1) {
            //h = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
            h = this.getHeight();
        }
        int x = 0;
        int y = 0;
        // F.debugOutput(w+" "+h);
        for (int i = 0; i < views.size(); i++) {
            View v = views.get(i);
            // F.debugOutput(w+" "+h);
            v.measure(w, h);
            int mw = v.getMeasuredWidth();
            int mh = v.getMeasuredHeight();
            if (x + mw > w - getPaddingRight() - getPaddingLeft()) {
                x = 0;
                y += mh + spacing_v;
            }
            addView(v, new AbsoluteLayout.LayoutParams(mw, mh, x, y));
            x += mw + spacing_h;

        }
    }

    public void setSpacing(int h, int v) {
        spacing_h = h;
        spacing_v = v;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.AbsoluteLayout#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.skinlab.interfaces.Constructable#setValue(android.os.Message)
     */
    @Override
    public void setValue(Message msg) {
        removeAllViews();
        setData((ArrayList<View>) msg.obj, msg.arg1, msg.arg2);
    }

}
