package video;

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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.gc.materialdesign.views.Slider;
import studio.archangel.toolkitv2.AngelActivity;
import studio.archangel.toolkitv2.R;
import studio.archangel.toolkitv2.dialogs.LoadingDialog;
import studio.archangel.toolkitv2.util.Logger;
import studio.archangel.toolkitv2.util.text.Notifier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//@ContentView(R.layout.act_video)
public class MediaActivity extends AngelActivity {

    // 自定义VideoView
//    @ViewInject(R.id.video_main)
    private FullScreenVideoView video;

    // 头部View
//    @ViewInject(R.id.video_header)
    private View header;

    // 底部View
//    @ViewInject(R.id.video_footer)
    private View footer;
    // 视频播放拖动条
//    @ViewInject(R.id.video_seekbar)
    Slider seekbar;
    //    private SeekBar seekbar;
    //    @ViewInject(R.id.video_play)
    private ImageView iv_play;
    //    @ViewInject(R.id.video_played)
    private TextView tv_played;
    //    @ViewInject(R.id.video_duration)
    private TextView tv_duration;
    private TextView tv_title;

    // 音频管理器
    MediaPlayer player;
    private AudioManager am;

    // 屏幕宽高
    private float width;
    private float height;

    // 视频播放时间
    private int played_time;

    private String url = null, title = "视频播放";
    // 自动隐藏顶部和底部View的时间
    private static final int auto_hide_offset = 3000;

    // 声音调节Toast
    private MediaController vc;

    // 原始屏幕亮度
    private int original_lightness;

    @Override
    protected boolean shouldOverrideOrientation() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, new int[]{Window.FEATURE_NO_TITLE}, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.act_video);
        vc = new MediaController(this);
        video = (FullScreenVideoView) findViewById(R.id.video_main);
        tv_played = (TextView) findViewById(R.id.video_played);
        tv_title = (TextView) findViewById(R.id.video_title);
        tv_duration = (TextView) findViewById(R.id.video_duration);
        iv_play = (ImageView) findViewById(R.id.video_play);
//        seekbar = (SeekBar) findViewById(R.id.video_seekbar);
        seekbar = (Slider) findViewById(R.id.video_seekbar);
        header = findViewById(R.id.video_header);
        footer = findViewById(R.id.video_footer);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        width = DensityUtil.getWidthInPx(this);
        height = DensityUtil.getHeightInPx(this);
        threshold = DensityUtil.dip2px(this, 18);

        original_lightness = LightnessController.getLightness(this);

        iv_play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                vc.setIsVolume(false);
                vc.setIsBrightness(false);
                vc.setShowProgressBar(false);

                if (video.isPlaying()) {
                    vc.setIcon(R.drawable.icon_media_pause);
                    video.pause();
                    iv_play.setImageResource(R.drawable.icon_media_play);
                } else {
                    vc.setIcon(R.drawable.icon_media_play);
                    video.start();
                    iv_play.setImageResource(R.drawable.icon_media_pause);
                }
                vc.show();
            }
        });

//        seekbar.setOnSeekBarChangeListener(mSeekBarChangeListener);
        seekbar.setShowBuffer(true);
        seekbar.setBuffer(getResources().getColor(R.color.grey));
        seekbar.setMax(100);
        seekbar.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
//                int currentTime = current + forwardTime;
//                video.seekTo(currentTime);
////        seekbar.setProgress(currentTime * 100 / video.getDuration());
//                seekbar.setValue(currentTime * 100 / video.getDuration());
                int time = (int) (value * video.getDuration() / 100.0);
                video.seekTo(time);
//                Logger.out("onValueChanged value:" + value + " time:" + time);
            }
        });
        seekbar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mHandler.removeCallbacks(hideRunnable);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mHandler.postDelayed(hideRunnable, auto_hide_offset);
                }
                return false;
            }
        });
        Bundle b = getIntent().getExtras();
        url = b.getString("url");
        title = b.getString("title", title);
        tv_title.setText(title);
        int volume_color = b.getInt("volume_color", -1);
        int slider_color = b.getInt("slider_color", -1);
        if (slider_color != -1) {
            seekbar.setBackgroundColor(getResources().getColor(slider_color));
        }
        vc.setViewColor(volume_color);
        if (url != null) {
            playVideo();
            iv_play.setImageResource(R.drawable.icon_media_pause);
        }
        dialog = new LoadingDialog(this, slider_color, true);

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
        LightnessController.setLightness(this, original_lightness);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

