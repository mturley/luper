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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ViewGroup;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.Random;
import java.util.ArrayList;

/**
 * A custom view for a color chip for an event that can be drawn differently
 * according to the event's status.
 *
 */
public class ProjectView extends ViewGroup {

	//a tag for XML layouts, Manifest
    private static final String TAG = "ProjectView";
    // Style of drawing
    // Full rectangle for accepted events
    // Border for tentative events
    // Cross-hatched with 50% transparency for declined events
    private final int background = Color.LTGRAY;

    private static final int DEF_BORDER_WIDTH = 4;

    int mBorderWidth = DEF_BORDER_WIDTH;

    int mColor;
    Paint mPaint;
    
    ArrayList<TrackView> tracks = new ArrayList<TrackView>();
    ScrollView scroller;

    public ProjectView(Context context) {
        super(context);
    	System.out.println("ON MAKE PV");
    	scroller = new ScrollView(context);
        init();
    }

    public ProjectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller = new ScrollView(context);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Style.FILL_AND_STROKE);
        for(TrackView tv: tracks){
        	scroller.addView(tv);
        }
    }
    
    @Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  
    @Override
    public void onDraw(Canvas c) {
    	System.out.println("ON DRAW PV");
    	super.onDraw(c);
    	c.drawColor(background);
        
        }
}

