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

public class LuperDataSource {
  // Database fields
  private SQLiteDatabase database;
  private LuperSQLiteHelper dbHelper;
  private long activeUserID; // TODO change this on register / login

  public LuperDataSource(Context context) {
    this(context, -1);
  }
  public LuperDataSource(Context context, long userID) {
    dbHelper = new LuperSQLiteHelper(context);
    activeUserID = userID;
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Sequence createSequence(String title) {
    if(title == "") title = "Untitled";
    ContentValues values = new ContentValues();
    values.put("title", title);
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
    Sequence sequence = new Sequence(this, false);
    sequence.setId(cursor.getLong(cursor.getColumnIndex("_id")));
    sequence.setOwnerUserID(cursor.getLong(cursor.getColumnIndex("ownerUserID")));
    sequence.setTitle(cursor.getString(cursor.getColumnIndex("title")));
    sequence.setSharingLevel(cursor.getInt(cursor.getColumnIndex("sharingLevel")));
    sequence.setPlaybackOptions(cursor.getString(cursor.getColumnIndex("playbackOptions")));
    sequence.setDirty(cursor.getInt(cursor.getColumnIndex("isDirty")) == 1);
    sequence.setAutoSaveEnabled(true);
    return sequence;
  }
  
  // PROCEED WITH CAUTION, THIS DOES EXACTLY WHAT IT SOUNDS LIKE
  public void dropAllData() {
    // forces a drop and recreate of all tables
    dbHelper.onUpgrade(database, 0, dbHelper.getVersion());
  }

}
