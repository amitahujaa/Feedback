//package com.onlyflights.source.apiController;
//
//import com.onlyflights.source.models.Customer;
//import com.onlyflights.source.models.Feedback;
//import com.onlyflights.source.services.CustomerDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//
//@Controller
//public class HomeController {
//
//    @Autowired
//    private CustomerDetailService customerDetailService;
//
//
//    @RequestMapping(value = "/home", method = RequestMethod.GET)
//    public ModelAndView home() {
//        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Customer user = customerDetailService.findUserByEmail(auth.getName());
//        modelAndView.addObject("currentUser", user);
//        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
//        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
//        modelAndView.setViewName("feedback");
//        return modelAndView;
//    }
//
//
//}
