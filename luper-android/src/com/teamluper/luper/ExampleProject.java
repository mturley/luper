package com.teamluper.luper;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
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
import android.widget.Button;
import android.view.View;
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
        pText.setText("\nThis is our dummy Project. For now, it's pretty boring. But soon it will be loaded with bells, whistles, gizmos, and loops, naturally.\n");

        
        /*TextView bottomText = new TextView(this);
        bottomText.setTextColor(0xFF000000);
        bottomText.setGravity(Gravity.BOTTOM);
        bottomText.setTextSize(20f);
        bottomText.setText("Bottom  shtuff");*/

        // this LinearLayout is used in place of an XML file.
        // Android lets you do your layouts either programattically like this,
        // or with an XML file.
        LinearLayout layout = new LinearLayout(this);
        layout.addView(pText);
        layout.addView(bottomText);
        setContentView(layout);
        
        
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
