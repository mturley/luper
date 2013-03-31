package com.teamluper.luper;

public class Sequence {
  private long id;
  private String title;
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  // to be used by ArrayAdapters in ListViews?
  @Override
  public String toString() {
    return title;
  }
}
