package com.teamluper.luper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.os.Bundle;
import android.view.View;

public class CanvasTest extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(new GraphicsView(this));
  }
  static public class GraphicsView extends View {
    private static final String QUOTE = "This is a test. This is a demo.";
    private Path circle;
    private Paint cPaint;
    private Paint tPaint;

    public GraphicsView(Context context) {
      super(context);

      int color = Color.BLUE; 

      circle = new Path();
      circle.addCircle(150, 150, 100, Direction.CW);
<<<<<<< HEAD
 
=======


>>>>>>> bea36a22ca473b2b8495bd0dd85ec88733d28453
      cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      cPaint.setStyle(Paint.Style.STROKE);
      cPaint.setColor(Color.LTGRAY);
      cPaint.setStrokeWidth(3);


      //setBackgroundResource(R.drawable.ic_launcher);
      this.setBackgroundColor(0x0000FF00 );
      this.invalidate();
      
      
    }

    @Override
    protected void onDraw(Canvas canvas) {
      canvas.drawPath(circle, cPaint);
    }
  }
}
