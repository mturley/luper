-- Adminer 3.6.3 MySQL dump
-- Execute this file as mysql root at first server installation.
-- WARNING: if you are trying to fix a LIVE database, DO NOT use this.
--   IT WILL DELETE EVERYONE'S PROJECTS PERMANENTLY. ALL OF THEM.

SET NAMES utf8;
SET foreign_key_checks = 0;
SET time_zone = 'SYSTEM';
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP DATABASE IF EXISTS `luper`;
CREATE DATABASE `luper` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `luper`;

DROP TABLE IF EXISTS `Clips`;
CREATE TABLE `Clips` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `ownerUserID` int(11) NOT NULL,
  `parentTrackID` int(11) NOT NULL,
  `audioFileID` int(11) NOT NULL,
  `startTime` int(11) NOT NULL,
  `durationMS` int(11) DEFAULT NULL,
  `loopCount` int(11) NOT NULL,
  `isLocked` tinyint(1) NOT NULL DEFAULT '0',
  `playbackOptions` text,
  PRIMARY KEY (`_id`,`ownerUserID`),
  KEY `ownerUserID` (`ownerUserID`),
  KEY `parentTrackID` (`parentTrackID`),
  KEY `audioFileID` (`audioFileID`),
  CONSTRAINT `Clips_ibfk_3` FOREIGN KEY (`audioFileID`) REFERENCES `Files` (`_id`),
  CONSTRAINT `Clips_ibfk_1` FOREIGN KEY (`ownerUserID`) REFERENCES `Users` (`_id`),
  CONSTRAINT `Clips_ibfk_2` FOREIGN KEY (`parentTrackID`) REFERENCES `Tracks` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Files`;
CREATE TABLE `Files` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `ownerUserID` int(11) NOT NULL,
  `clientFilePath` varchar(256) NOT NULL,
  `serverFilePath` varchar(256) DEFAULT NULL,
  `fileFormat` varchar(8) NOT NULL,
  `bitrate` double DEFAULT NULL,
  `durationMS` int(11) DEFAULT NULL,
  `isReadyOnClient` tinyint(1) NOT NULL DEFAULT '0',
  `isReadyOnServer` tinyint(1) NOT NULL DEFAULT '0',
  `renderSequenceID` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`,`ownerUserID`),
  KEY `ownerUserID` (`ownerUserID`),
  KEY `renderSequenceID` (`renderSequenceID`),
  CONSTRAINT `Files_ibfk_2` FOREIGN KEY (`renderSequenceID`) REFERENCES `Sequences` (`_id`),
  CONSTRAINT `Files_ibfk_1` FOREIGN KEY (`ownerUserID`) REFERENCES `Users` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Sequences`;
CREATE TABLE `Sequences` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `ownerUserID` int(11) NOT NULL,
  `title` int(11) NOT NULL,
  `sharingLevel` int(11) NOT NULL DEFAULT '0',
  `playbackOptions` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`,`ownerUserID`),
  KEY `ownerUserID` (`ownerUserID`),
  CONSTRAINT `Sequences_ibfk_1` FOREIGN KEY (`ownerUserID`) REFERENCES `Users` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Tracks`;
CREATE TABLE `Tracks` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `ownerUserID` int(11) NOT NULL,
  `parentSequenceID` int(11) NOT NULL,
  `isMuted` int(11) NOT NULL DEFAULT '0',
  `isLocked` int(11) NOT NULL DEFAULT '0',
  `playbackOptions` text,
  PRIMARY KEY (`_id`,`ownerUserID`),
  KEY `ownerUserID` (`ownerUserID`),
  KEY `parentSequenceID` (`parentSequenceID`),
  CONSTRAINT `Tracks_ibfk_2` FOREIGN KEY (`parentSequenceID`) REFERENCES `Sequences` (`_id`),
  CONSTRAINT `Tracks_ibfk_1` FOREIGN KEY (`ownerUserID`) REFERENCES `Users` (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `passwordHash` varchar(512) NOT NULL,
  `challengeSalt` varchar(512) DEFAULT NULL,
  `isActiveUser` tinyint(1) NOT NULL DEFAULT '0',
  `linkedFacebookID` int(11) DEFAULT NULL,
  `preferences` text,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP USER 'luper'@'localhost';
CREATE USER 'luper'@'localhost' IDENTIFIED BY 'luper';
GRANT USAGE ON * . * TO  'luper'@'localhost' IDENTIFIED BY 'luper' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ;
GRANT ALL PRIVILEGES ON  `luper` . * TO  'luper'@'localhost`';

-- 2013-04-18 01:55:29
