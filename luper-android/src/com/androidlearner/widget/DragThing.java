package com.androidlearner.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;



public class DragThing extends TextView{

	//private variable shtuff
	private int[] current = new int[4] ; // the position

	//original x and y vals (to be changed when dragged)
	private int prevx = 0;
	private int prevy = 0;

	//constructor!
	public DragThing(Context context, AttributeSet attribute, int style) {
		super(context, attribute, style);
	}
	public DragThing(Context context, AttributeSet attribute) {
		this(context, attribute, 0);
	}
	//returns the position
	public int[] getCurrent(){ return current ; }
	
	//returns prevx
	public int getPrevx(){return prevx;}
	
	//returns prevy
	public int getPrevy(){return prevy;}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final int action = event.getAction();

		final int x = (int)event.getX(); //teh current x val
		final int y = prevy;
		//final int y = (int)event.getY(); //teh current y val 

		switch(action){
		case MotionEvent.ACTION_DOWN:
			prevx = x; //updates points
			prevy = y;
			break;

		case MotionEvent.ACTION_MOVE:
			int changex = x - prevx; //calculates the change in position
			int changey = y - prevy;
			final int left = getLeft();
			final int top = getTop();
			if(changex != 0 || changey != 0) { // if there is a change, then update the layout
				//values and stuff
				layout(left + changex, top + changey, left + changex + getWidth(), top + changey + getHeight());
			}

			prevx = x - changex;
			prevy = y - changey;

			//redefining array
			current[0] = left + changex;
			current[1] = top + changey;
			current[2] = left + changex + getWidth();
			current[3] = top + changey + getHeight();
			break;

		case MotionEvent.ACTION_UP:
			break;

		case MotionEvent.ACTION_CANCEL:
			break;
		}


		return true;
	}
}

