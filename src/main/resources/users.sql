USE breathingspace;

CREATE TABLE users (
	user_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	role_id INT(11) UNSIGNED NOT NULL,
	username VARCHAR(256) NOT NULL,
	password VARCHAR(256) NOT NULL,
	email VARCHAR(256) NOT NULL,
	level int(11) UNSIGNED NOT NULL,
	xp int(11) UNSIGNED NOT NULL,
	enabled TINYINT NOT NULL DEFAULT 1,
	PRIMARY KEY (user_id),
	CONSTRAINT fk_username FOREIGN KEY (role_id) REFERENCES user_roles (user_role_id));