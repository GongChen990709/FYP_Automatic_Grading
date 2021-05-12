åCREATE TABLE `Major` (
  `code` VARCHAR(100) NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  PRIMARY KEY(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Student` (
  `ucd_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `pwd` VARCHAR(100),
  `email` VARCHAR(100) NOT NULL,
  `major_code` VARCHAR(100),
  FOREIGN KEY(`major_code`) REFERENCES `Major`(`code`) ON UPDATE CASCADE ON DELETE SET NULL,
  PRIMARY KEY(`ucd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Department` (
  `code` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Teacher` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `pwd` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `department_code` VARCHAR(100),
  FOREIGN KEY(`department_code`) REFERENCES `Department`(`code`) ON UPDATE CASCADE ON DELETE SET NULL,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `TA` (
  `ucd_id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `pwd` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `major_code` VARCHAR(100),
  `teacher_id` INT NOT NULL,
  FOREIGN KEY(`major_code`) REFERENCES `Major`(`code`) ON UPDATE CASCADE ON DELETE SET NULL,
  FOREIGN KEY(`teacher_id`) REFERENCES `Teacher`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`ucd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Module` (
  `code` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `teacher_id` INT NOT NULL,
  FOREIGN KEY(`teacher_id`) REFERENCES `Teacher`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Module_registration` (
  `student_id` INT NOT NULL,
  `module_code` VARCHAR(100) NOT NULL,
  FOREIGN KEY(`student_id`) REFERENCES `Student`(`ucd_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`module_code`) REFERENCES `Module`(`code`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`student_id`,`module_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Assignment` (
  `id` varchar(300) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `requirement_path` varchar(300) DEFAULT NULL,
  `datatype_path` varchar(300) DEFAULT NULL,
  `data_path` varchar(300) DEFAULT NULL,
  `module_code` varchar(100) NOT NULL,
  `due_date` datetime DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `teacher_id` int DEFAULT NULL,
  `TA_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `module_code` (`module_code`),
  KEY `teacher_id` (`teacher_id`),
  KEY `TA_id` (`TA_id`),
  CONSTRAINT `assignment_ibfk_1` FOREIGN KEY (`module_code`) REFERENCES `Module` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assignment_ibfk_2` FOREIGN KEY (`teacher_id`) REFERENCES `Teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `assignment_ibfk_3` FOREIGN KEY (`TA_id`) REFERENCES `Student` (`ucd_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Assessment` (
  `assignment_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `grade` INT,
  `description` VARCHAR(300),
  `pdf_path` VARCHAR(100),
  FOREIGN KEY(`assignment_id`) REFERENCES `Assignment`(`id`) ON UPDATE CASCADE ON DELETE NO ACTION,
  FOREIGN KEY(`student_id`) REFERENCES `Student`(`ucd_id`) ON UPDATE CASCADE ON DELETE NO ACTION,
  PRIMARY KEY(`assignment_id`,`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





CREATE TABLE `Assignment_submission` (
  `student_id` INT NOT NULL,
  `assignment_id` INT NOT NULL,
  `file_path` VARCHAR(100),
  `submission_date` DATETIME,
  FOREIGN KEY(`student_id`) REFERENCES `Student`(`ucd_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`assignment_id`) REFERENCES `Assignment`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`student_id`,`assignment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `Administrator` (
  `id` INT NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `pwd` VARCHAR(100) NOT NULL,
   PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



