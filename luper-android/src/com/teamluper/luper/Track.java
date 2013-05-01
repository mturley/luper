//Track class will hold a sequence of clips. Tracks will be
//played simultaneously with other tracks. Volume will be
//adjustable at a track level as well.
//
//Created by: Cam 3/30/13
//Adapted by: Mike 4/1/13

package com.teamluper.luper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.androidlearner.widget.DragThing;

public class Track {
  private SQLiteDataSource dataSource;

  // database field variables
	private long id;
	private long ownerUserID;
	private long parentSequenceID;
	private boolean isMuted;
	private boolean isLocked;
	private String playbackOptions;
	private boolean isDirty; // dirty = contains unsynced changes

  // what's going on with these??
	DragThing deMovingTxt;
	int [] paramz;

	// references to related data
	public ArrayList<Clip> clips;
  public Clip nextClip;

  // references to any views depending on this data, so we can invalidate them automatically on set___ calls.
  public ArrayList<View> associatedViews = null;
  public TrackView trackView = null;

  // NOTE: DO NOT CALL THIS CONSTRUCTOR DIRECTLY unless in a cursorToTrack method.
  // instead, use SQLiteDataSource.createTrack()!
	public Track(SQLiteDataSource dataSource, long id, long ownerUserID,
               long parentSequenceID, boolean isMuted, boolean isLocked,
               String playbackOptions, boolean isDirty) {
	  this.dataSource = dataSource;
	  this.id = id;
	  this.ownerUserID = ownerUserID;
	  this.parentSequenceID = parentSequenceID;
	  this.isMuted = isMuted;
	  this.isLocked = isLocked;
	  this.playbackOptions = playbackOptions;
	  this.isDirty = isDirty;
    this.clips = new ArrayList<Clip>();
    this.associatedViews = new ArrayList<View>();
    this.nextClip = null;
	}

  public void addAssociatedView(View view) {
    this.associatedViews.add(view);
    if(TrackView.class.isInstance(view)) {
      this.trackView = (TrackView) view;
    }
  }
  public void removeAssociatedView(View view) {
    this.associatedViews.remove(view);
  }
  public ArrayList<View> getAssociatedViews() {
    return this.associatedViews;
  }
  public void invalidateAssociatedViews() {
    for(View v : this.associatedViews) {
      if(TrackView.class.isInstance(v)) {
        ((TrackView) v).render();
      }
      v.requestLayout();
      v.refreshDrawableState();
      if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
        // we're in the main-thread / UI Thread.
        Log.i("luper", "INVALIDATE CALLING");
        v.invalidate();
      } else {
        // we're in a background thread.
        Log.i("luper", "POSTINVALIDATE CALLING");
        v.postInvalidate();
      }
    }
  }


	// temporary constructor for compatability with other files
  // TODO: WE NEED TO REMOVE THIS!
	public Track() {
	  this.dataSource = null;
	}

	// Mike's getters and setters for database abstraction
	public long getId() { return id; }
	public void setId(long id) {
    long oldId = this.id;
	  this.id = id;
    dataSource.updateLong("Tracks", oldId, "_id", id);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public long getOwnerUserID() { return ownerUserID; }
	public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
    dataSource.updateLong("Tracks", this.id, "ownerUserID", ownerUserID);
    invalidateAssociatedViews();
    this.isDirty = true;
	}
	public long getParentSequenceID() { return parentSequenceID; }
  public void setParentSequenceID(long parentSequenceID) {
    this.parentSequenceID = parentSequenceID;
    dataSource.updateLong("Tracks", this.id, "parentSequenceID", parentSequenceID);
    invalidateAssociatedViews();
    this.isDirty = true;
  }
	public boolean isMuted() { return isMuted; }
  public void setMuted(boolean isMuted) {
    this.isMuted = isMuted;
    dataSource.updateInt("Tracks", this.id, "isMuted", (isMuted ? 1 : 0));
    invalidateAssociatedViews();
    this.isDirty = true;
  }
  public boolean isLocked() { return isLocked; }
  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
    dataSource.updateInt("Tracks", this.id, "isLocked", (isLocked ? 1 : 0));
    invalidateAssociatedViews();
    this.isDirty = true;
  }
  public String getPlaybackOptions() { return playbackOptions; }
  public void setPlaybackOptions(String playbackOptions) {
    this.playbackOptions = playbackOptions;
    dataSource.updateString("Tracks", this.id, "playbackOptions", playbackOptions);
    invalidateAssociatedViews();
    this.isDirty = true;
  }
  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
    dataSource.updateInt("Tracks", this.id, "isDirty", (isDirty ? 1 : 0));
    invalidateAssociatedViews();
  }

  public void loadAllClipData() {
    this.clips = (ArrayList<Clip>) dataSource.getClipsByTrackId(this.id);
    for(Clip clip : this.clips) {
      clip.loadFileMetadata();
    }
  }

  public void loadAllClipAudio() {
    if(this.clips == null) loadAllClipData();
    for(Clip clip : this.clips) {
      clip.loadAudio();
    }
  }

  public boolean prepareNextClip(int afterTimeMS) {
    // sort all clips by startTime
    Collections.sort(this.clips, new Comparator<Clip>() {
      public int compare(Clip a, Clip b) {
        return a.getStartTime() - b.getStartTime();
      }
    });
    // iterate through them and find the one following afterTimeMS
    Clip found = null;
    for(Clip c : this.clips) {
      if(c.getStartTime() > afterTimeMS) {
        found = c;
        break;
      }
    }
    if(found != null) {
      this.nextClip = found;
      this.trackView.prepareClip(this.nextClip);
      return true;
    }
    return false;
  }

  //SIZE
  public int size()
  {
    return clips.size();
  }

  public ArrayList<Clip> getClips() {
		return this.clips;
	}

  //test methods
  public int findLargestClip()
  {
    int max=0, length;
    for(Clip c : clips)
    {
      length = c.getDurationMS()-c.getStartTime();
      if(length>max)
        max=length;
    }
    return max;
  }
  public int findLastClipTime()
  {
    int temp = 0, last=0;
    for(Clip c : clips)
    {
      if(c!=null)
      {
        temp = c.getStartTime()+c.getDurationMS();
        if(temp>last)
          last=temp;
      }
      else
        last = 100;
    }
    return last;
  }
}
