/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author 90001332
 *
 */
@Controller
public class ShowStatusInTVController {

	private static final Logger logger = LoggerFactory.getLogger(ShowStatusInTVController.class);

	@Autowired
	private ProductService productService;
	private static final String orderStatus = "OrderStatus";

	/**
	 * This method helps us to hit orderstatus page.
	 * @param
	 * @return the specified orderstatus.JSP page
	 */
	@RequestMapping(value = "/tvstatus", method = RequestMethod.GET)
	public String tvStatus() {
		logger.info("tvStatus() method in ShowStatusInTVController Class :- Start");
		logger.info("tvStatus() method in ShowStatusInTVController Class :- End");
		return orderStatus;
	}
	
	/**
	 * gets the order status from DB to display on TV screen
	 * @return a JSON object having not picked products and picked orders 
	 */
	@RequestMapping(value = "/getorderstatus", method = RequestMethod.POST)
	public @ResponseBody String getOrderStatus() {
		
		logger.info("getOrderStatus() method in ShowStatusInTVController Class :- Start");
		
		String jsonList = null;
		try {
			//gets the list from serviceImplementation class
			List<ProductBarcode> list = this.productService.getOrderStatus();
			ObjectMapper objMapper = new ObjectMapper();
			jsonList = objMapper.writeValueAsString(list);			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("getOrderStatus() method in ShowStatusInTVController Class :- End");
		return jsonList;			
	}

}
