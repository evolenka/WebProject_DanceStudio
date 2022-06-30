USE `dancestudio_db`;
CREATE INDEX date ON danceclass(date);
CREATE INDEX login_password ON user (login, password);
CREATE INDEX startdate ON membership (start_date);