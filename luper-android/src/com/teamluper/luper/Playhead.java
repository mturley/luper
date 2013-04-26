package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.graphics.*;
import android.view.*;


//playhead class view to be implemented --Eric
public class Playhead extends View {
  private final float STARTY = 0;
  private final float ENDY = 5000;
  private float X;

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
      X = 100;
      mpaint.setColor(lineColor);
//      this.setX(this.getStartTime());
//      this.setY(5000);
//      this.setWidth(this.getStartTime() + this.getLength());
  }


  public void draw(Canvas canvas) {
      canvas.drawLine(X, STARTY, X, ENDY, mpaint);
     invalidate();
  }
}