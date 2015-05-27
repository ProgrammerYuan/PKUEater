package studio.archangel.toolkitv2.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gc.materialdesign.views.CustomLinearLayout;
import com.gc.materialdesign.views.LinearLayout;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.ui.SelectorProvider;
import studio.archangel.toolkitv2.util.ui.ShapeProvider;

public class OptionItem extends LinearLayout {
    Context c;
    ImageView iv_icon, iv_arrow;
    TextView tv_title, tv_content;
    static int res_default_arrow = R.drawable.frag_arrow_right_black;

    public OptionItem(Context context) {
        super(context, null);
        init(context);
    }

    public OptionItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        c = context;
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_option_item, this);
        iv_icon = (ImageView) findViewById(R.id.widget_option_item_icon);
        iv_arrow = (ImageView) findViewById(R.id.widget_option_item_arrow);
        tv_title = (TextView) findViewById(R.id.widget_option_item_title);
        tv_content = (TextView) findViewById(R.id.widget_option_item_content);
        iv_arrow.setImageResource(res_default_arrow);
        setBackgroundColor(getResources().getColor(R.color.white));
    }

    public static void setDefaultArrowResource(int def) {
        res_default_arrow = def;
    }

    public TextView getContentView() {
        return tv_content;
    }

    public String getContent() {
        return tv_title.getText().toString();
    }


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setIcon(int res_icon, int res_color, int round_corner_radius, int padding) {
        ShapeDrawable back = ShapeProvider.getRoundRect(round_corner_radius, getResources().getColor(res_color));
        SelectorProvider.setBackgroundFor(iv_icon, back);
//
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            iv_icon.setBackgroundDrawable(back);
//        } else {
//            iv_icon.setBackground(back);
//        }
        iv_icon.setPadding(padding, padding, padding, padding);
        iv_icon.setImageResource(res_icon);
    }

    public void setIcon(int res_icon, int res_color, int padding) {
        setIcon(res_icon, res_color, 2, padding);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    public void setArrowResource(int res_arrow) {
        iv_arrow.setImageResource(res_arrow);
    }

    public void setContent(String content) {
        tv_content.setText(content);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
//        listener = l;
        super.setOnClickListener(l);
//        Logger.out("OptionItem#" + this.getTag() + " setOnClickListener");
    }

    public void setArrowVisible(boolean visible) {
        iv_arrow.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setIconVisible(boolean visible) {
        iv_icon.setVisibility(visible ? VISIBLE : GONE);
    }
}
