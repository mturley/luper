package com.teamluper.luper;

import android.os.Bundle;
import com.actionbarsherlock.app.SherlockActivity;
import com.androidlearner.widget.DragThing;
import com.androidlearner.widget.DragThingPlayhead;
import com.googlecode.androidannotations.annotations.EActivity;


@EActivity
public class DragTest extends SherlockActivity {
	
	//the widget. why can't it be a class? :/
	DragThing deMovingTxt;
  DragThingPlayhead theplayhead;
	int [] paramz;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragtest); //XML!

		//the draggable text. Takes from xml
		deMovingTxt = (DragThing) findViewById(R.id.detext);
    theplayhead = new DragThingPlayhead(this);
    setContentView(theplayhead);
  }

	@Override
	protected void onPause() {
		super.onPause();
		//gets the current layout
		paramz = deMovingTxt.getCurrent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//updates the array in DragThing
		if(paramz != null) deMovingTxt.layout(paramz[0] , 0, paramz[2], 0);
		//if(paramz != null) deMovingTxt.layout(paramz[0] , paramz[1], paramz[2], paramz[3]);
	}


}
