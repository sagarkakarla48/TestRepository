package com.ltts.picktolight.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ltts.picktolight.domain.IncompleteOrders;
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.domain.ProductCartDetails;
import com.ltts.picktolight.util.StringConstatns;

public class CartServiceDAOImpl implements CartServiceDAO{

	private static final Logger logger = LoggerFactory.getLogger(LowInventoryInfoDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}
	
	@Override
	public String saveCartDetailsOfOrdersInSupermarket(ProductCartDetails cartDetails) {
		
		logger.info(" saveCartDetailsOfOrdersInSupermarket() method in  Class CartServiceDAOImpl:- Start");
		Session session = this.sessionFactory.getCurrentSession();	
		try{
		  //Save cartdetails in table
			session.save(cartDetails);
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		logger.info("saveCartDetailsOfOrdersInSupermarket() method in  Class CartServiceDAOImpl:- End");
		return StringConstatns.success;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCartDetails> getCartDetailsOfOrdersInSupermarket() {
		logger.info("getCartDetailsOfOrdersInSupermarket() method in CartServiceDAOImpl Class :- Start");
		List<ProductCartDetails> cartDetailsList = new ArrayList<ProductCartDetails>();
		Session session = this.sessionFactory.getCurrentSession();
		try{
			cartDetailsList = session.createQuery("from ProductCartDetails").list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getCartDetailsOfOrdersInSupermarket() method in CartServiceDAOImpl Class :- End");
		return cartDetailsList;
	}	

	//TODO: task on 24/05/2017 - similar update for missed products
	@SuppressWarnings("unchecked")
	@Override
	public String updatedOrderIncompleteProducts(IncompleteOrders orderUpdate) {
		logger.info("updatedOrderIncompleteProducts() method in CartServiceDAOImpl Class :- Start");
		String status=null;
		Session session = this.sessionFactory.getCurrentSession();
		String prdUpdQuery = "update ProductBarcode ";
		String mmpUpdQuery = "update MisMatchProducts ";
		prdUpdQuery = prdUpdQuery+"set forceCompleteStatus='"+StringConstatns.forceCompleteStatusYes+"',status='"+StringConstatns.picked+"' where orderid='"+orderUpdate.getOrderId()+"' and (productId='"+ orderUpdate.getProductDetails().get(0).getProductId()+"'";
		mmpUpdQuery = mmpUpdQuery+"set forceCompleteStatus='"+StringConstatns.forceCompleteStatusYes+"',status='"+StringConstatns.picked+"' where orderid='"+orderUpdate.getOrderId()+"' and (productId='"+ orderUpdate.getProductDetails().get(0).getProductId()+"'";
		//strBuffer.append("update ProductBarcode set status='"+StringConstatns.picked+"' where orderid='"+orderUpdate.getOrderId()+"' and productId='"+ orderUpdate.getProductDetails().get(0).getProductId()+"'");
		for(int i=1 ;i < orderUpdate.getProductDetails().size() ;i++)
		{
			prdUpdQuery = prdUpdQuery+" or productId='"+orderUpdate.getProductDetails().get(i).getProductId()+"'";
			mmpUpdQuery = mmpUpdQuery+" or productId='"+orderUpdate.getProductDetails().get(i).getProductId()+"'";
			//strBuffer.append(" or productId='"+orderUpdate.getProductDetails().get(i).getProductId()+"'");
		}
		prdUpdQuery = prdUpdQuery+")";
		mmpUpdQuery = mmpUpdQuery+")";
//		strBuffer.append(" where productId=:productId and superMarketDesc=:supermarket and lowStock=:lowStock");
		//String hqlOrders=strBuffer.toString();		
		try{
			session.createQuery(prdUpdQuery).executeUpdate();
			session.createQuery(mmpUpdQuery).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("updatedOrderIncompleteProducts() method in CartServiceDAOImpl Class :- End");
		return status;
	}	


}
