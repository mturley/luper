package com.teamluper.luper;

import android.os.Looper;
import android.view.View;

import java.util.ArrayList;

public class Sequence {
  private SQLiteDataSource dataSource;

  // database field variables
  private long id;
  private long ownerUserID;
  private String title;
  private int sharingLevel;
  private String playbackOptions;
  private boolean isDirty; // dirty = contains unsynced changes
  private boolean isReady;

  // references to related objects
  public ArrayList<Track> tracks = null;

  // references to any views depending on this data, so we can invalidate them automatically on set___ calls.
  public ArrayList<View> associatedViews = null;

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
    this.isReady = false;
    this.associatedViews = new ArrayList<View>();
  }

  public void addAssociatedView(View view) {
    this.associatedViews.add(view);
  }
  public void removeAssociatedView(View view) {
    this.associatedViews.remove(view);
  }
  public ArrayList<View> getAssociatedViews() {
    return this.associatedViews;
  }
  public void invalidateAssociatedViews() {
    for(View v : this.associatedViews) {
      v.requestLayout();
      v.refreshDrawableState();
      if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
        // we're in the main-thread / UI Thread.
        v.invalidate();
      } else {
        // we're in a background thread.
        v.postInvalidate();
      }
    }
  }

  // getters and setters for everything, for custom onChange-style hooks
  public long getId() { return id; }
  public void setId(long id) {
    long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Sequences", oldId, "_id", id);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

  public long getOwnerUserID() { return ownerUserID; }
  public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
    dataSource.updateLong("Sequences", this.id, "ownerUserID", ownerUserID);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

  public String getTitle() { return title; }
  public void setTitle(String title) {
    this.title = title;
    dataSource.updateString("Sequences", this.id, "title", title);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

  public int getSharingLevel() { return sharingLevel; }
  public void setSharingLevel(int sharingLevel) {
    this.sharingLevel = sharingLevel;
    dataSource.updateInt("Sequences", this.id, "sharingLevel", sharingLevel);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

  public String getPlaybackOptions() { return playbackOptions; }
  public void setPlaybackOptions(String playbackOptions) {
    this.playbackOptions = playbackOptions;
    dataSource.updateString("Sequences", this.id, "playbackOptions", playbackOptions);
    invalidateAssociatedViews();
    this.isDirty = true;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
    dataSource.updateInt("Sequences", this.id, "isDirty", (isDirty ? 1 : 0));
    invalidateAssociatedViews();
  }

  public boolean isReady() { return this.isReady; }
  public void setReady(boolean ready) {
    this.isReady = ready;
  }

  public void loadAllTrackData() {
    this.tracks = dataSource.getTracksBySequenceId(this.id);
    for(Track track : this.tracks) {
      track.loadAllClipData();
    }
    this.isReady = true;
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
