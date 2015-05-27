package com.jit.video;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import studio.archangel.toolkitv2.AngelActivity;

@ContentView(R.layout.act_video)
public class VideoActivity extends AngelActivity {

    // 自定义VideoView
    @ViewInject(R.id.video_main)
    private FullScreenVideoView video;

    // 头部View
    @ViewInject(R.id.video_header)
    private View header;

    // 底部View
    @ViewInject(R.id.video_footer)
    private View footer;
    // 视频播放拖动条
    @ViewInject(R.id.video_seekbar)
    private SeekBar seekbar;
    @ViewInject(R.id.video_play)
    private ImageView iv_play;
    @ViewInject(R.id.video_played)
    private TextView tv_played;
    @ViewInject(R.id.video_duration)
    private TextView tv_duration;

    // 音频管理器

    private AudioManager am;

    // 屏幕宽高
    private float width;
    private float height;

    // 视频播放时间
    private int playTime;

    private String url = null, title = "视频播放";
    // 自动隐藏顶部和底部View的时间
    private static final int auto_hide_offset = 3000;

    // 声音调节Toast
    private VolumnController vc;

    // 原始屏幕亮度
    private int orginalLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, new int[]{ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE});
//		setContentView(R.layout.activity_main);
        vc = new VolumnController(this);
