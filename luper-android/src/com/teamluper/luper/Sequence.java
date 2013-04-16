package com.teamluper.luper;

import java.util.List;

public class Sequence {
  // database field variables
  private long id;
  private long ownerUserID;
  private String title;
  private int sharingLevel;
  private String playbackOptions;
  private boolean isDirty; // dirty = contains unsynced changes

  // references to related objects
  private List<Track> tracks = null;

  // database access variables
  private SQLiteDataSource dataSource;

  // NOTE: DO NOT CALL THIS CONSTRUCTOR DIRECTLY unless in a cursorToSequence method.
  // instead, use SQLiteDataSource.createSequence()!
  public Sequence(SQLiteDataSource dataSource, long id, long ownerUserID,
                  String title, int sharingLevel, String playbackOptions,
                  boolean isDirty) {
    this.dataSource = dataSource;
    this.id = id;
    this.ownerUserID = ownerUserID;
    this.title = title;
    this.sharingLevel = sharingLevel;
    this.playbackOptions = playbackOptions;
    this.isDirty = isDirty;
  }

  // getters and setters for everything, for custom onChange-style hooks
  public long getId() { return id; }
  public void setId(long id) {
    long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Sequences", oldId, "_id", id);
    this.isDirty = true;
  }

  public long getOwnerUserID() { return ownerUserID; }
  public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
    dataSource.updateLong("Sequences", this.id, "ownerUserID", ownerUserID);
    this.isDirty = true;
  }

  public String getTitle() { return title; }
  public void setTitle(String title) {
    this.title = title;
    dataSource.updateString("Sequences", this.id, "title", title);
    this.isDirty = true;
  }

  public int getSharingLevel() { return sharingLevel; }
  public void setSharingLevel(int sharingLevel) {
    this.sharingLevel = sharingLevel;
    dataSource.updateInt("Sequences", this.id, "sharingLevel", sharingLevel);
    this.isDirty = true;
  }

  public String getPlaybackOptions() { return playbackOptions; }
  public void setPlaybackOptions(String playbackOptions) {
    this.playbackOptions = playbackOptions;
    dataSource.updateString("Sequences", this.id, "playbackOptions", playbackOptions);
    this.isDirty = true;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
    dataSource.updateInt("Sequences", this.id, "isDirty", (isDirty ? 1 : 0));
  }

  public void loadAllTrackData() {
    this.tracks = dataSource.getTracksBySequenceId(this.id);
    for(Track track : this.tracks) {
      track.loadAllClipData();
    }
  }

  public void loadAllTrackAudio() {
    if(this.tracks == null) loadAllTrackData();
    for(Track track : this.tracks) {
      track.loadAllClipAudio();
    }
  }

  @Override
  public String toString() {
    return title;
  }
}
