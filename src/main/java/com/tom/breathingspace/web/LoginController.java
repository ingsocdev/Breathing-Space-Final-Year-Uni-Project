package com.tom.breathingspace.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tom.breathingspace.database.UserDAO;
import com.tom.breathingspace.model.Login;
import com.tom.breathingspace.model.User;
import com.tom.breathingspace.spring.ApplicationContextProvider;

@Controller
public class LoginController {

  protected final Log logger = LogFactory.getLog(getClass());
  private ApplicationContext context;

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login(@RequestParam(value = "error", required = false) String error,
      @RequestParam(value = "logout", required = false) String logout) {

    ModelAndView model = new ModelAndView();
    if (error != null) {
      model.addObject("error", "Invalid username and password!");
    }

    if (logout != null) {
      model.addObject("msg", "You've been logged out successfully.");
    }
    model.setViewName("login");

    return model;
  }

  @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
  public ModelAndView submitLogin(@ModelAttribute("breathingspace") Login login) {
    context = ApplicationContextProvider.getApplicationContext();
    UserDAO userDAO = (UserDAO) context.getBean("userDAO");

    ModelAndView model = new ModelAndView();
    if (!login.getUsername().equals("") && !login.getPassword().equals("")) {
      if (!userDAO.userExists(login.getUsername())) {
        userDAO.persist(new User(login.getUsername(), login.getPassword(), login.getEmail()));
        model.addObject("success", "User " + login.getUsername() + " successfully created.");
      } else {
        model.addObject("error", "Username " + login.getUsername() + " is already taken!");
      }
    } else {
      model.addObject("error", "You must enter a username and password!");
    }

    model.setViewName("login");
    return model;
  }

}
