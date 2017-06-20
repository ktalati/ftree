package com.example.controller;

import com.example.model.User;
import com.example.service.DashboardService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value="/dashboard", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");

        modelAndView.addObject("memberCount",dashboardService.getMemberCount());
        modelAndView.addObject("groupCount",dashboardService.getGroupCount());
        modelAndView.addObject("remainingMessages",dashboardService.getRemainingMessages());
        modelAndView.addObject("totalSubscription",dashboardService.getTotalSubscriptions());

        modelAndView.addObject("birthdays",dashboardService.findMemberByBirthdate(new Date()));

        modelAndView.setViewName("dashboard");
        modelAndView.addObject("menu","dashboard");
        return modelAndView;
    }
}
