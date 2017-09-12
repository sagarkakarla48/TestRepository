/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.LowInventoryInfo;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.service.LowInventoryInfoService;

/**
 * @author 90001332
 *
 */
@Controller
public class LowInventoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(LowInventoryController.class);
	
	@Autowired
	LowInventoryInfoService lowInventoryInfoService;
	
	/**
	 * @author 90001332  
	 * Calling Missed Products Page 
	 * @return MissedProducts.jsp page
	 *
	 */
	@RequestMapping(value = "/lowInventoryInfoHome", method = RequestMethod.POST)
	public String missedProductsHome() {
		logger.info("missedProduct() method in LowInventoryController Class :- Start");
		logger.info("missedProduct() method in LowInventoryController Class :- End");
		return "LowInventoryInfo";
	}
	
	/**
	 * @author 90001332  
	 * getting Missed Products 
	 * @return MissedProducts details
	 *
	 */
	@RequestMapping(value = "/getLowInventoryInfo", method = RequestMethod.POST)
	@ResponseBody
	public String getLowInventoryInfo() {
		logger.info("getLowInventoryInfo() method in LowInventoryController Class :- Start");

		LowInventoryInfo prodInfo = new LowInventoryInfo();
		String json=null;
		try{
			List<LowInventoryInfo> list=lowInventoryInfoService.getLowInventoryInfo(prodInfo);
			if(list!=null && list.size()>0){
				for(LowInventoryInfo productInfo:list){
					UploadProducts products=new UploadProducts();
					products=productInfo.getUploadProducts();
					productInfo.setRackId(products.getRackNum());
					productInfo.setProductName(products.getDescription());
				}
			}
			ObjectMapper omapper=new ObjectMapper();
			json=omapper.writeValueAsString(list);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getLowInventoryInfo() method in LowInventoryController Class :- End");
		return json;
	}
	
	/**
	 * @author 90001332  
	 * updating inventory details status 
	 * @return update status
	 *
	 */
	@RequestMapping(value = "/updateLowInventoryInfo", method = RequestMethod.POST)
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
