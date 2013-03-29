package com.teamluper.luper;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.googlecode.androidannotations.annotations.EFragment;
import com.teamluper.luper.AudioRecorderTest.PlayButton;
import com.teamluper.luper.AudioRecorderTest.RecordButton;

public class ExampleProject extends SherlockActivity {
	
	@Override
    public void onCreate(Bundle icicle) {

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
        super.onCreate(icicle);
        
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
        
        ImageView add = new ImageView(this);
        add.setImageResource(R.drawable.add);
        
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

        add.setLayoutParams(params1);
        layout.addView(add);
        
        setContentView(layout);
        
        
    }

    @Override
    public void onPause() {
        super.onPause();
    }
    
    

}
