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

import android.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewSwitcher;
import android.widget.ViewSwitcher.ViewFactory;

import java.util.Random;
import java.util.ArrayList;

/**
 * A custom view for a color chip for an event that can be drawn differently
 * according to the event's status.
 *
 */
public class TrackView extends ViewGroup {

	//a tag for XML layouts, Manifest
    private static final String TAG = "TrackView";
    // Style of drawing
    // Full rectangle for accepted events
    // Border for tentative events
    // Cross-hatched with 50% transparency for declined events
    
    private static final int CLICKED = 0;
    private static final int UNCLICKED = 1;
    private static final int DEF_BORDER_WIDTH = 4;
    private float mDefStrokeWidth;

    int mBorderWidth = DEF_BORDER_WIDTH;

    int mColor;
    Paint mPaint;
    
    ArrayList<Clip> clips;
    ArrayList<ColorChipView> chips = new ArrayList<ColorChipView>();
    ImageButton play;
//  ViewSwitcher switcher;
    OnClickListener listener;
    
    public TrackView(Context context, ArrayList<Clip> c) {
        super(context);
        clips = c;
    	
//    	switcher = new ViewSwitcher(context);
        init(context);
        System.out.println("ON MAKE TV");
    }

    public TrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        System.out.println("ON MAKE TV");
    }
    
    @Override public void setOnClickListener(TrackView.OnClickListener l){
    	super.setOnClickListener(l);
    }
    

    private void init(Context context) {
    	
        mPaint = new Paint();
        mDefStrokeWidth = mPaint.getStrokeWidth();
        mPaint.setStyle(Style.FILL_AND_STROKE);
        
        play = new ImageButton(context);
        play.setImageResource(getResources().getIdentifier(
    			"ic_menu_play_clip", 
    			"drawable",
    			"com.teamluper.luper"));
        this.addView(play);
        play.setClickable(true);
        
        for(Clip clip: clips){
        	chips.add(new ColorChipView(context, clip));
        	this.addView(chips.get(clips.indexOf(clip)));
        }
        this.setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, 100));
        
        requestLayout();
    }

//    public void setDrawStyle(int style) {
//        if (style != CLICKED && style != UNCLICKED) {
//            return;
//        }
//        mDrawStyle = style;
//        invalidate();
//    }

    public void setBorderWidth(int width) {
        if (width >= 0) {
            mBorderWidth = width;
            invalidate();
        }
    }

    public void setColor(int color) {
        mColor = color;
        invalidate();
    }
    
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	measureChildren(widthMeasureSpec,heightMeasureSpec);
    }
    
    public boolean onKeyDown (int keyCode, KeyEvent event){
    	return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onDraw(Canvas c) {
    	System.out.println("ON DRAW TV");
    	super.onDraw(c);
        mPaint.setColor(Color.parseColor("80#000000"));
        int right = getWidth();
        int bottom = getHeight() - 50;
        c.drawRect(10, getWidth(), right, bottom, mPaint);

        }

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		MarginLayoutParams lps = (MarginLayoutParams) this.getLayoutParams();
		play.layout(
				20 + lps.leftMargin,
				lps.topMargin - 20,
				lps.WRAP_CONTENT,
				20 + lps.bottomMargin
				);
		for (ColorChipView ccv: chips) {
				
				// optimization: we are moving through the children in order
				// when we hit null, there are no more children to layout so return
				if (ccv == null) return;
				// get the child's layout parameters so that we can honour their margins
				// we don't support gravity, so the arithmetic is simplified
				if (ccv == chips.get(0)){
					ccv.layout(
							70 + lps.leftMargin,
							lps.topMargin - 20,
							lps.WRAP_CONTENT,
							20 + lps.bottomMargin
							);
				}
			}
		
	}

}


