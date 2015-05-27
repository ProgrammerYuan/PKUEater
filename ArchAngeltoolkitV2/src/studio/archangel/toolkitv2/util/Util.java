/**
 *
 */
package studio.archangel.toolkitv2.util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.PowerManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import studio.archangel.toolkitv2.AngelActivity;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.widgets.AngelActionBar;

import java.io.File;
import java.lang.reflect.Field;

/**
 * 一些实用方法
 *
 * @author Michael
 */
public class Util {
    public static Context c;
    public final static String namespace_android = "http://schemas.android.com/apk/res/android";
    private static PowerManager.WakeLock wakeLock = null;

    /**
     * 隐藏软键盘
     *
     * @param v 正在输入内容（调用软键盘）的控件
     * @param c 上下文
     */
    public static void hideInputBoard(View v, Context c) {

        if (c instanceof Activity) {
            ((Activity) c).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        } else {
            InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }


    public static int createDarkerColor(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        if (r + g + b == 0) {
            return Color.argb(Math.min(a + 15, 255), 0, 0, 0);
        }
//        a = (int) (a * 7 / 8.0);
        r = (int) (r * 7 / 8.0);
        g = (int) (g * 7 / 8.0);
        b = (int) (b * 7 / 8.0);
        return Color.rgb(r, g, b);
    }

    /**
     * 计算两点之间距离
     *
     * @param a 点A
     * @param b 点B
     * @return AB间距离
     */
    public static float getDistance(Point a, Point b) {
        return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    /**
     * 隐藏软键盘
     *
     * @param a 正在显示软键盘的界面
     *          有可能没效果！
     */
    public static void hideInputBoard(Activity a) {
        InputMethodManager imm = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        View v = a.getCurrentFocus();
        if (v != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param target 需要输入内容的控件
     * @param a      正在显示软键盘的界面
     */
    public static void showInputBoard(View target, Activity a) {
        InputMethodManager keyboard = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(target, InputMethodManager.SHOW_FORCED);
    }

    public static void setupActionBar(AngelActivity act, String title) {
        setupActionBar(act, title, null);
    }

    public static void setupActionBar(AngelActivity act, String title, String left) {
        ActionBar bar = act.getActionBar();
        if (bar == null) {
            return;
        }
        bar.setIcon(c.getResources().getDrawable(R.color.trans));
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
        bar.setDisplayShowHomeEnabled(false);
        bar.setTitle("");
        AngelActionBar aab = act.getAngelActionBar();
        if (aab == null) {
            aab = new AngelActionBar(act);
            act.setAngelActionBar(aab);
        }
        bar.setCustomView(aab);
        aab.setTitleText(title);
        aab.setLeftText(left);
    }

    /**
     * 将sp转换为px
     *
     * @param spValue sp值
     * @return 相应的px
     */
    public static int getPXfromSP(float spValue) {
        float fontScale = c.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);

    }

    /**
     * 将dp转换为px
     *
     * @param dipValue dp值
     * @return 相应的px
     */
    public static int getPX(float dipValue) {
        final float scale = c.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px转换为dip
     *
     * @param pxValue px值
     * @return 相应的dp
     */
    public static int getDP(float pxValue) {
        final float scale = c.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getReversedColor(int color) {
        int a = Color.alpha(color);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(a, 255 - r, 255 - g, 255 - b);
    }

    /**
     * 为指定的EditText设置输入限制，并把提示信息显示到指定的TextView
     *
     * @param et  要设置输入限制的EditText
     * @param tv  用来显示提示信息的TextView
     * @param max 最大输入长度
     */
    public static void setInputLimit(final EditText et, final TextView tv, final int max) {
        int l = et.getText().toString().length();
        if (l <= max) {
            tv.setText("还可以输入" + (max - l) + "字");
            tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
        } else {
            tv.setText("已超出" + (l - max) + "字");
            tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int l = s.length();
                if (l <= max) {
                    tv.setText("还可以输入" + (max - l) + "字");
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
                } else {
                    tv.setText("已超出" + (l - max) + "字");
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
                }
            }
        });
    }

    public static void setRemainInputLimit(final EditText et, final TextView tv, final int max,final String template) {
        int l = et.getText().toString().length();
        tv.setText(template.replace("count",String.valueOf(max - l)));
        if (l <= max) {
            tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
        } else {
            tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int l = s.length();
                tv.setText(template.replace("count",String.valueOf(max - l)));
                if (l <= max) {
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
                } else {
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
                }
            }
        });
    }

    public static void setInputLimit(final EditText et, final TextView tv, final int max,final String template) {
        int l = et.getText().toString().length();
        tv.setText(template.replace("count",String.valueOf(l)));
        if (l <= max) {
            tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
        } else {
            tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int l = s.length();
                tv.setText(template.replace("count",String.valueOf(l)));
                if (l <= max) {
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
                } else {
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
                }
            }
        });
    }

    public static void setInputLimit(final EditText et, final TextView tv, final int max,final String templateEnable,final String templateDisable) {
        int l = et.getText().toString().length();
        if (l <= max) {
            tv.setText(templateEnable.replace("count",String.valueOf(max - l)));
            tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
        } else {
            tv.setText(templateEnable.replace("count",String.valueOf(l-max)));
            tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
        }
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int l = s.length();
                if (l <= max) {
                    tv.setText("还可以输入" + (max - l) + "字");
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.black));
                } else {
                    tv.setText("已超出" + (l - max) + "字");
                    tv.setTextColor(et.getContext().getResources().getColor(R.color.red));
                }
            }
        });
    }

    /**
     * 获得屏幕锁
     *
     * @param c 上下文
     * @return 屏幕锁
     */
    static PowerManager.WakeLock getWakeLock(Context c) {
        if (wakeLock != null) {
            return wakeLock;
        }
        try {
            PowerManager powerManager = (PowerManager) c.getSystemService(Context.POWER_SERVICE);
            int field = 0x00000020;

            // Yeah, this is hidden field.
            field = PowerManager.class.getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);

            wakeLock = powerManager.newWakeLock(field, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wakeLock;
    }

    /**
     * 激活近物传感器。当被遮挡时关闭屏幕
     *
     * @param c 上下文
     */
    public static void enableProximitySensor(Context c) {
        PowerManager.WakeLock wakeLock = getWakeLock(c);
        if (!wakeLock.isHeld()) {
            wakeLock.acquire();
        }
    }

    /**
     * 关闭近物传感器
     *
     * @param c 上下文
     */
    public static void disableProximitySensor(Context c) {
        PowerManager.WakeLock wakeLock = getWakeLock(c);
        if (wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    /**
     * 强制显示Actionbar的Overflow图标（三个点）
     *
     * @param a
     */
    public static void setForceOverFlowIcon(Application a) {
        try {
            ViewConfiguration config = ViewConfiguration.get(a);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    /**
     * 获取用户友好的文件大小
     *
     * @param size 文件大小，单位：字节
     * @return
     */
    public static String getFileSize(float size) {
        try {
            if (size < 1024) {
                return String.format("%dbytes", (int) size);
            } else {
                size /= 1024;
                if (size < 1024) {
                    return String.format("%.1fKB", size);
                } else {
                    size /= 1024;
                    if (size < 1024) {
                        return String.format("%.1fMB", size);
                    } else {
                        size /= 1024;
                        return String.format("%.1fGB", size);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    public static String getFileSize(String path) {
        return getFileSize(new File(path));
    }

    public static String getFileSize(File f) {
        float size = f.length();
        return getFileSize(size);

    }
}
