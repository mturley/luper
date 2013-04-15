package com.teamluper.luper;

public class AudioFile {
  private SQLiteDataSource dataSource;

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

  public AudioFile(SQLiteDataSource dataSource, long id, long ownerUserID,
                   String clientFilePath, String serverFilePath,
                   String fileFormat, double bitrate, double durationMS,
                   boolean isReadyOnClient, boolean isReadyOnServer,
                   long renderSequenceID, boolean isDirty) {
    this.dataSource = dataSource;
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
    long oldId = this.id;
    this.id = id;
    dataSource.updateLong("Files", oldId, "_id", id);
    this.isDirty = true;
  }

  public long getOwnerUserID() { return ownerUserID; }
  public void setOwnerUserID(long ownerUserID) {
    this.ownerUserID = ownerUserID;
    dataSource.updateLong("Files", this.id, "ownerUserID", ownerUserID);
    this.isDirty = true;
  }

  public String getClientFilePath() { return clientFilePath; }
  public void setClientFilePath(String clientFilePath) {
    this.clientFilePath = clientFilePath;
    dataSource.updateString("Files", this.id, "clientFilePath", clientFilePath);
    this.isDirty = true;
  }

  public String getServerFilePath() { return serverFilePath; }
  public void setServerFilePath(String serverFilePath) {
    this.serverFilePath = serverFilePath;
    dataSource.updateString("Files", this.id, "serverFilePath", serverFilePath);
    this.isDirty = true;
  }

  public String getFileFormat() { return fileFormat; }
  public void setFileFormat(String fileFormat) {
    this.fileFormat = fileFormat;
    dataSource.updateString("Files", this.id, "fileFormat", fileFormat);
    this.isDirty = true;
  }

  public double getBitrate() { return bitrate; }
  public void setBitrate(double bitrate) {
    this.bitrate = bitrate;
    dataSource.updateDouble("Files", this.id, "bitrate", bitrate);
    this.isDirty = true;
  }

  public double getDurationMS() { return durationMS; }
  public void setDurationMS(double durationMS) {
    this.durationMS = durationMS;
    dataSource.updateDouble("Files", this.id, "durationMS", durationMS);
    this.isDirty = true;
  }

  public boolean isReadyOnClient() { return isReadyOnClient; }
  public void setReadyOnClient(boolean isReadyOnClient) {
    this.isReadyOnClient = isReadyOnClient;
    dataSource.updateInt("Files", this.id, "isReadyOnClient", (isReadyOnClient ? 1 : 0));
    this.isDirty = true;
  }

  public boolean isReadyOnServer() { return isReadyOnServer; }
  public void setReadyOnServer(boolean isReadyOnServer) {
    this.isReadyOnServer = isReadyOnServer;
    dataSource.updateInt("Files", this.id, "isReadyOnServer", (isReadyOnServer ? 1 : 0));
    this.isDirty = true;
  }

  public long getRenderSequenceID() { return renderSequenceID; }
  public void setRenderSequenceID(long renderSequenceID) {
    this.renderSequenceID = renderSequenceID;
    dataSource.updateLong("Files", this.id, "renderSequenceID", renderSequenceID);
    this.isDirty = true;
  }

  public boolean isDirty() { return isDirty; }
  public void setDirty(boolean isDirty) {
    this.isDirty = isDirty;
    dataSource.updateInt("Files", this.id, "isDirty", (isDirty ? 1 : 0));
  }
}
