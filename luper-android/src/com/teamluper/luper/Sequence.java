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
  private SQLiteDataSource dataSource;

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
    this.tracks = null;
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
