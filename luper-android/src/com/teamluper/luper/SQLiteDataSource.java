package com.teamluper.luper;

// TODO: generalize this to all database tables, and do something more clever with the cursors.
// TODO: instead of selection by id, use composite key of id and ownerUserId

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteDataSource {
  // Database fields
  private SQLiteDatabase database;
  private SQLiteHelper dbHelper;
  private long activeUserID; // TODO change this on register / login

  public SQLiteDataSource(Context context) {
    this(context, -1);
  }
  public SQLiteDataSource(Context context, long userID) {
    dbHelper = new SQLiteHelper(context);
    activeUserID = userID;
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Sequence createSequence(User owner, String title) {
    if(title == "") title = "Untitled";
    ContentValues values = new ContentValues();
    values.put("ownerUserID", 0); // FIXME use owner.getID
    values.put("title", title);
    values.put("isDirty", 1);
    long insertId = database.insert("Sequences", null, values);
    Cursor cursor = database.query("Sequences", null,
        "_id = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Sequence newSequence = cursorToSequence(cursor);
    cursor.close();
    return newSequence;
  }

  public void deleteSequence(Sequence sequence) {
    long id = sequence.getId();
    System.out.println("Sequence deleted with id: " + id);
    database.delete("Sequences", "_id = " + id, null);
  }
  
  public Track createTrack(Sequence parentSequence) {
    ContentValues values = new ContentValues();
    values.put("ownerUserID", parentSequence.getOwnerUserID());
    values.put("parentSequenceID", parentSequence.getId());
    values.put("isMuted", false);
    values.put("isLocked", false);
    values.put("isDirty", 1);
    long insertId = database.insert("Tracks", null, values);
    Cursor cursor = database.query("Tracks", null,
        "_id = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Track newTrack = cursorToTrack(cursor);
    cursor.close();
    return newTrack;
  }
  public void deleteTrack(Track track) {
    // TODO
  }
  
  public AudioFile createAudioFile(User owner, String filePath) {
    ContentValues values = new ContentValues();
    values.put("ownerUserID", 0); // FIXME replace with owner.getID
    values.put("clientFilePath", filePath);
    values.put("fileFormat", "3gp");
    values.put("isReadyOnClient", 1);
    values.put("isDirty", 1);
    long insertId = database.insert("Files", null, values);
    Cursor cursor = database.query("Files", null,
        "_id = " + insertId, null, null, null, null);
    AudioFile newFile = cursorToFile(cursor);
    cursor.close();
    return newFile;
  }
  public void deleteAudioFile(AudioFile file) {
    // TODO
  }
  
  public Clip createClip(Track parentTrack, AudioFile file) {
    ContentValues values = new ContentValues();
    values.put("ownerUserID", parentTrack.getOwnerUserID());
    values.put("parentTrackID", parentTrack.getId());
    values.put("audioFileID", file.getId());
    return null;
  }
  public void deleteClip(Clip clip) {
    // TODO
  }

  public List<Sequence> getAllSequences() {
    List<Sequence> sequences = new ArrayList<Sequence>();

    Cursor cursor = database.query("Sequences", null, null,
        null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Sequence sequence = cursorToSequence(cursor);
      sequences.add(sequence);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return sequences;
  }
  
  private Sequence cursorToSequence(Cursor cursor) {
    Sequence sequence = new Sequence(this, false,
      cursor.getLong(cursor.getColumnIndex("_id")),
      cursor.getLong(cursor.getColumnIndex("ownerUserID")),
      cursor.getString(cursor.getColumnIndex("title")),
      cursor.getInt(cursor.getColumnIndex("sharingLevel")),
      cursor.getString(cursor.getColumnIndex("playbackOptions")),
      cursor.getInt(cursor.getColumnIndex("isDirty")) == 1
    );
    sequence.setAutoSaveEnabled(true);
    return sequence;
  }
  private Track cursorToTrack(Cursor cursor) {
    Track track = new Track(this, false,
      cursor.getLong(cursor.getColumnIndex("_id")),
      cursor.getLong(cursor.getColumnIndex("ownerUserID")),
      cursor.getLong(cursor.getColumnIndex("parentSequenceID")),
      cursor.getInt(cursor.getColumnIndex("isMuted")) == 1,
      cursor.getInt(cursor.getColumnIndex("isLocked")) == 1,
      cursor.getString(cursor.getColumnIndex("playbackOptions")),
      cursor.getInt(cursor.getColumnIndex("isDirty")) == 1
    );
    track.setAutoSaveEnabled(true);
    return track;
  }

  private Clip cursorToClip(Cursor cursor) {
    Clip clip = new Clip(this, false,
      cursor.getLong(cursor.getColumnIndex("_id")),
      cursor.getLong(cursor.getColumnIndex("ownerUserID")),
      cursor.getLong(cursor.getColumnIndex("parentSequenceID")),
      cursor.getLong(cursor.getColumnIndex("audioFileID")),
      cursor.getInt(cursor.getColumnIndex("startTime")),
      cursor.getInt(cursor.getColumnIndex("durationMS")),
      cursor.getInt(cursor.getColumnIndex("loopCount")),
      cursor.getInt(cursor.getColumnIndex("isLocked")) == 1,
      cursor.getString(cursor.getColumnIndex("playbackOptions")),
      cursor.getInt(cursor.getColumnIndex("isDirty")) == 1
    );
    clip.setAutoSaveEnabled(true);
    return clip;
  }
  
  private AudioFile cursorToFile(Cursor cursor) {
    AudioFile file = new AudioFile(this, false,
      cursor.getLong(cursor.getColumnIndex("_id")),
      cursor.getLong(cursor.getColumnIndex("ownerUserID")),
      cursor.getString(cursor.getColumnIndex("clientFilePath")),
      cursor.getString(cursor.getColumnIndex("serverFilePath")),
      cursor.getString(cursor.getColumnIndex("fileFormat")),
      cursor.getDouble(cursor.getColumnIndex("bitrate")),
      cursor.getDouble(cursor.getColumnIndex("durationMS")),
      cursor.getInt(cursor.getColumnIndex("isReadyOnClient")) == 1,
      cursor.getInt(cursor.getColumnIndex("isReadyOnServer")) == 1,
      cursor.getLong(cursor.getColumnIndex("renderSequenceID")),
      cursor.getInt(cursor.getColumnIndex("isDirty")) == 1
    );
    file.setAutoSaveEnabled(true);
    return file;
  }
  
  
  // PROCEED WITH CAUTION, THIS DOES EXACTLY WHAT IT SOUNDS LIKE
  public void dropAllData() {
    // forces a drop and recreate of all tables
    dbHelper.onUpgrade(database, 0, dbHelper.getVersion());
  }

}
