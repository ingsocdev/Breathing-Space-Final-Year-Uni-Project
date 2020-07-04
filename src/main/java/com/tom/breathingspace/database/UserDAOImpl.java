package com.tom.breathingspace.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tom.breathingspace.model.User;
import com.tom.breathingspace.model.UserRole;

public class UserDAOImpl implements UserDAO {

  private SessionFactory sessionFactory;

  public void setSessionFactory(final SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public void persist(final User user) {
    Session session = this.sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    final UserRole userRole = new UserRole(user.getUsername(), "ROLE_USER");
    session.persist(userRole);
    user.setUserRole(userRole);
    session.persist(user);
    session.flush();
    transaction.commit();
    session.close();
  }

  public void update(final User user) {
    Session session = this.sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
    session.saveOrUpdate(user);
    session.flush();
    transaction.commit();
    session.close();
  }

  @SuppressWarnings("unchecked")
  public List<User> listUsers() {
    Session session = this.sessionFactory.openSession();
    List<User> userList = session.createQuery("from User").list();
    session.close();
    return userList;
  }

  @SuppressWarnings("unchecked")
  public boolean userExists(final String username) {
    Session session = this.sessionFactory.openSession();
    Query<User> query = session.createQuery("FROM User U WHERE U.username = :username");
    query.setParameter("username", username);
    final List<User> user = query.list();
    session.close();
    return (0 != user.size() ? true : false);
  }

  @SuppressWarnings("unchecked")
  public User getUser(final String username) {
    Session session = this.sessionFactory.openSession();
    Query<User> query = session.createQuery("FROM User U WHERE U.username = :username");
    query.setParameter("username", username);
    final User user = (User) query.list().get(0);
    session.close();
    return user;
  }
}
