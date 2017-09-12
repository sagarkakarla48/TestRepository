/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.APKFileDetails;
import com.ltts.picktolight.util.StringConstatns;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 90001332
 *
 */
@Repository
public class APKFileDetailsDAOImpl implements APKFileDetailsDAO {

	private static final Logger logger = LoggerFactory.getLogger(APKFileDetailsDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	/**
	 * here the uploaded APK file details will be saved to database
	 * @param apkFileDetails POJO class object which holds APK file details
	 * @return String success message
	 */
	@Override
	public String saveAPKDetails(APKFileDetails apkFileDetails) {

		logger.info("saveAPKDetails() method in APKFileDetailsDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		try{
			//query to save  the APK details in database
			session.save(apkFileDetails);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("saveAPKDetails() method in APKFileDetailsDAOImpl Class :- End");
		return StringConstatns.success;
	}


	/**
	 * gets the APK details from database based on date and time
	 * @param apkFileDetails POJO class object
	 * @return POJO class object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public APKFileDetails getAPKDetails(APKFileDetails apkFileDetails) {

		logger.info("getAPKDetails() method in APKFileDetailsDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();

		// Query to select the details of APK file by date and time
		List<APKFileDetails> apkFileList = session.createQuery("from APKFileDetails order by datetime desc")
				.setMaxResults(1).list();
		APKFileDetails apkFileDet = new APKFileDetails();

		try {
			if (apkFileList != null) {

				// looping through APK files in apkFileList
				for (APKFileDetails apkfile : apkFileList) {
					apkFileDet = apkfile;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("getAPKDetails() method in APKFileDetailsDAOImpl Class :- End");
		return apkFileDet;
	}


}
