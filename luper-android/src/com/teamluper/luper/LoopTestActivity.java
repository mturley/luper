package com.teamluper.luper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

@SuppressLint("WrongCall")
public class LoopTestActivity extends Activity {
	ArrayList<Clip> cliplist = new ArrayList<Clip>();
//	ColorClipView CCV;
	int numTimes;

	private OnClickListener onClickListener = new OnClickListener() {
	    @Override
	    public void onClick(final View v) {
	    	MediaPlayer mp2;
	        mp2 = MediaPlayer.create(getApplicationContext(), R.raw.button10);
	        mp2.setLooping(true);
	        try {
				mp2.prepare();
			} catch (IllegalStateException e) {
				System.out.println("Something fuqed up");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Something fuqed up");
				e.printStackTrace();
			}
	        mp2.start();
	    }
	};

	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //CCV = new ColorChipView(this, Color.BLUE); // FIXME BAD CALL, ColorChipView's constructor doesn't take a Color
        //setContentView(CCV);

        TextView pText = new TextView(this);
        pText.setTextColor(0xFF000000);
        pText.setGravity(Gravity.CENTER_HORIZONTAL);
        pText.setTextSize(20f);
        pText.setMaxHeight(200);
        pText.setText("\nThis is our dummy Project. For now, it's pretty boring. But soon it will be loaded with bells, whistles, gizmos, and loops, naturally.\n");


        TextView bottomText = new TextView(this);
        bottomText.setTextColor(0xFF000000);
        bottomText.setGravity(Gravity.BOTTOM);
        bottomText.setTextSize(20f);
        bottomText.setGravity(300);
        bottomText.setMaxHeight(100);
        bottomText.setText("Bottom  shtuff");

        Button ply = new Button(this);
        ply.setOnClickListener(onClickListener);

        //creating a random layout
        RelativeLayout layout = new RelativeLayout(this);

        //setting positions of text
        RelativeLayout.LayoutParams params0 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params0.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        pText.setLayoutParams(params0);
        layout.addView(pText);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        bottomText.setLayoutParams(params1);
        layout.addView(bottomText);

        ply.setLayoutParams(params1);
        layout.addView(ply);

        setContentView(layout);

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
//		ColorClipView chip;
		protected Canvas canvas;

	    public ProjectView(Context context) {
	      super(context);
	      /*chip = new ColorChipView(context, Color.BLUE); // FIXME BAD CALL, ColorChipView's constructor doesn't take a Color
	      chip.setDrawStyle(1);
	      chip.setLayoutParams(new LayoutParams(500, 500)); */
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
	      //chip.onDraw(canvas);  // FIXME BAD CALL, should probably be .draw()
	      ;
	    }
	  }

    @Override
    public void onPause() {
        super.onPause();
    }
    public void loopNum()
    {
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);

    	alert.setTitle("Title");
    	alert.setMessage("Message");

    	// Set an EditText view to get user input
    	final EditText input = new EditText(this);
    	alert.setView(input);

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {


		public void onClick(DialogInterface dialog, int whichButton) {
    	  Editable value = input.getText();
    	  // Do something with value!
    	  numTimes = Integer.parseInt(input.getText().toString());
    	  }
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	  public void onClick(DialogInterface dialog, int whichButton) {
    	    // Canceled.
    	  }
    	});

    	alert.show();
    }
}
