package com.ProgrammerYuan.PKUEater.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ProgrammerYuan.PKUEater.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;

/**
 * Created by ProgrammerYuan
 */
public class GifWithTextView extends RelativeLayout {

    Context c;
    Resources res;
    int res_id;
    boolean recycled = true;
    GifDrawable resource;
    GifImageView giv;
    TextView txt;

    public GifWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context,AttributeSet attr){
        c = context;
        res = c.getResources();
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.giv_layout,this);
        giv = (GifImageView)findViewById(R.id.gtv_g);
        txt = (TextView)findViewById(R.id.gtv_tv);
        TypedArray a = context.obtainStyledAttributes(attr, R.styleable.GifWithTextView);
        if(a.hasValue(R.styleable.GifWithTextView_src)){
            res_id = a.getResourceId(R.styleable.GifWithTextView_src,-1);
            setResource(res_id);
        }
        if(a.hasValue(R.styleable.GifWithTextView_text)){
            setText(a.getText(R.styleable.GifWithTextView_text).toString());
        }
    }

    public void setData(int res_id,String text){
        setResource(res_id);
        setText(text);
    }

    public void setResource(int res_id){
        if(res_id == this.res_id && !recycled) return;
        if(res_id != -1 && "drawable".equals(res.getResourceTypeName(res_id))){
            try {
                this.res_id = res_id;
                if(!recycled) resource.recycle();
                resource = new GifDrawable(res,res_id);
                giv.setImageDrawable(resource);
                recycled = false;
//                resource.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setText(String text){
        txt.setText(text);
    }

    public int getResId(){
        return res_id;
    }

    public void start(){
        if(resource != null){
            if(!recycled) resource.start();
            else setResource(res_id);
        }
    }

    public void stop(){
        if(resource != null || !recycled) resource.stop();
    }

    public void recycle(){
        if(resource != null || !recycled){
            resource.recycle();
            recycled = true;
        }
    }
}
