package com.imitation.Zxing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class IndexActivity extends Activity
{
    private Button mCaptureButton, mExzitButton;
    private TextView mResultTextView;
    private ImageView mResultImageView;

    public static String RESULT_MESSAGE = null;
    public static Bitmap RESULT_BITMAP = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        init();
    }

    private void init()
    {
        mCaptureButton = (Button) findViewById(R.id.bt_capture);
        mExzitButton = (Button) findViewById(R.id.bt_exit);
        mResultTextView = (TextView) findViewById(R.id.tv_result);
        mResultImageView = (ImageView) findViewById(R.id.iv_result);

        mCaptureButton.setOnClickListener(listener);
        mExzitButton.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            int id = v.getId();
			if (id == R.id.bt_capture) {
				Intent intent = new Intent(IndexActivity.this,
				        CaptureActivity.class);
				IndexActivity.this.startActivity(intent);
			} else if (id == R.id.bt_exit) {
				IndexActivity.this.finish();
			} else {
			}
        }
    };

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();

        if (RESULT_MESSAGE != null && RESULT_BITMAP != null)
        {
            mResultTextView.setText(RESULT_MESSAGE);
            mResultImageView.setImageBitmap(RESULT_BITMAP);

            RESULT_MESSAGE = null;
            RESULT_BITMAP = null;
        }
    }
}
