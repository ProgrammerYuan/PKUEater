package studio.archangel.toolkitv2.widgets;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import studio.archangel.toolkitv2.AngelViewPagerFragmentV4;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Logger;
import uk.co.androidalliance.edgeeffectoverride.ContextWrapperEdgeEffect;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michael on 2014/12/27.
 */
public class AngelViewPagerV4 extends LinearLayout {
    Activity act = null;
    //    ActionBar bar;
    AngelPagerAdapterV4 adapter;
    NotiTabGroup tabs;
    ViewPager pager;
    PYOnPageChangeListener listener = null;
    int color_bg = R.color.white;
    int color_text = R.color.black;
    int color_indicator = R.color.red;
//    int pager_last_widthMeasureSpec;
//    int pager_last_heightMeasureSpec;
    boolean dynamic_height = false;
    static int instance_count = 0;
    ScrollView parent_scrollview = null;
    int screen_height = -1;
    ArrayList<Integer> children_height;
    //    int tab_noti_text_color_res_id = R.color.white;
    public ArrayList<View> reddots;
//    PagerSlidingTabStrip strip;

    public AngelViewPagerV4(Context context) {
//        super(new ContextWrapperEdgeEffect(context));
        super(context);
        init(context);
    }

    public AngelViewPagerV4(Context context, AttributeSet attrs) {
//        super(new ContextWrapperEdgeEffect(context), attrs);
        super(context, attrs);
        init(context);
    }

    public void setColors(int color_bg, int color_indicator, int color_text) {
        this.color_bg = color_bg;
        this.color_indicator = color_indicator;
        this.color_text = color_text;
        tabs.setColors(this.color_bg, this.color_indicator, this.color_text);
    }

    public ViewPager getPager() {
        return pager;
    }

    public NotiTabGroup getTabs() {
        return tabs;
    }

