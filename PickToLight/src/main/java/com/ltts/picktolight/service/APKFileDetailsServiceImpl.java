/**
 * 
 */
package com.ltts.picktolight.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.APKFileDetailsDAO;
import com.ltts.picktolight.domain.APKFileDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 90001332
 *
 */
@Service
public class APKFileDetailsServiceImpl implements APKFileDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(APKFileDetailsServiceImpl.class);

	private APKFileDetailsDAO apkFileDetailsDAO;


	public void setApkDetailsDAO(APKFileDetailsDAO apkFileDetailsDAO) {
		this.apkFileDetailsDAO = apkFileDetailsDAO;
	}


	/**
	 * this method sends the APK file details to DAO implementation class
	 * @param apkFileDetails POJO class object which holds APK file details
	 * @return String which holds status as successfully saved
	 */
	@Override
	@Transactional
	public String saveAPKDetails(APKFileDetails apkFileDetails) {
		logger.info("saveAPKDetails() method in APKFileDetailsServiceImpl Class :- Start");
		//calling saveAPKDetails() DAO Implementation class
		String str = this.apkFileDetailsDAO.saveAPKDetails(apkFileDetails);
		logger.info("saveAPKDetails() method in APKFileDetailsServiceImpl Class :- End");
		return str;
	}


	/**
	 * this method sends the APK file details to DAO implementation class
	 * @param apkFileDetails POJO class object which holds APK file details
	 * @return Object which holds APK file details
	 */
	@Override
	@Transactional
	public APKFileDetails getAPKDetails(APKFileDetails apkFileDetails) {
		logger.info("getAPKDetails() method in APKFileDetailsServiceImpl Class :- Start");
		//calling getAPKDetails() DAO Implementation class
		APKFileDetails aPKFileDetails = this.apkFileDetailsDAO.getAPKDetails(apkFileDetails);
		logger.info("getAPKDetails() method in APKFileDetailsServiceImpl Class :- End");
		return aPKFileDetails;
	}

}
