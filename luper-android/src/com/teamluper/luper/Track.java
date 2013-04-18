//Track class will hold a sequence of clips. Tracks will be
//played simultaneously with other tracks. Volume will be
//adjustable at a track level as well.
//
//Created by: Cam 3/30/13
//Adapted by: Mike 4/1/13

package com.teamluper.luper;

import java.util.ArrayList;

public class Track {
  // Mike's database field variables
	private long id;
	private long ownerUserID;
	private long parentSequenceID;
	private boolean isMuted;
	private boolean isLocked;
	private String playbackOptions;
	private boolean isDirty; // dirty = contains unsynced changes

	// Mike's database access variables
	private SQLiteDataSource dataSource;

	// references to relevant data
	public ArrayList<Clip> clips = new ArrayList<Clip>();

	// Mike's constructor
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
	}
	// temporary constructor for compatability with other files
	public Track() {
	  this.dataSource = null;
	}

	// Mike's getters and setters for database abstraction
	public long getId() { return id; }
	public void setId(long id) {
    long oldId = this.id;
	  this.id = id;
    dataSource.updateLong("Tracks", oldId, "_id", id);
    this.isDirty = true;
	}

	public long getOwnerUserID() { return ownerUserID; }
	public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
    dataSource.updateLong("Tracks", this.id, "ownerUserID", ownerUserID);
    this.isDirty = true;
	}
	public long getParentSequenceID() { return parentSequenceID; }
  public void setParentSequenceID(long parentSequenceID) {
    this.parentSequenceID = parentSequenceID;
    dataSource.updateLong("Tracks", this.id, "parentSequenceID", parentSequenceID);
    this.isDirty = true;
  }
	public boolean isMuted() { return isMuted; }
  public void setMuted(boolean isMuted) {
    this.isMuted = isMuted;
    dataSource.updateInt("Tracks", this.id, "isMuted", (isMuted ? 1 : 0));
    this.isDirty = true;
  }
  public boolean isLocked() { return isLocked; }
  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
    dataSource.updateInt("Tracks", this.id, "isLocked", (isLocked ? 1 : 0));
    this.isDirty = true;
  }
  public String getPlaybackOptions() { return playbackOptions; }
  public void setPlaybackOptions(String playbackOptions) {
    this.playbackOptions = playbackOptions;
    dataSource.updateString("Tracks", this.id, "playbackOptions", playbackOptions);
    this.isDirty = true;
  }
  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
    dataSource.updateInt("Tracks", this.id, "isDirty", (isDirty ? 1 : 0));
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
  /*DEPRECATE OR DELETE
	public void createPBList()
	{
		for(int i = 0; i < clips.size(); i++)
		{
			playBackList[i]=clips.get(i).name;
		}
	}*/

	//gets the track length by calculating the length of all the clips it contains
	public long getTrackLength()
	{
		long sum = 0;
		for(Clip c:clips)
		{
			sum+=c.getDuration();
		}
		return sum;
	}
	//DEPRECATE
	public void putClip(Clip clip)
	{
		clips.add(clip);
	}

}
