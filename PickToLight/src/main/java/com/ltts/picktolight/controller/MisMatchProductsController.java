/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.service.MisMatchProductsService;

/**
 * @author 90001332
 *
 */
@Controller
public class MisMatchProductsController {

	private static final Logger logger = LoggerFactory.getLogger(MisMatchProductsController.class);

	@Autowired
	private MisMatchProductsService misMatchProductsService;

	/**
	 * to save all the products details of an order
	 * here we are sending those details to addproduct() 
	 * @param productlist holds products details
	 * @return
	 */
	@RequestMapping(value = "/saveMisMatchedProducts", method = RequestMethod.POST)
	public @ResponseBody String saveMisMatchedProducts(@RequestBody List<MisMatchProducts> productlist) {

		logger.info("saveMisMatchedProducts() method in MisMatchProductsController Class :- Start");	
		String status=null;
		try {
			if (productlist != null) {
				status=this.misMatchProductsService.saveMisMatchedProducts(productlist);
				if("Success".equalsIgnoreCase(status)){
					List<MisMatchProducts> misMatchProductsList = this.misMatchProductsService.getMisMatchproducts();
					if(misMatchProductsList!=null && misMatchProductsList.size()>0){
						Map<String, List<MisMatchProducts>>	pushNotification = new LinkedHashMap<String, List<MisMatchProducts>>();
						pushNotification.put("unavailableProducts", misMatchProductsList);
						ObjectMapper resultMapper=new ObjectMapper();
						String messageString = resultMapper.writeValueAsString(pushNotification);	
						MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
						client.connect();
						MqttMessage message = new MqttMessage();
						message.setPayload(messageString.getBytes());
						client.publish("unavailable_items", message);
						System.out.println("\tMessage '"+ messageString +"' to 'unavailable_items'");
						client.disconnect();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("saveMisMatchedProducts() method in MisMatchProductsController Class :- End");	
		return status;
	}
	

}
