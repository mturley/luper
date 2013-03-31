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

public class DataSourceSequences {
  // Database fields
  private SQLiteDatabase database;
  private LuperSQLiteHelper dbHelper;

  public DataSourceSequences(Context context) {
    dbHelper = new LuperSQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Sequence createSequence(String title) {
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
    System.out.println("Comment deleted with id: " + id);
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
    Sequence sequence = new Sequence();
    sequence.setId(cursor.getLong(0));
    sequence.setTitle(cursor.getString(1));
    return sequence;
  }
}
