package com.teamluper.luper;

public class User {
  // database field variables
  private long id;
  private String username;
  private String email;
  private String passwordHash;
  private boolean isActiveUser;
  private long linkedFacebookID;
  private String preferences;
  private boolean isDirty;

  // database access variables
  private SQLiteDataSource dataSource;
  private boolean autoSaveEnabled;

  public User(SQLiteDataSource dataSource, boolean autoSaveEnabled,
              long id, String username, String email, String passwordHash,
              boolean isActiveUser, long linkedFacebookID, String preferences,
              boolean isDirty) {
    this.dataSource = dataSource;
    this.autoSaveEnabled = autoSaveEnabled;
    this.id = id;
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.isActiveUser = isActiveUser;
    this.linkedFacebookID = linkedFacebookID;
    this.preferences = preferences;
    this.isDirty = isDirty;
  }
  // getters and setters for everything, for custom onChange-style hooks
  public long getId() { return id; }
  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() { return username; }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() { return email; }
  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean isActiveUser() { return isActiveUser; }
  public void setActiveUser(boolean activeUser) {
    isActiveUser = activeUser;
  }

  public long getLinkedFacebookID() { return linkedFacebookID; }
  public void setLinkedFacebookID(long linkedFacebookID) {
    this.linkedFacebookID = linkedFacebookID;
  }

  public String getPreferences() { return preferences; }
  public void setPreferences(String preferences) {
    this.preferences = preferences;
  }

  public SQLiteDataSource getDataSource() { return dataSource; }
  public void setDataSource(SQLiteDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public boolean isAutoSaveEnabled() { return autoSaveEnabled; }
  public void setAutoSaveEnabled(boolean autoSaveEnabled) {
    this.autoSaveEnabled = autoSaveEnabled;
  }
}
