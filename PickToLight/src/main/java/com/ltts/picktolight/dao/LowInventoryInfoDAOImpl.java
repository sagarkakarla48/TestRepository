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

import com.ltts.picktolight.domain.LowInventoryInfo;
import com.ltts.picktolight.domain.UploadProducts;
import com.ltts.picktolight.util.CurrentDateTime;

/**
 * @author 90001332
 *
 */
@Repository
public class LowInventoryInfoDAOImpl implements LowInventoryInfoDAO {

	private static final Logger logger = LoggerFactory.getLogger(LowInventoryInfoDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public String saveLowInventoryInfo(LowInventoryInfo lowInventoryInfo) {

		logger.info("saveMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- Start");
		String status=null;
		Session session = this.sessionFactory.getCurrentSession();	
		try{
			@SuppressWarnings("unchecked")
			List<UploadProducts> uploadedProduct = session.createCriteria(UploadProducts.class)
			.add(Restrictions.eq("productId", lowInventoryInfo.getProductId()))
			.list();
			//Query to save product details
			if(uploadedProduct!=null && uploadedProduct.size()>0){
				lowInventoryInfo.setUploadProducts(uploadedProduct.get(0));
				session.save(lowInventoryInfo);
				status="Success";
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {

		}
		logger.info("saveMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- End");
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LowInventoryInfo> getLowInventoryInfo(LowInventoryInfo lowInventoryInfo) {

		logger.info("getMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<LowInventoryInfo> productList = new ArrayList<LowInventoryInfo>();
		try{
			productList = session.createQuery("from LowInventoryInfo where lowStock=:status")
					.setParameter("status", Boolean.TRUE).list();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- End");
		return productList;
	}

	@Override
	public String updateLowInventoryInfo(List<LowInventoryInfo> lowInventoryInfoList) {
		logger.info("updateMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- Start");
		String status=null;
		Session session = this.sessionFactory.getCurrentSession();	
		try{

			String datetime=CurrentDateTime.getCurrentTimeStamp();

			for(LowInventoryInfo lowInventoryInfo:lowInventoryInfoList){

				StringBuffer strBuffer=new StringBuffer();
				strBuffer.append("update LowInventoryInfo set lowStock=:status,updateddatetime=:datetime ");
				strBuffer.append(" where productId=:productId and superMarketDesc=:supermarket and lowStock=:lowStock");
				String hql=strBuffer.toString();

				session.createQuery(hql)
				.setParameter("status", Boolean.FALSE)
				.setParameter("lowStock", Boolean.TRUE)
				.setParameter("datetime", datetime)
				.setParameter("productId", lowInventoryInfo.getProductId())
				.setParameter("supermarket", lowInventoryInfo.getSuperMarketDesc()).executeUpdate();
			}
			status="Success";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally {

		}
		logger.info("updateMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- End");
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LowInventoryInfo> getLowInventoryDetails(LowInventoryInfo prodInfo) {

		logger.info("getMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- Start");
		Session session = this.sessionFactory.getCurrentSession();
		List<LowInventoryInfo> productList = new ArrayList<LowInventoryInfo>();
		try{
			productList = session.createQuery("from LowInventoryInfo where lowStock=:status and productId=:productId and rackId=:rackId")
					.setParameter("rackId", prodInfo.getRackId())
					.setParameter("productId", prodInfo.getProductId())
					.setParameter("status", Boolean.TRUE).list();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{

		}
		logger.info("getMissedProductInfo() method in LowInventoryInfoDAOImpl Class :- End");
		return productList;
	}

}
