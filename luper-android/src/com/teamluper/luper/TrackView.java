package com.teamluper.luper;
/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ViewGroup;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;


import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

import com.teamluper.luper.AudioRecorderTestActivity.RecordButton;

/**
 * A custom view for a color chip for an event that can be drawn differently
 * according to the event's status.
 *
 */
public class TrackView extends RelativeLayout {
	private static final String LOG_TAG = "TrackView";
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private TextView fileSelected;
    
    Track associated;
	
	//the track that will be associated with this TrackView
	//Track associated;
	
	//constructor
	public TrackView(Context context){
		super(context);
		associated = new Track();
		init();
	}
	
	//set a click listener for the buttons that will activate promptDialog() when clicked
	OnClickListener clicker = new OnClickListener(){
		public void onClick(View v){
			promptDialog();
		}
	};
	
	public void init(){
		this.setPadding(0, 10, 0, 5);
		
//		add a linear layout to the left side that will have a playtrack button
//		as well as a button to add a clip to this track
		LinearLayout trackControl = new LinearLayout(this.getContext());
		trackControl.setOrientation(LinearLayout.VERTICAL);
		
//		create the addClipButton then set its image to add and add it to the trackControl
		ImageButton addClipButton = new ImageButton(this.getContext());
		addClipButton.setImageResource(R.drawable.add);		
		addClipButton.setOnClickListener(clicker);
		trackControl.addView(addClipButton);
		
//		create the playButton then set its image to play and add it to the trackControl
		ImageButton playButton = new ImageButton(this.getContext());
		playButton.setImageResource(R.drawable.play);		
		trackControl.addView(playButton);
		
		this.addView(trackControl);
//		testing...
//        Clip clip1 = new Clip(); clip1.begin = 0; clip1.end = 500; clip1.duration = 1500;
//        ColorChipButton chip;
//        this.associated.putClip(clip1);
//        for(int i = 0; i < this.associated.clips.size(); i++){
//        	chip = new ColorChipButton(this.getContext(), this.associated.clips.get(i));
//        	chip.setBackgroundColor(Color.RED);
//        	this.addView(chip);
//        }
	}
	
	public void promptDialog(){
		//our custom layout for inside the dialog
		LinearLayout custom = new LinearLayout(this.getContext());
		custom.setOrientation(LinearLayout.VERTICAL);
		
		LinearLayout ll = new LinearLayout(this.getContext());		
		mRecordButton = new RecordButton(this.getContext());
        ll.addView(mRecordButton,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        
        LinearLayout ll2 = new LinearLayout(this.getContext());
        fileSelected = new AutoCompleteTextView(this.getContext());   
        fileSelected.setHint("Select a File");
        ll2.addView(fileSelected,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT,
                        0));

        custom.addView(ll,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        custom.addView(ll2,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
		
		new AlertDialog.Builder(getContext())
			.setTitle("Record or Browse?")
			.setView(custom)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		        	//want it to pass a new clip back to the editor panel and add it to the screen
		        }
		    })
		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    })
			.show();

	}
	
    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    private void onRecord(boolean start) {

        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
    	//Sets the name of the file when you start recording as opposed to when you click "Audio Record Test" from the main screen
        mFileName = Environment.getExternalStorageDirectory()+"/LuperApp/Clips";
        mFileName += "/clip_" + System.currentTimeMillis() +".3gp";
        
        mRecorder = new MediaRecorder();
        //System.out.println("here");
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //System.out.println("and here");
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
      
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
        	System.out.println(e.toString());
            Log.e(LOG_TAG, "prepare() failed2");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        
        Clip newClip = new Clip(mFileName); 

        try {
			newClip.getDuration();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        //playBackTest.putClip(newClip);        
        //alertDialog("Clip Created! The clip's length is: " + newClip.duration + "(ms). The tracks size is " + playBackTest.size() + " and it's name in the track is ..." + playBackTest.clips.get(0).name);
        
       // alertDialog("Clip Created! The clip's length is: " + newClip.duration + "(ms) the clip's name is: " + newClip.name);
        
        fileSelected.setText(mFileName);
        mRecorder = null;
    }


}


