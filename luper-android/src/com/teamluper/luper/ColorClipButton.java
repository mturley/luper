package com.teamluper.luper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ColorClipButton extends Button {
	
	private static final String TAG = "ColorClipButton";
	
	//the clip that is associated with this CCB
	Clip associated;
	
	public ColorClipButton(Context context, Clip clip){
		super(context);
		associated = clip;
	}

	public ColorClipButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

}
