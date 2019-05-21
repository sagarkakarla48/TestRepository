package com.ltts.picktolight.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.ltts.picktolight.domain.AdminLogin;
import com.ltts.picktolight.util.StringConstatns;

@Repository
public class AdminLoginDaoImpl implements AdminLoginDao{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginDaoImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	
	/**
	 * here we are checking whether the user name and password are exist in
	 * the table
	 * @returns boolean if user name and password present in table
	 */
	@SuppressWarnings("unchecked")
	public boolean checkLogin(String userName, String password) {

		logger.info("checkLogin() method In AdminLoginDaoImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		boolean userFound = StringConstatns.booleenFalse;

		try {

			// Query to check the user name and password in the table
			List<AdminLogin> list = session
					.createQuery("from AdminLogin where userName = :userName and  password = :password")
					.setString("userName", userName).setString("password", password).list();
			if ((list != null) && (list.size() > 0)) {
				userFound = StringConstatns.booleanTrue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("checkLogin() method In AdminLoginDaoImpl class :- End");
		return userFound;

	}
	
	
}
