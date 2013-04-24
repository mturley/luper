package com.teamluper.luper;

import android.media.MediaPlayer;

import java.io.FileDescriptor;
import java.io.FileInputStream;

public class Clip {
  // Mike's database field variables
  private long id;
  private long ownerUserID;
  private long parentTrackID;
  private long audioFileID;
  private int startTime;
  private int durationMS;
  private int loopCount;
  private boolean isLocked;
  private String playbackOptions;
  private boolean isDirty; // dirty = contains unsynced changes

  // Mike's database access variables
  private SQLiteDataSource dataSource;

  // references to relevant data
  private AudioFile audioFile = null;

  // Brad's variables
	String name = null;
	int begin, end, duration; // FIXME these will need to be removed and instead the above stuff used

	//needs an array of attributes/filters/modifications?
	// yeah, eventually playbackOptions will be that -Mike

  // NOTE: DO NOT CALL THIS CONSTRUCTOR DIRECTLY unless in a cursorToClip method.
  // instead, use SQLiteDataSource.createClip()!
	public Clip(SQLiteDataSource dataSource, long id, long ownerUserID,
              long parentTrackID, long audioFileID, int startTime,
              int durationMS, int loopCount, boolean isLocked,
              String playbackOptions, boolean isDirty) {
	  this.dataSource = dataSource;
	  this.id = id;
	  this.ownerUserID = ownerUserID;
	  this.parentTrackID = parentTrackID;
	  this.audioFileID = audioFileID;
	  this.startTime = startTime;
	  this.durationMS = durationMS;
	  this.loopCount = loopCount;
	  this.isLocked = isLocked;
	  this.playbackOptions = playbackOptions;
	  this.isDirty = isDirty;
	  // brad's stuff
		name = null;
		durationMS = 0;
		begin = 0;
		end = duration;
	}
	public Clip() {
	  dataSource = null;
	}
	public Clip(String cName)
	{
	  dataSource = null;
	}
	//Extra length method using mediaplayer which oddly uses an integer for the duration
	public int getDuration() //FIXME update database with current duration , refactor
	{
		try {
			MediaPlayer mp = new MediaPlayer();
			FileInputStream fs;
			FileDescriptor fd;
			fs = new FileInputStream(name);
			fd = fs.getFD();
			mp.setDataSource(fd);
			mp.prepare();
			int length = mp.getDuration();
			mp.release();
			duration = length;
		} catch (Exception e) {
			e.printStackTrace();
			duration = 0;
		}
		return duration;
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
