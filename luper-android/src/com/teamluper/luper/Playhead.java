package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.*;
import android.media.MediaPlayer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


//playhead class view to be implemented --Eric
public class Playhead extends LinearLayout {
  private final float STARTY = 0;
  private final float ENDY = 5000;
  //private float X;
  private float currentTime;

  //Playhead Color
  Paint mpaint = new Paint();
  private final int lineColor = Color.BLACK;

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
    mpaint.setColor(lineColor);
//      this.setX(this.getStartTime());
//      this.setY(5000);
//      this.setWidth(this.getStartTime() + this.getLength());
  }
  public void moveHead(MediaPlayer mPlayer){
    if(mPlayer.isPlaying())
    {
      currentTime++;
      this.invalidate();
    }
    else
      stopHead(mPlayer);


  }
  public void stopHead(MediaPlayer mPlayer){
    currentTime=mPlayer.getCurrentPosition();
  }

  @Override
  public void onDraw(Canvas canvas) {
    canvas.drawLine(currentTime, STARTY, currentTime, ENDY, mpaint);
    invalidate();
  }
}