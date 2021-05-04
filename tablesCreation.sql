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
  `id` INT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `description` VARCHAR(300),
  `marking_scheme` VARCHAR(300),
  `pdf_path` VARCHAR(100),
  `module_code` VARCHAR(100) NOT NULL,
  `due_date` DATETIME,
  FOREIGN KEY(`module_code`) REFERENCES `Module`(`code`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`id`)
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

CREATE TABLE `Assignment_creation` (
  `creation_date` DATE,
  `assignment_id` INT NOT NULL,
  `ta_id` INT NOT NULL,
  `teacher_id` INT NOT NULL,
  FOREIGN KEY(`assignment_id`) REFERENCES `Assignment`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`ta_id`) REFERENCES `TA`(`ucd_id`) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(`teacher_id`) REFERENCES `Teacher`(`id`) ON UPDATE CASCADE ON DELETE CASCADE,
  PRIMARY KEY(`assignment_id`,`ta_id`,`teacher_id`)
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



