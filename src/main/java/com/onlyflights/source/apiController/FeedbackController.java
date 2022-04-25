package com.onlyflights.source.apiController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.onlyflights.source.models.Customer;
import com.onlyflights.source.models.Feedback;
import com.onlyflights.source.repositories.FeedbackRepo;
import com.onlyflights.source.services.CustomerDetailService;

@Controller
public class FeedbackController {

	@Autowired
	private CustomerDetailService customerDetailService;

	@Autowired
	private FeedbackRepo feedbackRepo;

	static boolean check = false;

	@GetMapping("/feedback")
	public ModelAndView feedbackForm(Model model) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer user = customerDetailService.findUserByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getEmail());
		modelAndView.addObject("feedback", new Feedback(1, user.getEmail(), ""));
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("feedback");
		return modelAndView;
	}

	@PostMapping("/feedback")
	public ModelAndView feedbackSubmit(@ModelAttribute Feedback feedback, Model model) {

		if (!check) {
			Feedback fed1 = new Feedback(1, "Amit@gmail.com", "This website has Bugs!!! ");
			Feedback fed2 = new Feedback(2, "Rohit@gmail.com", "This wesbite is Secure!!! ");

			feedbackRepo.saveAndFlush(fed1);
			feedbackRepo.saveAndFlush(fed2);
			check = true;
		}

		feedbackRepo.saveAndFlush(feedback);

		List<Feedback> feedbackList = feedbackRepo.findAll();

		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer user = customerDetailService.findUserByEmail(auth.getName());
		modelAndView.addObject("currentUser", user);
		modelAndView.addObject("fullName", "Welcome " + user.getFullname());
		modelAndView.addObject("feedbackList", feedbackList);
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("result");
		return modelAndView;
	}

}