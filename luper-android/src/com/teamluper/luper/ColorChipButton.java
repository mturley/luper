package com.teamluper.luper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.view.View;
import android.widget.*;
import java.util.Random;
import android.graphics.Color;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EView;
import com.googlecode.androidannotations.annotations.UiThread;

@EView
public class ColorChipButton extends Button {

  private static final String TAG = "ColorClipButton";
  public static final float PIXELS_PER_MILLISECOND = 0.3f;

  //the clip that is associated with this CCB
  Clip associated;
  int mColor;
  Random rnd = new Random();

  //constructor sets the associated clip, calls init, and sets the onclicklistener
  public ColorChipButton(Context context, Clip clip){
    super(context);
    associated = clip;
    mColor = clip.getColor();
    init();
    setOnClickListener(clicker);
  }

  //set a click listener for the buttons that will activate promptDialog() when clicked
  OnClickListener clicker = new OnClickListener(){
    public void onClick(View v){
      //promptDialog();
      showListDialog();
    }
  };

  public void showListDialog(){
    final CharSequence[] items = {"Details", "Edit", "Delete", "Cancel"};
    new AlertDialog.Builder(getContext())
        .setTitle("Clip Options")
        .setItems(items, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            if (items[item].equals("Details")) {
              showDetailDialog();
            }
            if (items[item].equals("Edit")) {
              showEditDialog();
            /*DialogFactory.prompt(getContext(),"Edit Start Time","",
              new Lambda.StringCallback() {
                public void go(String value) {
                  int val= Integer.parseInt(value);
                  associated.setStartTime(val);
                }
              }
            );*/
            } else if (items[item].equals("Delete")) {
              final Clip c = associated;
              DialogFactory.confirm(getContext(), "Really Delete Clip?", "The recording used in this clip will " +
                  "not be deleted, only this instance of that audio will be deleted.  You can find the AudioFile " +
                  "again by using the Browse button in the Add Clip dialog.", new Lambda.BooleanCallback() {
                @Override
                public void go(boolean pressedYes) {
                  if (pressedYes) {
                    if (!c.deleteFromProject()) {
                      DialogFactory.alert(getContext(), "Error", "Failed to delete clip");
                    }
                  }
                }
              });
            } else if (items[item].equals("Cancel")) {
              // does nothing and will never do anything
            }
          }
        }).show();
  }
  public void showEditDialog(){
    final CharSequence[] items = {"Start Time", "Loop Count", "Cancel"};
    new AlertDialog.Builder(getContext())
        .setTitle("Clip Edit Options")
        .setItems(items, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int item) {
            if (items[item].equals("Start Time")) {
              DialogFactory.prompt(getContext(), "Edit Start Time", "",
                  new Lambda.StringCallback() {
                    public void go(String value) {
                      int val = Integer.parseInt(value);
                      associated.setStartTime(val);
                    }
                  }
              );

            } else if (items[item].equals("Loop Count")) {
              DialogFactory.prompt(getContext(), "Number of times to Lüp this clip", "",
                  new Lambda.StringCallback() {
                    public void go(String value) {
                      int val = Integer.parseInt(value);
                      associated.setLoopCount(val);
                    }
                  }
              );

            } else if (items[item].equals("Cancel")) {
              // should bring user back to the previous dialog menu
              showListDialog();
            }
          }
        }).show();
  }

    public void showDetailDialog(){
       LinearLayout detailLayout = new LinearLayout(this.getContext());
        detailLayout.setOrientation(LinearLayout.VERTICAL);
        TextView lengthView = new TextView(this.getContext());
        lengthView.setText("Length: " + this.associated.getDurationMS() + " ms");
        TextView startTimeView = new TextView(this.getContext());
        startTimeView.setText("Start Time: " + this.associated.getStartTime() + " ms");

            detailLayout.addView(lengthView);
        detailLayout.addView(startTimeView);

            new AlertDialog.Builder(getContext())
                .setTitle("Clip Details You Fuck")
                .setView(detailLayout)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                      }
              })
                .show();
      }


  public void render() {
    init();
  }

  //this method will determine where the clip should be placed, based on its start time

  @UiThread
  public void init(){
    //this.setStartToEnd();
    //promptDialog();
    this.setX(this.getStartTime()*PIXELS_PER_MILLISECOND+100);
    this.setWidth(Math.round(this.getLength()*PIXELS_PER_MILLISECOND));
    this.setHeight(140);
    this.setBackgroundColor(mColor);
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
    return this.associated.getDurationMS();
  }

  //returns the start time of the button (and clip) in this track
  public int getStartTime(){
    return this.associated.getStartTime();
  }
  public void setColor(int color) {
    mColor = color;
    this.setBackgroundColor(mColor);
    this.invalidateSafely();
  }

  //	when you click the button this method is activated. currently it only shows the length of
//	the clip. eventually it should allow you to modify things about the clip
  public void promptDialog(){
    new AlertDialog.Builder(getContext())
        .setTitle("Clip Details")
        .setMessage("starttime " + associated.parentTrack.findLastClip())
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

  public void invalidateSafely() {
    this.requestLayout();
    if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
      // we're in the main-thread / UI Thread.
      this.invalidate();
    } else {
      // we're in a background thread.
      this.postInvalidate();
    }
  }
  @Background
  public void setStartToEnd()
  {
    this.setX(associated.parentTrack.findLastClip());
  }

}
