//package com.androidlearner.widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.widget.TextView;
//import com.teamluper.luper.Playhead
//
//
///**
// * Created with IntelliJ IDEA.
// * User: ericsmith
// * Date: 4/25/13
// * Time: 7:47 PM
// * To change this template use File | Settings | File Templates.
// */
//public class DragThingPlayhead extends Playhead{
//
//  //private variable shtuff
//  private int current; // the position
//
//  //original x and y vals (to be changed when dragged)
//  private int prevX = 100;
//
//  //constructor!
//  public DragThingPlayhead(Context context, AttributeSet attribute, int style) {
//    super(context, attribute, style);
//  }
//  public DragThingPlayhead(Context context, AttributeSet attribute) {
//    this(context, attribute, 0);
//  }
//  //returns the position
//  public int getCurrent(){ return current; }
//
//
//  @Override
//  public boolean onTouchEvent(MotionEvent event) {
//
//    final int action = event.getAction();
//
//    final int x = (int)event.getX(); //teh current x val
//    //final int y = (int)event.getY(); //teh current y val
//
//    switch(action){
//      case MotionEvent.ACTION_DOWN:
//        prevx = x; //updates points
//        prevy = y;
//        break;
//
//      case MotionEvent.ACTION_MOVE:
//        int changex = x - prevx; //calculates the change in position
//        int changey = y - prevy;
//        final int left = getLeft();
//        final int top = getTop();
//        if(changex != 0 || changey != 0) { // if there is a change, then update the layout
//          //values and stuff
//          layout(left + changex, top + changey, left + changex + getWidth(), top + changey + getHeight());
//        }
//
//        prevx = x - changex;
//        prevy = y - changey;
//
//        //redefining array
//        current[0] = left + changex;
//        current[1] = top + changey;
//        current[2] = left + changex + getWidth();
//        current[3] = top + changey + getHeight();
//        break;
//
//      case MotionEvent.ACTION_UP:
//        break;
//
//      case MotionEvent.ACTION_CANCEL:
//        break;
//    }
//
//
//    return true;
//  }
//}
//
//
//
//}
