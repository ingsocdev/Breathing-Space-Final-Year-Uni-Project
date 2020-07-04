USE breathingspace;

CREATE TABLE notes (
  note_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  fk_user_id INT(11) UNSIGNED NOT NULL,
  created_date DATETIME NOT NULL,
  last_updated DATETIME NOT NULL,
  title VARCHAR(256) NOT NULL,
  description LONGBLOB NOT NULL,
  is_archived TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (note_id),
  CONSTRAINT notes_fk_user_id FOREIGN KEY (fk_user_id) REFERENCES users (user_id));