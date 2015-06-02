package com.ProgrammerYuan.PKUEater;

import studio.archangel.toolkitv2.AngelApplication;
import studio.archangel.toolkitv2.util.Util;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

/**
 * Created by mac on 15/5/28.
 */
public class PKUApplication extends AngelApplication {

	@Override
	public void onCreate(){
		Util.c = this;
		AngelActionBar.default_color = R.color.main_2;
		AngelActionBar.default_arrow_drawable = R.drawable.icon_back_new_arrow;
		prefix = "PKUApplication";

	}

	@Override
	public void loadLocalData() {

	}

	@Override
	public void saveLocalData() {

	}

	@Override
	public void clearLocalData() {

	}
}