    public void setDynamicHeight(boolean b) {
        dynamic_height = b;
    }
    public void setChildHeight(int index,int height){
        try {
            if(children_height.size() > index) children_height.set(index,height);
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
    public void setScreen_height(int screen_height) {
        this.screen_height = screen_height;
    }

    public void setNotiTabBackgroundResource(int res) {
        tabs.setBackgroundResource(res);
    }

    public void setParentScrollview(ScrollView parent_scrollview) {
        this.parent_scrollview = parent_scrollview;
    }

    void init(Context context) {
        setOrientation(VERTICAL);
        try {
            act = (Activity) context;
        } catch (ClassCastException e){
            act = null;
            e.printStackTrace();
        }
        tabs = new NotiTabGroup(getContext());
        pager = new InternalViewPager(new ContextWrapperEdgeEffect(context));
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        tabs.setLayoutParams(params);
        tabs.setBackgroundResource(R.color.white);
        addView(tabs);
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        pager.setLayoutParams(params);
        pager.setId((0x1000 + ++instance_count));
        addView(pager);
        children_height=new ArrayList<Integer>();
    }

    public void setCurrentSelectedPage(int index){
        pager.setCurrentItem(index);
    }

    public void setActivity(Activity activity){
        if(act == null && activity != null) act = activity;
        return;
    }

    /**
     * 设置要显示的Fragments
     *
     * @param fragment 容器Fragment
     * @param frags    要显示的Fragments
     */
    public void setFragments(Fragment fragment, ArrayList<? extends AngelViewPagerFragmentV4> frags) {
        generateAdapter(fragment, frags);
        //确保让所有的Fragment都在内存中加载好
        pager.setOffscreenPageLimit(frags.size() * 2);
    }

    public void setFragments(FragmentActivity act, ArrayList<? extends AngelViewPagerFragmentV4> frags) {
        generateAdapter(act, frags);
        //确保让所有的Fragment都在内存中加载好
        pager.setOffscreenPageLimit(frags.size() * 2);
    }

    public void setupTabs(ArrayList<? extends AngelViewPagerFragmentV4> frags, ArrayList<String> titles) {
        reddots = new ArrayList<View>();
        if (tabs == null) {
            tabs = new NotiTabGroup(getContext());
        }
        tabs.setNotiEnabled(true);
        tabs.setIndicatorEnabled(true);
        tabs.setColors(color_bg, color_indicator, color_text);
        tabs.setTabs(titles.toArray(new String[0]));
        for (int i = 0; i < frags.size(); i++) {
            final NotiTab tab = tabs.getTab(i);
            final int index = i;
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(index);
                }
            });
            frags.get(i).setRedDot(tab.noti);
            reddots.add(tab.noti);

        }
        tabs.setSelectedTab(0);
    }


    /**
     * 生成Adapter
     *
     * @param fragment 容器Fragment
     * @param frags    要显示的Fragments
     */
    void generateAdapter(Fragment fragment, ArrayList<? extends AngelViewPagerFragmentV4> frags) {
        adapter = new AngelPagerAdapterV4(act, pager, fragment.getChildFragmentManager(), frags);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(adapter);
    }

    void generateAdapter(FragmentActivity act, ArrayList<? extends AngelViewPagerFragmentV4> frags) {
        adapter = new AngelPagerAdapterV4(act, pager, act.getSupportFragmentManager(), frags);
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(adapter);
    }

    public class AngelPagerAdapterV4 extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {//}, ActionBar.TabListener {
        ViewPager pager;
        ArrayList<AngelViewPagerFragmentV4> frags = new ArrayList<AngelViewPagerFragmentV4>();
        int last_child_height = -1;

        public AngelPagerAdapterV4(Activity activity, ViewPager p, FragmentManager fm, ArrayList<? extends AngelViewPagerFragmentV4> fragments) {
            super(fm);
            act = activity;
            pager = p;
            frags.clear();
            frags.addAll(fragments);
        }


        @Override
        public Fragment getItem(int i) {
            return frags.get(i);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return frags.get(position).name;
        }

        @Override
        public int getCount() {
            return frags.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int i) {
            Logger.out("onPageSelected target:" + i);

            if(listener != null) listener.onPageChanged(i);
            tabs.setSelectedTab(i);
            frags.get(i).onBroughtToFront();
            for (int index = 0; index < frags.size(); index++) {
                if (index != i) {
                    frags.get(index).onBroughtToBack();
                }
            }
            if (dynamic_height && parent_scrollview != null) {
                View child = pager.getChildAt(i);
                if (child == null) {
                    return;
                }
                int h = children_height.get(i);
                if (last_child_height != -1 && parent_scrollview.getScrollY() > h) {
                    parent_scrollview.scrollTo(0, (int) (pager.getY() + h - screen_height));
                }
                last_child_height = h;
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
//
//        @Override
//        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//            if (pager != null)
//                pager.setCurrentItem(tab.getPosition());
//        }
//
//        @Override
//        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//        }
//
//        @Override
//        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//        }


    }

    public void setPYOnPageChangeListener(PYOnPageChangeListener listener){
        this.listener = listener;
    }

    public interface PYOnPageChangeListener {
        public void onPageChanged(int index);
    }

    class InternalViewPager extends ViewPager {

        public InternalViewPager(Context context) {
            super(context);
        }

        public InternalViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (dynamic_height) {
                int height = 0;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                    int h = child.getMeasuredHeight();
                    if (h > height) height = h;
                    children_height.add(h);
                }
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            }
//            pager_last_widthMeasureSpec = widthMeasureSpec;
//            pager_last_heightMeasureSpec = heightMeasureSpec;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        @Override
        protected boolean canScroll(View v,boolean checkV,int dx,int x,int y){
            if(v instanceof SwipeLayout) {
                if(dx > 0 && !((SwipeLayout)v).settleToOpen) return false;
                return true;
            } else return super.canScroll(v,checkV,dx,x,y);
        }
    }
}