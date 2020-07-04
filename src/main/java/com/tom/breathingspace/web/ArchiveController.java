package com.tom.breathingspace.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tom.breathingspace.service.UserService;

@Controller
public class ArchiveController {

  protected final Log logger = LogFactory.getLog(getClass());
  @Autowired
  private UserService userService;

  @RequestMapping(value = "/archive/archiveNote", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public void archiveNote(HttpServletRequest request, @RequestParam("noteId") final int noteId,
      @RequestParam("archive") final boolean archive) {
    userService.archiveNote(noteId, archive);
  }

  @RequestMapping(value = "/archive**", method = RequestMethod.GET)
  public String dashboard(ModelMap model, HttpServletRequest request) {
    if (null != userService) {
      model.put("userService", userService);
    }
    return "archive";
  }
}
