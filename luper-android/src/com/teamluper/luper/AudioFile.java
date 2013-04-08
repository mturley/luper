package com.teamluper.luper;

public class AudioFile {
  private SQLiteDataSource dataSource;
  private boolean autoSaveEnabled;
   
  private long id;
  private long ownerUserID;
  private String clientFilePath;
  private String serverFilePath;
  private String fileFormat;
  private double bitrate;
  private double durationMS;
  private boolean isReadyOnClient;
  private boolean isReadyOnServer;
  private long renderSequenceID;
  private boolean isDirty;
  
  public AudioFile(SQLiteDataSource dataSource, boolean autoSaveEnabled,
      long id, long ownerUserID, String clientFilePath, String serverFilePath,
      String fileFormat, double bitrate, double durationMS,
      boolean isReadyOnClient, boolean isReadyOnServer,
      long renderSequenceID, boolean isDirty) {
    this.dataSource = dataSource;
    this.setAutoSaveEnabled(autoSaveEnabled);
    this.id = id;
    this.ownerUserID = ownerUserID;
    this.clientFilePath = clientFilePath;
    this.serverFilePath = serverFilePath;
    this.fileFormat = fileFormat;
    this.bitrate = bitrate;
    this.durationMS = durationMS;
    this.isReadyOnClient = isReadyOnClient;
    this.isReadyOnServer = isReadyOnServer;
    this.renderSequenceID = renderSequenceID;
    this.isDirty = isDirty;
  }

  public long getId() { return id; }
  public void setId(long id) {
    this.id = id;
  }

  public long getOwnerUserID() { return ownerUserID; }
  public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
  }

  public String getClientFilePath() { return clientFilePath; }
  public void setClientFilePath(String clientFilePath) {
    this.clientFilePath = clientFilePath;
  }

  public String getServerFilePath() { return serverFilePath; }
  public void setServerFilePath(String serverFilePath) {
    this.serverFilePath = serverFilePath;
  }

  public String getFileFormat() { return fileFormat; }
  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
  }

  public double getBitrate() { return bitrate; }
  public void setBitrate(double bitrate) {
    this.bitrate = bitrate;
  }

  public double getDurationMS() { return durationMS; }
  public void setDurationMS(double durationMS) {
    this.durationMS = durationMS;
  }

  public boolean isReadyOnClient() { return isReadyOnClient; }
  public void setReadyOnClient(boolean isReadyOnClient) {
    this.isReadyOnClient = isReadyOnClient;
  }

  public boolean isReadyOnServer() { return isReadyOnServer; }
  public void setReadyOnServer(boolean isReadyOnServer) {
    this.isReadyOnServer = isReadyOnServer;
  }

  public long getRenderSequenceID() { return renderSequenceID; }
  public void setRenderSequenceID(long renderSequenceID) {
    this.renderSequenceID = renderSequenceID;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
  }

  public boolean isAutoSaveEnabled() { return autoSaveEnabled; }
  public void setAutoSaveEnabled(boolean autoSaveEnabled) {
    this.autoSaveEnabled = autoSaveEnabled;
  }
}
