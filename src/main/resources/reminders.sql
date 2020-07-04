USE breathingspace;

CREATE TABLE reminders (
  reminder_id INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  fk_user_id INT(11) UNSIGNED NOT NULL,
  created_date DATETIME NOT NULL,
  last_updated DATETIME NOT NULL,
  due_date DATETIME NOT NULL,
  title VARCHAR(256) NOT NULL,
  description LONGBLOB NOT NULL,
  is_archived TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (reminder_id),
  CONSTRAINT reminders_fk_user_id FOREIGN KEY (fk_user_id) REFERENCES users (user_id));