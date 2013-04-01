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
	//int begin, end, duration; // replacing with Mike's stuff if that's cool
	
	//needs an array of attributes/filters/modifications?
	// yeah, eventually playbackOptions will be that -Mike
	public Clip(LuperDataSource dataSource, boolean autoSaveEnabled) {
		name = null;
		durationMS = 0;
		begin = 0;
		end = duration;
	}
	public Clip(String cName)
	{
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
	public void getDuration() throws IOException
	{
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
