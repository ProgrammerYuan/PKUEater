package com.ProgrammerYuan.PKUEater;

import studio.archangel.toolkitv2.AngelApplication;
import studio.archangel.toolkitv2.util.Util;

/**
 * Created by mac on 15/5/28.
 */
public class PKUApplication extends AngelApplication {

	@Override
	public void onCreate(){
		Util.c = this;
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
