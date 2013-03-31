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
import java.util.Random;
import java.util.ArrayList;

/**
 * A custom view for a color chip for an event that can be drawn differently
 * according to the event's status.
 *
 */
public class TrackView extends ViewGroup{

	//a tag for XML layouts, Manifest
    private static final String TAG = "TrackView";
    // Style of drawing
    // Full rectangle for accepted events
    // Border for tentative events
    // Cross-hatched with 50% transparency for declined events
    
    private static final int CLICKED = 0;
    private static final int UNCLICKED = 1;
    private static final int DEF_BORDER_WIDTH = 4;

    int mBorderWidth = DEF_BORDER_WIDTH;

    int mColor;
    Paint mPaint;
    ArrayList<ColorChipView> colorChip = new ArrayList<ColorChipView>();

    public TrackView(Context context) {
        super(context);
    	System.out.println("ON MAKE PV");
        init();
    }

    public TrackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mDefStrokeWidth = mPaint.getStrokeWidth();
        mPaint.setStyle(Style.FILL_AND_STROKE);
    }


    public void setDrawStyle(int style) {
        if (style != CLICKED && style != UNCLICKED) {
            return;
        }
        mDrawStyle = style;
        invalidate();
    }

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
    }
    
    public boolean onKeyDown (int keyCode, KeyEvent event){
    	return super.onKeyDown(keyCode,event);
    }

    @Override
    public void onDraw(Canvas c) {
    	System.out.println("ON DRAW CCV");
    	super.onDraw(c);
        int right = getWidth() - 1;
        int bottom = getHeight() - 1;

        switch (mDrawStyle) {
            case UNCLICKED:
                mPaint.setStrokeWidth(mDefStrokeWidth);
                c.drawRect(100, 100, right, bottom, mPaint);
                break;
            case UNCLICKED:
                if (mBorderWidth <= 0) {
                    return;
                }
                int halfBorderWidth = mBorderWidth / 2;
                int top = halfBorderWidth;
                int left = halfBorderWidth;
                mPaint.setStrokeWidth(mBorderWidth);

                float[] lines = new float[16];
                int ptr = 0;
                lines [ptr++] = 0;
                lines [ptr++] = top;
                lines [ptr++] = right;
                lines [ptr++] = top;
                lines [ptr++] = 0;
                lines [ptr++] = bottom - halfBorderWidth;
                lines [ptr++] = right;
                lines [ptr++] = bottom - halfBorderWidth;
                lines [ptr++] = left;
                lines [ptr++] = 0;
                lines [ptr++] = left;
                lines [ptr++] = bottom;
                lines [ptr++] = right - halfBorderWidth;
                lines [ptr++] = 0;
                lines [ptr++] = right - halfBorderWidth;
                lines [ptr++] = bottom;
                c.drawLines(lines, mPaint);
                break;
        }
    }

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		
	}
}


