-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.15 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for meetingroomapp
DROP DATABASE IF EXISTS `meetingroomapp`;
CREATE DATABASE IF NOT EXISTS `meetingroomapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `meetingroomapp`;


-- Dumping structure for table meetingroomapp.login
DROP TABLE IF EXISTS `login`;
CREATE TABLE IF NOT EXISTS `login` (
  `userId` int(11) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- Dumping data for table meetingroomapp.login: ~1 rows (approximately)
DELETE FROM `login`;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` (`userId`, `userName`, `password`, `status`) VALUES
	(1, 'admin', 'admin', 'ACTIVE');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


/* ************************************** 	
+	Server		: Gayumi-local 
+	Updated By	: GK
+	Date		: 2017/02/08 17.32
****************************************/
/* NF 2017-FEB-14*/
ALTER TABLE `login`
	CHANGE COLUMN `userId` `userId` INT(11) NOT NULL AUTO_INCREMENT FIRST;



CREATE TABLE `facilities` (
	`fid` INT(10) NOT NULL,
	`fName` VARCHAR(50) NOT NULL,
	`owner` VARCHAR(50) NOT NULL,
	`ext` INT(11) NULL DEFAULT NULL,
	`email` VARCHAR(50) NULL DEFAULT NULL,
	`details` VARCHAR(50) NULL DEFAULT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
	`createById` INT(10) NOT NULL,
	`createByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(11) NOT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`fid`),
	UNIQUE INDEX `fName` (`fName`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `locations` (
	`lid` INT(10) NOT NULL AUTO_INCREMENT,
	`locationName` VARCHAR(50) NOT NULL,
	`building` VARCHAR(10) NOT NULL,
	`floor` INT(11) NOT NULL,
	`address` VARCHAR(150) NULL DEFAULT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT(11) NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(11) NOT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`lid`),
	UNIQUE INDEX `locationName` (`locationName`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


CREATE TABLE `resources` (
	`rid` INT(10) NOT NULL,
	`rName` VARCHAR(50) NOT NULL,
	`rType` VARCHAR(50) NOT NULL,
	`owner` VARCHAR(50) NULL DEFAULT NULL,
	`detail` VARCHAR(150) NULL DEFAULT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT(10) NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(11) NOT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`rid`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `users` (
	`uid` INT(10) NOT NULL,
	`uName` VARCHAR(50) NOT NULL,
	`uDep` VARCHAR(50) NULL DEFAULT NULL,
	`uBuild` VARCHAR(50) NULL DEFAULT NULL,
	`uFloor` VARCHAR(50) NULL DEFAULT NULL,
	`userN` VARCHAR(50) NOT NULL,
	`uRoll` VARCHAR(50) NOT NULL,
	`pw` VARCHAR(50) NULL DEFAULT NULL,
	`details` VARCHAR(150) NULL DEFAULT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT(11) NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(11) NOT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`uid`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


ALTER TABLE `facilities`
	CHANGE COLUMN `fid` `fid` INT(10) NOT NULL AUTO_INCREMENT FIRST;
ALTER TABLE `resources`
	CHANGE COLUMN `rid` `rid` INT(10) NOT NULL AUTO_INCREMENT FIRST;
	
ALTER TABLE `users`
	CHANGE COLUMN `uid` `uid` INT(10) NOT NULL AUTO_INCREMENT FIRST;
	

ALTER TABLE `facilities`
	CHANGE COLUMN `details` `details` VARCHAR(200) NULL DEFAULT NULL AFTER `email`;
	
ALTER TABLE `resources`
	CHANGE COLUMN `detail` `detail` VARCHAR(200) NULL DEFAULT NULL AFTER `owner`;
ALTER TABLE `users`
	CHANGE COLUMN `details` `details` VARCHAR(200) NULL DEFAULT NULL AFTER `uRoll`,
	DROP COLUMN `userN`,
	DROP COLUMN `pw`;
	

CREATE TABLE `mail` (
	`from` VARCHAR(50) NOT NULL,
	`to` TEXT NOT NULL,
	`cc` TEXT NULL,
	`bcc` TEXT NULL,
	`subject` TEXT NULL,
	`message` TEXT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

	

/*NF 2017-FEB-25*/
ALTER TABLE `locations`
	ALTER `updatedById` DROP DEFAULT;
ALTER TABLE `locations`
	CHANGE COLUMN `updatedById` `updatedById` INT(11) NULL AFTER `createdByTime`;



/*GK 2017-FEB-28*/
ALTER TABLE `facilities`
	ALTER `updatedById` DROP DEFAULT;
ALTER TABLE `facilities`
	CHANGE COLUMN `updatedById` `updatedById` INT(11) NULL AFTER `createByTime`;
	
ALTER TABLE `resources`
	ALTER `updatedById` DROP DEFAULT;
ALTER TABLE `resources`
	CHANGE COLUMN `updatedById` `updatedById` INT(11) NULL AFTER `createdByTime`;
	
	ALTER TABLE `users`
	ALTER `updatedById` DROP DEFAULT;
ALTER TABLE `users`
	CHANGE COLUMN `updatedById` `updatedById` INT(11) NULL AFTER `createdByTime`;




/*GK 2017-FEB-28*/
ALTER TABLE `mail`
	ADD COLUMN `senderName` VARCHAR(50) NULL AFTER `message`,
	ADD COLUMN `recieverName` VARCHAR(50) NULL AFTER `senderName`,
	ADD COLUMN `createdById` INT NOT NULL AFTER `recieverName`,
	ADD COLUMN `createdByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `createdById`;
ALTER TABLE `mail`
	ADD COLUMN `readStatus` INT NOT NULL DEFAULT '0' COMMENT '1=Read and 0=Unread' AFTER `recieverName`;
	
	
ALTER TABLE `facilities`
	ALTER `createById` DROP DEFAULT,
	ALTER `createByTime` DROP DEFAULT;
ALTER TABLE `facilities`
	CHANGE COLUMN `createById` `createdById` INT(10) NOT NULL AFTER `status`,
	CHANGE COLUMN `createByTime` `createdByTime` VARCHAR(50) NOT NULL AFTER `createdById`;
	
/*GK 2017-MAR-01*/	
	
	ALTER TABLE `users`
	ADD COLUMN `mobile` INT NOT NULL AFTER `uRoll`,
	ADD COLUMN `email` VARCHAR(50) NOT NULL AFTER `mobile`;

ALTER TABLE `users`
	CHANGE COLUMN `status` `status` VARCHAR(10) NULL DEFAULT 'ACTIVE' AFTER `details`;
	
	
/*GK 2017-MAR-02*/		
	ALTER TABLE `login`
	ADD COLUMN `createdById` INT NOT NULL AFTER `status`,
	ADD COLUMN `createdByTime` VARCHAR(50) NOT NULL AFTER `createdById`,
	ADD COLUMN `updatedById` VARCHAR(50) NOT NULL AFTER `createdByTime`,
	ADD COLUMN `updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `updatedById`;

ALTER TABLE `login`
	ALTER `userId` DROP DEFAULT,
	ALTER `updatedById` DROP DEFAULT;
ALTER TABLE `login`
	CHANGE COLUMN `userId` `userId` INT(11) NOT NULL FIRST,
	CHANGE COLUMN `updatedById` `updatedById` VARCHAR(50) NULL AFTER `createdByTime`;
ALTER TABLE `login`
	CHANGE COLUMN `userId` `userId` INT(11) NOT NULL AUTO_INCREMENT FIRST;
ALTER TABLE `mail`
	ADD COLUMN `senderId` INT NULL DEFAULT NULL AFTER `senderName`,
	ADD COLUMN `recieverId` INT NULL DEFAULT NULL AFTER `recieverName`;




/*GK 2017-MAR-02*/		
CREATE TABLE `meetingRoom` (
	`mRoomId` INT(10) NOT NULL AUTO_INCREMENT,
	`mLocation` INT(10) NOT NULL,
	`seatingC` INT(10) NOT NULL,
	`admin` VARCHAR(50) NOT NULL,
	`tp` INT NOT NULL,
	`notes` VARCHAR(200) NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`mRoomId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

ALTER TABLE `meetingroom`
	CHANGE COLUMN `mRoomId` `mId` INT(10) NOT NULL AUTO_INCREMENT FIRST;
CREATE TABLE `meetingR_Resources` (
	`MRid` INT(10) NOT NULL,
	`resourceId` INT(10) NOT NULL,
	`status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE'
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

ALTER TABLE `meetingR_Resources`
	RENAME TO `meetingroom_resources`,
	CHANGE COLUMN `MRid` `mrid` INT(10) NOT NULL FIRST,
	ADD INDEX `mrid` (`mrid`),
	ADD INDEX `resourceId` (`resourceId`);

/* NF 2017-03-03*/
ALTER TABLE `login`
	ALTER `userId` DROP DEFAULT;
ALTER TABLE `login`
	CHANGE COLUMN `userId` `userId` INT(11) NOT NULL FIRST;



/* NF 2017-03-03*/
ALTER TABLE `users`
	ALTER `mobile` DROP DEFAULT;
ALTER TABLE `users`
	CHANGE COLUMN `mobile` `mobile` INT(11) NULL AFTER `uRoll`;
/* ************************************** 	
+	Server		: Maheshi-local 
+	Updated By	: GK
+	Date		: 2017/03/03 10.10
****************************************/

/*GK 2017-MAR-03*/	
ALTER TABLE `meetingroom`
	ALTER `seatingC` DROP DEFAULT;
ALTER TABLE `meetingroom`
	CHANGE COLUMN `seatingC` `seatingCapacity` INT(10) NOT NULL AFTER `mLocation`;
	
	ALTER TABLE `meetingroom`
	ALTER `mLocation` DROP DEFAULT,
	ALTER `admin` DROP DEFAULT;
ALTER TABLE `meetingroom`
	CHANGE COLUMN `mLocation` `mLocationId` INT(10) NOT NULL AFTER `mId`,
	CHANGE COLUMN `admin` `adminId` INT(50) NOT NULL AFTER `seatingCapacity`,
	ADD INDEX `mLocationId` (`mLocationId`),
	ADD INDEX `adminId` (`adminId`);
	ALTER TABLE `meetingroom`
	ADD COLUMN `mRoomName` VARCHAR(70) NOT NULL AFTER `mId`;
	

	
/* NF 2017/MAR/03*/
ALTER TABLE `mail`
	ALTER `createdById` DROP DEFAULT;
ALTER TABLE `mail`
	CHANGE COLUMN `createdById` `senderId` INT(11) NOT NULL AFTER `readStatus`,
	CHANGE COLUMN `createdByTime` `sentDateTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `senderId`,
	ADD COLUMN `receiverId` INT NOT NULL AFTER `sentDateTime`;






/* MN 2017/MAR/03*/	
ALTER TABLE `mail`
	ADD COLUMN `mailId` INT NOT NULL AUTO_INCREMENT FIRST,
	ADD PRIMARY KEY (`mailId`);


/*GK 2017-MAR-06*/	
ALTER TABLE `users`
	ADD UNIQUE INDEX `uName` (`uName`);





/* MN 2017/MAR/08*/	
CREATE TABLE `booking_facilities` (
	`bookingId` INT(10) NOT NULL AUTO_INCREMENT,
	`facilityId` INT(10) NOT NULL DEFAULT '0',
	`status` VARCHAR(10) NOT NULL DEFAULT '0',
	INDEX `bookingId` (`bookingId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `booking_resources` (
	`bookingId` INT(10) NOT NULL AUTO_INCREMENT,
	`resourceId` INT(10) NULL DEFAULT '0',
	`status` VARCHAR(10) NULL DEFAULT '0',
	INDEX `bookingId` (`bookingId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

/*GK 2017-MAR-08*/	
CREATE TABLE `booking` (
	`bid` INT(10) NOT NULL AUTO_INCREMENT,
	`meetingRoomId` INT(10) NOT NULL,
	`title` VARCHAR(50) NOT NULL,
	`start` VARCHAR(50) NOT NULL,
	`end` VARCHAR(50) NOT NULL,
	`allDay` INT NOT NULL,
	`remarks` VARCHAR(200) NOT NULL,
	`repeating` INT NOT NULL,
	`bookingStatus` VARCHAR(50) NOT NULL DEFAULT 'APPROVED',
	`status` VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`bid`)
)

/* ************************************** 	
+	Server		: Gayumi-local 
+	Updated By	: GK
+	Date		: 2017/03/08 11.17
****************************************/
/*GK 2017-MAR-08*/	
ALTER TABLE `booking`
	ALTER `remarks` DROP DEFAULT,
	ALTER `repeating` DROP DEFAULT;
ALTER TABLE `booking`
	CHANGE COLUMN `remarks` `remarks` VARCHAR(200) NULL AFTER `allDay`,
	CHANGE COLUMN `repeating` `repeating` INT(11) NULL AFTER `remarks`;
	
	ALTER TABLE `booking`
	ALTER `allDay` DROP DEFAULT;
ALTER TABLE `booking`
	CHANGE COLUMN `allDay` `allDay` INT(11) NULL COMMENT '1=true and 0=false' AFTER `end`,
	CHANGE COLUMN `repeating` `repeating` INT(11) NULL DEFAULT NULL COMMENT '1=true and 0=false' AFTER `remarks`;





/*MN 2017-MAR-09*/

CREATE TABLE `role` (
	`roleId` INT(10) NOT NULL AUTO_INCREMENT,
	`roleName` VARCHAR(50) NOT NULL,
	`status` VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT(50) NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(50) NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`roleId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



/*MN 2017-MAR-09*/

CREATE TABLE `role` (
	`roleId` INT(11) NOT NULL AUTO_INCREMENT,
	`roleName` VARCHAR(50) NOT NULL,
	`status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT(11) NOT NULL,
	`createdByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT(50) NULL DEFAULT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`roleId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;



/*NF 2017-03-09*/
ALTER TABLE `booking_resources`
	CHANGE COLUMN `status` `status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE' AFTER `resourceId`;
ALTER TABLE `booking_facilities`
	CHANGE COLUMN `status` `status` VARCHAR(10) NOT NULL DEFAULT 'ACTIVE' AFTER `facilityId`;

/*GK 2017-MAR-09*/		
CREATE TABLE `options` (
	`oId` INT(10) NOT NULL AUTO_INCREMENT,
	`optionName` VARCHAR(50) NOT NULL,
	`optionType` VARCHAR(10) NOT NULL,
	`optionIcon` VARCHAR(50) NULL,
	`parentID` VARCHAR(50) NULL,
	`status` VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
	`createdById` INT NOT NULL,
	`cretaedByTime` VARCHAR(50) NOT NULL,
	`updatedById` INT NULL,
	`updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`oId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

ALTER TABLE `options`
	RENAME TO `permission`;
	
ALTER TABLE `permission`
	ADD COLUMN `optionLink` VARCHAR(70) NOT NULL AFTER `optionName`;
	
	/*GK 2017-MAR-10*/	
	
	ALTER TABLE `users`
	ALTER `uRoll` DROP DEFAULT;
ALTER TABLE `users`
	CHANGE COLUMN `uRoll` `uRoll` INT(50) NOT NULL AFTER `uFloor`;





/*NF 2017-MAR-11*/
ALTER TABLE `permission`
	ALTER `cretaedByTime` DROP DEFAULT;
ALTER TABLE `permission`
	CHANGE COLUMN `cretaedByTime` `createdByTime` VARCHAR(50) NOT NULL AFTER `createdById`;
ALTER TABLE `permission`
	ALTER `optionLink` DROP DEFAULT;
ALTER TABLE `permission`
	CHANGE COLUMN `optionLink` `optionLink` VARCHAR(70) NULL AFTER `optionName`;

CREATE TABLE `permission_matrix` (
	`roleId` INT NOT NULL,
	`permissionId` INT NOT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (1, 'Calendar', NULL, 'MAIN', 'fa fa-calendar', NULL, 'ACTIVE', 1, '2017-03-11 11:42:59', NULL, '2017-03-11 11:42:59');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (2, 'Booking', 'booking', 'MAIN', 'fa fa-book', NULL, 'ACTIVE', 1, '2017-03-11 11:58:12', NULL, '2017-03-11 11:58:13');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (3, 'Manage', '#', 'MAIN', 'fa fa-fw fa-wrench', NULL, 'ACTIVE', 1, '2017-03-11 11:58:39', NULL, '2017-03-11 11:58:39');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (4, 'Meeting Rooms', 'meeting-room', 'SUB', NULL, '3', 'ACTIVE', 1, '2017-03-11 12:05:30', NULL, '2017-03-11 12:05:30');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (5, 'Locations', 'location', 'SUB', NULL, '3', 'ACTIVE', 1, '2017-03-11 12:06:18', NULL, '2017-03-11 12:06:18');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (6, 'Users', 'users', 'SUB', NULL, '3', 'ACTIVE', 1, '2017-03-11 12:06:48', NULL, '2017-03-11 12:06:48');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (7, 'Facilities', 'facilities', 'SUB', NULL, '3', 'ACTIVE', 1, '2017-03-11 12:07:03', NULL, '2017-03-11 12:07:03');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (8, 'Access', '#', 'MAIN', 'fa fa-cog', NULL, 'ACTIVE', 1, '2017-03-11 12:13:20', NULL, '2017-03-11 12:13:20');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (9, 'Role', 'role', 'SUB', NULL, '8', 'ACTIVE', 1, '2017-03-11 12:15:09', NULL, '2017-03-11 12:15:09');
INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `parentID`, `status`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (10, 'Permission', 'permission', 'SUB', NULL, '8', 'ACTIVE', 1, '2017-03-11 12:15:31', NULL, '2017-03-11 12:15:31');



/*MN 2017-MAR-13*/
DROP TABLE `permission_matrix`;
CREATE TABLE `permission_matrix` (
	`roleId` INT(11) NOT NULL,
	`permissionId` INT(11) NOT NULL,
	`status` VARCHAR(50) NOT NULL,
	`createdById` INT(10) NOT NULL,
	`createdByTime` VARCHAR(10) NOT NULL,
	`updatedById` INT(10) NULL DEFAULT NULL,
	`updatedByTime` INT(10) NOT NULL
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

/*GK 2017-MAR-13*/		

ALTER TABLE `meetingroom`
	ADD COLUMN `approval` INT NOT NULL AFTER `notes`;
	
	
	
ALTER TABLE `permission_matrix`
	CHANGE COLUMN `updatedByTime` `updatedByTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP AFTER `updatedById`;

ALTER TABLE `permission_matrix`
	ALTER `createdByTime` DROP DEFAULT;
ALTER TABLE `permission_matrix`
	CHANGE COLUMN `createdByTime` `createdByTime` VARCHAR(20) NOT NULL AFTER `createdById`;

ALTER TABLE `permission_matrix`
	ADD PRIMARY KEY (`roleId`, `permissionId`);


/*GK 2017-MAR-21*/		

INSERT INTO `permission` (`oId`, `optionName`, `optionLink`, `optionType`, `optionIcon`, `createdById`, `createdByTime`, `updatedById`, `updatedByTime`) VALUES (14, 'My Bookings', 'my-bookings', 'MAIN', 'fa fa-address-book', 1, '2017-03-21 09:35:18', 1, '2017-03-21 12:16:06');	

ALTER TABLE `permission`
	ADD COLUMN `optionOrder` VARCHAR(50) NOT NULL AFTER `parentID`,
	ADD UNIQUE INDEX `optionOrder` (`optionOrder`);
	
	
	
/* ************************************** 	
+	Server		: NISAL-local 
+	Updated By	: NF
+	Date		: 2017/02/28 09.46
****************************************/

/* ************************************** 	
+	Server		: Gayumi-local 
+	Updated By	: GK
+	Date		: 2017/03/09 13.10
+
****************************************/

/*GK 2017-MAR-22*/		
ALTER TABLE `users`
	ADD UNIQUE INDEX `email` (`email`);

/* ************************************** 	
+	Server		: Maheshi-local 
+	Updated By	: GK
+	Date		: 2017/03/22 10.10
****************************************/



/*GK 2017-MAR-28*/		

ALTER TABLE `booking_facilities`
	ADD COLUMN `count` INT NOT NULL DEFAULT '0' AFTER `facilityId`;
	
ALTER TABLE `facilities`
	ADD COLUMN `countable` INT NULL DEFAULT '0' AFTER `details`;
	
ALTER TABLE `facilities`
	CHANGE COLUMN `countable` `countable` INT(11) NULL DEFAULT '0' COMMENT '1=true and 0=false' AFTER `details`;

/*GK 2017-MAR-29*/			
	
	
	ALTER TABLE `facilities`
	ALTER `owner` DROP DEFAULT;
ALTER TABLE `facilities`
	CHANGE COLUMN `owner` `owner` INT(50) NOT NULL AFTER `fName`;

/* ************************************** 	
+	Server		: SERVER 
+	Updated By	: NF
+	Date		: 2017/03/14 12.48
****************************************/
