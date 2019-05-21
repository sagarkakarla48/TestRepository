package com.ltts.picktolight.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.BatteryInfo;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.service.BatteryInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class BatteryInfoController {

	private static final Logger logger = LoggerFactory.getLogger(BatteryInfoController.class);

	@Autowired
	BatteryInfoService batteryInfoService;
	private static final String batteryInfo = "BatteryInfo";


	/**
	 * This method gets the battery Info of each rack
	 * @param model to send list to the UI
	 * @return batteryLevel JSP page
	 * @throws JSONException
	 */
	@RequestMapping(value="/getBatteryStatus",method=RequestMethod.POST)
	public String getBatteryStatus(Model model) throws JSONException
	{
		logger.info("getBatteryStatus() method In BatteryInfoController Class :- Start");

		//gets the list of rack's battery level
		List<BatteryInfo> statuslist=this.batteryInfoService.getBattery();
		JSONArray jArray = new JSONArray();
		try{
			//Loops through rackid's in statuslist
			for (BatteryInfo battery : statuslist)
			{
				JSONObject batryJSON = new JSONObject();
				batryJSON.put("blevel", battery.getBatterylevel());
				batryJSON.put("rackid", battery.getRackNo());
				jArray.put(batryJSON);
			}
			model.addAttribute("list", jArray);
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{

		}		

		logger.info("getBatteryStatus() method In BatteryInfoController Class :- End");
		return batteryInfo;

	}


	@RequestMapping(value = "/getBatteryCount", method = RequestMethod.POST)
	public @ResponseBody int getBatteryCount() {

		logger.info("getOrderStatus() method in ShowStatusInTVController Class :- Start");

		//String status = null;
		int count = 0;
		try {
			List<BatteryInfo> batteryList=this.batteryInfoService.getBattery();
			for(BatteryInfo batteryInfo: batteryList){
				double batLevel = Double.parseDouble(batteryInfo.getBatterylevel());
				if(batLevel <= 30){
					count++;
				}
			}
			//status = count + " Devices Are Have < 30% Battery.";
			//System.out.println(status);

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getOrderStatus() method in ShowStatusInTVController Class :- End");
		return count;			
	}



}
