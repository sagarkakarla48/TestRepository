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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.ProductCartDetails;
import com.ltts.picktolight.service.CartService;
import com.ltts.picktolight.service.MisMatchProductsService;
import com.ltts.picktolight.service.ProductService;

/**
 * @author 90001334
 *
 */
@RestController
public class CartIncompleteRestController {

	private static final Logger logger = LoggerFactory.getLogger(CartIncompleteRestController.class);

	@Autowired
	CartService cartService;
	@Autowired
	private MisMatchProductsService misMatchProductsService;
	@Autowired
	private ProductService productService;

	/**
	 * API to get in complete cart details
	 * @param order id from android APP through REST End point
	 * @return in complete cart details form of JSON 
	 *
	 */

	@RequestMapping("/getIncompleteCartDetails")
	public Map<String, List<Object>> getIncompletecartDetails(){
		logger.info("getIncompletecartDetails() method in CartIncompleteRestController Class :- Start");
		Map<String, List<Object>> objectssmap = new LinkedHashMap<String, List<Object>>();
		List<Object> inCompleteOrdersList = new ArrayList<Object>();		
		try{
			List<ProductBarcode> ordersList = this.productService.getOrdersToIncompleteOrders();

			List<MisMatchProducts> misMatchProducts = this.misMatchProductsService.getMisMatchproductsToIncompleteOrders();

			List<ProductCartDetails> cartDetails = this.cartService.getCartDetailsOfOrdersInSupermarket();

			List<IncompleteOrders> inCompleteOrders = new ArrayList<IncompleteOrders>();	

			IncompleteOrders order_item;

			for(int i = 0; i < misMatchProducts.size(); i++)
			{
				ProductBarcode product_item = new ProductBarcode();
				product_item.setProductId(misMatchProducts.get(i).getProductId());
				product_item.setOrderid(misMatchProducts.get(i).getOrderid());
				product_item.setSuperMarketDesc(misMatchProducts.get(i).getSuperMarketDesc());
				ordersList.add(product_item);
			}

			for(int i = 0; i < ordersList.size(); i++)
			{
				boolean orderIdExists =  false;
				for(int j = 0; j < inCompleteOrders.size() ; j++ )
				{
					if(inCompleteOrders.get(j).getOrderId().equals(ordersList.get(i).getOrderid())) 
					{
						orderIdExists =  true;
						inCompleteOrders.get(j).addProduct(ordersList.get(i));
						break;
					}
				}
				if(!orderIdExists)
				{
					order_item = new IncompleteOrders();
					order_item.setOrderId(ordersList.get(i).getOrderid());
					order_item.addProduct(ordersList.get(i));
					inCompleteOrders.add(order_item);
				}

			}
			for(int i = 0; i < cartDetails.size(); i++)
			{
				for(int j = 0; j < inCompleteOrders.size() ; j++ )
				{
					if(inCompleteOrders.get(j).getOrderId().equals(cartDetails.get(i).getOrderId())) 
					{
						inCompleteOrders.get(j).setCartId(cartDetails.get(i).getCartId());
						break;
					}
				}

			}

			inCompleteOrdersList.addAll(inCompleteOrders);
			objectssmap.put("incompleteCartDetails",inCompleteOrdersList);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());			
		}
		logger.info("getIncompletecartDetails() method in CartIncompleteRestController Class :- End");
		return objectssmap;
	}



	@RequestMapping("/orderIncomplete/{orderId}/{supermarket}")
	public String getIncompleteOrderProducts(@PathVariable String orderId, @PathVariable String supermarket){
		logger.info("getIncompleteOrderProducts() method in CartIncompleteRestController Class :- Start");

		Map<String, List<ProductBarcode>> objectssmap = new LinkedHashMap<String, List<ProductBarcode>>();
		ProductBarcode products = new ProductBarcode();
		String status=null;
		try{
			products.setOrderid(orderId);
			products.setSuperMarketDesc(supermarket);
			//String status_1=this.productService.updateForceCompleteStatus(products);
			List<ProductBarcode> orderProductsList = this.productService.getIncompleteOrderProducts(products);
			status = "200";
			ObjectMapper omapper=new ObjectMapper();
			status=omapper.writeValueAsString(status);
			
			if(orderProductsList!=null && orderProductsList.size()>0){
				objectssmap.put("orderIncomplete",orderProductsList);
				for(ProductBarcode orderProducts:orderProductsList){
					String rackId = orderProducts.getUploadProducts().getRackNum();
					String messageString="RESET";
					MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
					client.connect();
					MqttMessage message = new MqttMessage();
					message.setPayload(messageString.getBytes());
					client.publish(rackId, message);						    
					System.out.println("\tMessage '"+ messageString +"' to '"+rackId);
					client.disconnect();
				}
			}			

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());		
		}
		logger.info("getIncompleteOrderProducts() method in CartIncompleteRestController Class :- End");
		return status;		
	}


	@RequestMapping("/completeOrder")
	public String updatedOrderIncompleteProducts(@RequestBody IncompleteOrders orderProducts){
		logger.info("updatePlacedProducts() method in CartIncompleteRestController Class :- Start");
		String status=null;
		try{
			if(orderProducts!=null){
				status=this.cartService.updatedOrderIncompleteProducts(orderProducts);
				ObjectMapper omapper=new ObjectMapper();
				status=omapper.writeValueAsString(status);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());			
		}
		logger.info("updatePlacedProducts() method in CartIncompleteRestController Class :- End");
		return status;
	}

}
