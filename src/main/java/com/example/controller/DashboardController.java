package com.example.controller;

import com.example.model.User;
import com.example.service.DashboardService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private DashboardService dashboardService;

    public static final int DEFAULT_NUMBER_OF_DAYS = 7;

    public static final String BIRTHDAY_URI = "birthdays";

    public static final String ANNIVERSARY_URI = "anniversaries";

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

        modelAndView.addObject("birthdays",dashboardService.getUpcomingBirthDays(DEFAULT_NUMBER_OF_DAYS));
        modelAndView.addObject("anniversaries",dashboardService.getUpcomingAnniversaries(DEFAULT_NUMBER_OF_DAYS));

        modelAndView.setViewName("dashboard");
        modelAndView.addObject("menu","dashboard");
        return modelAndView;
    }

    @RequestMapping(value="/occasion/{occasionType}/{noOfDays}", method = RequestMethod.GET)
    public ModelAndView getUpcomingBirthdays(@PathVariable(value = "occasionType") String occasionType, @PathVariable(value = "noOfDays") Integer noOfDays){
        ModelAndView modelAndView = new ModelAndView();

        if(noOfDays==null || noOfDays <= 0 || noOfDays > 90){
            noOfDays = 30;
        }

        if(BIRTHDAY_URI.equalsIgnoreCase(occasionType)){
            modelAndView.addObject("members",dashboardService.getUpcomingBirthDays(noOfDays));
            modelAndView.addObject("occasionTitle","Upcoming Birthdays");
        }else if(ANNIVERSARY_URI.equalsIgnoreCase(occasionType)){
            modelAndView.addObject("members",dashboardService.getUpcomingAnniversaries(noOfDays));
            modelAndView.addObject("occasionTitle","Upcoming Anniversaries");
        }

        modelAndView.addObject("noOfDays",noOfDays);
        modelAndView.addObject("occasionType",occasionType);
        modelAndView.setViewName("occasionList");
        modelAndView.addObject("menu","dashboard");
        return modelAndView;
    }
}
