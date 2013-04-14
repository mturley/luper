package com.teamluper.luper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorChipButton extends Button {
	
	private static final String TAG = "ColorClipButton";
	
	//the clip that is associated with this CCB
	Clip associated;
	
	//set a click listener for the buttons that will activate promptDialog() when clicked
	OnClickListener clicker = new OnClickListener(){
		public void onClick(View v){
			promptDialog();
		}
	};
	
	//constructor sets the associated clip, calls init, and sets the onclicklistener
	public ColorChipButton(Context context, Clip clip){
		super(context);
		associated = clip;
		init();
		setOnClickListener(clicker);
	}
	
	//this method will determine where the clip should be placed, based on its start time
	public void init(){
		this.setX(this.getStartTime());
		this.setWidth(this.getStartTime() + this.getLength());
	}
	
	public void displayStats(){
//		to do, make it return the clip's length and start time; start time being editable
	}
	
	//returns the clip associated with this button
	public Clip getClip(){
		return this.associated;
	}
	
	//returns the length of this button (and inherently its clip)
	public int getLength(){
		return this.associated.duration;
	}
	
	//returns the start time of the button (and clip) in this track
	public int getStartTime(){
		return this.associated.begin;
	}
	
//	when you click the button this method is activated. currently it only shows the length of
//	the clip. eventually it should allow you to modify things about the clip
	public void promptDialog(){
		new AlertDialog.Builder(getContext())
			.setTitle("Clip Details Yo!")
			.setMessage("Length " + associated.duration + " ms")
		    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		      	  //Do nothing for now
		        }
		    })
		    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int whichButton) {
		            // Do nothing.
		        }
		    })
		    .show();
	}

}
