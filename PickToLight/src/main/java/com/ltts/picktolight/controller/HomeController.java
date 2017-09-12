/**
 * 
 */
package com.ltts.picktolight.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 90001332
 *
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String homePage = "Home";

	/**
	 * To revert homepage
	 * @return Home.jsp page
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		logger.info("home() method In HomeController Class :- Start");
		logger.info("home() method In HomeController Class :- End");
		return homePage;
	}

}
