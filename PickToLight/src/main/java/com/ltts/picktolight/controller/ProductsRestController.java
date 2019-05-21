/**
 * 
 */
package com.ltts.picktolight.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltts.picktolight.domain.APKFileDetails;
import com.ltts.picktolight.domain.BatteryInfo;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.ProductCartDetails;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.service.APKFileDetailsService;
import com.ltts.picktolight.service.BatteryInfoService;
import com.ltts.picktolight.service.CartService;
import com.ltts.picktolight.service.LowInventoryInfoService;
import com.ltts.picktolight.service.ProductService;
import com.ltts.picktolight.service.UploadProductService;
import com.ltts.picktolight.util.CurrentDateTime;
import com.ltts.picktolight.util.StringConstatns;
/**
 * @author 90001332
 *
 */
@RestController
public class ProductsRestController {

	private static final Logger logger = LoggerFactory.getLogger(ProductsRestController.class);

	private static final String String = null;

	@Autowired
	private ProductService productService;
	@Autowired
	private BatteryInfoService batteryInfoService;
	@Autowired
	APKFileDetailsService apkFileDetailsService;
	@Autowired
	UploadProductService uploadProductService;
	@Autowired
	LowInventoryInfoService lowInventoryInfoService;
	@Autowired
	CartService cartService;

	/**
	 * API to get different orders of a product belongs to the rack id
	 * @param rackid from android APP through REST End point
	 * @return different orders of product belongs to the rack id in the form of JSON and 
	 * at a time we are returning utmost 3 orders
	 *
	 */

