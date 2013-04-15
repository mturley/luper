package com.teamluper.luper;

public class Lambda {
  public static interface VoidCallback {
    void go();
  }
  public static interface StringCallback {
    void go(String value);
  }
}