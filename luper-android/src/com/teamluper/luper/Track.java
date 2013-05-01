//Track class will hold a sequence of clips. Tracks will be
//played simultaneously with other tracks. Volume will be
//adjustable at a track level as well.
//
//Created by: Cam 3/30/13
//Adapted by: Mike 4/1/13

package com.teamluper.luper;

import java.util.ArrayList;

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

  private TrackView associatedView = null;

  // what's going on with these??
	DragThing deMovingTxt;
	int [] paramz;

	// references to relevant data
	public ArrayList<Clip> clips;

  // references to any views depending on this data, so we can invalidate them automatically on set___ calls.
  public ArrayList<View> associatedViews = null;

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
	}

  public void addAssociatedView(View view) {
    this.associatedViews.add(view);
  }
  public void removeAssociatedView(View view) {
    this.associatedViews.remove(view);
  }
  public ArrayList<View> getAssociatedViews() {
    return this.associatedViews;
  }
  public void invalidateAssociatedViews() {
    for(View v : this.associatedViews) {
      v.invalidate();
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

  //SIZE
  public int size()
  {
    return clips.size();
  }

  public ArrayList<Clip> getClips() {
		return this.clips;
	}

	//gets the track length by calculating the length of all the clips it contains
	public long getTrackLength()
	{
		long sum = 0;
		for(Clip c:clips)
		{
			sum+=c.getDurationMS();
		}
		return sum;
	}

}
