package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

//playhead class view to be implemented --Eric
public class Playhead extends View {
  private final float STARTY = 0;
  private final float ENDY = 5000;
  private float X;

  public Playhead(Context context){
    super(context);
    init();
  }

  public Playhead(Context context, AttributeSet attribute, int style) {
    super(context, attribute, style);
  }

  public Playhead(Context context, AttributeSet attribute) {
    this(context, attribute, 0);
  }

  public void init(){
//      this.setX(this.getStartTime());
//      this.setY(5000);
//      this.setWidth(this.getStartTime() + this.getLength());
  }

//    public boolean onTouchEvent(MotionEvent event) {
//      int action = event.getAction();
//      switch (action) {
//        case MotionEvent.ACTION_DOWN:
//          x = event.getX();
//          break;
//        case MotionEvent.ACTION_MOVE:
//        case MotionEvent.ACTION_UP:
//        case MotionEvent.ACTION_CANCEL:
//          x = initialX + event.getX() - offsetX;
//          y = initialY + event.getY() - offsetY;
//          break;
//      }
//      return (true);
//    }

//    public void draw(Canvas canvas) {
//      int width = canvas.getWidth();
//      int height = canvas.getHeight();
//      drawLine(X, STARTY, X, float ENDY);
  //   invalidate();
  //  }
}