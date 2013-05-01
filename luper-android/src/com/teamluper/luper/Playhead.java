package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;

//Libraries for Media Player Listening
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//playhead class view
public class Playhead extends LinearLayout {

  private static final float PPM = TrackView.PIXELS_PER_MILLISECOND;
  //PIXELS FOR MILLISECOND
  private final float STARTY = 0;
  private final float ENDY = 5000;

  private int currentTimeMS; // time we are at w/r/t the beginning of the whole project
  private int startTimeMS;   // what the value of currentTimeMS was when startPlayback() was called
  private long playbackTimestamp; // what the system clock time was when startPlayback() was called
  private float xPosition;   // pixel value representation of currentTimeMS

  private Context context;

  ScheduledExecutorService playbackClock = null;
  Handler monitorHandler;

  //PAINT ASSOCIATED WITH THE PLAYHEAD
  Paint mpaint;
  private final int lineColor = Color.BLACK;

  public Playhead(Context context) {
    super(context);
    this.context = context;
    currentTimeMS = 0;
    init();
  }

  public Playhead(Context context, AttributeSet attribute, int style) {
    super(context, attribute, style);
    this.context = context;
    currentTimeMS = 0;
    init();
  }

  public Playhead(Context context, AttributeSet attribute) {
    this(context, attribute, 0);
  }

  public float getXPosition() { return xPosition; }
  public void setXPosition(float x) {
    xPosition = x;
    this.invalidate();
  }

  public int getCurrentTimeMS() { return currentTimeMS; }
  public void setCurrentTimeMS(int x) {
    currentTimeMS = x;
    setXPosition(currentTimeMS * PPM + TrackView.LEFT_MARGIN);
  }

  public void init() {
    currentTimeMS = 0;
    mpaint = new Paint();
    mpaint.setColor(lineColor);
    mpaint.setStyle(Paint.Style.STROKE);
    mpaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));

    monitorHandler = new Handler(){
      @Override
      public void handleMessage(Message msg) {
        updateTime();
      }
    };
  }

  private void updateTime() {
    long elapsedMS = (System.nanoTime() - playbackTimestamp) / 1000000;
    setCurrentTimeMS((int) (elapsedMS + startTimeMS));
    ((LuperProjectEditorActivity) context).playNextClipIfExistsAtTime(currentTimeMS);
  }

  public void startPlayback(int startTime) {
    this.setCurrentTimeMS(startTime);
    startPlayback();
  }

  public void startPlayback() {
    startTimeMS = currentTimeMS;
    playbackTimestamp = System.nanoTime();

    if(isPlaying()) stopPlayback();
    playbackClock = Executors.newScheduledThreadPool(1);

    playbackClock.scheduleWithFixedDelay(
        new Runnable() {
          @Override
          public void run() {
            monitorHandler.sendMessage(monitorHandler.obtainMessage());
          }},
        1, //initialDelay
        1, //delay
        TimeUnit.MILLISECONDS);

    ((LuperProjectEditorActivity) this.context).supportInvalidateOptionsMenu();
  }

  public void stopPlayback(int newCurrentTimeMS) {
    stopPlayback();
    setCurrentTimeMS(newCurrentTimeMS);
  }

  public void stopPlayback() {
    playbackClock.shutdown();
    playbackClock = null;
    ((LuperProjectEditorActivity) this.context).supportInvalidateOptionsMenu();
  }

  public boolean isPlaying() {
    return playbackClock != null;
  }

  @Override
  public void onDraw(Canvas canvas) {
    canvas.drawLine(xPosition, STARTY, xPosition, ENDY, mpaint);
    invalidate();
  }
}