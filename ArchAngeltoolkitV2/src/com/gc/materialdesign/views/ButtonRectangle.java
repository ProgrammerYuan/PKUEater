package com.gc.materialdesign.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gc.materialdesign.utils.Utils;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.util.Util;

public class ButtonRectangle extends Button {
    RelativeLayout rect;
    TextView textButton;
    int paddingTop, paddingBottom, paddingLeft, paddingRight;


    public ButtonRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setDefaultProperties();
    }

    @Override
    protected void setDefaultProperties() {
        super.minWidth = 96;
        super.minHeight = 48;
        super.background = R.drawable.background_button_rectangle;
        super.setDefaultProperties();
    }


    // Set atributtes of XML to View
    protected void setAttributes(AttributeSet attrs) {
        super.setAttributes(attrs);
        //Set background Color
        // Color by resource
        int bacgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
        if (bacgroundColor != -1) {
            setBackgroundColor(getResources().getColor(bacgroundColor));
        } else {
            // Color by hexadecimal
            // Color by hexadecimal
            background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (background != -1)
                setBackgroundColor(background);
        }

        // Set Padding
//		String value = attrs.getAttributeValue(ANDROIDXML,"padding");


        // Set text button
        String text = null;
        int textResource = attrs.getAttributeResourceValue(ANDROIDXML, "text", -1);
        int textSize = attrs.getAttributeResourceValue(ANDROIDXML, "textSize", -1);
        int textColor = attrs.getAttributeResourceValue(ANDROIDXML, "textColor", -1);
        if (textResource != -1) {
            text = getResources().getString(textResource);
        } else {
            text = attrs.getAttributeValue(ANDROIDXML, "text");
        }
//        int padding = attrs.getAttributeIntValue(ANDROIDXML, "padding", -1);
//        int paddingLeft;
//        int paddingTop;
//        int paddingRight;
//        int paddingBottom;
//        if (padding == -1) {
//            paddingLeft = attrs.getAttributeIntValue(ANDROIDXML, "paddingLeft", -1);
//            paddingTop = attrs.getAttributeIntValue(ANDROIDXML, "paddingTop", -1);
//            paddingRight = attrs.getAttributeIntValue(ANDROIDXML, "paddingRight", -1);
//            paddingBottom = attrs.getAttributeIntValue(ANDROIDXML, "paddingBottom", -1);
//            paddingLeft = (paddingLeft == -1) ? 2 : paddingLeft;
//            paddingTop = (paddingTop == -1) ? 2 : paddingTop;
//            paddingRight = (paddingRight == -1) ? 2 : paddingRight;
//            paddingBottom = (paddingBottom == -1) ? 2 : paddingBottom;
//        } else {
//            paddingLeft = paddingTop = paddingRight = paddingBottom = padding;
//        }
        rect = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//        params.setMargins(Utils.dpToPx(2, getResources()), Utils.dpToPx(2, getResources()), Utils.dpToPx(2, getResources()), Utils.dpToPx(2, getResources()));

        rect.setLayoutParams(params);
        if (text != null) {

            textButton = new TextView(getContext());
            textButton.setText(text);
            if (textColor != -1) {
                textButton.setTextColor(getResources().getColor(textColor));
            } else {
                textButton.setTextColor(Color.WHITE);
            }
            textButton.setTextSize(textSize != -1 ? textSize : 16);
//			textButton.setTypeface(null, Typeface.BOLD);
            params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
//            textButton.setPadding(Util.getPX(paddingLeft), Util.getPX(paddingTop), Util.getPX(paddingRight), Util.getPX(paddingBottom));
//            params.setMargins(Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()), Utils.dpToPx(5, getResources()));
            setPaddingFor(textButton, attrs);
            textButton.setLayoutParams(params);
            rect.addView(textButton);
            addView(rect);
        }

//		ripple_speed = attrs.getAttributeFloatValue(MATERIALDESIGNXML,
//				"rippleSpeed", Utils.dpToPx(6, getResources()));
    }

//    Integer height;
//    Integer width;

    //	@Override
//	protected void onDraw(Canvas canvas) {
//		super.onDraw(canvas);
//		if (x != -1) {
//			Rect src = new Rect(0, 0, getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
//			Rect dst = new Rect(Utils.dpToPx(6, getResources()), Utils.dpToPx(6, getResources()), getWidth()-Utils.dpToPx(6, getResources()), getHeight()-Utils.dpToPx(7, getResources()));
//			canvas.drawBitmap(makeCircle(), src, dst, null);
//		}
//		invalidate();
//	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (draw_effect) {

            Rect src = new Rect(0, 0, getWidth(), getHeight());
            Rect dst = new Rect(0, 0, getWidth(), getHeight());
            Bitmap bitmap = makeBitmap();
            canvas.drawBitmap(bitmap, src, dst, null);
//            bitmap.recycle();
//            bitmap = null;

//            Bitmap bitmap = makeBitmap();
//            rect.setBackground(new BitmapDrawable(bitmap));
            invalidate();
        }
    }

    public void setText(String text) {
        textButton.setText(text);
    }

    public void setTextColor(int color) {
        textButton.setTextColor(color);
    }

    @Override
    public TextView getTextView() {
        return textButton;
    }

    public String getText() {
        return textButton.getText().toString();
    }

}
