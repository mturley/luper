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
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;

import java.io.IOException;
import java.util.ArrayList;

import com.androidlearner.widget.DragThing;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EView;
import com.googlecode.androidannotations.annotations.UiThread;
import com.teamluper.luper.AudioRecorderTestActivity.PlayButton;
import com.teamluper.luper.AudioRecorderTestActivity.playTrackButton;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.androidlearner.widget.DragThing;
import com.googlecode.androidannotations.annotations.EActivity;

/**
 * A custom view for a color chip for an event that can be drawn differently
 * according to the event's status.
 *
 */
@EView
public class TrackView extends RelativeLayout {
	private static final String LOG_TAG = "TrackView";
	
	DragThing deMovingTxt;
	int [] paramz;
	
	private String lastRecordedFileName = null;
	private AudioFile lastRecordedFile = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;
    
    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;

    private TextView fileSelected;
    
    private SQLiteDataSource dataSource;

    Track associated;

	//the track that will be associated with this TrackView
	//Track associated;

	//constructor
	public TrackView(Context context, Track track, SQLiteDataSource dataSource)
	{
		super(context);
		associated = track;
		this.dataSource = dataSource;
		init();
	}

	//set a click listener for the buttons that will activate promptDialog() when clicked
	OnClickListener clicker = new OnClickListener(){
		public void onClick(View v){
			promptDialog(0); // later on this will be the time corresponding to where in the empty timeline we touched.
			// TODO
		}
	};
	OnClickListener playClicker = new OnClickListener(){
		public void onClick(View v){
				startPlayingTrack(); //need track playback but track class + audio hook-up not working yet; this does work though
		}
	};

	protected void onPause() {
		//super.onPause();
		//gets the current layout
		paramz = deMovingTxt.getCurrent();
	}

	protected void onResume() {
		//super.onResume();
		//updates the array in DragThing
		if(paramz != null) deMovingTxt.layout(paramz[0] , 0, paramz[2], 0);
		//if(paramz != null) deMovingTxt.layout(paramz[0] , paramz[1], paramz[2], paramz[3]);
	}
	
	public void init(){
		mPlayer = new MediaPlayer();
		
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
		playButton.setOnClickListener(playClicker);
		trackControl.addView(playButton);

		this.addView(trackControl);
//		testing...
        //Clip clip1 = new Clip(); clip1.begin = 100; clip1.end = 350; clip1.duration = 250;
        //Clip clip2 = new Clip(); clip2.begin = 0; clip2.end = 450; clip2.duration = 450;
        ColorChipButton chip;
        //this.associated.putClip(clip1);
        //this.associated.putClip(clip2);
        for(int i = 0; i < this.associated.clips.size(); i++){
        	System.out.println("Here " + this.associated.getClips().get(i).begin);
        	chip = new ColorChipButton(this.getContext(), this.associated.getClips().get(i));
        	chip.setBackgroundColor(Color.RED);
        	System.out.println("Chips x pos " + chip.associated.begin);
        	this.addView(chip);
        }
	}
	

	public void promptDialog(int startTime){
		//our custom layout for inside the dialog
		LinearLayout custom = new LinearLayout(this.getContext());
		custom.setOrientation(LinearLayout.VERTICAL);

		deMovingTxt = (DragThing) findViewById(R.id.detext);
		
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
        custom.addView(deMovingTxt,
        		new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        
        final int finalStartTime = startTime;
        final Track finalTrack = associated;

		new AlertDialog.Builder(getContext())
			.setTitle("Record or Browse?")
			.setView(custom)
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		        	//want it to pass a new clip back to the editor panel and add it to the screen
		        	//NEED TO ADD CLIP TO THE TRACK
		        	Clip newClip = dataSource.createClip(finalTrack, lastRecordedFile, finalStartTime);
		        	associated.putClip(newClip);
		        	
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
        lastRecordedFileName = Environment.getExternalStorageDirectory()+"/LuperApp/Clips";
        lastRecordedFileName += "/clip_" + System.currentTimeMillis() +".3gp";

        mRecorder = new MediaRecorder();
        //System.out.println("here");
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //System.out.println("and here");
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(lastRecordedFileName);

        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed", e);
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        
        lastRecordedFile = dataSource.createAudioFile(dataSource.getActiveUser(), lastRecordedFileName);
        lastRecordedFile.setReadyOnClient(true);

        fileSelected.setText(lastRecordedFileName);
        mRecorder = null;
    }
    /*private void startPlaying() {
    	
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            //playFrom(1000);
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed1");
        }
    }*/

   /*private void stopPlaying() {
      if(mPlayer != null) {
        mPlayer.release();
        mPlayer = null;
      }
    }*/

    @UiThread
    public void startPlayingTrack() {
      ArrayList<Clip> clips = associated.getClips();
      for(Clip c : clips) {
    	playClipInBackground(c); // later on will also take a startTime parameter (current playhead time)
      }
    }

    // later on will also take a startTime parameter (current playhead time)
    @Background
    public void playClipInBackground(Clip c) {
    	String clipFileName = c.getAudioFile().getClientFilePath();
    	try {
    		mPlayer.setDataSource(clipFileName);
    		mPlayer.prepare();
    		Thread.sleep(c.getDurationMS());
    		mPlayer.start();
    	} catch (Exception e) {
    		//handle interrupted exceptions in a different way
    		Log.e(LOG_TAG, "TRACK PLAYBACK FAILED");
    	}
    }

    private void stopPlayingTrack() {
        if(mPlayer != null) {
          mPlayer.release();
          mPlayer = null;
        }
      }
    
    
}


