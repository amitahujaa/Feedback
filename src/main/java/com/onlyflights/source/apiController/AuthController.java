package com.onlyflights.source.apiController;

import javax.validation.Valid;

import com.onlyflights.source.models.Customer;
import com.onlyflights.source.services.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

	@Autowired
	private CustomerDetailService userService;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signIn() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("siginin");
		return modelAndView;
	}

	@RequestMapping(value = "/errorPageException", method = RequestMethod.GET)
	public ModelAndView errorPageException() {
		throw new NullPointerException();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
		Customer customer = new Customer();
		modelAndView.addObject("customer", customer);
		modelAndView.setViewName("signup");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@Valid Customer customer, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		Customer customer1 = userService.findUserByEmail(customer.getEmail());
		if (customer1 != null) {
			result.rejectValue("email", "error.customer",
					"There is already a customer registered with the username provided");
		}
		if (result.hasErrors()) {
			modelAndView.setViewName("signup");
		} else {
			userService.createUser(customer);
			modelAndView.addObject("successMessage", "successfully registered");
			modelAndView.addObject("customer", new Customer());
			modelAndView.setViewName("siginin");

		}
		return modelAndView;
	}

}
