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
	
	OnClickListener clicker = new OnClickListener(){
		public void onClick(View v){
			promptDialog();
			//System.out.println("got here dawg");
		}
	};
	
	public ColorChipButton(Context context, Clip clip){
		super(context);
		associated = clip;
		init();
		setOnClickListener(clicker);
	}
	
	public void init(){
		this.setX(this.getStartTime());
		this.setWidth(this.getStartTime() + this.getLength());
	}
	
	public void displayStats(){
//		to do, make it return the clip's length and start time; start time being editable
	}
	
	public Clip getClip(){
		return this.associated;
	}
	
	public int getLength(){
		return this.associated.duration;
	}
	
	public int getStartTime(){
		return this.associated.begin;
	}
	
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
