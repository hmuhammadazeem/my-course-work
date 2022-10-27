CREATE DATABASE  ap_arabic_corpus;

-- MySQL PHPMYADMIN EXTRACT

CREATE TABLE `poem` (
 `poemId` int(11) NOT NULL AUTO_INCREMENT,
 `poemTitle` varchar(255) CHARACTER SET utf8 NOT NULL,
 `bookName` varchar(255) CHARACTER SET utf8 NOT NULL,
 `poetName` varchar(255) CHARACTER SET utf8 NOT NULL,
 PRIMARY KEY (`poemId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1

CREATE TABLE `couplet` (
 `coupletId` int(11) NOT NULL AUTO_INCREMENT,
 `poemId` int(11) NOT NULL,
 `line1` varchar(500) CHARACTER SET utf8 NOT NULL,
 `line2` varchar(500) CHARACTER SET utf8 NOT NULL,
 PRIMARY KEY (`coupletId`),
 KEY `poemId` (`poemId`),
 CONSTRAINT `couplet_ibfk_1` FOREIGN KEY (`poemId`) REFERENCES `poem` (`poemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1


--CREATE TABLE ROOT(
--    rootId INTEGER PRIMARY KEY AUTO_INCREMENT,
--    rootLetters TEXT,
--    inQuran BOOLEAN
--);
--
--CREATE TABLE TOKEN(
--    tokenId INTEGER PRIMARY KEY AUTO_INCREMENT,
--    rootId INTEGER,
--    tokenValue TEXT,
--    FOREIGN KEY (rootId) REFERENCES ROOT(rootId)
--)
