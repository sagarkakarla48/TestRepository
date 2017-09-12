package com.ltts.picktolight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.AdminLoginDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AdminLoginServiceImpl implements AdminLoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginServiceImpl.class);
	
	@Autowired
	private AdminLoginDao adminLoginDao;	
	
	
	
	public void setAdminLoginDao(AdminLoginDao adminLoginDao) {
		logger.info("setAdminLoginDao() method in AdminLoginServiceImpl Class :- Start");
		this.adminLoginDao = adminLoginDao;
		logger.info("setAdminLoginDao() method in AdminLoginServiceImpl Class :- End");
	}

	
	/**
	 * check the user authorization
	 * @param userName user name entered
	 * @param password	password entered
	 */
	@Override
	@Transactional
	public boolean checkLogin(String userName, String password) {
		logger.info("setAdminLoginDao() method in AdminLoginServiceImpl Class :- Start");
		
		//checking the credentials existence in DB
		boolean result =  adminLoginDao.checkLogin(userName,password);
		logger.info("setAdminLoginDao() method in AdminLoginServiceImpl Class :- End");
		return result;
	} 
	 
	
	
}
