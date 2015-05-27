package studio.archangel.toolkitv2.widgets;


import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import studio.archangel.toolkitv2.R;

/**
 * Created by Michael on 2014/12/30.
 */
public class NotiTab extends RelativeLayout {


    int color_bg, color_indicator, color_text;
    TextView text;
    com.gc.materialdesign.views.RelativeLayout back;
    ImageView indicator, noti;
    boolean noti_enable = false;
    boolean indicator_enable = true;
    NotiTabGroup owner;

    public NotiTab(Context context) {
        super(context);
        init(context);
    }

    public NotiTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.actionbar_tab_with_noti2, this);
        back = (com.gc.materialdesign.views.RelativeLayout) findViewById(R.id.actionbar_notitab_bg);
        text = (TextView) findViewById(R.id.actionbar_notitab_text);
        indicator = (ImageView) findViewById(R.id.actionbar_notitab_indicator);
        noti = (ImageView) findViewById(R.id.actionbar_notitab_noti);
    }

    public void setColors(int color_bg, int color_indicator, int color_text) {
        this.color_bg = color_bg;
        this.color_indicator = color_indicator;
        this.color_text = color_text;

        back.makePressColor(getResources().getColor(this.color_bg));
        indicator.setImageResource(color_indicator);
        try {
            text.setTextColor(getResources().getColorStateList(color_text));
        } catch (Exception e) {
            text.setTextColor(getResources().getColor(color_text));
            e.printStackTrace();
        }
    }

    public void setText(String title) {
        text.setText(title);
    }

    public void setTextSize(float size){
        text.setTextSize(size);
    }
    public void setNotiEnabled(boolean enabled) {
        noti_enable = enabled;
    }

    public void setIndicatorEnabled(boolean enabled) {
        indicator_enable = enabled;
    }

    public void setOwner(NotiTabGroup group) {
        owner = group;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        back.setOnClickListener(l);

    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        text.setSelected(selected);
        indicator.setVisibility(selected ? View.VISIBLE : View.GONE);
    }
}
