package com.tom.breathingspace.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tom.breathingspace.service.UserService;

@Controller
public class DashboardController {

  protected final Log logger = LogFactory.getLog(getClass());
  private ApplicationContext context;
  private boolean firstrun = true;

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/dashboard/editNote", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<String> addNote(@RequestParam("noteId") final int noteId,
      @RequestParam("title") final String title,
      @RequestParam("description") final String description) {

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

    JSONObject json = new JSONObject(userService.editNote(noteId, title, description));
    return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/dashboard/addNote", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<String> addNote(@RequestParam("title") final String title,
      @RequestParam("description") final String description) {

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setContentType(MediaType.APPLICATION_JSON);

    JSONObject json = new JSONObject(userService.addNote(title, description));
    return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
  }

  @RequestMapping(value = "/dashboard/deleteNote", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public void deleteNote(HttpServletRequest request, @RequestParam("noteId") final int noteId) {
    userService.deleteNote(noteId);
  }

  @RequestMapping(value = "/dashboard**", method = RequestMethod.GET)
  public String dashboard(ModelMap model, HttpServletRequest request) {
    userService.setUser(request.getUserPrincipal().getName());

    if (null != userService) {
      model.put("userService", userService);
    }

    model.put("firstrun", firstrun);
    firstrun = false;
    logger.info("In DashboardController");

    return "dashboard";
  }
}
