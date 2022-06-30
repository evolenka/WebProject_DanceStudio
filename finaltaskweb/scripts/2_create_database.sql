CREATE DATABASE `dancestudio_db` DEFAULT CHARACTER SET utf8;

CREATE USER 'dancestudio_application'@'localhost' IDENTIFIED BY 'admin';
CREATE USER 'dancestudio_application'@'%' IDENTIFIED BY 'admin';

GRANT SELECT,INSERT,UPDATE,DELETE
ON `dancestudio_db`.*
TO dancestudio_application@localhost;

GRANT SELECT,INSERT,UPDATE,DELETE
ON `dancestudio_db`.*
TO dancestudio_application@'%';