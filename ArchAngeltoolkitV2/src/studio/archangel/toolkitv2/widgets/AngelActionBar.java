/**
 *
 */
package studio.archangel.toolkitv2.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gc.materialdesign.views.*;
import org.w3c.dom.Text;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.interfaces.Constructable;
import studio.archangel.toolkitv2.util.Logger;

import java.util.ArrayList;

/**
 * @author Administrator
 */
public class AngelActionBar extends RelativeLayout {
    TextView tv_title, tv_left;
    ButtonFlat tv_right;
    LinearLayout ll_buttons;
    LinearLayout ll_left;
    OnClickListener listener_title, listener_left, listener_right;
    ImageView iv_arrow, iv_right;
    RelativeLayout rl_right;
    ImageView shadow;
    int color;
    public static int default_color = -1;
    public static int default_arrow_drawable = R.drawable.icon_back_new_arrow;

    public AngelActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * @param context
     */
    public AngelActionBar(Context context) {
        super(context);
        init(context);
    }

    void init(Context context) {
        if (default_color == -1) {
            default_color = context.getResources().getColor(R.color.blue);
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_actionbar, this);
        tv_title = (TextView) findViewById(R.id.widget_actionbar_title);
        tv_left = (TextView) findViewById(R.id.widget_actionbar_left);
        iv_arrow = (ImageView) findViewById(R.id.widget_actionbar_arrow);
        iv_arrow.setImageResource(default_arrow_drawable);
        Logger.out(default_arrow_drawable + "/" + R.drawable.icon_back_new_arrow);
        iv_right = (ImageView) findViewById(R.id.widget_actionbar_right_image_content);
        rl_right = (RelativeLayout) findViewById(R.id.widget_actionbar_right_image);
        tv_right = (ButtonFlat) findViewById(R.id.widget_actionbar_right);
        ll_buttons = (LinearLayout) findViewById(R.id.widget_actionbar_buttons);
        ll_left = (LinearLayout) findViewById(R.id.widget_actionbar_left_container);
        shadow = (ImageView)findViewById(R.id.widget_actionbar_shadow);
        tv_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_title != null) {
                    listener_title.onClick(v);
                }
            }
        });
        ll_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_left != null) {
                    listener_left.onClick(v);
                } else {
                    Context c = getContext();
                    if (c instanceof Activity) {
                        ((Activity) c).onBackPressed();
                    } else if (c instanceof FragmentActivity) {
                        ((FragmentActivity) c).onBackPressed();
                    }
                }
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_right != null) {
                    listener_right.onClick(v);
                }
            }
        });
        rl_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener_right != null) {
                    listener_right.onClick(v);
                }
            }
        });

        color = getResources().getColor(default_color);
        setMainColor(color);
        tv_right.setVisibility(View.GONE);
    }

    public void setTitleListener(OnClickListener listener_title) {
        this.listener_title = listener_title;
    }

    public void setLeftListener(OnClickListener listener_left) {
        this.listener_left = listener_left;
    }

    public void setRightListener(OnClickListener listener_right) {
        this.listener_right = listener_right;
    }

    public ImageView getShadow(){
        return shadow;
    }

    public TextView getTitle(){
        return tv_title;
    }

    public void showShadow(){
        shadow.setVisibility(View.VISIBLE);
    }

    public void setMainColor(int c) {
        color = c;
        tv_title.setTextColor(color);
        tv_left.setTextColor(color);
        tv_right.getTextView().setTextColor(color);
    }

    public void setTitleText(String s) {
        if (s == null) {
            s = "";
        }
        tv_title.setText(s);
    }

    public void setLeftText(String s) {
        if (s == null) {
            s = "";
        }
        tv_left.setText(s);
    }

    public void setRightText(String s) {
        if (s == null) {
            s = "";
        }
        tv_right.setVisibility(View.VISIBLE);
        rl_right.setVisibility(View.GONE);
        tv_right.getTextView().setText(s);
    }

    public void setMultiButtonEnabed(boolean b) {
        ll_buttons.setVisibility(b ? View.VISIBLE : View.GONE);
        tv_right.setVisibility(b ? View.GONE : View.VISIBLE);

    }

    public LinearLayout getLeftButton() {
        return ll_left;
    }

    public ButtonFlat getRightButton() {
        return tv_right;
    }

    public RelativeLayout getRightImageButton() {
        return rl_right;
    }

    public void setRightImage(int res) {
        tv_right.setVisibility(View.GONE);
        rl_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(res);
    }

    public void setLeftImage(int res) {
        tv_left.setVisibility(View.GONE);
        iv_arrow.setImageResource(res);
    }

    public void addMenuItem(String name, OnClickListener l) {
        addMenuItem(name, -1, l);
    }

    public void addMenuItem(int icon_res, OnClickListener l) {
        addMenuItem(null, icon_res, l);
    }

    public void addMenuItem(String name, int icon_res, OnClickListener l) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout button = (RelativeLayout) inflater.inflate(R.layout.widget_actionbar_menu_item, null);
        button.setBackgroundResource(getContext().getResources().getColor(R.color.trans));
        boolean ok = false;
        if (icon_res != -1) {
            ImageView iv = (ImageView) button.findViewById(R.id.widget_actionbar_menu_item_icon);
            iv.setImageResource(icon_res);
            ok = true;
        } else if (name != null) {
            TextView tv = (TextView) button.findViewById(R.id.widget_actionbar_menu_item_text);
            tv.setText(name);
            tv.setTextColor(color);
            ok = true;
        }
        if (ok) {
            setMultiButtonEnabed(true);
            button.setOnClickListener(l);
            ll_buttons.addView(button);
        } else {
            Logger.out("ActionBar addMenuItem not ok.");
        }

    }

    public void clearMenuItems() {
        ll_buttons.removeAllViews();
    }
}
