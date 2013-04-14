package com.teamluper.luper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.teamluper.luper.AudioRecorderTestActivity.PlayButton;
import com.teamluper.luper.AudioRecorderTestActivity.RecordButton;
import com.teamluper.luper.CanvasTestActivity.GraphicsView;


public class LuperProjectEditorActivity extends SherlockActivity {
	ActionBar AB;
	ArrayList<Clip> clips = new ArrayList<Clip>();
	ArrayList<Track> tracks = new ArrayList<Track>();
	
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        alertDialog("Incomplete Feature",
            "The Project Editor is not yet abstracted to open " +
            "any given project.  You're being taken instead to a " +
            "hard-coded Dummy project to demonstrate the editor UI. " +
            "Note also that the UI buttons are mostly unimplemented.");
        
        long ID = getIntent().getExtras().getLong("com.teamluper.luper.ProjectId");

        AB = getSupportActionBar();
        
        final ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); // Gives us Tabs!   
        
        /* TESTS FUNCTIONALLITY OF THE TRACK VIEW RENDERING */
          Clip clip1 = new Clip(); clip1.begin = 0; clip1.end = 500; clip1.duration = 500;
          Clip clip2 = new Clip(); clip2.begin = 250; clip2.end = 1000; clip2.duration = 650;
          Clip clip3 = new Clip(); clip3.begin = 100; clip3.end = 600; clip3.duration = 500;
          
        LinearLayout base = new LinearLayout(this);
        base.setBackgroundColor(Color.parseColor("#e2dfd8"));        
        
        base.setOrientation(LinearLayout.VERTICAL);
        
        TrackView track1 = new TrackView(this);
        
//        ImageButton addClipButton = new ImageButton(this);
//        addClipButton.setImageResource(R.drawable.add);
//        
//        track1.addView(addClipButton);

//        RelativeLayout track1 = new RelativeLayout(this);
//        RelativeLayout track2 = new RelativeLayout(this);
//        RelativeLayout track3 = new RelativeLayout(this);
//        
//        ColorChipButton chip1 = new ColorChipButton(this, clip1);
//        ColorChipButton chip2 = new ColorChipButton(this, clip2);
//        ColorChipButton chip3 = new ColorChipButton(this, clip3);
//        
//        track1.addView(chip1);
//        track2.addView(chip2);
//        track3.addView(chip3);        
//        
//        track1.setPadding(0, 10, 0, 5);
//        track2.setPadding(0, 10, 0, 5);
//        track3.setPadding(0, 10, 0, 5);
//        
//        chip1.setBackgroundColor(Color.GRAY); 
//        chip2.setBackgroundColor(Color.RED); 
//        chip3.setBackgroundColor(Color.WHITE); 
        
        TextView timelinetxt = new TextView(this);
        timelinetxt.setText("_____|__________________|___________________|___________________|___________________|_________\n");
        
        base.addView(track1,
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
//        base.addView(track2,
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
//        base.addView(track3,
//                new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT));
        
        setContentView(base);
   
    }
	
	// #Creates the Actionbar 
	@Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inf = getSupportMenuInflater();
    inf.inflate(R.menu.editor_bar, menu);
    return super.onCreateOptionsMenu(menu);
  }
	@Override
  public boolean onOptionsItemSelected(MenuItem item) {
	  boolean incomplete = true;
    if(item.getItemId() == R.id.editor_play) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_edit_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_add_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_delete_clip) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_volume) {
      // TODO
    }
    if(item.getItemId() == R.id.editor_help) {
      // TODO
    }
    if(incomplete) alertDialog("Incomplete Feature",
        "That button hasn't been hooked up to anything.");
    return true;
  }
	
  void alertDialog(String message) {
    alertDialog(null, message);
  }
  
  void alertDialog(String title, String message) {
    alertDialog(this, title, message);
  }
  
  void alertDialog(Context context, String title, String message) {
    AlertDialog.Builder dialog = new AlertDialog.Builder(context)
    .setCancelable(false)
    .setMessage(message)
    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int which) {
        // do nothing
      }
    });
    if(title != null) dialog.setTitle(title);
    dialog.show();
  }
}