package com.ProgrammerYuan.PKUEater.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import studio.archangel.toolkitv2.dialogs.LoadingDialog;

public class B2Fragment extends Fragment {
    protected View cache = null;
    LoadingDialog dialog;
    private String realname = null;

    /**
     * 初始化方法，可以使用缓存，使得此Fragment在ViewPager中不会因为划出屏幕而重新加载
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @param layout
     * @return
     */
    public boolean onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int layout) {
        boolean use_cache = (cache != null);
        if (!use_cache) {
            cache = inflater.inflate(layout, null);
        }

        ViewGroup parent = (ViewGroup) cache.getParent();
        if (parent != null) {
            parent.removeView(cache);
        }
        return use_cache;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public String getRealName() {
        return realname;
    }

    public void setRealName(String name) {
        realname = name;
    }

    @Override
    public void onDestroyView() {
        if (dialog != null) {
            dialog.forceDismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
