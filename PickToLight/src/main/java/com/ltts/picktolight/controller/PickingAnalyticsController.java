/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.service.ProductService;

/**
 * @author 90001332
 *
 */
@Controller
public class PickingAnalyticsController {
	
	private static final Logger logger = LoggerFactory.getLogger(PickingAnalyticsController.class);

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/pickingAnalytics",method=RequestMethod.POST)
	public String superMarketPickingTimeHome(){
		return "PickingAnalytics";
	}

	@RequestMapping(value = "/getPickingTimeDetails", method = RequestMethod.POST)
	@ResponseBody
	public String getPickingTimeDetails(@RequestParam String date) {
		logger.info("getPickingTimeDetails() method in PickingAnalyticsController Class :- Start");

		List<ProductBarcode> prodInfoList = new ArrayList<ProductBarcode>();
		String json=null;
		try{
			ProductBarcode productBarcode=new ProductBarcode();
			productBarcode.setDatetime(date);
			prodInfoList=this.productService.countTimePeriodForOrder(productBarcode);
			ObjectMapper omapper=new ObjectMapper();
			json=omapper.writeValueAsString(prodInfoList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getPickingTimeDetails() method in PickingAnalyticsController Class :- End");
		return json;
	}
}
