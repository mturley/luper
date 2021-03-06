package com.teamluper.luper;

import android.media.MediaPlayer;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Clip {
  private SQLiteDataSource dataSource;

  // database field variables
  private long id;
  private long ownerUserID;
  private long parentTrackID;
  private long audioFileID;
  private int startTime;
  private int durationMS;
  private int loopCount;
  private int color;
  private boolean isLocked;
  private String playbackOptions;
  private boolean isDirty; // dirty = contains unsynced changes

  // references to relevant data
  public AudioFile audioFile = null;
  public Track parentTrack = null;

  public int remainingLoops;

  // references to any views depending on this data, so we can invalidate them automatically on set___ calls.
  public ArrayList<View> associatedViews = null;

  // Brad's variables (WE NEED TO REMOVE THESE)
	String name = null;
	int begin, end, duration; // FIXME these will need to be removed and instead the above stuff used

	//needs an array of attributes/filters/modifications?
	// yeah, eventually playbackOptions will be that -Mike

  // NOTE: DO NOT CALL THIS CONSTRUCTOR DIRECTLY unless in a cursorToClip method.
  // instead, use SQLiteDataSource.createClip()!
	public Clip(SQLiteDataSource dataSource, long id, long ownerUserID,
              long parentTrackID, long audioFileID, int startTime,
              int durationMS, int loopCount, int color, boolean isLocked,
              String playbackOptions, boolean isDirty) {
	  this.dataSource = dataSource;
	  this.id = id;
	  this.ownerUserID = ownerUserID;
	  this.parentTrackID = parentTrackID;
	  this.audioFileID = audioFileID;
	  this.startTime = startTime;
	  this.durationMS = durationMS;
	  this.loopCount = loopCount;
    this.remainingLoops = loopCount;
    this.color = color;
	  this.isLocked = isLocked;
	  this.playbackOptions = playbackOptions;
	  this.isDirty = isDirty;
    this.associatedViews = new ArrayList<View>();

	  // brad's stuff
		name = null;
		begin = 0;
		end = duration;
	}

  public void addAssociatedView(View view) {
    this.associatedViews.add(view);
    Log.i("luper", "TRACK ASSOCIATED VIEWS SIZE: " + this.associatedViews.size());
  }
  public void removeAssociatedView(View view) {
    this.associatedViews.remove(view);
  }
  public ArrayList<View> getAssociatedViews() {
    return this.associatedViews;
  }
  public void invalidateAssociatedViews() {
    for(View v : this.associatedViews) {
      if(ColorChipButton.class.isInstance(v)) {
        ((ColorChipButton) v).render();
      }
      v.requestLayout();
      v.refreshDrawableState();
      if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
        // we're in the main-thread / UI Thread.
        v.invalidate();
      } else {
        // we're in a background thread.
        v.postInvalidate();
      }
    }
  }

  //CANNOT USE THIS, NEED TO USE THE DB CALLS. THIS IS ONLY HERE SO AudioRecorderTest COMPILES, ART IS OUTDATED.
  Clip(String Cname) {
    dataSource = null;
  }

  public void resetLoop() {
    this.remainingLoops = this.loopCount;
  }


  // mike's database getters and setters.
  // TODO migrate all above stuff to use the below fields, setters, and getters
	public long getId() { return id; }
	public void setId(long id) {
		long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Clips", oldId, "_id", id);
    invalidateAssociatedViews();
    this.isDirty = true;
	}


  // TODO: THIS STUFF NEEDS TO BE REMOVED!!!
	public void setBegin(int begin){
		this.begin = begin;
	}
	public void setEnd(int end){
		this.end = end;
	}
	public void setDuration(int duration){
		this.duration = duration;
	}


	public long getOwnerUserID() { return ownerUserID; }
	public void setOwnerUserID(long ownerUserID) {
		this.ownerUserID = ownerUserID;
    dataSource.updateLong("Clips", this.id, "ownerUserID", ownerUserID);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public long getParentTrackID() { return parentTrackID; }
	public void setParentTrackID(long parentTrackID) {
		this.parentTrackID = parentTrackID;
    dataSource.updateLong("Clips", this.id, "parentTrackID", parentTrackID);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public long getAudioFileID() { return audioFileID; }
	public void setAudioFileID(long audioFileID) {
		this.audioFileID = audioFileID;
    dataSource.updateLong("Clips", this.id, "audioFileID", audioFileID);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public int getStartTime() { return startTime; }
	public void setStartTime(int startTime) {
		this.startTime = startTime;
    dataSource.updateInt("Clips", this.id, "startTime", startTime);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public int getDurationMS() { return durationMS; }
	public void setDurationMS(int durationMS) {
		this.durationMS = durationMS;
    dataSource.updateInt("Clips", this.id, "durationMS", durationMS);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public int getLoopCount() { return loopCount;	}
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
    dataSource.updateInt("Clips", this.id, "loopCount", loopCount);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

  public int getColor() { return color; }
  public void setColor(int color) {
    this.color = color;
    dataSource.updateInt("Clips", this.id, "color", color);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

	public boolean isLocked() { return isLocked; }
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
    dataSource.updateInt("Clips", this.id, "isLocked", (isLocked ? 1 : 0));
    invalidateAssociatedViews();
    this.isDirty = true;
	}

	public String getPlaybackOptions() { return playbackOptions; }
	public void setPlaybackOptions(String playbackOptions) {
		this.playbackOptions = playbackOptions;
    dataSource.updateString("Clips", this.id, "playbackOptions", playbackOptions);
    invalidateAssociatedViews();
    this.isDirty = true;
	}

  public boolean isDirty() { return isDirty; }
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
    dataSource.updateInt("Clips", this.id, "isDirty", (isDirty ? 1 : 0));
    invalidateAssociatedViews();
	}

	public AudioFile getAudioFile() {
		return this.audioFile;
	}

  public void loadFileMetadata() {
    this.audioFile = dataSource.getAudioFileById(this.audioFileID);
  }

  public void loadAudio() {
    if(this.audioFile == null) loadFileMetadata();
    this.audioFile.loadAudio();
  }

  public boolean deleteFromProject() {
    // first, delete from the db
    this.dataSource.deleteClip(this.id);
    // fail if we have no reference to parentTrack (set immediately after construction)
    if(this.parentTrack == null) return false;
    for(View trackview : this.parentTrack.getAssociatedViews()) {
      if(TrackView.class.isInstance(trackview)) { // is this really a TrackView?
        // remove any and all colorchips associated with this clip
        TrackView tv = ((TrackView) trackview);
        for(View clipview : this.associatedViews) {
          tv.removeView(clipview);
        }
      }
    }
    return true;
  }
}