//        video = (FullScreenVideoView) findViewById(R.id.videoview);
//        tv_played = (TextView) findViewById(R.id.play_time);
//        tv_duration = (TextView) findViewById(R.id.total_time);
//        iv_play = (ImageView) findViewById(R.id.play_btn);
//        seekbar = (SeekBar) findViewById(R.id.seekbar);
//        header = findViewById(R.id.top_layout);
//        footer = findViewById(R.id.bottom_layout);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        width = DensityUtil.getWidthInPx(this);
        height = DensityUtil.getHeightInPx(this);
        threshold = DensityUtil.dip2px(this, 18);

        orginalLight = LightnessController.getLightness(this);

        iv_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video.isPlaying()) {
                    video.pause();
                    iv_play.setImageResource(R.drawable.video_btn_down);
                } else {
                    video.start();
                    iv_play.setImageResource(R.drawable.video_btn_on);
                }
            }
        });
        seekbar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        title = b.getString("title", title);
        if (url != null) {
            playVideo();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height = DensityUtil.getWidthInPx(this);
            width = DensityUtil.getHeightInPx(this);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            width = DensityUtil.getWidthInPx(this);
            height = DensityUtil.getHeightInPx(this);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LightnessController.setLightness(this, orginalLight);
    }

    private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mHandler.postDelayed(hideRunnable, auto_hide_offset);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mHandler.removeCallbacks(hideRunnable);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if (fromUser) {
                int time = progress * video.getDuration() / 100;
                video.seekTo(time);
            }
        }
    };

    private void backward(float delataX) {
        int current = video.getCurrentPosition();
        int backwardTime = (int) (delataX / width * video.getDuration());
        int currentTime = current - backwardTime;
        video.seekTo(currentTime);
        seekbar.setProgress(currentTime * 100 / video.getDuration());
        tv_played.setText(formatTime(currentTime));
    }

    private void forward(float delataX) {
        int current = video.getCurrentPosition();
        int forwardTime = (int) (delataX / width * video.getDuration());
        int currentTime = current + forwardTime;
        video.seekTo(currentTime);
        seekbar.setProgress(currentTime * 100 / video.getDuration());
        tv_played.setText(formatTime(currentTime));
    }

    private void volumeDown(float delatY) {
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int down = (int) (delatY / height * max * 3);
        int volume = Math.max(current - down, 0);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int transformatVolume = volume * 100 / max;
        vc.show(transformatVolume);
    }

    private void volumeUp(float delatY) {
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int up = (int) ((delatY / height) * max * 3);
        int volume = Math.min(current + up, max);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int transformatVolume = volume * 100 / max;
        vc.show(transformatVolume);
    }

    private void lightDown(float delatY) {
        int down = (int) (delatY / height * 255 * 3);
        int transformatLight = LightnessController.getLightness(this) - down;
        LightnessController.setLightness(this, transformatLight);
    }

    private void lightUp(float delatY) {
        int up = (int) (delatY / height * 255 * 3);
        int transformatLight = LightnessController.getLightness(this) + up;
        LightnessController.setLightness(this, transformatLight);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
        mHandler.removeCallbacksAndMessages(null);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (video.getCurrentPosition() > 0) {
                        tv_played.setText(formatTime(video.getCurrentPosition()));
                        int progress = video.getCurrentPosition() * 100 / video.getDuration();
                        seekbar.setProgress(progress);
                        if (video.getCurrentPosition() > video.getDuration() - 100) {
                            tv_played.setText("00:00");
                            seekbar.setProgress(0);
                        }
                        seekbar.setSecondaryProgress(video.getBufferPercentage());
                    } else {
                        tv_played.setText("00:00");
                        seekbar.setProgress(0);
                    }

                    break;
                case 2:
                    showOrHide();
                    break;

                default:
                    break;
            }
        }
    };

    private void playVideo() {
        video.setVideoPath(url);
        video.requestFocus();
        video.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                video.setVideoWidth(mp.getVideoWidth());
                video.setVideoHeight(mp.getVideoHeight());

                video.start();
                if (playTime != 0) {
                    video.seekTo(playTime);
                }

                mHandler.removeCallbacks(hideRunnable);
                mHandler.postDelayed(hideRunnable, auto_hide_offset);
                tv_duration.setText(formatTime(video.getDuration()));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(1);
                    }
                }, 0, 1000);
            }
        });
        video.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                iv_play.setImageResource(R.drawable.video_btn_down);
                tv_played.setText("00:00");
                seekbar.setProgress(0);
            }
        });
        video.setOnTouchListener(mTouchListener);
    }

    private Runnable hideRunnable = new Runnable() {

        @Override
        public void run() {
            showOrHide();
        }
    };

    @SuppressLint("SimpleDateFormat")
    private String formatTime(long time) {
        DateFormat formatter = new SimpleDateFormat("mm:ss");
        return formatter.format(new Date(time));
    }

    private float mLastMotionX;
    private float mLastMotionY;
    private int startX;
    private int startY;
    private int threshold;
    private boolean isClick = true;

    private OnTouchListener mTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final float x = event.getX();
            final float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLastMotionX = x;
                    mLastMotionY = y;
                    startX = (int) x;
                    startY = (int) y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mLastMotionX;
                    float deltaY = y - mLastMotionY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    // 声音调节标识
                    boolean isAdjustAudio = false;
                    if (absDeltaX > threshold && absDeltaY > threshold) {
                        if (absDeltaX < absDeltaY) {
                            isAdjustAudio = true;
                        } else {
                            isAdjustAudio = false;
                        }
                    } else if (absDeltaX < threshold && absDeltaY > threshold) {
                        isAdjustAudio = true;
                    } else if (absDeltaX > threshold && absDeltaY < threshold) {
                        isAdjustAudio = false;
                    } else {
                        return true;
                    }
                    if (isAdjustAudio) {
                        if (x < width / 2) {
                            if (deltaY > 0) {
                                lightDown(absDeltaY);
                            } else if (deltaY < 0) {
                                lightUp(absDeltaY);
                            }
                        } else {
                            if (deltaY > 0) {
                                volumeDown(absDeltaY);
                            } else if (deltaY < 0) {
                                volumeUp(absDeltaY);
                            }
                        }

                    } else {
                        if (deltaX > 0) {
                            forward(absDeltaX);
                        } else if (deltaX < 0) {
                            backward(absDeltaX);
                        }
                    }
                    mLastMotionX = x;
                    mLastMotionY = y;
                    break;
                case MotionEvent.ACTION_UP:
                    if (Math.abs(x - startX) > threshold
                            || Math.abs(y - startY) > threshold) {
                        isClick = false;
                    }
                    mLastMotionX = 0;
                    mLastMotionY = 0;
                    startX = (int) 0;
                    if (isClick) {
                        showOrHide();
                    }
                    isClick = true;
                    break;

                default:
                    break;
            }
            return true;
        }

    };
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.video_play:
//                if (video.isPlaying()) {
//                    video.pause();
//                    iv_play.setImageResource(R.drawable.video_btn_down);
//                } else {
//                    video.start();
//                    iv_play.setImageResource(R.drawable.video_btn_on);
//                }
//                break;
//            default:
//                break;
//        }
//    }

    private void showOrHide() {
        if (header.getVisibility() == View.VISIBLE) {
            header.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this,
                    R.anim.option_leave_from_top);
            animation.setAnimationListener(new AnimationImp() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    header.setVisibility(View.GONE);
                }
            });
            header.startAnimation(animation);

            footer.clearAnimation();
            Animation animation1 = AnimationUtils.loadAnimation(this,
                    R.anim.option_leave_from_bottom);
            animation1.setAnimationListener(new AnimationImp() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    footer.setVisibility(View.GONE);
                }
            });
            footer.startAnimation(animation1);
        } else {
            header.setVisibility(View.VISIBLE);
            header.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this,
                    R.anim.option_entry_from_top);
            header.startAnimation(animation);

            footer.setVisibility(View.VISIBLE);
            footer.clearAnimation();
            Animation animation1 = AnimationUtils.loadAnimation(this,
                    R.anim.option_entry_from_bottom);
            footer.startAnimation(animation1);
            mHandler.removeCallbacks(hideRunnable);
            mHandler.postDelayed(hideRunnable, auto_hide_offset);
        }
    }

    private class AnimationImp implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

}
