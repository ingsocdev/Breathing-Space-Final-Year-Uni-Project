package com.tom.breathingspace.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 7069775613306437277L;
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userId;
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;
  @Column(name = "email")
  private String email;
  @Column(name = "level")
  private int level = 1;
  @Column(name = "xp")
  private long xp = 0;

  @OneToOne
  @JoinColumn(name = "role_id")
  private UserRole userRole;

  @OneToMany(targetEntity = Note.class, fetch = FetchType.EAGER, mappedBy = "user",
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("note_id ASC")
  private Set<Note> notes;

  @OneToMany(targetEntity = Reminder.class, mappedBy = "user", cascade = CascadeType.ALL,
      orphanRemoval = true)
  @OrderBy("reminder_id ASC")
  private Set<Reminder> reminders;

  public User() {};

  public User(final String username, final String password, final String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public int getUserId() {
    return this.userId;
  }

  public void setUserId(final int userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public UserRole getUserRole() {
    return this.userRole;
  }

  public void setUserRole(final UserRole userRole) {
    this.userRole = userRole;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(final int level) {
    this.level = level;
  }

  public long getXp() {
    return this.xp;
  }

  public void setXp(final long xp) {
    this.xp = xp;
  }

  public Note addNote(final Note note) {
    note.setUser(this);
    this.notes.add(note);
    return note;
  }

  public void deleteNote(final int noteId) {
    Iterator<Note> iterator = this.notes.iterator();
    while (iterator.hasNext()) {
      final Note note = iterator.next();
      if (note.getNoteId() == noteId) {
        iterator.remove();
      }
    }
  }

  public Note getNote(final int noteId) {
    for (Iterator<Note> iterator = this.notes.iterator(); iterator.hasNext();) {
      final Note note = iterator.next();
      if (note.getNoteId() == noteId) {
        return note;
      }
    }
    return null;
  }

  public Set<Note> getNotes() {
    return this.notes;
  }

  public Note editNote(final int noteId, final String title, final String description) {
    for (Iterator<Note> iterator = this.notes.iterator(); iterator.hasNext();) {
      final Note note = iterator.next();
      if (note.getNoteId() == noteId) {
        note.setTitle(title);
        note.setDescription(description);
        note.setUpdatedDate(new Date());
        return note;
      }
    }
    return null;
  }

  public void archiveNote(final int noteId, final boolean toggle) {
    for (Iterator<Note> iterator = this.notes.iterator(); iterator.hasNext();) {
      final Note note = iterator.next();
      if (note.getNoteId() == noteId) {
        note.setIsArchived((toggle ? 1 : 0));
      }
    }
  }

  public Reminder addReminder(final Reminder reminder) {
    reminder.setUser(this);
    this.reminders.add(reminder);
    return reminder;
  }

  public void deleteReminder(final int reminderId) {
    Iterator<Reminder> iterator = this.reminders.iterator();
    while (iterator.hasNext()) {
      final Reminder reminder = iterator.next();
      if (reminder.getReminderId() == reminderId) {
        iterator.remove();
      }
    }
  }

  public Reminder getReminder(final int reminderId) {
    for (Iterator<Reminder> iterator = this.reminders.iterator(); iterator.hasNext();) {
      final Reminder reminder = iterator.next();
      if (reminder.getReminderId() == reminderId) {
        return reminder;
      }
    }
    return null;
  }

  public Set<Reminder> getReminders() {
    return this.reminders;
  }

  public Reminder editReminder(final int reminderId, final String title, final String description) {
    for (Iterator<Reminder> iterator = this.reminders.iterator(); iterator.hasNext();) {
      final Reminder reminder = iterator.next();
      if (reminder.getReminderId() == reminderId) {
        reminder.setTitle(title);
        reminder.setDescription(description);
        reminder.setUpdatedDate(new Date());
        return reminder;
      }
    }
    return null;
  }

  public void archiveReminder(final int reminderId, final boolean toggle) {
    for (Iterator<Reminder> iterator = this.reminders.iterator(); iterator.hasNext();) {
      final Reminder reminder = iterator.next();
      if (reminder.getReminderId() == reminderId) {
        reminder.setIsArchived((toggle ? 1 : 0));
      }
    }
  }
}
