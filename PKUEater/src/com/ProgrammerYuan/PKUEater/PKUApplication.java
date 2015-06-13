package com.ProgrammerYuan.PKUEater;

import com.ProgrammerYuan.PKUEater.utils.EaterDB;
import com.ProgrammerYuan.PKUEater.utils.Net;
import studio.archangel.toolkitv2.AngelApplication;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.Util;
import studio.archangel.toolkitv2.util.image.ImageProvider;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

/**
 * Created by mac on 15/5/28.
 */
public class PKUApplication extends AngelApplication {

	public static boolean db_inited = false;

	@Override
	public void onCreate(){
		super.onCreate();
		instance = this;
		Util.c = this;
		Logger.setEnable(true);
		AngelActionBar.default_color = R.color.main_2;
		AngelActionBar.default_arrow_drawable = R.drawable.icon_back_new_arrow;
		prefix = "PKUApplication";
		Net.init();
		EaterDB.init(this);
//		cache = getFilesDir();
		ImageProvider.init(this, getDir("image_cache", MODE_PRIVATE).getAbsolutePath(), R.color.trans, R.color.trans, true);

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
