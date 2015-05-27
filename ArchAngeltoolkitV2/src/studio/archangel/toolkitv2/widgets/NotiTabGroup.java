package studio.archangel.toolkitv2.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import studio.archangel.toolkitv2.R;

import java.util.ArrayList;

/**
 * Created by Michael on 2014/12/30.
 */
public class NotiTabGroup extends LinearLayout {

    //    int selected = 0;
    int color_bg, color_indicator, color_text;
    boolean noti_enable = false;
    boolean indicator_enable = true;
    ArrayList<NotiTab> tabs;

    public NotiTabGroup(Context context) {
        super(context);
        init(context);
    }

    public NotiTabGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(HORIZONTAL);
        tabs = new ArrayList<NotiTab>();
    }

    public void setColors(int color_bg, int color_indicator, int color_text) {
        this.color_bg = color_bg;
        this.color_indicator = color_indicator;
        this.color_text = color_text;

    }

    public void setNotiEnabled(boolean enabled) {
        noti_enable = enabled;
    }

    public void setIndicatorEnabled(boolean enabled) {
        indicator_enable = enabled;
    }

    public NotiTab getTab(int index) {
        return tabs.get(index);
    }

    public void setTabs(String[] titles) {
        removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            NotiTab tab = new NotiTab(getContext());
            tab.setColors(color_bg, color_indicator, color_text);
            tab.setIndicatorEnabled(indicator_enable);
            tab.setNotiEnabled(noti_enable);
            tab.setText(titles[i]);
            tab.setTextSize(16);
            LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tab.setOwner(this);
            lp.gravity = Gravity.CENTER;
            lp.weight = 1;
            tab.setLayoutParams(lp);
            tabs.add(tab);
            addView(tab);
        }

    }

    public void setSelectedTab(int index) {
//        if (selected == index) {
//            return;
//        }
        for (int i = 0; i < tabs.size(); i++) {
            NotiTab tab = tabs.get(i);
            tab.setSelected(i == index);
        }
    }
}
