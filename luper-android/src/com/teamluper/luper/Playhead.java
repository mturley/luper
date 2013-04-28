package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.*;
import android.media.MediaPlayer;


//playhead class view to be implemented --Eric
public class Playhead extends View {
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

  public void init(){
      currentTime = 100;
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


  public void onDraw(Canvas canvas) {
      canvas.drawLine(currentTime, STARTY, currentTime, ENDY, mpaint);
     invalidate();
  }
}