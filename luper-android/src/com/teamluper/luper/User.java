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
  private boolean isDirty; // dirty = contains unsynced changes

  // database access variables
  private SQLiteDataSource dataSource;

  // NOTE: DO NOT CALL THIS CONSTRUCTOR DIRECTLY unless in a cursorToUser method.
  // instead, use SQLiteDataSource.createUser()!
  public User(SQLiteDataSource dataSource, long id, String username,
              String email, String passwordHash, boolean isActiveUser,
              long linkedFacebookID, String preferences, boolean isDirty) {
    this.dataSource = dataSource;
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
    long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Users", oldId, "_id", id);
    this.isDirty = true;
  }

  public String getUsername() { return username; }
  public void setUsername(String username) {
    this.username = username;
    dataSource.updateString("Users", this.id, "username", username);
    this.isDirty = true;
  }

  public String getEmail() { return email; }
  public void setEmail(String email) {
    this.email = email;
    dataSource.updateString("Users", this.id, "email", email);
    this.isDirty = true;
  }

  public String getPasswordHash() { return passwordHash; }
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
    dataSource.updateString("Users", this.id, "passwordHash", passwordHash);
    this.isDirty = true;
  }

  public boolean isActiveUser() { return isActiveUser; }
  public void setActiveUser(boolean activeUser) {
    isActiveUser = activeUser;
    dataSource.updateInt("Users", this.id, "isActiveUser", (activeUser ? 1 : 0));
    this.isDirty = true;
  }

  public long getLinkedFacebookID() { return linkedFacebookID; }
  public void setLinkedFacebookID(long linkedFacebookID) {
    this.linkedFacebookID = linkedFacebookID;
    dataSource.updateLong("Users", this.id, "linkedFacebookID", linkedFacebookID);
    this.isDirty = true;
  }

  public String getPreferences() { return preferences; }
  public void setPreferences(String preferences) {
    this.preferences = preferences;
    dataSource.updateString("Users", this.id, "preferences", preferences);
    // TODO do some magic with JSON here instead? to a HashTable?
    this.isDirty = true;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean dirty) {
    isDirty = dirty;
    dataSource.updateInt("Users", this.id, "isDirty", (dirty ? 1 : 0));
  }

  public SQLiteDataSource getDataSource() { return dataSource; }
  public void setDataSource(SQLiteDataSource dataSource) {
    this.dataSource = dataSource;
  }
}
