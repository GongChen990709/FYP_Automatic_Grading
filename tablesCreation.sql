CREATE TABLE `Student` (
  `studentNum` int unsigned NOT NULL AUTO_INCREMENT,
  `studentName` varchar(30) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `stage` enum('1','2','3','4') DEFAULT NULL,
  PRIMARY KEY (`studentNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





