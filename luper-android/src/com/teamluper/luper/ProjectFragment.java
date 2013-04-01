//package com.teamluper.luper;
///*
// * Copyright (C) 2011 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//import android.annotation.TargetApi;
//import android.app.Fragment;
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.view.ViewGroup;
//import android.graphics.Paint;
//import android.graphics.Paint.Style;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ScrollView;
//import android.widget.ViewSwitcher;
//import android.widget.ViewSwitcher.ViewFactory;
//import java.util.Random;
//import java.util.ArrayList;
//
//
///**
// * A custom view for a color chip for an event that can be drawn differently
// * according to the event's status.
// *
// */
//
//public class ProjectFragment extends Fragment implements ViewFactory{
//
//	//a tag for XML layouts, Manifest
//    private static final String TAG = "ProjectView";
//    // Style of drawing
//    // Full rectangle for accepted events
//    // Border for tentative events
//    // Cross-hatched with 50% transparency for declined events
//    private final int background = Color.LTGRAY;
//    int mColor;
//    Paint mPaint;
//    
//    ArrayList<TrackView> tracks = new ArrayList<TrackView>();
//    TrackView SELECTED;
//    ViewSwitcher switcher;
//    ScrollView scroller;
//    ExampleProject exampleproject = (ExampleProject)this.getActivity();
//    Context context = (Context)exampleproject;
//    
//
//    public ProjectFragment() {
//    	System.out.println("ON MAKE PF");
//    	scroller = new ScrollView(context);
//    	switcher = new ViewSwitcher(context);
//        init();
//    }
//
////    public ProjectFragment(Context context, AttributeSet attrs) {
////        super(context, attrs);
////    	System.out.println("ON MAKE PF");
////        scroller = new ScrollView(context);
////    	switcher = new ViewSwitcher(context);
////        init();
////    }
//
//    private void init() {
//        mPaint = new Paint();
//        mPaint.setStyle(Style.FILL_AND_STROKE);
//        for(TrackView tv: tracks){
//        	scroller.addView(tv);
//        }
//    }
//    
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        View v = tracks.get(tracks.indexOf(SELECTED));
//
//        switcher = (ViewSwitcher)v;
//        switcher.setFactory(this);
//        switcher.getCurrentView().requestFocus();
////      ((TrackView) switcher.getCurrentView()).updateTitle();
//
//        return v;
//    }
//    
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
//    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//  
//    @Override
//    public void onDraw(Canvas c) {
//    	System.out.println("ON DRAW PV");
//    	super.onDraw(c);
//    	c.drawColor(background);
//        
//        }
//
//	@Override
//	public View makeView() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//	/*
//	 * Copyright (C) 2010 The Android Open Source Project
//	 *
//	 * Licensed under the Apache License, Version 2.0 (the "License");
//	 * you may not use this file except in compliance with the License.
//	 * You may obtain a copy of the License at
//	 *
//	 *      http://www.apache.org/licenses/LICENSE-2.0
//	 *
//	 * Unless required by applicable law or agreed to in writing, software
//	 * distributed under the License is distributed on an "AS IS" BASIS,
//	 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	 * See the License for the specific language governing permissions and
//	 * limitations under the License.
//	 */
//
////	/**
////	 * This is the base class for Day and Week Activities.
////	 */
////	public class DayFragment extends Fragment implements CalendarController.EventHandler, ViewFactory {
////	    /**
////	     * The view id used for all the views we create. It's OK to have all child
////	     * views have the same ID. This ID is used to pick which view receives
////	     * focus when a view hierarchy is saved / restore
////	     */
////	    private static final int VIEW_ID = 1;
////
////	    protected static final String BUNDLE_KEY_RESTORE_TIME = "key_restore_time";
////
////	    protected ProgressBar mProgressBar;
////	    protected ViewSwitcher mViewSwitcher;
////	
////	    Time mSelectedTrack = new TrackView();
////
////	    private final Runnable mTZUpdater = new Runnable() {
////	        @Override
////	        public void run() {
////	            if (!DayFragment.this.isAdded()) {
////	                return;
////	            }
////	            String tz = Utils.getTimeZone(getActivity(), mTZUpdater);
////	            mSelectedDay.timezone = tz;
////	            mSelectedDay.normalize(true);
////	        }
////	    };
////
////	    //number of tracks visible in the pane
////	    private int mNumTracks;
////
////	    public TrackFragment() {
////	        mSelectedDay.setToNow();
////	    }
////
////	    public DayFragment(long timeMillis, int numOfDays) {
////	        mNumDays = numOfDays;
////	        if (timeMillis == 0) {
////	            mSelectedDay.setToNow();
////	        } else {
////	            mSelectedDay.set(timeMillis);
////	        }
////	    }
////
////	    @Override
////	    public void onCreate(Bundle icicle) {
////	        super.onCreate(icicle);
////
////	        Context context = getActivity();
////
////	        mInAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_in);
////	        mOutAnimationForward = AnimationUtils.loadAnimation(context, R.anim.slide_left_out);
////	        mInAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
////	        mOutAnimationBackward = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);
////
////	        mEventLoader = new EventLoader(context);
////	    }
////
////	    @Override
////	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////	            Bundle savedInstanceState) {
////	        View v = inflater.inflate(R.layout.day_activity, null);
////
////	        mViewSwitcher = (ViewSwitcher) v.findViewById(R.id.switcher);
////	        mViewSwitcher.setFactory(this);
////	        mViewSwitcher.getCurrentView().requestFocus();
////	        ((DayView) mViewSwitcher.getCurrentView()).updateTitle();
////
////	        return v;
////	    }
////
////	    public View makeView() {
////	        mTZUpdater.run();
////	        DayView view = new DayView(getActivity(), CalendarController
////	                .getInstance(getActivity()), mViewSwitcher, mEventLoader, mNumDays);
////	        view.setId(VIEW_ID);
////	        view.setLayoutParams(new ViewSwitcher.LayoutParams(
////	                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
////	        view.setSelected(mSelectedDay, false, false);
////	        return view;
////	    }
////
////	    @Override
////	    public void onResume() {
////	        super.onResume();
////	        mEventLoader.startBackgroundThread();
////	        mTZUpdater.run();
////	        eventsChanged();
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        view.handleOnResume();
////	        view.restartCurrentTimeUpdates();
////
////	        view = (DayView) mViewSwitcher.getNextView();
////	        view.handleOnResume();
////	        view.restartCurrentTimeUpdates();
////	    }
////
////	    @Override
////	    public void onSaveInstanceState(Bundle outState) {
////	        super.onSaveInstanceState(outState);
////
////	        long time = getSelectedTimeInMillis();
////	        if (time != -1) {
////	            outState.putLong(BUNDLE_KEY_RESTORE_TIME, time);
////	        }
////	    }
////
////	    @Override
////	    public void onPause() {
////	        super.onPause();
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        view.cleanup();
////	        view = (DayView) mViewSwitcher.getNextView();
////	        view.cleanup();
////	        mEventLoader.stopBackgroundThread();
////
////	        // Stop events cross-fade animation
////	        view.stopEventsAnimation();
////	        ((DayView) mViewSwitcher.getNextView()).stopEventsAnimation();
////	    }
////
////	    void startProgressSpinner() {
////	        // start the progress spinner
////	        mProgressBar.setVisibility(View.VISIBLE);
////	    }
////
////	    void stopProgressSpinner() {
////	        // stop the progress spinner
////	        mProgressBar.setVisibility(View.GONE);
////	    }
////
////	    private void goTo(Time goToTime, boolean ignoreTime, boolean animateToday) {
////	        if (mViewSwitcher == null) {
////	            // The view hasn't been set yet. Just save the time and use it later.
////	            mSelectedDay.set(goToTime);
////	            return;
////	        }
////
////	        DayView currentView = (DayView) mViewSwitcher.getCurrentView();
////
////	        // How does goTo time compared to what's already displaying?
////	        int diff = currentView.compareToVisibleTimeRange(goToTime);
////
////	        if (diff == 0) {
////	            // In visible range. No need to switch view
////	            currentView.setSelected(goToTime, ignoreTime, animateToday);
////	        } else {
////	            // Figure out which way to animate
////	            if (diff > 0) {
////	                mViewSwitcher.setInAnimation(mInAnimationForward);
////	                mViewSwitcher.setOutAnimation(mOutAnimationForward);
////	            } else {
////	                mViewSwitcher.setInAnimation(mInAnimationBackward);
////	                mViewSwitcher.setOutAnimation(mOutAnimationBackward);
////	            }
////
////	            DayView next = (DayView) mViewSwitcher.getNextView();
////	            if (ignoreTime) {
////	                next.setFirstVisibleHour(currentView.getFirstVisibleHour());
////	            }
////
////	            next.setSelected(goToTime, ignoreTime, animateToday);
////	            next.reloadEvents();
////	            mViewSwitcher.showNext();
////	            next.requestFocus();
////	            next.updateTitle();
////	            next.restartCurrentTimeUpdates();
////	        }
////	    }
////
////	    /**
////	     * Returns the selected time in milliseconds. The milliseconds are measured
////	     * in UTC milliseconds from the epoch and uniquely specifies any selectable
////	     * time.
////	     *
////	     * @return the selected time in milliseconds
////	     */
////	    public long getSelectedTimeInMillis() {
////	        if (mViewSwitcher == null) {
////	            return -1;
////	        }
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        if (view == null) {
////	            return -1;
////	        }
////	        return view.getSelectedTimeInMillis();
////	    }
////
////	    public void eventsChanged() {
////	        if (mViewSwitcher == null) {
////	            return;
////	        }
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        view.clearCachedEvents();
////	        view.reloadEvents();
////
////	        view = (DayView) mViewSwitcher.getNextView();
////	        view.clearCachedEvents();
////	    }
////
////	    Event getSelectedEvent() {
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        return view.getSelectedEvent();
////	    }
////
////	    boolean isEventSelected() {
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        return view.isEventSelected();
////	    }
////
////	    Event getNewEvent() {
////	        DayView view = (DayView) mViewSwitcher.getCurrentView();
////	        return view.getNewEvent();
////	    }
////
////	    public DayView getNextView() {
////	        return (DayView) mViewSwitcher.getNextView();
////	    }
////
////	    public long getSupportedEventTypes() {
////	        return EventType.GO_TO | EventType.EVENTS_CHANGED;
////	    }
////
////	    public void handleEvent(EventInfo msg) {
////	        if (msg.eventType == EventType.GO_TO) {
////	// TODO support a range of time
////	// TODO support event_id
////	// TODO support select message
////	            goTo(msg.selectedTime, (msg.extraLong & CalendarController.EXTRA_GOTO_DATE) != 0,
////	                    (msg.extraLong & CalendarController.EXTRA_GOTO_TODAY) != 0);
////	        } else if (msg.eventType == EventType.EVENTS_CHANGED) {
////	            eventsChanged();
////	        }
////	    }
////	}
////
////	
////}
////
