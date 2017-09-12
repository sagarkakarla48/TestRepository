/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.BatteryInfo;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.CurrentDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 90001332
 *
 */
@Repository
public class BatteryInfoDAOImpl implements BatteryInfoDAO {


	private static final Logger logger = LoggerFactory.getLogger(BatteryInfoDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}



	/**
	 * to update the battery info of each rack time to time
	 * @param batteryInfo POJO class object which holds the details of racks
	 * @return number of racks updated
	 */
	@Override
	public int updateBatteryLevel(BatteryInfo batteryInfo) {
		logger.info("updateBatteryLevel() method in BatteryInfoDAOImpl Class :- Start");

		int x=0;
		String hql=null;
		String datetime=CurrentDateTime.getCurrentTimeStamp();

		//Query to update the status of battery level of each rack
		StringBuffer strBuffer=new StringBuffer();
		strBuffer.append("update BatteryInfo bl set bl.batterylevel='"+batteryInfo.getBatterylevel()+"',");
		strBuffer.append("bl.status='"+batteryInfo.getStatus()+"',bl.datetime='"+datetime+"' ");
		strBuffer.append(" where bl.rackNo='"+batteryInfo.getRackNo()+"' ");
		hql=strBuffer.toString();

		try{
			Session session = this.sessionFactory.getCurrentSession();	

			//executing the update query 
			x=session.createQuery(hql).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		logger.info("updateBatteryLevel() method in BatteryInfoDAOImpl Class :- End");
		return x;
	}



	/**
	 * save the battery Info of all racks
	 * @param	batteryLevelList is a list that holds the rackid's,battery level and status
	 */
	@Override
	public void saveBatteryLevels(List<BatteryInfo> batteryLevelList) {
		logger.info("saveBatteryLevels() method in BatteryInfoDAOImpl Class :- Start");

		try{
			Session session = this.sessionFactory.getCurrentSession();

			//Looping through all the racks in batteryLevelList 
			for(BatteryInfo batteryInfo : batteryLevelList){
				@SuppressWarnings("unchecked")
				List<BatteryInfo> list = session.createCriteria(BatteryInfo.class)					    
				.add(Restrictions.eq("rackNo", batteryInfo.getRackNo()))
				.list();

				if(list == null || list.size() <= 0){
					//Query to save the details of battery level of each rack
					session.saveOrUpdate(batteryInfo);						
				}

			}	
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("saveBatteryLevels() method in BatteryInfoDAOImpl Class :- End");		
	}



	/**
	 * this method gives all the rack id's and their battery Info
	 * @return list which have rackid's and battery status 
	 */
	@SuppressWarnings("unchecked")
	public List<BatteryInfo> getBattery() {

		logger.info("saveBatteryLevels() method in BatteryInfoDAOImpl Class :- Start");

		List<BatteryInfo> statusList = new ArrayList<BatteryInfo>();
		try{

			Session session = this.sessionFactory.getCurrentSession();

			//this query select all the rack id's and battery level from database
			statusList =(List<BatteryInfo>)session.createQuery("from BatteryInfo").list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("saveBatteryLevels() method in BatteryInfoDAOImpl Class :- Start");
		return statusList;

	}

}
