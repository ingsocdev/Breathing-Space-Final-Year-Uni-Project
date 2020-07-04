package com.tom.breathingspace.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tom.breathingspace.database.UserDAO;
import com.tom.breathingspace.model.Level;
import com.tom.breathingspace.model.Note;
import com.tom.breathingspace.model.Reminder;
import com.tom.breathingspace.model.User;

@Service
public class UserServiceImpl implements UserService {
  private User user;
  @Autowired
  private UserDAO userDAO;

  public UserServiceImpl(final UserDAO uDAO) {
    userDAO = uDAO;
  }

  public UserDAO getUserDAO() {
    return userDAO;
  }

  public void setUser(final String username) {
    this.user = userDAO.getUser(username);
  }

  public User getUser() {
    return this.user;
  }

  public Note editNote(final int noteId, final String title, final String description) {
    final Note note = this.user.editNote(noteId, title, description);
    userDAO.update(this.user);
    return note;
  }

  public Note addNote(final String title, final String description) {
    final Note note = this.user.addNote(new Note(title, description));
    this.increaseXp(25);
    userDAO.update(this.user);
    return note;
  }

  public void deleteNote(final int noteId) {
    this.user.deleteNote(noteId);
    userDAO.update(this.user);
  }

  public void archiveNote(final int noteId, final boolean toggle) {
    this.user.archiveNote(noteId, toggle);
    userDAO.update(this.user);
  }

  public Reminder editReminder(final int reminderId, final String title, final String description) {
    final Reminder reminder = this.user.editReminder(reminderId, title, description);
    userDAO.update(this.user);
    return reminder;
  }

  public Reminder addReminder(final String title, final String description, final Date dueDate) {
    final Reminder reminder = this.user.addReminder(new Reminder(title, description, dueDate));
    this.increaseXp(25);
    userDAO.update(this.user);
    return reminder;
  }

  public void deleteReminder(final int reminderId) {
    this.user.deleteReminder(reminderId);
    userDAO.update(this.user);
  }

  public void archiveReminder(final int reminderId, final boolean toggle) {
    this.user.archiveReminder(reminderId, toggle);
    userDAO.update(this.user);
  }

  public int getMaxLevel() {
    return Level.getMaxLevel();
  }

  public String getPercentageComplete() {
    DecimalFormat decimalFormat = new DecimalFormat("#");
    decimalFormat.setRoundingMode(RoundingMode.CEILING);
    return decimalFormat.format(((float) user.getXp() / (float) this.getXpInLevel()) * 100);
  }

  public long getXpInLevel() {
    return Level.getXpInLevel(user.getLevel());
  }

  public String getLevelTagline() {
    return Level.getLevelTagline(user.getLevel());
  }

  public void increaseXp(final int xp) {
    final int currentLevel = this.user.getLevel();
    final long currentXp = this.user.getXp();
    final long xpDifferential = (currentXp + xp) - Level.getXpInLevel(currentLevel);

    if (xpDifferential >= 0) {
      this.levelUp(xpDifferential, currentLevel);
    } else {
      this.user.setXp(currentXp + xp);
    }

    userDAO.update(this.user);
  }

  public void levelUp(final long xp, final int currentLevel) {
    this.user.setLevel(currentLevel + 1);
    this.user.setXp(xp);
  }
}
