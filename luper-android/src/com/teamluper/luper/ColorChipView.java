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
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;




/**
 * A custom view for a color chip for an event that can be drawn differently
 * accroding to the event's status.
 *
 */
public class ColorChipView extends View {

	//a tag for XML layouts, Manifest
    private static final String TAG = "ColorChipView";
    // Style of drawing
    // Full rectangle for accepted events
    // Border for tentative events
    // Cross-hatched with 50% transparency for declined events

    public static final int CLICKED = 0;
    public static final int UNCLICKED = 1;
    Random r = new Random();

    private int mDrawStyle = UNCLICKED;
    private float mDefStrokeWidth;
    private Paint mPaint;

    private static final int DEF_BORDER_WIDTH = 4;

    int mBorderWidth = DEF_BORDER_WIDTH;

    int mColor;

    public ColorChipView(Context context) {
        super(context);
        init();
    }

    public ColorChipView(Context context, AttributeSet attrs) {
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

    @Override
    public void onDraw(Canvas c) {

        int right = getWidth() - 1;
        int bottom = getHeight() - 1;
        mPaint.setColor(r.nextInt(256));

        switch (mDrawStyle) {
            case UNCLICKED:
                mPaint.setStrokeWidth(mDefStrokeWidth);
                c.drawRect(0, 0, right, bottom, mPaint);
                break;
            case CLICKED:
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
}