//    private OnSeekBarChangeListener mSeekBarChangeListener = new OnSeekBarChangeListener() {
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            mHandler.postDelayed(hideRunnable, auto_hide_offset);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            mHandler.removeCallbacks(hideRunnable);
//        }
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress,
//                                      boolean fromUser) {
//            if (fromUser) {
//                int time = progress * video.getDuration() / 100;
//                video.seekTo(time);
//            }
//        }
//    };

    private void backward(float delta) {
        if (video.getDuration() == 0) {
            return;
        }
        int current = video.getCurrentPosition();
        int backwardTime = (int) (delta / width * video.getDuration());
        int currentTime = current - backwardTime;
        video.seekTo(currentTime);
//        seekbar.setProgress(currentTime * 100 / video.getDuration());
        seekbar.setValue(currentTime * 100 / video.getDuration());
        tv_played.setText(formatTime(currentTime));
    }

    private void forward(float delta) {
        if (video.getDuration() == 0) {
            return;
        }
        int current = video.getCurrentPosition();
        int forwardTime = (int) (delta / width * video.getDuration());
        int currentTime = current + forwardTime;
        video.seekTo(currentTime);
//        seekbar.setProgress(currentTime * 100 / video.getDuration());
        seekbar.setValue(currentTime * 100 / video.getDuration());
        tv_played.setText(formatTime(currentTime));
    }

    private void volumeDown(float delta) {
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int down = (int) (delta / height * max * 3);
        int volume = Math.max(current - down, 0);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int trans_volume = volume * 100 / max;
        vc.show(trans_volume);
    }

    private void volumeUp(float delta) {
        int max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        int up = (int) ((delta / height) * max * 3);
        int volume = Math.min(current + up, max);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        int trans_volume = volume * 100 / max;
        vc.show(trans_volume);
    }

    private void lightDown(float delta) {
        int down = (int) (delta / height * 255);
        int trans_light = LightnessController.getLightness(this) - down;
        LightnessController.setLightness(this, trans_light);
//        Logger.out("lightDown:" + trans_light);
        if (trans_light < 1) {
            trans_light = 1;
        } else if (trans_light > 255) {
            trans_light = 255;
        }

        vc.show((float) (trans_light * 100 / 255.0));
    }

    private void lightUp(float delta) {
        int up = (int) (delta / height * 255);
        int trans_light = LightnessController.getLightness(this) + up;
        LightnessController.setLightness(this, trans_light);
//        Logger.out("lightUp:" + trans_light);
        if (trans_light < 1) {
            trans_light = 1;
        } else if (trans_light > 255) {
            trans_light = 255;
        }

        vc.show((float) (trans_light * 100 / 255.0));
    }

    @Override
    protected void onDestroy() {
        Logger.out("onDestroy 1");
        super.onDestroy();
        Logger.out("onDestroy 2");
        mHandler.removeMessages(0);
        mHandler.removeCallbacksAndMessages(null);
//        if (player != null) {
//            if (player.isPlaying())
//                player.stop();
//            player.reset();
//            player.release();
//            player = null;
//        }
        Logger.out("onDestroy 3");
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
//                        seekbar.setProgress(progress);

                        seekbar.setValue(progress);
                        if (video.getCurrentPosition() > video.getDuration() - 100) {
                            tv_played.setText("00:00");
//                            seekbar.setProgress(0);
                            seekbar.setValue(0);

                        }
//                        seekbar.setSecondaryProgress(video.getBufferPercentage());
                        int buffer = video.getBufferPercentage();
                        seekbar.setBuffer(buffer);
//                        Logger.out("handleMessage value:" + progress + " buffer:" + buffer);

                    } else {
                        tv_played.setText("00:00");
//                        seekbar.setProgress(0);
                        seekbar.setValue(0);
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
                player = mp;
                video.setVideoWidth(mp.getVideoWidth());
                video.setVideoHeight(mp.getVideoHeight());
//                player.setOnInfoListener(new MediaPlayer.OnInfoListener() {
//                    @Override
//                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
//                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
//                            Logger.out("onInfo MEDIA_INFO_BUFFERING_START");
//                        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
//                            Logger.out("onInfo MEDIA_INFO_BUFFERING_END");
//                        }else{
//                            Logger.out("onInfo unknown:"+what+" "+extra);
//                        }
//                        return false;
//                    }
//                });
                mp.start();
//                video.start();
                if (played_time != 0) {
                    video.seekTo(played_time);
                }
                Logger.out("onPrepared");
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
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
                iv_play.setImageResource(R.drawable.icon_media_play);
                tv_played.setText("00:00");
//                seekbar.setProgress(0);
                seekbar.setValue(0);
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Logger.out("onError:" + what + "," + extra);
                Notifier.showNormalMsg(MediaActivity.this, "播放视频遇到问题，错误码：(" + what + "," + extra + ")");
                if (mp != null) {
                    try {
                        if (mp.isPlaying())
                            mp.stop();
                        mp.reset();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
//                    mp.release();
//                    mp=null;
                }
//                finish();
                return false;
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
                        if (x < width / 4.0) {
                            vc.setIsVolume(false);
                            vc.setIsBrightness(true);
                            vc.setShowProgressBar(true);
//                            vc.show();
                            if (deltaY > 0) {

                                lightDown(absDeltaY);
                            } else if (deltaY < 0) {
                                lightUp(absDeltaY);
                            }
                        } else if (x > width * 3.0 / 4) {
                            vc.setIsVolume(true);
                            vc.setIsBrightness(false);
                            vc.setShowProgressBar(true);
                            vc.show();
                            if (deltaY > 0) {

                                volumeDown(absDeltaY);
                            } else if (deltaY < 0) {
                                volumeUp(absDeltaY);
                            }
                        }

                    } else {
                        vc.setIsVolume(false);
                        vc.setIsBrightness(false);
                        vc.setShowProgressBar(false);
                        if (deltaX > 0) {
                            vc.setIcon(R.drawable.icon_media_forward);
                            vc.show();
                            forward(absDeltaX);
                        } else if (deltaX < 0) {
                            vc.show();
                            vc.setIcon(R.drawable.icon_media_rewind);
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
                    R.anim.slide_out_to_top);
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
                    R.anim.slide_out_to_bottom);
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
                    R.anim.slide_in_from_top);
            header.startAnimation(animation);

            footer.setVisibility(View.VISIBLE);
            footer.clearAnimation();
            Animation animation1 = AnimationUtils.loadAnimation(this,
                    R.anim.slide_in_from_bottom);
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
