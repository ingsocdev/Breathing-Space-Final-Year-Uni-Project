USE breathingspace;

CREATE TABLE user_roles (
  user_role_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  username VARCHAR(256) NOT NULL,
  role VARCHAR(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,user_role_id),
  KEY fk_username_idx (user_role_id));