package com.tom.breathingspace.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reminders")
public class Reminder implements Editable, Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -5112655611908481202L;
  @Id
  @Column(name = "reminder_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int reminderId;
  @Column(name = "created_date")
  private Date createdDate;
  @Column(name = "last_updated")
  private Date lastUpdatedDate;
  @Column(name = "due_date")
  private Date dueDate;
  @Column(name = "title")
  private String title;
  @Column(name = "description")
  private String description;
  @Column(name = "is_archived")
  private int isArchived;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "fk_user_id")
  private User user;

  public void setUser(final User user) {
    this.user = user;
  }

  public Reminder() {}

  public Reminder(final String title, final String description, final Date dueDate) {
    this.createdDate = new Date();
    this.lastUpdatedDate = this.createdDate;
    this.dueDate = dueDate;
    this.title = title;
    this.description = description;
  }

  public void setReminderId(final int reminderId) {
    this.reminderId = reminderId;
  }

  public int getReminderId() {
    return this.reminderId;
  }

  public void setCreatedDate(final Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getCreatedDate() {
    return this.createdDate;
  }

  public void setUpdatedDate(final Date updatedDate) {
    this.lastUpdatedDate = updatedDate;
  }

  public Date getUpdatedDate() {
    return this.lastUpdatedDate;
  }

  public void setDueDate(final Date dueDate) {
    this.dueDate = dueDate;
  }

  public Date getDueDate() {
    return this.dueDate;
  }


  public void setTitle(final String title) {
    this.title = title;
  }

  public String getTitle() {
    return this.title;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }

  public void setIsArchived(final int isArchived) {
    this.isArchived = isArchived;
  }

  public int getIsArchived() {
    return this.isArchived;
  }

}
