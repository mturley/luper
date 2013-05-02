drop table if exists Users;
drop table if exists Sequences;
drop table if exists Tracks;
drop table if exists Files;
drop table if exists Clips;
create table Users (
  _id               integer   primary key,
  username          text      not null,
  email             text      not null,
  isActiveUser      integer   not null,
  preferences       text,
  isDirty           integer   not null
);

create table Sequences (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  title             text      not null,
  sharingLevel      integer,
  playbackOptions   text,
  isDirty           integer   not null,
  foreign key (ownerUserID) references Users (_id)
);

create table Tracks (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  parentSequenceID  integer   not null,
  isMuted           integer   not null,
  isLocked          integer   not null,
  playbackOptions   text,
  isDirty           integer   not null,
  foreign key (ownerUserID)      references Users (_id),
  foreign key (parentSequenceID) references Sequences (_id)
);

create table Files (
  _id               integer   primary key autoincrement,
  ownerUserID       integer   not null,
  clientFilePath    text      not null,
  serverFilePath    text,
  fileFormat        text      not null,
  bitrate           real,
  durationMS        real,
  isReadyOnClient   integer   not null,
  isReadyOnServer   integer   not null,
  renderSequenceID  integer,
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
  durationMS        integer,
  loopCount         integer   not null,
  color             integer,
  isLocked          integer   not null,
  playbackOptions   text,
  isDirty           integer   not null,
  foreign key (ownerUserID)   references Users (_id),
  foreign key (parentTrackID) references Tracks (_id),
  foreign key (audioFileID)   references Files (_id)
);