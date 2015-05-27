package studio.archangel.toolkitv2.widgets;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonFlat;
import net.simonvt.numberpicker.NumberPicker;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.text.DateProvider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2014/10/18.
 */
public class AngelCommonPickerDialog extends DialogFragment {

    NumberPicker picker;

    /**
     * 年份和月份选择器的父控件
     */
//    RelativeLayout rl_container;
    List<Object> data;
    String[] data_display;
    ValueDisplayer displayer;
    ValueFormatter formatter;
    /**
     * 标题
     */
    TextView tv_title;
    /**
     * 用户点击确定后会调用此监听器
     */
    OnValuePickedListener l1;
    OnPostInitListener l2;

    String title;
    int res_divider, default_value;
    /**
     * 主题
     */
    int res_theme;
    /**
     * 月份的字符串转化器
     */

    /**
     * 实例化
     *
     * @param context     上下文
     * @param values
     * @param title       标题
     * @param res_divider 资源ID，NumberPicker的分割线的颜色
     * @param res_theme   NumberPicker的主题
     * @param ovpl        监听器
     * @return 一个新实例
     */
    public static AngelCommonPickerDialog newInstance(Context context, List values, int default_value, String title, int res_divider, int res_theme, OnValuePickedListener ovpl, ValueDisplayer vd) {
        AngelCommonPickerDialog d = (AngelCommonPickerDialog) Fragment.instantiate(context, AngelCommonPickerDialog.class.getName(), null);
        d.setStyle(AngelCommonPickerDialog.STYLE_NORMAL, res_theme);
        d.title = title;
        d.displayer = vd;
        d.res_divider = res_divider;
        d.default_value = default_value;
        d.data = values;
        d.data_display = new String[d.data.size()];

        d.res_theme = res_theme;
        d.setOnDatePickedListener(ovpl);
        return d;
    }

    String getValue() {
        int index = picker.getValue();
        if (data == null || index >= data.size()) {
            return null;
        }
        Object t = data.get(index);
        if (displayer != null) {
            return displayer.getString(t);
        } else {
            return null;
        }
    }

    public class ValueFormatter implements NumberPicker.Formatter {

        @Override
        public String format(int value) {
            if (displayer != null) {
                Object v = data.get(value);
                return displayer.getString(v);
            }
            return null;
        }
    }

    public interface ValueDisplayer {
        public String getString(Object value);
    }

    public void setDefaultValue(int i) {
        picker.setValue(i, true);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View custom = getActivity().getLayoutInflater().inflate(R.layout.dialog_common_picker, null);
        picker = (NumberPicker) custom.findViewById(R.id.dialog_common_picker_picker);
        tv_title = (TextView) custom.findViewById(R.id.dialog_common_picker_title);
        tv_title.setText(title);

        picker.setMinValue(0);
        picker.setMaxValue(data.size() - 1);
        picker.setFocusable(true);
        picker.setFocusableInTouchMode(true);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        picker.setDivider(getResources().getDrawable(res_divider));
        formatter = new ValueFormatter();

        setDefaultValue(default_value);

        try {
            Field f = NumberPicker.class.getDeclaredField("mInputText");
            f.setAccessible(true);
            EditText inputText = (EditText) f.get(picker);
            inputText.setFilters(new InputFilter[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        com.gc.materialdesign.widgets.Dialog dialog = new com.gc.materialdesign.widgets.Dialog(getActivity(), null, "");
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (l2 != null) {
                    l2.onPostInit();

                }
                picker.setFormatter(formatter);
            }
        });
        dialog.setCustomView(custom);

        dialog.show();
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (l1 != null) {
                    l1.onValuePicked(getValue());
                }
            }
        });
        ButtonFlat ab = dialog.getButtonAccept();
        TypedArray a = getActivity().getTheme().obtainStyledAttributes(res_theme, new int[]{R.attr.selectionDivider});
        int attributeResourceId = a.getResourceId(0, 0);
        a.recycle();
        ab.getTextView().setTextColor(getResources().getColor(attributeResourceId));
        ab.setText("确定");
        ButtonFlat cb = dialog.getButtonCancel();
        cb.setText("取消");


        return dialog;
    }

    /**
     * 设置监听器
     *
     * @param lis
     */
    public void setOnDatePickedListener(OnValuePickedListener lis) {
        l1 = lis;
    }

    /**
     * 设置监听器
     *
     * @param lis
     */
    public void setOnPostInitListener(OnPostInitListener lis) {
        l2 = lis;
    }

    /**
     * 日期选择监听器
     */
    public interface OnValuePickedListener {
        /**
         * 当用户按下对话框确定键时，调用此方法
         *
         * @param result 用户选择的日期
         */
        public void onValuePicked(String result);
    }

    /**
     * 初始化结束监听器
     */
    public interface OnPostInitListener {
        /**
         * 初始化完毕后，调用此方法
         */
        public void onPostInit();
    }
}