	//@RequestMapping("/getOrders/{rackid}")
	public Map<String, List<Map<String, String>>> getOrders(@PathVariable String rackid) {// REST Endpoint.

		logger.info("getOrders() method in ProductsRestController Class :- Start");
		Map<String, List<Map<String, String>>> objectssmap = new LinkedHashMap<String, List<Map<String, String>>>();
		UploadProducts products = new UploadProducts();

		try{
			products.setRackNum(rackid);
			//To get orders of the product
			objectssmap = this.productService.getProductDetailsByRackId(products);

			//This is to delete the completed orders
			this.productService.checkForCompletedOrders();

			UploadProducts productinfo=new UploadProducts();
			productinfo.setRackNum(rackid);
			productinfo.setDatetime(CurrentDateTime.getCurrentTimeStamp());

			//This is to update the product datetime to track mobile device working or not
			this.uploadProductService.updateProductDateTime(productinfo);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("getOrders() method in ProductsRestController Class :- End");
		return objectssmap;
	}



	/*//@RequestMapping("/getOrders/{rackid}")   @PathVariable 
	public Map<String, List<Map<String, String>>> getOrders(String rackid) {// REST Endpoint.

		logger.info("getOrders() method in ProductsRestController Class :- Start");
		Map<String, List<Map<String, String>>> objectssmap = new LinkedHashMap<String, List<Map<String, String>>>();
		UploadProducts products = new UploadProducts();

		try{
			products.setRackNum(rackid);
			//To get orders of the product
			objectssmap = this.productService.getProductDetailsByRackId(products);

			//This is to delete the completed orders
			this.productService.checkForCompletedOrders();

			UploadProducts productinfo=new UploadProducts();
			productinfo.setRackNum(rackid);
			productinfo.setDatetime(CurrentDateTime.getCurrentTimeStamp());

			//This is to update the product datetime to track mobile device working or not
			this.uploadProductService.updateProductDateTime(productinfo);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("getOrders() method in ProductsRestController Class :- End");
		return objectssmap;
	}*/

	/**
	 * API to update status for picked products based on order id and product id/barcode
	 * @param orderid, @param productid from android app through REST Endpoint
	 * @return status in the form of json as successfully Picked or not
	 *
	 */
	@RequestMapping("/updateProductOrderStatus/{orderid}/{productid}")
	public Map<String, String> productOrderDetailsUpdate(@PathVariable String orderid, @PathVariable String productid) {// REST Endpoint.

		logger.info("productOrderDetailsUpdate() method in ProductsRestController Class :- Start");

		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		ProductBarcode prodbean = new ProductBarcode();
		try{

			prodbean.setBarcode(productid);
			prodbean.setProductId(productid);
			prodbean.setOrderid(orderid);
			prodbean.setDatetime(CurrentDateTime.getCurrentTimeStamp());
			prodbean.setPickEndTime(CurrentDateTime.getCurrentTimeStamp());
			prodbean.setForceCompleteStatus(StringConstatns.forceCompleteStatusNo);
			resultmap = productService.updateProduct(prodbean);
			
			String messageString="";
			ObjectMapper om = new ObjectMapper();					
			messageString = om.writeValueAsString(prodbean);
			MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
			client.connect();
			MqttMessage message = new MqttMessage();
			message.setPayload(messageString.getBytes());
			client.publish("incomplete_cart_update", message);	
			System.out.println("\tMessage '"+ messageString +"' to incomplete_cart_update");
			client.disconnect();

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			resultmap.put("status", "Order Not Picked");
		}finally{

		}

		logger.info("productOrderDetailsUpdate() method in ProductsRestController Class :- End");
		return resultmap;
	}

	/**
	 * API to update battery status
	 * @param rackid, @param batterylevel and @param status from android app through REST Endpoint
	 * @return status in the form of JSON as successfully updated or not
	 */ 
	@RequestMapping("/updateBatteryInfo/{rackid}/{batterylevel}/{status}")
	public Map<String, String> batteryInfoUpdate(@PathVariable String rackid, @PathVariable float batterylevel,@PathVariable String status) {// REST Endpoint.

		logger.info("batteryInfoUpdate() method in ProductsRestController Class :- Start");
		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		BatteryInfo batteryInfo=new BatteryInfo();

		try{

			batteryInfo.setRackNo(rackid);
			batteryInfo.setBatterylevel(String.valueOf(batterylevel));
			batteryInfo.setStatus(status);
			System.out.println(rackid+""+batterylevel);
			//To update battery level based on rack id
			resultmap=batteryInfoService.updateBatteryLevel(batteryInfo);

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			resultmap.put("status", "Not Updated");
		}finally{

		}

		logger.info("batteryInfoUpdate() method in ProductsRestController Class :- End");
		return resultmap;
	}


	/**
	 * API to check for APK updates
	 * @param version from android app through REST Endpoint
	 * @return apk path in the form of json if version differs otherwise empty
	 *
	 */ 
	@RequestMapping("/checkForApkUpdates/{version}/")
	public Map<String, String> checkForApkUpdates(@PathVariable float version) {// REST Endpoint.

		logger.info("checkForApkUpdates() method in ProductsRestController Class :- Start");
		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		APKFileDetails apkFileDetails = new APKFileDetails();
		apkFileDetails.setVersion(String.valueOf(version));

		try{
			//To get Apk details
			apkFileDetails = apkFileDetailsService.getAPKDetails(apkFileDetails);

			if (apkFileDetails != null && apkFileDetails.getVersion()!=null) {
				float newVersion = Float.valueOf(apkFileDetails.getVersion());

				//checking installed APK version with recently uploaded APK version
				if (version < newVersion) {
					String apkpath = "resources/" + "apkFiles/" + apkFileDetails.getApkname();
					resultmap.put("apkpath", apkpath);
				}
			} 
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}finally{

		}
		logger.info("checkForApkUpdates() method in ProductsRestController Class :- End");
		return resultmap;
	}


	public void subscriber(String rackNumber) throws MqttException{
		System.out.println("== START SUBSCRIBER ==");

		MqttClient client=new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
		client.setCallback( new SimpleMqttCallBack() );
		client.connect();    
		client.subscribe(rackNumber);
	}

	/**
	 * API to get data from phone barcode scanner
	 * @param barcode from android app through REST Endpoint
	 * @return status
	 *
	 */ 
	@RequestMapping("/ordersThroughPhoneBarcodeScanner/{contents}")
	public Map<String, String> ordersThroughPhoneBarcodeScanner(@PathVariable String contents) {// REST Endpoint.
		logger.info("ordersThroughPhoneBarcodeScanner() method in ProductsRestController Class :- Start");
		String status="No Records Found";
		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		//List<String> supermarketList=new ArrayList<String>();
		Map<String, List<Map<String, String>>> objectssmap=null;
		try{
			String[] content=contents.split(",");
			if(content!=null && content.length==3){
				String superMarket = content[0].substring(1,content[0].length());
				String cartId = content[1].trim();
				String orderId = content[2].substring(0,content[2].length()-1).trim();
				String date = CurrentDateTime.getCurrentTimeStamp();
				ProductBarcode prodBarcode =new ProductBarcode();
				prodBarcode.setSuperMarketDesc(superMarket);
				prodBarcode.setOrderid(orderId);
				prodBarcode.setDatetime(date);
				prodBarcode.setPickStartTime(date);
				status = this.productService.makeOrderedProductActive(prodBarcode);

				logger.info("saving cart details--start");

				ProductCartDetails cartDetails=new ProductCartDetails();
				cartDetails.setSuperMarketDesc(superMarket);
				cartDetails.setCartId(cartId);
				cartDetails.setOrderId(orderId);				
				cartDetails.setDatetime(date);
				String cartStatus=this.cartService.saveCartDetailsOfOrdersInSupermarket(cartDetails);
				System.out.println("Status of cartdetails saved to database:"+cartStatus);

				logger.info("saving cart details--end");

				if("Success".equalsIgnoreCase(status)){
					String rackNumber = "";
					List<ProductBarcode> productOrdersList = this.productService.getProductOrdersWithActive(prodBarcode);
					for(ProductBarcode productOrder:productOrdersList){
						String messageString = "";
						rackNumber = productOrder.getUploadProducts().getRackNum();
						objectssmap = new LinkedHashMap<String, List<Map<String, String>>>();
						objectssmap = getOrders(rackNumber);
						ObjectMapper om = new ObjectMapper();					
						messageString = om.writeValueAsString(objectssmap);
						MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
						client.connect();
						MqttMessage message = new MqttMessage();
						message.setPayload(messageString.getBytes());
						message.setQos(1);
						client.publish(rackNumber, message);	
						System.out.println("\tMessage '"+ messageString +"' to '"+rackNumber);
						client.disconnect();
						/*List<UploadProducts> productsInfoList = getSupermarketsNotHavingProducts(productOrder);	
						if(productsInfoList!=null && productsInfoList.size()>0){
							for(UploadProducts product:productsInfoList){
								supermarketList.add(product.getSuperDescription());	
								System.out.println("In Super market : "+supermarketList.toString()+" Products : "+product.getProductId()+" Not Available");
							}
						}else{
							resultmap.put("status",status);
						}*/
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			resultmap.put("status",status);
		}
		logger.info("ordersThroughPhoneBarcodeScanner() method in ProductsRestController Class :- End");
		return resultmap;
	}

	/*@RequestMapping("/ordersThroughPhoneBarcodeScanner/{contents}")
	public Map<String, String> ordersThroughPhoneBarcodeScanner(@PathVariable String contents) {// REST Endpoint.
		logger.info("ordersThroughPhoneBarcodeScanner() method in ProductsRestController Class :- Start");
		String status="No Records Found";
		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		List<UploadProducts> supermarketList=new ArrayList<UploadProducts>();
		try{
			String[] content=contents.split(",");
			if(content!=null && content.length==3){
				ProductBarcode prodBarcode =new ProductBarcode();
				System.out.println(content[0].substring(1,content[0].length())+" == "+content[1]+" == "+content[2].substring(0,content[2].length()-1));
				prodBarcode.setSuperMarketDesc(content[0].substring(1,content[0].length()));
				prodBarcode.setSerialNo(content[1].trim());
				prodBarcode.setOrderid(content[2].substring(0,content[2].length()-1).trim());
			ProductBarcode prodBarcode =new ProductBarcode();
			prodBarcode.setSuperMarketDesc("supermarket_1");
			prodBarcode.setSerialNo("ser_1");
			prodBarcode.setOrderid("order_1");
			prodBarcode.setForceCompleteStatus(StringConstatns.forceCompleteStatusYes);
			prodBarcode.setDatetime(CurrentDateTime.getCurrentTimeStamp());
			prodBarcode.setPickStartTime(CurrentDateTime.getCurrentTimeStamp());
			//status = this.productService.makeOrderedProductActive(prodBarcode);
			if(prodBarcode.getForceCompleteStatus()=="NO"){
				List<ProductBarcode> productOrdersList = this.productService.getProductOrdersWithActive(prodBarcode);
				if(productOrdersList!=null && productOrdersList.size()>0){
					supermarketList = getSuperMarketsNotHavingAnyProducts(productOrdersList);
					if(supermarketList!=null && supermarketList.size()>0){
						//resultmap.put("status",supermarketList);	
						for(UploadProducts superMarket: supermarketList){
							System.out.println(superMarket.getSuperDescription()+" doesn't have "+superMarket.getProductId());
						}
					}else{
						status = this.productService.makeOrderedProductActive(prodBarcode);
						resultmap.put("status",status);	
					}
				}				
			}else{
				status = this.productService.makeOrderedProductActive(prodBarcode);
				resultmap.put("status",status);					
			}
			//}	
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("ordersThroughPhoneBarcodeScanner() method in ProductsRestController Class :- End");
		return resultmap;
	}

	private List<UploadProducts> getSuperMarketsNotHavingAnyProducts(List<ProductBarcode> productOrdersList) {
		logger.info("getSupermarketsNotHavingProducts() method in ProductsRestController Class :- Start");	
		List<UploadProducts> supermarketList=new ArrayList<UploadProducts>();
		try {	
			supermarketList=this.uploadProductService.getSuperMarketsNotHavingAnyProducts(productOrdersList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getSupermarketsNotHavingProducts() method in ProductsRestController Class :- End");	
		return supermarketList;	
	}*/

	/**
	 * API to get data from phone barcode scanner after forcefullysubmitted
	 * @param barcode from android app through REST Endpoint
	 * @return status
	 *
	 */
	@RequestMapping("/orderForcefullySubmit/{content}")
	public Map<String,String> ordersWhenForcefullySubmit(@PathVariable String contents){
		logger.info("ordersWhenForcefullySubmit() method in ProductRestController Class :- Start");
		String status="No records found";
		Map<String,String> resultMap = new LinkedHashMap<String,String>();
		try{
			String[] content = contents.split(",");
			if(content!=null && content.length==3){
				ProductBarcode prodBarcode =new ProductBarcode();
				System.out.println(content[0].substring(1,content[0].length())+" == "+content[1]+" == "+content[2].substring(0,content[2].length()-1));
				prodBarcode.setSuperMarketDesc(content[0].substring(1,content[0].length()));
				prodBarcode.setSerialNo(content[1].trim());
				prodBarcode.setOrderid(content[2].substring(0,content[2].length()-1).trim());	
				prodBarcode.setDatetime(CurrentDateTime.getCurrentTimeStamp());
				prodBarcode.setPickStartTime(CurrentDateTime.getCurrentTimeStamp());
				status = this.productService.makeOrderedProductActive(prodBarcode);				
			}			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			resultMap.put("status",status);
		}
		logger.info("ordersWhenForcefullySubmit() method in ProductRestController Class :- End");
		return null;
	}




	/**
	 * API to get order status
	 * @param rackid from android APP through REST End point
	 * @param supermarket from android APP through REST End point
	 * @return order completion status in the form of JSON 
	 */
	@RequestMapping("/orderCompletionStatus/{orderid}/{supermarket}")
	public Map<String, Long> getOrderCompletionStatus(@PathVariable String orderid,@PathVariable String supermarket) {// REST Endpoint.

		logger.info("getOrderCompletionStatus() method in ProductsRestController Class :- Start");
		Map<String, Long> prodcount = new LinkedHashMap<String, Long>();
		long remainingProducts=0;
		try{

			ProductBarcode product=new ProductBarcode();
			product.setSuperMarketDesc(supermarket);
			product.setOrderid(orderid);
			//gets the list from serviceImplementation class
			remainingProducts = this.productService.getOrderCompletionStatus(product);
			prodcount.put("count", remainingProducts);
			logger.info("getOrderCompletionStatus() method in ProductsRestController Class :- End");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("getOrderCompletionStatus() method in ProductsRestController Class :- End");
		return prodcount;
	}

	/**
	 * API to get not picked product details 
	 * @param rackid from android APP through REST End point
	 * @param supermarket from android APP through REST End point
	 * @return order completion status in the form of JSON 
	 */
	@RequestMapping("/getNotPickedProductsDetails/{orderid}/{supermarket}")
	public Map<String,List<Map<String, String>>> getNotPickedProductDetails(@PathVariable String orderid,@PathVariable String supermarket) {// REST Endpoint.

		logger.info("getNotPickedProductDetails() method in ProductsRestController Class :- Start");
		Map<String,List<Map<String, String>>> productsmap=new LinkedHashMap<String,List<Map<String, String>>>();
		List<Map<String, String>> productslist = new ArrayList<Map<String, String>>();
		try{

			ProductBarcode product=new ProductBarcode();
			product.setSuperMarketDesc(supermarket);
			product.setOrderid(orderid);
			//gets the list from serviceImplementation class
			productslist = this.productService.getNotPickedProductDetails(product);
			productsmap.put("productdetails", productslist);
			logger.info("getOrderCompletionStatus() method in ProductsRestController Class :- End");
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("getNotPickedProductDetails() method in ProductsRestController Class :- End");
		return productsmap;
	}

	/**
	 * API to update status for picked products based on order id and product id/barcode
	 * @param orderid, @param productid from android app through REST Endpoint
	 * @return status in the form of json as successfully Picked or not
	 *
	 */
	@RequestMapping("/countTimePeriodForOrder")
	public Map<String, String> countTimePeriodForOrder() {// REST Endpoint.

		logger.info("countTimePeriodForOrder() method in ProductsRestController Class :- Start");

		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		List<ProductBarcode> prodInfoList = new ArrayList<ProductBarcode>();
		try{
			//prodInfoList=this.productService.countTimePeriodForOrder();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			resultmap.put("status", null);
		}finally{

		}

		logger.info("countTimePeriodForOrder() method in ProductsRestController Class :- End");
		return null;
	}



	/**
	 * API to Known Which Supermarkets doesn't have products
	 * @return Supermarkets which are not having products
	 *
	 */	
	public List<UploadProducts> getSupermarketsNotHavingProducts(ProductBarcode productOrder){
		logger.info("getSupermarketsNotHavingProducts() method in ProductsRestController Class :- Start");	
		List<UploadProducts> productInfoList=new ArrayList<UploadProducts>();
		List<String> supermarketList=new ArrayList<String>();
		UploadProducts productInfo = new UploadProducts();
		String json=null;
		try {
			productInfo.setProductId(productOrder.getUploadProducts().getProductId());
			productInfo.setSuperDescription(productOrder.getSuperMarketDesc());
			productInfoList=this.uploadProductService.getSupermarketsNotHavingProducts(productInfo);		

		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("getSupermarketsNotHavingProducts() method in ProductsRestController Class :- End");	
		return productInfoList;
	}



}
