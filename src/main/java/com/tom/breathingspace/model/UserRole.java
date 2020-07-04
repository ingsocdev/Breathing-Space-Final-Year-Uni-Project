package com.tom.breathingspace.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole {
  @Id
  @Column(name = "user_role_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userRoleId;
  @Column(name = "username")
  private String username;
  @Column(name = "role")
  private String role;

  public UserRole() {}

  public UserRole(final String username, final String role) {
    this.role = role;
    this.username = username;
  }

  public int getUserRoleId() {
    return this.userRoleId;
  }

  public void setUserRoleId(final int userRoleId) {
    this.userRoleId = userRoleId;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(final String username) {
    this.username = username;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(final String role) {
    this.role = role;
  }

}
