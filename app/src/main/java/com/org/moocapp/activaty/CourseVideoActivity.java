package com.org.moocapp.activaty;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.org.moocapp.R;
import com.org.moocapp.api.Api;
import com.org.moocapp.api.ApiConfig;
import com.org.moocapp.api.TtitCallback;
import com.org.moocapp.entity.CourseChapterEntity;
import com.org.moocapp.entity.CourseEntity;
import com.org.moocapp.entity.LearnProgressEntity;
import com.org.moocapp.entity.Response.LearnProgressResponse;
import com.org.moocapp.entity.Response.ResponseHeader;
import com.org.moocapp.util.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

public class CourseVideoActivity extends BaseActivity {
    private BaseVideoView player;
    private CourseChapterEntity data;
    private CourseEntity course;
    private LearnProgressEntity learnProgressEntity;

    private LocalDateTime start_time;
    private LocalDateTime end_time;

    @Override
    protected int initLayout() {
        return R.layout.activity_course_video;
    }

    @Override
    protected void initView() {
        player = findViewById(R.id.player);
    }

    @Override
    protected void initData() {
        data = (CourseChapterEntity) getIntent().getSerializableExtra("data");
        course = (CourseEntity) getIntent().getSerializableExtra("course");
        getLearnProgress();
        initVideo();
        player.start();
    }

    public void initVideo(){
        if(data!=null){
            player.setUrl(String.valueOf(data.getResourceUrl()));
            StandardVideoController controller = new StandardVideoController(this);
            controller.addDefaultControlComponent(data.content, false);
            player.setVideoController(controller); //设置控制器
        }
        player.setOnStateChangeListener(new BaseVideoView.OnStateChangeListener() {
            @Override
            public void onPlayerStateChanged(int playerState) {
                switch (playerState) {
                    case VideoView.PLAYER_NORMAL://小屏
                        break;
                    case VideoView.PLAYER_FULL_SCREEN://全屏
                        break;
                }
            }
            @Override
            public void onPlayStateChanged(int playState) {
                switch (playState) {
                    case VideoView.STATE_IDLE://静止状态，此时播放器还未进行初始化

                        break;
                    case VideoView.STATE_PREPARING://正在准备播放，调用了prepareAsync()即进入此状态

                    case VideoView.STATE_PREPARED://准备完成状态，播放器此时会回调了onPrepared()方法

                        break;
                    case VideoView.STATE_PLAYING://正在播放状态，调用了start()方法即进入此状态

                        start_time = LocalDateTime.now();


                        break;
                    case VideoView.STATE_PAUSED://暂停播放状态，调用了pause()方法即进入此状态

                        end_time = LocalDateTime.now();
                        Duration duration = java.time.Duration.between(start_time,end_time);
                        LocalDateTime localDateTime = LocalDateTime.of(end_time.toLocalDate(), LocalTime.of(0,0));
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        LocalDateTime preUpTime;
                        if(learnProgressEntity!=null){
                            preUpTime = LocalDateTime.parse(learnProgressEntity.getUpdateTime(), df);
                            learnProgressEntity.setReadTime(learnProgressEntity.getReadTime()+duration.toMillis());
                        }else{
                            learnProgressEntity = new LearnProgressEntity();
                            learnProgressEntity.setChaIndex(data.getChaIndex());
                            learnProgressEntity.setChaId(data.getId());
                            learnProgressEntity.setCouId(course.getId());
                            preUpTime = start_time.minusDays(1);
                            learnProgressEntity.setReadTime(duration.toMillis());
                            learnProgressEntity.setDayTime(0L);
                        }
                        learnProgressEntity.setNextStartTime(player.getCurrentPosition());

                        if(start_time.getHour()>end_time.getHour()){
                            learnProgressEntity.setDayTime(java.time.Duration.between(localDateTime,end_time).toMillis());
                        }else{
                            if(!preUpTime.toLocalDate().isEqual(end_time.toLocalDate())){
                                learnProgressEntity.setDayTime(java.time.Duration.between(start_time,end_time).toMillis());
                            }else{
                                learnProgressEntity.setDayTime(learnProgressEntity.getDayTime()+java.time.Duration.between(start_time,end_time).toMillis());
                            }
                        }

                        learnProgressEntity.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        if(learnProgressEntity.getReadTime()>=player.getDuration()){
                            learnProgressEntity.setProgress(1);
                        }else{
                            learnProgressEntity.setProgress(utils.div(new Double(learnProgressEntity.getReadTime()),new Double(player.getDuration()),3));
                        }
                        upLearnProgress();
                        break;
                    case VideoView.STATE_BUFFERING://播放完成状态，播放器此时会回调onCompletion()方法

                        System.out.println(player.getCurrentPosition());
                        break;
                    case VideoView.STATE_BUFFERED://缓冲状态，seekTo方法会触发此状态，播放器此时会回调onInfo()方法


                        if(learnProgressEntity!=null&&learnProgressEntity.getReadTime()<player.getCurrentPosition()){

                        }
                        break;
                    case VideoView.STATE_PLAYBACK_COMPLETED://缓冲结束状态，播放器此时会回调onInfo()方法

                        break;
                    case VideoView.STATE_ERROR://播放出错
                        break;
                }
            }
        });
    }


    // 页面暂停
    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }
    // 页面恢复
    @Override
    protected void onResume() {
        super.onResume();
        player.resume();
        getLearnProgress();
    }
    // 页面销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
    // 页面停止
    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        if (!player.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void getLearnProgress(){
        Api.config(ApiConfig.shLearnProgress+course.getId()+"/"+data.getId(),null).getRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                LearnProgressResponse learnProgressResponse = new Gson().fromJson(res,LearnProgressResponse.class);
                if(learnProgressResponse.getCode()==200&&learnProgressResponse.getData()!=null){
                    learnProgressEntity = learnProgressResponse.getData();
                    player.skipPositionWhenPlay(learnProgressEntity.getNextStartTime().intValue());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void upLearnProgress(){
        HashMap<String,Object> map =  new Gson().fromJson(new Gson().toJson(learnProgressEntity),HashMap.class);
        Api.config(ApiConfig.upLearnProgress,map).postRequest(mContext, new TtitCallback() {
            @Override
            public void onSuccess(String res) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ResponseHeader responseHeader = new Gson().fromJson(res,ResponseHeader.class);
                        if(responseHeader.getCode()==200&&responseHeader.getData()!=null){
                        }
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                showToastSync("保存学习记录失败");
            }
        });
    }


}