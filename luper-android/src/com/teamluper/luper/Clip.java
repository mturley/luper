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
  private LuperDataSource dataSource;
  private boolean autoSaveEnabled;
  
  // Brad's variables
	String name = null;
	int begin, end, duration; // FIXME these will need to be removed and instead the above stuff used
	
	//needs an array of attributes/filters/modifications?
	// yeah, eventually playbackOptions will be that -Mike
	public Clip(LuperDataSource dataSource, boolean autoSaveEnabled) {
		name = null;
		setDurationMS(0);
		begin = 0;
		end = duration;
	}
	public Clip() {
	  dataSource = null;
	  autoSaveEnabled = false;
	  name = null;
	  duration = 0;
	  begin = 0;
	  end = duration;
	}
	public Clip(String cName)
	{
	  dataSource = null;
	  autoSaveEnabled = false;
		name = cName;
		duration = 0;
		begin = 0;
		end = duration;
	}
	//Getters and setters for the fields
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOwnerUserID() {
		return ownerUserID;
	}
	public void setOwnerUserID(long ownerUserID) {
		this.ownerUserID = ownerUserID;
	}
	public long getParentTrackID() {
		return parentTrackID;
	}
	public void setParentTrackID(long parentTrackID) {
		this.parentTrackID = parentTrackID;
	}
	public long getAudioFileID() {
		return audioFileID;
	}
	public void setAudioFileID(long audioFileID) {
		this.audioFileID = audioFileID;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getDurationMS() {
		return durationMS;
	}
	public void setDurationMS(int durationMS) {
		this.durationMS = durationMS;
	}
	public int getLoopCount() {
		return loopCount;
	}
	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}
	public boolean isLocked() {
		return isLocked;
	}
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	public String getPlaybackOptions() {
		return playbackOptions;
	}
	public void setPlaybackOptions(String playbackOptions) {
		this.playbackOptions = playbackOptions;
	}
	public boolean isDirty() {
		return isDirty;
	}
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
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
