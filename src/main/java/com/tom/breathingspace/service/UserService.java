package com.tom.breathingspace.service;

import java.util.Date;

import com.tom.breathingspace.database.UserDAO;
import com.tom.breathingspace.model.Note;
import com.tom.breathingspace.model.Reminder;
import com.tom.breathingspace.model.User;

public interface UserService {

  public UserDAO getUserDAO();

  public void setUser(final String username);

  public User getUser();

  public Note editNote(final int noteId, final String title, final String description);

  public Note addNote(final String title, final String description);

  public void deleteNote(final int noteId);

  public void archiveNote(final int noteId, final boolean toggle);

  public Reminder editReminder(final int reminderId, final String title, final String description);

  public Reminder addReminder(final String title, final String description, final Date dueDate);

  public void deleteReminder(final int reminderId);

  public void archiveReminder(final int reminderId, final boolean toggle);

  public int getMaxLevel();

  public String getPercentageComplete();

  public long getXpInLevel();

  public String getLevelTagline();

  public void increaseXp(final int xp);

  public void levelUp(final long xp, final int currentLevel);

}
