/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.ColorPicker;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */
@Repository
public class ColorPickerDAOImpl implements ColorPickerDAO {

	private static final Logger logger = LoggerFactory.getLogger(ColorPickerDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	/**
	 * we are getting all the colors from database whose status is false
	 * @return POJO list which have color codes
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ColorPicker> getColorList() {

		logger.info("getColorList() method in ColorPickerDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		String status = StringConstatns.statusFalse;
		List<ColorPicker> colorList = new ArrayList<ColorPicker>();
		try {

			// selecting colorcodes from database whose status is false
			colorList = session.createQuery("from ColorPicker where status=:status").setParameter("status", status)
					.setMaxResults(1).list();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("getColorList() method in ColorPickerDAOImpl Class :- End");
		return colorList;
	}



	/**
	 *To update the color status,if all the products in the order PICKED.
	 *@param cpobj holds the color of the color assigned to the Picked order
	 *@return status as True
	 */
	@Override
	public String updateColorStatus(ColorPicker cpobj) {

		logger.info("updateColorStatus() method in ColorPickerDAOImpl Class :- Start");

		String colorValue=cpobj.getColorValue();

		String status= StringConstatns.statusFalse;
		Session session = this.sessionFactory.getCurrentSession();

		try{
			
			//Updates the status of the color assigned to PICKED order as false to make it available for next orders
			int x=session.createQuery("update ColorPicker c set c.status='"+cpobj.getStatus()+"' where c.colorValue=:cname").setParameter("cname",colorValue).executeUpdate();
			if(x>0){
				status=StringConstatns.statusTrue;			
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}	

		logger.info("updateColorStatus() method in ColorPickerDAOImpl Class :- End");
		return status;
	}


}
