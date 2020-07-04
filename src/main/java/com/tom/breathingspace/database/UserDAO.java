package com.tom.breathingspace.database;

import java.util.List;

import com.tom.breathingspace.model.User;

public interface UserDAO {
  public void persist(final User user);

  public void update(final User user);

  public List<User> listUsers();

  public boolean userExists(final String username);

  public User getUser(final String username);

}
