package com.ltts.picktolight.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltts.picktolight.domain.AdminLogin;
import com.ltts.picktolight.service.AdminLoginService;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001334
 *
 */
@Controller
public class AdminLoginController{


	private static final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

	@Autowired
	private AdminLoginService adminLoginService;
	private static final String homePage = "Home";	

	public void setAdminLoginService(AdminLoginService adminLoginService) {		
		this.adminLoginService = adminLoginService;
	}

	/**
	 * @author 90001334  
	 * checks whether the given user name and password are authorized or not 
	 * @param login POJO class object which holds user name and password
	 * @return boolean success if user exists otherwise failure
	 *
	 */
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public @ResponseBody String adminLogin(AdminLogin login)
	{
		logger.info("adminLogin() method In AdminLoginController Class :- Start");
		
		//calling checkLogin() in Service class
		boolean userExists = adminLoginService.checkLogin(login.getUserName(),login.getPassword());
		String mesg = "", result = "";

		try{

			if(userExists){			
				mesg = StringConstatns.success;				
				result = mesg;
			}else{
				mesg = StringConstatns.failure;				
				result = mesg;	
			}	
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{

		}	
		logger.info("adminLogin() method In AdminLoginController Class :- End");
		return result;
	}



	/**
	 * @author 90001334  
	 * Calling Home Page When Admin Logout
	 * @return Home.jsp page
	 *
	 */
	@RequestMapping(value = "/backTohomePage", method = RequestMethod.POST)
	public String productsExcel() {
		logger.info("productsExcel() method In AdminLoginController Class :- Start");
		logger.info("productsExcel() method In AdminLoginController Class :- End");
		return homePage;
	}

}
