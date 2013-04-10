package com.teamluper.luper;

import java.util.ArrayList;

import android.util.Log;

public class Sequence {
  // database field variables
  private long id;
  private long ownerUserID;
  private String title;
  private int sharingLevel;
  private String playbackOptions;
  private boolean isDirty; // dirty = contains unsynced changes
  
  // references to related objects
  private ArrayList<Track> tracks;
  
  // database access variables
  private LuperDataSource dataSource;
  private boolean autoSaveEnabled;
  
  public Sequence(LuperDataSource dataSource, boolean autoSaveEnabled,
      long id, long ownerUserID, String title, int sharingLevel,
      String playbackOptions, boolean isDirty) {
    this.dataSource = dataSource;
    this.autoSaveEnabled = autoSaveEnabled;
    this.id = id;
    this.ownerUserID = ownerUserID;
    this.title = title;
    this.sharingLevel = sharingLevel;
    this.playbackOptions = playbackOptions;
    this.isDirty = isDirty;
    this.tracks = null;
  }
  
  // getters and setters for everything, for custom onChange-style hooks
  public long getId() { return id; }
  public void setId(long id) {
    this.id = id;
  }
  
  public long getOwnerUserID() { return ownerUserID; }
  public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
  }
  
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  
  public int getSharingLevel() { return sharingLevel; }
  public void setSharingLevel(int sharingLevel) {
    this.sharingLevel = sharingLevel;
  }

  public String getPlaybackOptions() { return playbackOptions; }
  public void setPlaybackOptions(String playbackOptions) {
    this.playbackOptions = playbackOptions;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) { this.isDirty = isDirty; }

  public boolean isAutoSaveEnabled() { return autoSaveEnabled; }
  public void setAutoSaveEnabled(boolean autoSaveEnabled) {
    this.autoSaveEnabled = autoSaveEnabled;
  }
  
  public void loadTracks() {
    // query by this sequence's id
    if(this.id == 1) {
      Log.w("Sequence.loadTracks()",
          "attempted to loadTracks of a sequence with an undefined id!  aborted.");
      return;
    }
    // from this point on we can assume this.id is valid
    // TODO create tracks and fill arraylist
    // TODO delegate to each track's loadclip
  }

  @Override
  public String toString() {
    return title;
  }
}
