/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */
@Repository
public class ProductDAOImpl implements ProductDAO {

	private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}


	/**
	 * inserts the order details into database and change the color status to
	 * true to indicate that, the color is assigned to an order
	 */
	@Override
	public void addProduct(List<ProductBarcode> orderProdList) {

		logger.info("addProduct() method in ProductDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();

		try {
			// this query saves the order details in database
			for(ProductBarcode product:orderProdList){		
				
				product.setForceCompleteStatus(StringConstatns.forceCompleteStatusNo);	
				
				@SuppressWarnings("unchecked")
				List<ProductBarcode> list = session.createCriteria(ProductBarcode.class)
				.add(Restrictions.eq("barcode", product.getBarcode()))
				.add(Restrictions.eq("orderid", product.getOrderid()))
				.add(Restrictions.eq("superMarketDesc", product.getSuperMarketDesc()))
				.list();

				if(list==null || list.size()<=0){
					//Query to save product details
					session.save(product);
				}else{
					//Query to update product details
					StringBuffer sBuffer=new StringBuffer();
					sBuffer.append("update ProductBarcode p set p.quantity=:quantity,p.datetime=:datetime,");
					sBuffer.append("p.color=:color,p.description=:description,p.serialNo=:serialNo,p.unitdDesc=:unitdDesc,");
					sBuffer.append("p.pickerOrder=:pickerOrder,p.iDesc=:iDesc,p.installation=:installation where p.id=:id");

					String query=sBuffer.toString();
					session.createQuery(query)
					.setParameter("quantity", product.getQuantity())
					.setParameter("datetime", product.getDatetime())
					.setParameter("color", product.getColor())
					.setParameter("description", product.getDescription())
					.setParameter("serialNo", product.getSerialNo())
					.setParameter("unitdDesc", product.getUnitdDesc())
					.setParameter("pickerOrder", product.getPickerOrder())
					.setParameter("iDesc", product.getiDesc())
					.setParameter("installation", product.getInstallation())
					.setParameter("id", list.get(0).getId()).executeUpdate();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("addProduct() method in ProductDAOImpl Class :- End");
	}



	/**
	 * once the product is picked here we are going to change its status as
	 * PICKED
	 * @param productDetails
	 * POJO class object
	 * @return number of products updated
	 */
	@Override
	public int updateProduct(ProductBarcode productDetails) {

		logger.info("updateProduct() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		int i = 0;
		try {

			// this query updates the status of product as PICKED
			i = session
					.createQuery("update ProductBarcode p set p.status=:status ,p.pickEndTime=:pickEndTime ,p.datetime='"
							+ productDetails.getDatetime() + "' where p.orderid='" + productDetails.getOrderid()
							+ "' and p.barcode='" + productDetails.getBarcode() + "'")
					.setParameter("status", StringConstatns.picked)
					.setParameter("pickEndTime", productDetails.getPickEndTime()).executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("updateProduct() method in ProductDAOImpl Class :- End");
		return i;
	}


	/**
	 * checking the status of products in the orders to release its color
	 * if the status is PICKED
	 * @param prodBarcode POJO class object
	 * @return POJO list 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> checkOrderStaus(ProductBarcode prodBarcode) {

		logger.info("checkOrderStaus() method in ProductDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> productsList = new ArrayList<ProductBarcode>();

		try{

			//Query to check the status of order
			productsList = session.createQuery("from ProductBarcode where orderid='"
					+ prodBarcode.getOrderid() + "' and status='" + prodBarcode.getStatus() + "'").list();		

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}	

		logger.info("checkOrderStaus() method in ProductDAOImpl Class :- End");
		return productsList;
	}



	/**
	 * this method gives the description of the products 
	 * @param barcode product barcode
	 * @return POJO class object which holds the description of products
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UploadProducts getProductDescriptionDetails(ProductBarcode productDetails,String checkFor) {

		logger.info("getProductDescriptionDetails() method in ProductDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		UploadProducts returnProductDetails=null;
		try{

			List<UploadProducts> list =null;
			//Selects description of products 
			if("product".equalsIgnoreCase(checkFor)){

				list = session.createQuery("from UploadProducts up where up.productId=:barcode")
						.setParameter("barcode", productDetails.getBarcode()).list();
				System.out.println("up=" + list);

			}else if("productmarket".equalsIgnoreCase(checkFor)){

				list = session.createQuery("from UploadProducts up where up.productId=:barcode and up.superDescription=:superDescription")
						.setParameter("barcode", productDetails.getBarcode())
						.setParameter("superDescription", productDetails.getSuperMarketDesc()).list();

			}
			if(list!=null && list.size()>0){
				returnProductDetails=list.get(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}		
		logger.info("getProductDescriptionDetails() method in ProductDAOImpl Class :- End");
		return returnProductDetails;
	}



	/**
	 * method provides the details of a rackid which we give
	 * @param products POJO class object which holds the details of racks
	 * @return POJO object which holds the rack details
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UploadProducts getProductDetailsByRackId(UploadProducts products) {

		logger.info("getProductDetailsByRackId() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		UploadProducts productDetails = null;

		try{
			//This query selects the details of particular rackid provided
			List<UploadProducts> list = session.createQuery("from UploadProducts up where up.rackNum=:rackvalue")
					.setParameter("rackvalue", products.getRackNum()).list();
			if (list != null && list.size() > 0) {
				productDetails = list.get(0);
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}

		logger.info("getProductDetailsByRackId() method in ProductDAOImpl Class :- End");
		return productDetails;
	}



	/**
	 * Here we are getting distinct 5 order Id's at a time by using the order Id
	 * we are getting not picked product list and picked products List
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Object[]> getOrderStatus() {

		logger.info("getOrderStatus() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<Object[]> allorders = new ArrayList<Object[]>();
		String deliverorderid;
		try{
			// By using this query we are getting 5 unique order ID's and storing in
			// plist
			List<String> plist = session.createQuery("select distinct p.orderid from ProductBarcode p").setMaxResults(5)
					.list();

			// Looping through all order Id's in the plist
			for (String str : plist) {

				// getting the not picked products in the order and storing in
				// object List porder
				List<Object[]> porder = session
						.createQuery(
								"select p.status,p.orderid,p.color from ProductBarcode p where p.orderid=:value and status=:status")
						.setParameter("value", str).setParameter("status", StringConstatns.notPicked).list();

				if (!porder.isEmpty()) {

					allorders.addAll(porder);

				} else {
					deliverorderid = str;

					// getting the picked products in the order and storing in
					// object List dorder
					List<Object[]> dorder = session
							.createQuery(
									"select p.status,p.orderid,p.color,p.datetime from ProductBarcode p where p.orderid=:value and status=:status order by p.datetime desc")
							.setParameter("value", deliverorderid).setParameter("status", StringConstatns.picked).setMaxResults(1).list();

					allorders.addAll(dorder);

				}

			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getOrderStatus() method in ProductDAOImpl Class :- End");
		return allorders;

	}


	/**
	 * Here we are deleting the orders whose products status is PICKED
	 * @param orderProdList this is a list which holds information of orders 
	 * whose products status is PICKED
	 * @return status as success
	 */
	@Override
	public String deletePickedOrders(List<ProductBarcode> orderProdList) {
		logger.info("uploadProduct() method in UploadProductDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		String status=StringConstatns.failure;
		try{
			for(ProductBarcode orderidobj : orderProdList){

				//delete the orders from table
				session.createQuery("delete from ProductBarcode where orderid='"+orderidobj.getOrderid()+"'").executeUpdate();
			}	
			status=StringConstatns.success;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}	
		logger.info("uploadProduct() method in UploadProductDAOImpl Class :- End");
		return status;
	}


	@Override
	public String makeOrderedProductActive(ProductBarcode productDetails) {
		logger.info("updateProduct() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		String status=null;
		try {

			// this query updates the activeStatus of products as Active
			int i = session
					.createQuery("update ProductBarcode p set p.activeStatus=:activeStatus,p.pickStartTime=:pickStartTime where p.orderid='" + productDetails.getOrderid()
					+ "' and p.superMarketDesc='" + productDetails.getSuperMarketDesc() + "'")
					.setParameter("activeStatus", StringConstatns.activeStatusTrue)
					.setParameter("pickStartTime", productDetails.getPickStartTime()).executeUpdate();
			if(i>0){
				status="Success";
			}else{
				status="No Records Found";
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("updateProduct() method in ProductDAOImpl Class :- End");
		return status;
	}

	@Override
	public long getOrderCompletionStatus(ProductBarcode orderDetails) {
		logger.info("updateProduct() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		long productCount=0;
		try{

			productCount =  (long) session
					.createQuery(
							"select count(*) from ProductBarcode p where p.orderid=:value and p.status=:status and p.superMarketDesc=:supermarket and p.activeStatus=:activestatus")
					.setParameter("value", orderDetails.getOrderid())
					.setParameter("status", StringConstatns.notPicked)
					.setParameter("supermarket", orderDetails.getSuperMarketDesc())
					.setParameter("activestatus", StringConstatns.activeStatusTrue).uniqueResult();


		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		return productCount;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> getNotPickedProductDetails(ProductBarcode prodBarcode) {
		logger.info("updateProduct() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> productList=new ArrayList<ProductBarcode>();
		try{

			productList =  session
					.createQuery(
							"from ProductBarcode p where p.orderid=:value and p.status=:status and p.superMarketDesc=:supermarket and p.activeStatus=:activestatus")
					.setParameter("value", prodBarcode.getOrderid())
					.setParameter("status", StringConstatns.notPicked)
					.setParameter("supermarket", prodBarcode.getSuperMarketDesc())
					.setParameter("activestatus", StringConstatns.activeStatusTrue).list();


		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		return productList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> getProductOrdersWithActive(ProductBarcode prodBarcode) {
		logger.info("getProductOrdersWithActive() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> productList=new ArrayList<ProductBarcode>();
		try{
			productList =  session.createQuery("from ProductBarcode p where p.orderid=:orderid and p.superMarketDesc=:superMarketDesc and p.forceCompleteStatus=:forceCompleteStatus")
					.setParameter("orderid", prodBarcode.getOrderid())
					.setParameter("superMarketDesc",prodBarcode.getSuperMarketDesc())
					.setParameter("forceCompleteStatus",StringConstatns.forceCompleteStatusNo)
					.list();


		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		return productList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> countTimePeriodForOrder(ProductBarcode productBarcode) {
		logger.info("countTimePeriodForOrder() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<String> productsInfoList=new ArrayList<String>();
		List<String> distOrderIdList = new ArrayList<String>();
		List<ProductBarcode> productsDetailsList=new ArrayList<ProductBarcode>();
		String date=productBarcode.getDatetime();
		try{

			distOrderIdList = session.createQuery("select distinct p.orderid from ProductBarcode p where p.datetime between '"+date+" 00:00:00' and '"+date+" 23:59:59'").list();
			productsInfoList = session.createQuery("select distinct p.orderid from ProductBarcode p where status='NOT PICKED' and p.datetime between '"+date+" 00:00:00' and '"+date+" 23:59:59'").list();

			for(String orderId:distOrderIdList){
				if(!productsInfoList.contains(orderId)){
					List<ProductBarcode> productsList=session.createQuery("from ProductBarcode p where p.orderid='"+orderId+"' and p.datetime between '"+date+" 00:00:00' and '"+date+" 23:59:59'").list();
					if(productsList!=null && productsList.size()>0){
						productsDetailsList.addAll(productsList);							
					}
				}

			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("countTimePeriodForOrder() method in ProductDAOImpl Class :- End");
		return productsDetailsList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> getOrdersToIncompleteOrders() {
		logger.info("getOrdersToIncompleteOrders() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> productsDetailsList=new ArrayList<ProductBarcode>();
		try{
			productsDetailsList= session.createQuery("from ProductBarcode p where p.status=:status and p.activeStatus=:activestatus")
					.setParameter("status", StringConstatns.notPicked)
					.setParameter("activestatus", StringConstatns.activeStatusTrue).list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getOrdersToIncompleteOrders() method in ProductDAOImpl Class :- End");		
		return productsDetailsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> getIncompleteOrderProducts(ProductBarcode products) {
		logger.info("getIncompleteOrderProducts() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> inCompleteProductsList=new ArrayList<ProductBarcode>();
		try{
			
			StringBuffer string_Buffer=new StringBuffer();
			string_Buffer.append("from ProductBarcode p where p.activeStatus=:activeStatus and p.status=:status ");
			string_Buffer.append("and p.orderid=:orderid and p.superMarketDesc=:superMarketDesc");

			String query=string_Buffer.toString();
			inCompleteProductsList=session.createQuery(query)
			.setParameter("activeStatus",StringConstatns.activeStatusTrue)
			.setParameter("superMarketDesc", products.getSuperMarketDesc())
			.setParameter("orderid", products.getOrderid())
			.setParameter("status", StringConstatns.notPicked).list();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getIncompleteOrderProducts() method in ProductDAOImpl Class :- End");		
		return inCompleteProductsList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String updateForceCompleteStatus(ProductBarcode products){
		logger.info("updateForceCompleteStatus() method in ProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		String status=null;
		try {
			int i = session
					.createQuery("update ProductBarcode p set p.forceCompleteStatus=:forceCompleteStatus where p.orderid='" + products.getOrderid()
					+ "' and p.superMarketDesc='" + products.getSuperMarketDesc() + "'")
					.setParameter("forceCompleteStatus", StringConstatns.forceCompleteStatusYes).executeUpdate();
			if(i>0){
				status="Success";
			}else{
				status="No Records Found";
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("updateForceCompleteStatus() method in ProductDAOImpl Class :- End");
		return status;	
	}

}
