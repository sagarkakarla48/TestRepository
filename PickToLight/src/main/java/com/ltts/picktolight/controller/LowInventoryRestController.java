/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.LowInventoryInfo;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.service.LowInventoryInfoService;
import com.ltts.picktolight.util.CurrentDateTime;

/**
 * @author 90001332
 *
 */

@RestController
public class LowInventoryRestController {

	private static final Logger logger = LoggerFactory.getLogger(LowInventoryRestController.class);

	@Autowired
	LowInventoryInfoService lowInventoryInfoService;

	/**
	 * API to save low inventory details
	 * @param productid, @param supermarket from android app through REST Endpoint
	 * @return status in the form of json as successfully saved or not
	 *
	 */
	@RequestMapping("/lowInventory/{productid}/{supermarket}/{rackid}")
	public Map<String, String> missedProduct(@PathVariable String productid,@PathVariable String supermarket,@PathVariable String rackid) {// REST Endpoint.

		logger.info("missedProduct() method in LowInventoryRestController Class :- Start");

		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		LowInventoryInfo prodInfo = new LowInventoryInfo();
		String status=null;
		try{
			prodInfo.setProductId(productid);
			prodInfo.setDatetime(CurrentDateTime.getCurrentTimeStamp());
			prodInfo.setSuperMarketDesc(supermarket);
			prodInfo.setUpdateddatetime(CurrentDateTime.getCurrentTimeStamp());
			prodInfo.setRackId(rackid);
			List<LowInventoryInfo> lowInventoryDataList=lowInventoryInfoService.getLowInventoryDetails(prodInfo);
			if(lowInventoryDataList==null || lowInventoryDataList.size()<=0){
				status=lowInventoryInfoService.saveLowInventoryInfo(prodInfo);
				if(status==null){
					status="Fail";
				}else{
					Map<String, String> pushNotification = new LinkedHashMap<String, String>();
					pushNotification.put("rackId",prodInfo.getRackId());
					pushNotification.put("productId",prodInfo.getProductId());
					pushNotification.put("superMarketDesc",prodInfo.getSuperMarketDesc());
					ObjectMapper oMapper=new ObjectMapper();
					String messageString = oMapper.writeValueAsString(pushNotification);
					MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
					client.connect();
					MqttMessage message = new MqttMessage();
					message.setPayload(messageString.getBytes());
					client.publish("low_stock", message);

					System.out.println("\tMessage '"+ messageString +"' to 'low_stock'");

					client.disconnect();
				}
				resultmap.put("status", status);				
			}else{
				resultmap.put("status", "Success");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			resultmap.put("status", "Fail");
		}finally{

		}

		logger.info("missedProduct() method in LowInventoryRestController Class :- End");
		return resultmap;
	}



	/**
	 * API to get low inventory details
	 * @return list of low inventory details
	 *
	 */
	
	@RequestMapping("/getLowInventoryInfoToApp")
	public Map<String,List<LowInventoryInfo>> getLowInventoryInfoToApp(){
		logger.info("getLowInventoryInfoToApp() method in LowInventoryRestController Class :- Start");
		Map<String,List<LowInventoryInfo>> resultObject = new LinkedHashMap<String,List<LowInventoryInfo>>();
		LowInventoryInfo prodInfo = new LowInventoryInfo();
		List<LowInventoryInfo> list = new ArrayList<LowInventoryInfo>();
		//String json=null;
		try{
			list=lowInventoryInfoService.getLowInventoryInfo(prodInfo);
			if(list!=null && list.size()>0){
				for(LowInventoryInfo productInfo:list){
					UploadProducts products=new UploadProducts();
					products=productInfo.getUploadProducts();
					productInfo.setRackId(products.getRackNum());
					productInfo.setProductName(products.getDescription());
				}
			}
			//ObjectMapper omapper=new ObjectMapper();
			//json=omapper.writeValueAsString(list);
			resultObject.put("lowInventoryData",list);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getLowInventoryInfo() method in LowInventoryController Class :- End");
		logger.info("getLowInventoryInfoToApp() method in LowInventoryRestController Class :- END");
		return resultObject;
	}

	/**
	 * @author 90001332  
	 * updating inventory details status 
	 * @return update status
	 *
	 */
	@RequestMapping(value = "/updateLowInventoryInfoFromApp", method = RequestMethod.POST)
	@ResponseBody
	public String updateLowInventoryInfo(@RequestBody List<LowInventoryInfo> inventoryList) {
		logger.info("updateLowInventoryInfo() method in LowInventoryController Class :- Start");
		String status=null;
		try{
			status=lowInventoryInfoService.updateLowInventoryInfo(inventoryList);
			ObjectMapper omapper=new ObjectMapper();
			status=omapper.writeValueAsString(status);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("updateLowInventoryInfo() method in LowInventoryController Class :- End");
		return status;
	}

}
