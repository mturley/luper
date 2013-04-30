package com.teamluper.luper;

import android.media.MediaPlayer;
import android.view.View;

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
  }
  public void removeAssociatedView(View view) {
    this.associatedViews.remove(view);
  }

  //CANNOT USE THIS, NEED TO USE THE DB CALLS. THIS IS ONLY HERE SO AudioRecorderTest COMPILES, ART IS OUTDATED.
  Clip(String Cname) {
    dataSource = null;
  }

  // mike's database getters and setters.
  // TODO migrate all above stuff to use the below fields, setters, and getters
	public long getId() { return id; }
	public void setId(long id) {
		long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Clips", oldId, "_id", id);
    this.isDirty = true;
	}

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
    this.isDirty = true;
	}

	public long getParentTrackID() { return parentTrackID; }
	public void setParentTrackID(long parentTrackID) {
		this.parentTrackID = parentTrackID;
    dataSource.updateLong("Clips", this.id, "parentTrackID", parentTrackID);
    this.isDirty = true;
	}

	public long getAudioFileID() { return audioFileID; }
	public void setAudioFileID(long audioFileID) {
		this.audioFileID = audioFileID;
    dataSource.updateLong("Clips", this.id, "audioFileID", audioFileID);
    this.isDirty = true;
	}

	public int getStartTime() { return startTime; }
	public void setStartTime(int startTime) {
		this.startTime = startTime;
    dataSource.updateInt("Clips", this.id, "startTime", startTime);
    this.isDirty = true;
	}

	public int getDurationMS() { return durationMS; }
	public void setDurationMS(int durationMS) {
		this.durationMS = durationMS;
    dataSource.updateInt("Clips", this.id, "durationMS", durationMS);
    this.isDirty = true;
	}

	public int getLoopCount() { return loopCount;	}
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
    dataSource.updateInt("Clips", this.id, "loopCount", loopCount);
    this.isDirty = true;
	}

  public int getColor() { return color; }
  public void setColor(int color) {
    this.color = color;
    dataSource.updateInt("Clips", this.id, "color", color);
  }

	public boolean isLocked() { return isLocked; }
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
    dataSource.updateInt("Clips", this.id, "isLocked", (isLocked ? 1 : 0));
    this.isDirty = true;
	}

	public String getPlaybackOptions() { return playbackOptions; }
	public void setPlaybackOptions(String playbackOptions) {
		this.playbackOptions = playbackOptions;
    dataSource.updateString("Clips", this.id, "playbackOptions", playbackOptions);
    this.isDirty = true;
	}

  public boolean isDirty() { return isDirty; }
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
    dataSource.updateInt("Clips", this.id, "isDirty", (isDirty ? 1 : 0));
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
}
