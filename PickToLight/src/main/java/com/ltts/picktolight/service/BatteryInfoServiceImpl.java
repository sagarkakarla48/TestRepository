/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.BatteryInfoDAO;
import com.ltts.picktolight.domain.BatteryInfo;
import com.ltts.picktolight.domain.UploadProducts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 90001332
 *
 */

@Service
public class BatteryInfoServiceImpl implements BatteryInfoService {

	private static final Logger logger = LoggerFactory.getLogger(BatteryInfoServiceImpl.class);

	private BatteryInfoDAO batteryInfoDAO;


	public void setBatteryLevelDAO(BatteryInfoDAO batteryInfoDAO) {
		this.batteryInfoDAO = batteryInfoDAO;
	}




	/**
	 * here we are sending battery details to DAOImplementation class to update in database
	 * @param batteryInfo POJO class object
	 * @return number of racks updated
	 */

	@Override
	@Transactional
	public Map<String, String> updateBatteryLevel(BatteryInfo batteryInfo) {
		logger.info("updateBatteryLevel() method in BatteryInfoServiceImpl Class :- Start");

		int i = 0;
		String updateStatus="";
		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		
		//calling updateBatteryLevel() in DAO
		i = this.batteryInfoDAO.updateBatteryLevel(batteryInfo);
		
		if(i>0){
			updateStatus="Updated Successfully";
		}else{
			updateStatus="Not Updated";
		}
		resultmap.put("status", updateStatus);

		logger.info("updateBatteryLevel() method in BatteryInfoServiceImpl Class :- End");
		return resultmap;
	}




	/**
	 * here we save the battery level and status  of each rack
	 * @param uploadProductList List which holds the details of racks
	 */

	@Override
	@Transactional
	public void saveBatteryLevels(List<UploadProducts> uploadProductList) {

		logger.info("saveBatteryLevels() method in BatteryInfoServiceImpl Class :- Start");

		List<BatteryInfo> batteryLevelList=new ArrayList<BatteryInfo>(); 

		if(uploadProductList!=null && uploadProductList.size()>0){
			for(UploadProducts upProd:uploadProductList){
				BatteryInfo batteryInfo=new BatteryInfo();
				batteryInfo.setRackNo(upProd.getRackNum());
				batteryInfo.setBatterylevel("100");
				batteryInfo.setStatus("Good");
				batteryLevelList.add(batteryInfo);
			}
			
			//calling saveBatteryLevels() in DAO
			this.batteryInfoDAO.saveBatteryLevels(batteryLevelList);
		}

		logger.info("saveBatteryLevels() method in BatteryInfoServiceImpl Class :- End");
	}


	/**
	 * this method gets the battery status of each rack
	 * @return List consists of rackid and battery level of all mobile devices
	 */

	@Transactional
	public List<BatteryInfo> getBattery() {

		logger.info("getBattery() method in BatteryInfoServiceImpl Class :- Start");
		List<BatteryInfo> batteryList = this.batteryInfoDAO.getBattery();	
		logger.info("getBattery() method in BatteryInfoServiceImpl Class :- End");
		return batteryList;
	}




}
