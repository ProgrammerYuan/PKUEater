package studio.archangel.toolkitv2.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gc.materialdesign.views.CheckBox;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Util;

/**
 * Created by Michael on 2014/11/28.
 */
public class MaterialCheckBox extends LinearLayout {
    CheckBox cb;
    TextView tv;


    onCheckChangedListener listener;
//    public MaterialCheckBox(Context context) {
//        super(context);
//    }

    public MaterialCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    void init(AttributeSet attrs) {
        String text = attrs.getAttributeValue(Util.namespace_android, "text");
        boolean checked = attrs.getAttributeBooleanValue(Util.namespace_android, "checked", false);
        int size = attrs.getAttributeIntValue(Util.namespace_android, "textSize", 16);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_checkbox, this);
        cb = (CheckBox) findViewById(R.id.widget_checkbox_box);
        cb.setChecked(checked);
        tv = (TextView) findViewById(R.id.widget_checkbox_text);
        tv.setText(text);
        tv.setTextColor(checked ? getResources().getColor(R.color.black) : getResources().getColor(R.color.grey));
        tv.setTextSize(size);
        cb.setOncheckListener(new CheckBox.OnCheckListener() {
            @Override
            public void onCheck(boolean check) {
                tv.setTextColor(cb.isCheck() ? getResources().getColor(R.color.black) : getResources().getColor(R.color.grey));
                if(listener!=null){
                    listener.onCheckChanged(check);
                }
            }
        });
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cb.toggle();
            }
        });
    }

    public boolean isChecked() {
        return cb.isCheck();
    }

    public void setChecked(boolean b) {
        cb.setChecked(b);
    }

    public void toggle() {
        cb.toggle();
    }

    public void setText(String s) {
        tv.setText(s);
    }

    public void setOnCheckChangedListener(onCheckChangedListener listener) {
        this.listener = listener;
    }

    public interface onCheckChangedListener {
        public void onCheckChanged(boolean new_status);
    }
}
