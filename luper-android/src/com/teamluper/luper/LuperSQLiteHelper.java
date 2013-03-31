package com.teamluper.luper;

import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LuperSQLiteHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "luperlocal.db";
  private static final int DATABASE_VERSION = 1;
  
  private Context context;
  
  public LuperSQLiteHelper(Context c) {
    super(c, DATABASE_NAME, null, DATABASE_VERSION);
    context = c;
  }
  
  @Override
  public void onCreate(SQLiteDatabase database) {
    InputStream in = context.getResources().openRawResource(R.raw.create_sqlite_tables);
    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
    String sql = s.hasNext() ? s.next() : "";
    database.execSQL(sql);
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(LuperSQLiteHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    InputStream in = context.getResources().openRawResource(R.raw.drop_sqlite_tables);
    java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
    String sql = s.hasNext() ? s.next() : "";
    db.execSQL(sql);
    onCreate(db);
  }
  
  public int getVersion() {
    return DATABASE_VERSION;
  }
}