/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.ColorPickerDAO;
import com.ltts.picktolight.dao.OrderHistoryDAO;
import com.ltts.picktolight.dao.ProductDAO;
import com.ltts.picktolight.domain.ColorPicker;
import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.OrderHistory;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.CurrentDateTime;
import com.ltts.picktolight.util.DateTimeDifference;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	private ProductDAO productDAO;
	private ColorPickerDAO colorDAO;
	private OrderHistoryDAO orderHistoryDAO;

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	public void setColorDAO(ColorPickerDAO colorDAO) {
		this.colorDAO = colorDAO;
	}

	public void setOrderHistoryDAO(OrderHistoryDAO orderHistoryDAO) {
		this.orderHistoryDAO = orderHistoryDAO;
	}

	/**
	 * to save all the order details in Database
	 * @param prodBarcodeList
	 * @return POJO list which holds product details
	 */
	@Override
	@Transactional
	public Map<String,List<ProductBarcode>> addProduct(List<ProductBarcode> prodBarcodeList) {

		logger.info("addProduct() method in ProductServiceImpl Class :- Start");
		
		//String orderid = null;
		List<ProductBarcode> notAvailableProducts=new ArrayList<ProductBarcode>();
		List<OrderHistory> ordehistorylist=new ArrayList<OrderHistory>();
		List<ProductBarcode> superMarketMismatchProducts=new ArrayList<ProductBarcode>();
		Map<String,List<ProductBarcode>> returnData=new LinkedHashMap<String,List<ProductBarcode>>();
		try {

			// here we generate orderid randomly using SessionIdentifierGenerator class
			//String orderid = SessionIdentifierGenerator.nextSessionId();
			
			String colorValue = "#FFFFFF";
			
			if (prodBarcodeList != null) {

				// here products coming in the form of list and iterate them for availability of products in database and save in database

				for (ProductBarcode prodBarcode: prodBarcodeList) {
					OrderHistory orderHistory = new OrderHistory();
					prodBarcode.setDatetime(CurrentDateTime.getCurrentTimeStamp());
					prodBarcode.setActiveStatus(StringConstatns.activeStatusFlase);	
					prodBarcode.setProductId(prodBarcode.getBarcode());
					prodBarcode.setForceCompleteStatus(StringConstatns.forceCompleteStatusNo);
					int quantity=Integer.parseInt(prodBarcode.getQuantity());
					if(quantity>1){
						prodBarcode.setColor("#FF0000");
					}else{
						prodBarcode.setColor(colorValue);
					}
					/*orderid = prodBarcode.getOrderid();
					if (StringUtils.isNullOrEmpty(prodBarcode.getOrderid())) {
						prodBarcode.setOrderid(orderid);
					}*/
					UploadProducts uploadProducts = this.productDAO.getProductDescriptionDetails(prodBarcode,"product");
					if (uploadProducts != null) {
						prodBarcode.setUploadProducts(uploadProducts);
						//this.productDAO.addProduct(prodBarcode);
						orderHistory.setBarcode(prodBarcode.getBarcode());
						orderHistory.setColor(prodBarcode.getColor());
						orderHistory.setDescription(prodBarcode.getDescription());
						orderHistory.setPickStartTime(prodBarcode.getDatetime());
						orderHistory.setOrderid(prodBarcode.getOrderid());
						orderHistory.setQuantity(prodBarcode.getQuantity());
						orderHistory.setSuperMarketDesc(prodBarcode.getSuperMarketDesc());
						orderHistory.setSerialNo(prodBarcode.getSerialNo());
						orderHistory.setUnitdDesc(prodBarcode.getUnitdDesc());
						orderHistory.setPickerOrder(prodBarcode.getPickerOrder());
						orderHistory.setiDesc(prodBarcode.getiDesc());
						orderHistory.setInstallation(prodBarcode.getInstallation());
						orderHistory.setForceCompleteStatus(StringConstatns.forceCompleteStatusNo);
						ordehistorylist.add(orderHistory);
					}else{
						notAvailableProducts.add(prodBarcode);
					}

				}

				if(notAvailableProducts.size()==0){
					
					for(ProductBarcode prodBarcode: prodBarcodeList){
						UploadProducts product=this.productDAO.getProductDescriptionDetails(prodBarcode,"productmarket");
						if(product==null){
						UploadProducts uploadProducts = this.productDAO.getProductDescriptionDetails(prodBarcode,"product");
						if(uploadProducts!=null){
						prodBarcode.setSuperMarketDesc(uploadProducts.getSuperDescription());
						superMarketMismatchProducts.add(prodBarcode);
						}
						}
					}
					
					if(superMarketMismatchProducts==null || superMarketMismatchProducts.size()<=0){
					//saving order in database	
					this.productDAO.addProduct(prodBarcodeList);

					//saving order for history purpose
					this.orderHistoryDAO.addOrderHistory(ordehistorylist);

					//update the status of color code to true
					/*ColorPicker colPicker = new ColorPicker();
					colPicker.setColorValue(colorValue);
					colPicker.setStatus(StringConstatns.statusTrue);
					updateColorStatus(colPicker);*/
					returnData=null;
					}else{
						returnData.put("productmarket", superMarketMismatchProducts);
					}
				}else{
					returnData.put("product", notAvailableProducts);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		logger.info("addProduct() method in ProductServiceImpl Class :- End");
		return returnData;
	}

	/**
	 * here we are updating the status of products into PIKED
	 * and status of color assigned to the order as False to assign it for further orders
	 * @return Map which holds status as order picked successfully
	 */
	@Override
	@Transactional
	public Map<String, String> updateProduct(ProductBarcode prodbean) {

		logger.info("updateProduct() method in ProductServiceImpl Class :- Start");

		String status = "Order Not Picked";

		try {
			int i = this.productDAO.updateProduct(prodbean);
			if (i > 0) {
				OrderHistory orderHistory = new OrderHistory();
				orderHistory.setBarcode(prodbean.getBarcode());
				orderHistory.setPickEndTime(prodbean.getDatetime());
				orderHistory.setOrderid(prodbean.getOrderid());

				//sending updated details of product to DAO
				i = this.orderHistoryDAO.updateOrderHistory(orderHistory);

				if (i > 0) {
					prodbean.setStatus(StringConstatns.notPicked);
					List<ProductBarcode> productlist = checkOrderStaus(prodbean);
					if (productlist == null || productlist.size() <= 0) {
						prodbean.setStatus(StringConstatns.picked);
						productlist = checkOrderStaus(prodbean);
						if (productlist != null && productlist.size() > 0) {
							ColorPicker colorPicker = new ColorPicker();
							colorPicker.setColorValue(productlist.get(0).getColor());
							colorPicker.setStatus(StringConstatns.statusFalse);

							//updating the status of color assigned to the order 
							// whose all products are PICKED
							updateColorStatus(colorPicker);
						}
					}
					status = "Order Picked Successfully";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		Map<String, String> resultmap = new LinkedHashMap<String, String>();
		resultmap.put("responseCode", status);

		logger.info("updateProduct() method in ProductServiceImpl Class :- End");
		return resultmap;
	}

	/**
	 * checking the status of products in the orders to release its color
	 * @param orderid
	 * @return POJO list 
	 */
	@Override
	@Transactional
	public List<ProductBarcode> checkOrderStaus(ProductBarcode orderid) {

		logger.info("updateProduct() method in ProductServiceImpl Class :- Start");
		//calling checkOrderStaus() in DAO
		List<ProductBarcode> productList = this.productDAO.checkOrderStaus(orderid);
		logger.info("updateProduct() method in ProductServiceImpl Class :- End");
		return productList;
	}

	/**
	 * here we get the available colors from database to assign order 
	 * @return String which holds colorcode of a particular color
	 */
	@Override
	@Transactional
	public String getColorCode() {

		logger.info("getColorCode() method in ProductServiceImpl Class :- Start");

		String colorCode = null;
		ColorPicker cpicker = new ColorPicker();

		try {

			// list gets all the colors which are available in DB
			List<ColorPicker> color = this.colorDAO.getColorList();

			// here we take only one color from list
			if (color != null && color.size() > 0) {
				cpicker = color.get(0);
			}
			colorCode = cpicker.getColorValue();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		logger.info("getColorCode() method in ProductServiceImpl Class :- End");
		return colorCode;
	}


	/**
	 *If all products in the order picked successfully
	 *here we are updating the stauts of color assigned to
	 *that order as false
	 *@param cpobj POJO object that holds color details
	 *@return String status successfully updated
	 */
	@Override
	@Transactional
	public String updateColorStatus(ColorPicker cpobj) {
		logger.info("updateColorStatus() method in ProductServiceImpl Class :- Start");
		String status = this.colorDAO.updateColorStatus(cpobj);
		logger.info("updateColorStatus() method in ProductServiceImpl Class :- End");
		return status;
	}


	/**
	 * gets the product details based on rackid if the status of product is not picked
	 * @param POJO class object
	 * @return Map which holds the details of product belongs to particular rack
	 */
	@Override
	@Transactional
	public Map<String, List<Map<String, String>>> getProductDetailsByRackId(UploadProducts products) {

		logger.info("getProductDetailsByRackId() method in ProductServiceImpl Class :- Start");

		int count = 0;
		List<Map<String, String>> productsmap = new ArrayList<Map<String, String>>();

		try {

			//calling getProductDetailsByRackId() in DAO
			UploadProducts proddesc = this.productDAO.getProductDetailsByRackId(products);

			if (proddesc != null) {
				List<ProductBarcode> orderedproducts = proddesc.getProductBarcode();
				if (orderedproducts != null && orderedproducts.size() > 0) {
					for (ProductBarcode obj : orderedproducts) {
						if (count < 1) {
							if (obj.getStatus().equalsIgnoreCase(StringConstatns.notPicked) && obj.getActiveStatus().equalsIgnoreCase(StringConstatns.activeStatusTrue)) {
								count++;
								//adding all the product details to Map whose status is not picked
								Map<String, String> submap = new LinkedHashMap<String, String>();
								submap.put("orderid", obj.getOrderid());
								submap.put("productid", proddesc.getProductId());
								submap.put("quantity", obj.getQuantity());
								submap.put("color", obj.getColor());
								submap.put("description", proddesc.getDescription());
								submap.put("supermarket", obj.getSuperMarketDesc());
								productsmap.add(submap);
							}
						}

					}
				}
			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		Map<String, List<Map<String, String>>> objectssmap = new LinkedHashMap<String, List<Map<String, String>>>();
		objectssmap.put("products", productsmap);

		logger.info("getProductDetailsByRackId() method in ProductServiceImpl Class :- End");

		return objectssmap;
	}


	/**
	 * This is converting the object list into POJO array to give it to the controller to show on TV screen
	 * @return POJO list which holds the picked and not picked product details
	 */
	@Override
	@Transactional
	public List<ProductBarcode> getOrderStatus() {
		logger.info("getOrderStatus() method in ProductServiceImpl Class :- Start");
		List<ProductBarcode> allOrders = new ArrayList<ProductBarcode>();

		// gets the details of not picked and picked products from DB
		List<Object[]> productList = this.productDAO.getOrderStatus();

		// loops through all objects in productList
		for (Object[] p : productList) {
			String p1 = (String) p[0];
			String p2 = (String) p[1];
			String p3 = (String) p[2];
			ProductBarcode barcode = new ProductBarcode();
			barcode.setStatus(p1);
			barcode.setOrderid(p2);
			barcode.setColor(p3);
			allOrders.add(barcode);
		}

		logger.info("getOrderStatus() method in ProductServiceImpl Class :- End");
		return allOrders;

	}



	/**
	 * This method is used to delete the completed orders
	 * after some particular time
	 * @return the status
	 */
	@Override
	@Transactional
	public String checkForCompletedOrders() {
		logger.info("checkForCompletedOrders() method in ProductServiceImpl Class :- Start");

		String status = StringConstatns.success;
		List<ProductBarcode> completedOrderList = new ArrayList<ProductBarcode>();

		try {

			/* here we get the all the orders */
			List<Object[]> productList = this.productDAO.getOrderStatus();

			for (Object[] prodarr : productList) {
				if (prodarr.length == 4) {
					String orderid = (String) prodarr[1];
					String datetime = (String) prodarr[3];

					//getting the current system date and time
					String currentdatetime = CurrentDateTime.getCurrentTimeStamp();

					//comparing current time and the time when order PICKED
					long minutes = DateTimeDifference.getTimeDifference(datetime, currentdatetime);

					//checking if the time difference is 10 mins to delete that order
					if (minutes >= 1440) {
						System.out.println("orderid =" + orderid + "  completed");
						ProductBarcode completedOrder = new ProductBarcode();
						completedOrder.setOrderid(orderid);
						completedOrderList.add(completedOrder);
					}
				}

			}

			if (completedOrderList != null && completedOrderList.size() > 0) {
				//calling deletePickedOrders() in DAO implementation class
				//to delete completed orders
				status = this.productDAO.deletePickedOrders(completedOrderList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		System.out.println("status = " + status);
		logger.info("checkForCompletedOrders() method in ProductServiceImpl Class :- End");
		return null;
	}

	@Override
	@Transactional
	public String makeOrderedProductActive(ProductBarcode prodbean) {
		logger.info("makeOrderedProductActive() method in ProductServiceImpl Class :- Start");

		String status = null;

		try {
			status=this.productDAO.makeOrderedProductActive(prodbean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		logger.info("makeOrderedProductActive() method in ProductServiceImpl Class :- End");
		return status;
	}

	@Override
	@Transactional
	public long getOrderCompletionStatus(ProductBarcode prodbean) {
		logger.info("getOrderCompletionStatus() method in ProductServiceImpl Class :- Start");

		long productCount = 0;

		try {
			productCount=this.productDAO.getOrderCompletionStatus(prodbean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{

		}

		logger.info("getOrderCompletionStatus() method in ProductServiceImpl Class :- End");
		return productCount;
	}

	@Override
	@Transactional
	public List<Map<String, String>> getNotPickedProductDetails(ProductBarcode prodBarcode) {
		List<Map<String, String>> productsmap = new ArrayList<Map<String, String>>();
		try{
			List<ProductBarcode> productList=this.productDAO.getNotPickedProductDetails(prodBarcode);
			
			if(productList!=null && productList.size()>0){
				for(ProductBarcode product : productList){
					Map<String, String> submap = new LinkedHashMap<String, String>();
					/*submap.put("orderid", product.getOrderid());*/
					submap.put("productid", product.getBarcode());
					/*submap.put("quantity", product.getQuantity());*/
					submap.put("description", product.getDescription());
					submap.put("supermarket", product.getSuperMarketDesc());
					productsmap.add(submap);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
		return productsmap;
	}

	@Override
	@Transactional
	public List<ProductBarcode> getProductOrdersWithActive(ProductBarcode prodBarcode){
		return this.productDAO.getProductOrdersWithActive(prodBarcode);
	}

	@Override
	@Transactional
	public List<ProductBarcode> countTimePeriodForOrder(ProductBarcode productBarcode) {		
		return this.productDAO.countTimePeriodForOrder(productBarcode);
	}

	@Override
	@Transactional
	public List<ProductBarcode> getOrdersToIncompleteOrders() {
		return this.productDAO.getOrdersToIncompleteOrders();
	}
	
	@Override
	@Transactional
	public List<ProductBarcode> getIncompleteOrderProducts(ProductBarcode products) {
		return this.productDAO.getIncompleteOrderProducts(products);
	}	
	
	@Override
	@Transactional
	public String updateForceCompleteStatus(ProductBarcode products){
		return this.productDAO.updateForceCompleteStatus(products);
	}

}
