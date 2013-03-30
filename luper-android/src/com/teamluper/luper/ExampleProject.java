package com.teamluper.luper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import java.util.*;
import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.EFragment;
import com.teamluper.luper.AudioRecorderTest.PlayButton;
import com.teamluper.luper.AudioRecorderTest.RecordButton;

@SuppressLint("WrongCall")
public class ExampleProject extends Activity {
	ArrayList<Clip> cliplist = new ArrayList<Clip>();
	ColorChipView CCV;
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        CCV = new ColorChipView(this);
        setContentView(CCV);
        
        MediaPlayer mp;
        mp = MediaPlayer.create(getApplicationContext(), R.raw.beep24);
        mp.setLooping(true);
        try {
			mp.prepare();
		} catch (IllegalStateException e) {
			System.out.println("Something fuqed up");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something fuqed up");
			e.printStackTrace();
		}
        mp.start();
        
        // this LinearLayout is used in place of an XML file.
        // Android lets you do your layouts either programattically like this,
        // or with an XML file.
//        ll = new LinearLayout(this);
//        pText = new TextView(this);
//        pText.setTextColor(0xFF000000);
//        pText.setGravity(Gravity.CENTER_HORIZONTAL);
//        pText.setTextSize(20f);
//        pText.setText("\nThis is our dummy Project. For now, it's pretty boring. But soon it will be loaded with bells, whistles, gizmos, and loops, naturally.\n");

//        ProjectView PV = new ProjectView(this);
//        layout.addView(PV);

         
    }
	static public class ProjectView extends View {
//		ArrayList<ColorChipView> chipList = new ArrayList<ColorChipView>();
		ColorChipView chip;
		protected Canvas canvas;

	    public ProjectView(Context context) {
	      super(context);
	      chip = new ColorChipView(context);
	      chip.setDrawStyle(1);
	      chip.setLayoutParams(new LayoutParams(500, 500));
	      this.invalidate();
//	      int color = Color.BLUE; 

//	      circle = new Path();
//	      circle.addCircle(150, 150, 100, Direction.CW);

//	      cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//	      cPaint.setStyle(Paint.Style.STROKE);
//	      cPaint.setColor(Color.LTGRAY);
//	      cPaint.setStrokeWidth(3);

	    }

	    @Override
	    protected void onDraw(Canvas canvas) {
	      super.onDraw(canvas);
	      chip.onDraw(canvas);
	      ;
	    }
	  }

    @Override
    public void onPause() {
        super.onPause();
    }

}
