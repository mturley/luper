package com.teamluper.luper;

import android.content.Context;
import android.renderscript.Font;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.*;
import android.os.Handler;
import android.os.Message;
import android.media.MediaPlayer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.teamluper.luper.ColorChipButton;

//Libraries for Media Player Listening
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//playhead class view to be implemented --Eric
public class Playhead extends LinearLayout {

  private static final float PPM = ColorChipButton.PIXELS_PER_MILLISECOND;
  //PIXELS FOR MILLISECOND
  private final float STARTY = 0;
  private final float ENDY = 5000;
  //private float X;
  private float currentTime;
  Handler monitorHandler;

  //MEDIA PLAYER OBJECT ASSOCIATED WITH THE PLAYHEAD
  private MediaPlayer MP;

  //PAINT ASSOCIATED WITH THE PLAYHEAD
  Paint mpaint;
  private final int lineColor = Color.BLACK;
  //

  public Playhead(Context context){
    super(context);
    init();
  }

  public Playhead(Context context, AttributeSet attribute, int style) {
    super(context, attribute, style);
    init();
  }

  public Playhead(Context context, AttributeSet attribute) {
    this(context, attribute, 0);
    init();
  }

  public float getCurrentTime(){
    return currentTime;
  }

  public void setCurrentTime(float x){
    currentTime = x;
  }

  public void init(){
    currentTime = 50;
    mpaint = new Paint();
    mpaint.setColor(lineColor);
    mpaint.setStyle(Paint.Style.STROKE);
    mpaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
//      this.setX(this.getStartTime());
//      this.setY(5000);
//      this.setWidth(this.getStartTime() + this.getLength());
  }

  //method used for syncing playhead with the media player during playback
  public void setHandler(final MediaPlayer mPlayer){
    MP = mPlayer;
    monitorHandler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
        mediaPlayerMonitor(mPlayer);
      }
    };
  }

  private void mediaPlayerMonitor(MediaPlayer mPlayer){
    if (MP == null){
      //do nothing
    }else{
      if(MP.isPlaying()){
        setCurrentTime(MP.getCurrentPosition() * PPM);
        this.invalidate();

//        timeLine.setVisibility(View.VISIBLE);
//        timeFrame.setVisibility(View.VISIBLE);
//
//        int mediaDuration = mediaPlayer.getDuration();
//        int mediaPosition = mediaPlayer.getCurrentPosition();
//        timeLine.setMax(mediaDuration);
//        timeLine.setProgress(mediaPosition);
//        timePos.setText(String.valueOf((float)mediaPosition/1000) + "s");
//        timeDur.setText(String.valueOf((float)mediaDuration/1000) + "s");
      }//else{
//        timeLine.setVisibility(View.INVISIBLE);
//        timeFrame.setVisibility(View.INVISIBLE);
    }
  }

  public void playback(){
//    if(mPlayer.isPlaying())
//    {

    ScheduledExecutorService myScheduledExecutorService = Executors.newScheduledThreadPool(1);

    myScheduledExecutorService.scheduleWithFixedDelay(
        new Runnable(){
          @Override
          public void run() {
            monitorHandler.sendMessage(monitorHandler.obtainMessage());
          }},
        1, //initialDelay
        1, //delay
        TimeUnit.MILLISECONDS);
  }

  //}
//    else
//      stopHead(mPlayer);


  public void stopHead(MediaPlayer mPlayer){
    currentTime=mPlayer.getCurrentPosition();
  }

  @Override
  public void onDraw(Canvas canvas) {
    canvas.drawLine(currentTime, STARTY, currentTime, ENDY, mpaint);
    invalidate();
  }
}