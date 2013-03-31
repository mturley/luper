package com.teamluper.luper;

public class Sequence {
  private long id;
  private long ownerUserID;
  private String title;
  private int sharingLevel;
  private String playbackOptions;
    // TODO this should be deserialized JSON, not a String
  private boolean isDirty;
  
  private LuperDataSource dataSource;
  private boolean autoSaveEnabled;
  
  public Sequence(LuperDataSource dataSource, boolean autoSaveEnabled) {
    this.dataSource = dataSource;
    this.setAutoSaveEnabled(autoSaveEnabled);
  }
  
  public long getId() { return id; }
  public void setId(long id) {
    this.id = id;
    //if(autoSaveEnabled) {
      
    //}
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

  // to be used by ArrayAdapters in ListViews?
  @Override
  public String toString() {
    return title;
  }
}
