create table Users (
  _id               integer   primary key,
  username          text      not null,
  email             text      not null,
  passwordHash      text      not null,
  isActiveUser      integer   not null,
  linkedFacebookID  integer   null,
  preferences       text      null
);
insert into Users VALUES (-1, 'anonymous', '', '', 1, null, null);

create table Sequences (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  title             text      not null,
  sharingLevel      integer   null,
  playbackOptions   text      null,
  isDirty           integer   not null,
  foreign key (ownerUserID) references Users (_id)
);

create table Tracks (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  parentSequenceID  integer   not null,
  isMuted           integer   not null,
  isLocked          integer   not null,
  playbackOptions   text      null,
  isDirty           integer   not null,
  foreign key (ownerUserID)      references Users (_id),
  foreign key (parentSequenceID) references Sequences (_id)
);

create table Files (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  clientFilePath    text      not null,
  serverFilePath    text      null,
  fileFormat        text      not null,
  bitrate           real      null,
  durationMS        real      null,
  isReadyOnClient   integer   not null,
  isReadyOnServer   integer   not null,
  renderSequenceID  integer   null,
  isDirty           integer   not null,
  foreign key (ownerUserID)      references Users (_id),
  foreign key (renderSequenceID) references Sequences (_id)
);

create table Clips (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  parentTrackID     integer   not null,
  audioFileID       integer   not null,
  startTime         integer   not null,
  durationMS        integer   null,
  loopCount         integer   not null,
  isLocked          integer   not null,
  playbackOptions   text      null,
  isDirty           integer   not null,
  foreign key (ownerUserID)   references Users (_id),
  foreign key (parentTrackID) references Tracks (_id),
  foreign key (audioFileID)   references Files (_id)
);