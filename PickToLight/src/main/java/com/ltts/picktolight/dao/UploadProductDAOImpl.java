package com.ltts.picktolight.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.CurrentDateTime;
import com.ltts.picktolight.util.StringConstatns;


@Repository
public class UploadProductDAOImpl implements UploadProductDAO{

	private static final Logger logger = LoggerFactory.getLogger(UploadProductDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	/**
	 * this method save the product details got from excel into database
	 * @param productList POJO list which holds product details
	 */
	@Override
	public void uploadProduct(List<UploadProducts> productList) {

		logger.info("uploadProduct() method in UploadProductDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		try{

			// Delete The All Orders From Table			
			//deleteAllOrderProducts();

			// Delete The All Products From Table		
			//deleteAllUploadProducts();

			// Delete The All Battery Information From Table	
			//deleteAllBatteryInfo();

			//looping trough products in productList
			for(UploadProducts uploadProducts : productList){

				@SuppressWarnings("unchecked")
				List<UploadProducts> list = session.createCriteria(UploadProducts.class)
				.add(Restrictions.eq("productId", uploadProducts.getProductId()))
				.add(Restrictions.eq("rackNum", uploadProducts.getRackNum()))
				.list();

				if(list==null || list.size()<=0){
					//Query to save or update product details
					session.save(uploadProducts);
				}else{
					//Query to save or update product details
					uploadProducts.setId(list.get(0).getId());
					session.merge(uploadProducts);
				}
			}	

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}	

		logger.info("uploadProduct() method in UploadProductDAOImpl Class :- End");
	}

	/**
	 * this method Delete All Order Products From Database
	 */
	public void deleteAllOrderProducts(){		
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("delete from ProductBarcode").executeUpdate();		
	}

	/**
	 * this method Delete All The Products From Database
	 */
	public void deleteAllUploadProducts(){
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("delete from UploadProducts").executeUpdate();
	}

	/**
	 * this method Delete All The Battery Information
	 */
	public void deleteAllBatteryInfo(){		
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("delete from BatteryInfo").executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductBarcode> getAllProductOrdersData(){
		logger.info("getAllProductOrdersData() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<ProductBarcode> productOrdersList = new ArrayList<ProductBarcode>();
		try{
			productOrdersList = session.createQuery("from ProductBarcode p where p.status=:status").setParameter("status", StringConstatns.notPicked).list();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getAllProductOrdersData() method in UploadProductDAOImpl Class :- End");
		return productOrdersList;	
	}

	/**
	 * this method is to update datetime for tracking mobile device working or not 
	 */
	@Override
	public String updateProductDateTime(UploadProducts productDetails) {
		logger.info("updateProductDateTime() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		String status=StringConstatns.failure;
		try{
			session.createQuery("update UploadProducts set datetime='"+productDetails.getDatetime()+"' where rack_num='"+productDetails.getRackNum()+"'").executeUpdate();
			status=StringConstatns.success;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("updateProductDateTime() method in UploadProductDAOImpl Class :- End");
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UploadProducts> getProductInfoForDeviceTracking(UploadProducts productDetails) {
		logger.info("getProductInfoForDeviceTracking() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<UploadProducts> productOrdersList = new ArrayList<UploadProducts>();

		String datetime = null;
		String currentdate = CurrentDateTime.getCurrentTimeStamp();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse(currentdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, -10);
			Date tenMinBack  = cal.getTime();
			datetime= simpleDateFormat.format(tenMinBack);

			productOrdersList = session.createQuery("from UploadProducts up where up.datetime<'" + datetime + "' and up.datetime!=NULL")
					.list();
		} catch (ParseException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}
		logger.info("getProductInfoForDeviceTracking() method in UploadProductDAOImpl Class :- End");
		return productOrdersList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UploadProducts> getSupermarketsNotHavingProducts(UploadProducts productInfo) {
		logger.info("getSupermarketsNotHavingProducts() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<UploadProducts> productOrdersList = new ArrayList<UploadProducts>();

		String datetime = null;
		String currentdate = CurrentDateTime.getCurrentTimeStamp();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = simpleDateFormat.parse(currentdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, -10);
			Date tenMinBack  = cal.getTime();
			datetime= simpleDateFormat.format(tenMinBack);
			productOrdersList = session.createQuery("from UploadProducts up where up.datetime<'" + datetime + "' and up.datetime!=NULL and up.productId='"+productInfo.getProductId()+"' and up.superDescription='"+productInfo.getSuperDescription()+"'")
					.list();
		} catch (ParseException ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}finally {

		}
		logger.info("getSupermarketsNotHavingProducts() method in UploadProductDAOImpl Class :- End");
		return productOrdersList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public UploadProducts getSuperMarketsNotHavingAnyProducts(UploadProducts productInfo) {
		logger.info("getSuperMarketsNotHavingAnyProducts() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		UploadProducts productOrder = null;

		String datetime = null;
		String currentdate = CurrentDateTime.getCurrentTimeStamp();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try{
			Date date = simpleDateFormat.parse(currentdate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE, -10);
			Date tenMinBack  = cal.getTime();
			datetime= simpleDateFormat.format(tenMinBack);
			List<UploadProducts> productOrdersList = session.createQuery("from UploadProducts up where up.datetime<'" + datetime + "' and up.datetime!=NULL and up.productId='"+productInfo.getProductId()+"' and up.superDescription='"+productInfo.getSuperDescription()+"'")
					.list();
			if(productOrdersList!=null && productOrdersList.size()>0){
				productOrder = productOrdersList.get(0);
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}finally {

		}
		logger.info("getSuperMarketsNotHavingAnyProducts() method in UploadProductDAOImpl Class :- End");
		return productOrder;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UploadProducts> getProductInfo(UploadProducts productDetails) {
		logger.info("getProductInfo() method in UploadProductDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<UploadProducts> productOrdersList = new ArrayList<UploadProducts>();

		try {

			productOrdersList = session.createQuery("from UploadProducts up ").list();
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}
		logger.info("getProductInfo() method in UploadProductDAOImpl Class :- End");
		return productOrdersList;
	}

}
