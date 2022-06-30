SHOW DATABASES;

USE `dancestudio_db`;

CREATE TABLE `user` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(130) NOT NULL,
	`role` ENUM ('ADMIN', 'TEACHER', 'CLIENT'),
    CONSTRAINT PK_user PRIMARY KEY (`id`),
    CONSTRAINT UC_user UNIQUE (`login`)
);

CREATE TABLE `client` ( 
	`id` INTEGER NOT NULL,
	`surname` VARCHAR(100) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`patronymic` VARCHAR(100),
	`email` VARCHAR(254) NOT NULL,
	`phone` VARCHAR(50),
	CONSTRAINT PK_client PRIMARY KEY (`id`),
    CONSTRAINT FK_client_user FOREIGN KEY (`id`)
    REFERENCES `user` (`id`)
 	ON UPDATE cascade
 	ON DELETE CASCADE
 	
);

CREATE TABLE `teacher` (
	`id` INTEGER NOT NULL,
	`surname` VARCHAR(100) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`dancestyle` VARCHAR(100) NOT NULL,
	`portfolio` TEXT,
	
	CONSTRAINT PK_teacher PRIMARY KEY (`id`),
	CONSTRAINT FK_teacher_user FOREIGN KEY (`id`)
    REFERENCES `user` (`id`)
 	ON UPDATE cascade
 	ON DELETE CASCADE
 );

CREATE TABLE `group` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(30) NOT NULL,
	`teacher_id` INTEGER NOT NULL ,
	`level` ENUM ('BEG', 'PRO'),
	CONSTRAINT PK_group PRIMARY KEY (`id`),
	CONSTRAINT FK_group_teacher FOREIGN KEY (`teacher_id`)
    REFERENCES `teacher` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE RESTRICT,
 	CONSTRAINT UC_groups UNIQUE (`title`)
);

CREATE TABLE `schedule` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`weekday` ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'),
	`time` TIME NOT NULL,
	`duration` TINYINT NOT NULL,
	`group_id` INTEGER,
	CONSTRAINT PK_schedule PRIMARY KEY (`id`),
    CONSTRAINT FK_schedule_group FOREIGN KEY(`group_id`)
    REFERENCES `group` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE RESTRICT
);

CREATE TABLE `danceclass` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`schedule_id` INTEGER NOT NULL,
	`date` Date NOT NULL,
	`is_active` Bool default TRUE,
	CONSTRAINT PK_danceclass PRIMARY KEY (`id`),
    CONSTRAINT FK_danceclass_schedule FOREIGN KEY(`schedule_id`)
    REFERENCES `schedule` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE RESTRICT
);

CREATE TABLE `type_of_membership` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`title` ENUM ('GUEST', 'LIGHT','BASIC', 'STRONG', 'UNLIM'), 
	`max_class_quantity` TINYINT,
	`price` DECIMAL NOT NULL,
    CONSTRAINT PK_type_of_membership PRIMARY KEY (`id`),
    CONSTRAINT UC_type_of_membership UNIQUE (`title`, `max_class_quantity`),
	CONSTRAINT CHK_PRICE CHECK (`price` > 0),
	CONSTRAINT CHK_QUANTITY CHECK (`max_class_quantity` IN (1, 4, 8, 16))
);

CREATE TABLE `membership` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`client_id` INTEGER NOT NULL,
   	`type_of_membership_id` INTEGER NOT NULL,
	`start_date` DATE NOT NULL,
	`end_date` DATE NOT NULL,
	`balance_quantity` TINYINT,
	CONSTRAINT CHK_END_DATE CHECK (`end_date` >= `start_date`),
	CONSTRAINT CHK_BALANCE CHECK (`balance_quantity` >= 0),
    CONSTRAINT PK_memberships PRIMARY KEY (`id`),
    CONSTRAINT FK_membership_client FOREIGN KEY (`client_id`)
	REFERENCES `client` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE RESTRICT,
    CONSTRAINT FK_membership_type_of_membership FOREIGN KEY (`type_of_membership_id`)
    REFERENCES `type_of_membership` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE restrict
);

CREATE TABLE `visit` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `danceclass_id` BIGINT NOT NULL,
	`membership_id` BIGINT NOT NULL,
	`status` ENUM ('PLANNED','ATTENDED','ABSENT') default 'PLANNED',
	CONSTRAINT PK_membership PRIMARY KEY (`id`),
	CONSTRAINT FK_visit_danceclass FOREIGN KEY (`danceclass_id`)
    REFERENCES `danceclass` (`id`)
 	ON UPDATE CASCADE
 	ON DELETE RESTRICT,
   	CONSTRAINT FK_visit_membership FOREIGN KEY (`membership_id`)
	REFERENCES `membership` (`id`)
	ON UPDATE CASCADE
 	ON DELETE RESTRICT
);