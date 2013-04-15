package com.teamluper.luper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import android.media.MediaPlayer;

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

  // Brad's variables
	String name = null;
	int begin, end, duration; // FIXME these will need to be removed and instead the above stuff used

	//needs an array of attributes/filters/modifications?
	// yeah, eventually playbackOptions will be that -Mike
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
	  name = null;
	  duration = 0;
	  begin = 0;
	  end = duration;
	}
	public Clip(String cName)
	{
	  dataSource = null;
		name = cName;
		duration = 0;
		begin = 0;
		end = duration;
	}
	//Getters and setters for the fields TODO cleanup
	public String getClip()
	{
		return name;
	}
	public long getBegin()
	{
		return begin;
	}
	public long getEnd()
	{
		return end;
	}
	public void setClip(String cName)
	{
		name = cName;
	}
	public void setStart(int start)
	{
		begin = start;
	}
	public void setEnd(int stop)
	{
		end = stop;
	}
	//Extra length method using mediaplayer which oddly uses an integer for the duration
	public int getDuration()
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
			// TODO Auto-generated catch block
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




	//calculates the total playing length of the clip as an int, keeping in line with getDuration
	public int calcLength()
	{
		duration = end-begin;
		return duration;
	}
	/*//returns the clips path and save location as a string
	public String getClipPath()
	{
		String path = this.name.getPath();
		return path;
	}*/
}
