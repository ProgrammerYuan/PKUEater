package studio.archangel.toolkitv2;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import studio.archangel.toolkitv2.util.Logger;

/**
 * Created by Michael on 2014/9/24.
 */
public abstract class AngelApplication extends Application {
    protected static String prefix = "";
    public static AngelApplication instance;
    public static int screen_width;
    public static int screen_height;
    public static int status_bar_height;
    public static String device_des;
    public static int app_version_code;
    public static String app_version_name;

    @Override
    public void onCreate() {
        super.onCreate();
        getScreenSize();
        instance = this;
        device_des = android.os.Build.MODEL + "(" + Build.VERSION.RELEASE + ")";

        try {
            app_version_name = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            app_version_name = "未知版本";
            e.printStackTrace();
        }
        try {
            app_version_code = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            app_version_code = -1;
            e.printStackTrace();
        }

    }

    public SharedPreferences getPreference() {
        return getSharedPreferences(getPrefix(), MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    public SharedPreferences.Editor getEditor() {
        SharedPreferences pref = getPreference();
        SharedPreferences.Editor editor = pref.edit();
        return editor;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String p) {
        prefix = p;
    }

    public abstract void loadLocalData();

    public abstract void saveLocalData();

    public abstract void clearLocalData();

    void getScreenSize() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screen_width = size.x;
        screen_height = size.y;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            status_bar_height = getResources().getDimensionPixelSize(resourceId);
        }
        Logger.out("screen size:" + screen_width + "×" + screen_height);
    }
}
