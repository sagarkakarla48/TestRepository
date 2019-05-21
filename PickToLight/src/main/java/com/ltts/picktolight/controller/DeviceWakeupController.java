/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.ltts.picktolight.service.UploadProductService;

/**
 * @author 90001332
 *
 */
@Controller
public class DeviceWakeupController {
	
	@Autowired
	UploadProductService uploadProductService;
	
	@Scheduled(cron="0 17/25 0/6 * * *", zone="Asia/Kolkata")
	public void wakeupdevices() {
		try{
				String messageString = "true";
				MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
				client.connect();
				MqttMessage message = new MqttMessage();
				message.setQos(1);
				message.setPayload(messageString.getBytes());
				client.publish("update_mqtt", message);	
				System.out.println("\tMessage '"+ messageString +"' to 'update_mqtt' ");
				client.disconnect();
			
			System.out.println("Method executed at 06:00, 12.00, 18:00 and 24:00 every day. Current time is :: "+ new Date());
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
	}

}
