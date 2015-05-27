package studio.archangel.toolkitv2.widgets;

import android.app.ActionBar;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import studio.archangel.toolkitv2.AngelViewPagerFragmentV4;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Logger;
import uk.co.androidalliance.edgeeffectoverride.ContextWrapperEdgeEffect;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by Michael on 2014/12/27.
 */
public class AngelFragmentHostV4 extends FragmentTabHost {
    FragmentActivity act;

    ActionBar bar;
    //    AngelPagerAdapterV4 adapter;
    ArrayList<AngelViewPagerFragmentV4> fragments;

    public AngelFragmentHostV4(Context context) {
        super(new ContextWrapperEdgeEffect(context));
        init(context);
    }

    public AngelFragmentHostV4(Context context, AttributeSet attrs) {
        super(new ContextWrapperEdgeEffect(context), attrs);
        init(context);
    }

    void init(Context context) {
        act = (FragmentActivity) context;
        bar = act.getActionBar();
        ((ContextWrapperEdgeEffect) getContext()).setEdgeEffectColor(getResources().getColor(R.color.trans));

    }


    /**
     * 设置要显示的Fragments
     *
     * @param layout_id
     * @param text_id
     * @param icon_id
     * @param titles
     * @param icon_ids
     * @param classes
     */
    public void setFragments(/*ArrayList<? extends AngelViewPagerFragmentV4> frags, */int layout_id, int text_id, int icon_id, String[] titles, int[] icon_ids, Class[] classes,Bundle[] bundles) {
        fragments=new ArrayList<AngelViewPagerFragmentV4>();
        for (int i = 0; i < titles.length; i++) {
//            AngelViewPagerFragmentV4 f = frags.get(i);
            TabSpec tab = getTab(titles[i], layout_id, text_id, icon_id, icon_ids[i]);
            addTab(tab, classes[i], bundles[i]);
            AngelViewPagerFragmentV4 f = (AngelViewPagerFragmentV4) act.getSupportFragmentManager().findFragmentByTag(tab.getTag());
            fragments.add(f);
        }
//        generateAdapter(fragment, frags);
        //确保让所有的Fragment都在内存中加载好
//        setOffscreenPageLimit(frags.size() * 2);
    }

    TabHost.TabSpec getTab(String name, int layout_id, int text_id, int icon_id, int icon_res_id) {
        TabHost.TabSpec tab = newTabSpec(name);
        setIndicator(tab, name, layout_id, text_id, icon_id, icon_res_id);
        return tab;
    }

    public void setIndicator(TabHost.TabSpec spec, String name, int layout_id, int text_id, int icon_id, int icon_res_id) {
        View v = act.getLayoutInflater().inflate(layout_id, null);
//        Logger.out(v+" "+layoutId);
        TextView tv = (TextView) v.findViewById(text_id);
        ImageView iv = (ImageView) v.findViewById(icon_id);
        iv.setImageResource(icon_res_id);
        tv.setText(name);

        spec.setIndicator(v);
    }

//    public AngelPagerAdapterV4 getAdapter() {
//        return adapter;
//    }

    @Override
    public void onTabChanged(String tabId) {
        super.onTabChanged(tabId);
        Logger.out("onTabChanged:"+tabId);
    }
//    /**
//     * 设置Actionbar的Tab
//     *
//     * @param frags      要显示的Fragments
//     * @param titles     要显示的Fragments的标题
//     * @param force_tabs 强制将Tab显示在Actionbar上而不是Actionbar下方
//     */
//    public void setupTabs(ArrayList<? extends AngelViewPagerFragmentV4> frags, ArrayList<String> titles, boolean force_tabs) {
//        for (int i = 0; i < frags.size(); i++) {
//            bar.addTab(bar.newTab().setText(titles.get(i)).setTabListener(adapter));
//        }
//        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        if (force_tabs) {
//            forceTabs();
//        }
//    }


//    /**
//     * 强制将Tab显示在Actionbar上而不是Actionbar下方
//     */
//    public void forceTabs() {
//        try {
////            final ActionBar actionBar = getActionBar();
//            final Method setHasEmbeddedTabsMethod = bar.getClass()
//                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
//            setHasEmbeddedTabsMethod.setAccessible(true);
//            setHasEmbeddedTabsMethod.invoke(bar, true);
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 生成Adapter
//     *
//     * @param fragment 容器Fragment
//     * @param frags    要显示的Fragments
//     */
//    void generateAdapter(Fragment fragment, ArrayList<? extends AngelViewPagerFragmentV4> frags) {
//        adapter = new AngelPagerAdapterV4(act, bar, this, fragment.getChildFragmentManager(), frags);
//        setAdapter(adapter);
//        setOnPageChangeListener(adapter);
//    }

//    public class AngelPagerAdapterV4 extends FragmentPagerAdapter implements OnPageChangeListener, ActionBar.TabListener {
//        ViewPager pager;
//        ArrayList<AngelViewPagerFragmentV4> frags = new ArrayList<AngelViewPagerFragmentV4>();
//
//        public AngelPagerAdapterV4(Activity activity, ActionBar actionBar, ViewPager p, FragmentManager fm, ArrayList<? extends AngelViewPagerFragmentV4> fragments) {
//            super(fm);
//            act = activity;
//            bar = actionBar;
//            pager = p;
//            frags.clear();
//            frags.addAll(fragments);
//        }
//
//
//        @Override
//        public Fragment getItem(int i) {
//            return frags.get(i);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return frags.get(position).name;
//        }
//
//        @Override
//        public int getCount() {
//            return frags.size();
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
////            Logger.out("instantiateItem：" + position);
//            return super.instantiateItem(container, position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
////            Logger.out("destroyItem：" + position);
//            super.destroyItem(container, position, object);
//        }
//
//        @Override
//        public void onPageScrolled(int i, float v, int i1) {
//        }
//
//        @Override
//        public void onPageSelected(int i) {
////            Logger.out("onPageSelected：" + i);
//            bar.selectTab(bar.getTabAt(i));
//            bar.setSelectedNavigationItem(i);
//            frags.get(i).onBroughtToFront();
//            for (int index = 0; index < frags.size(); index++) {
//                if (index != i) {
//                    frags.get(index).onBroughtToBack();
//                }
//            }
//        }
//
//        @Override
//        public void onPageScrollStateChanged(int i) {
//        }
//
//        @Override
//        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
////            Logger.out("onTabSelected");
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
//
//
//    }
}
