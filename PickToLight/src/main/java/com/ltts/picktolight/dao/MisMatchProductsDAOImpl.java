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
import com.ltts.picktolight.domain.MisMatchProducts;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */
@Repository
public class MisMatchProductsDAOImpl implements MisMatchProductsDAO {


	private static final Logger logger = LoggerFactory.getLogger(MisMatchProductsDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}


	@Override
	public void deleteExistingMismatchProducts(List<MisMatchProducts> misMatchProductsList) {
		Session session = this.sessionFactory.getCurrentSession();
		if(misMatchProductsList!=null && misMatchProductsList.size()>0){
			 session.createQuery("delete from MisMatchProducts").executeUpdate();	
		}		
	}


	@Override
	public String saveMisMatchedProducts(List<MisMatchProducts> misMatchProductsList) {

		logger.info("saveMisMatchedProducts() method in MisMatchProductsDAOImpl Class :- Start");

		Session session = this.sessionFactory.getCurrentSession();
		String status = null;
		try {
			// this query saves the order details in database
			for(MisMatchProducts product:misMatchProductsList){

				product.setProductId(product.getBarcode());
				product.setForceCompleteStatus(StringConstatns.forceCompleteStatusNo);

				@SuppressWarnings("unchecked")
				List<MisMatchProducts> list = session.createCriteria(MisMatchProducts.class)
				.add(Restrictions.eq("barcode", product.getBarcode()))
				.add(Restrictions.eq("productId", product.getProductId()))
				.add(Restrictions.eq("orderid", product.getOrderid()))
				.add(Restrictions.eq("superMarketDesc", product.getSuperMarketDesc()))
				.list();

				if(list==null || list.size()<=0){
					//Query to save product details
					session.save(product);
					status = "Success";
				}else{
					//Query to update product details
					StringBuffer sBuffer=new StringBuffer();
					sBuffer.append("update MisMatchProducts p set p.quantity=:quantity,p.datetime=:datetime");
					sBuffer.append("p.description=:description where p.id=:id");

					String query=sBuffer.toString();
					session.createQuery(query)
					.setParameter("quantity", product.getQuantity())
					.setParameter("datetime", product.getDatetime())
					.setParameter("description", product.getDescription())
					.setParameter("id", list.get(0).getId()).executeUpdate();
					status = "Success";
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {

		}

		logger.info("saveMisMatchedProducts() method in MisMatchProductsDAOImpl Class :- End");
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MisMatchProducts> getMisMatchproducts() {
		logger.info("getMisMatchproducts() method in MisMatchProductsDAOImpl Class :- Start");
		List<MisMatchProducts> misMatchProductsList = new ArrayList<MisMatchProducts>();
		Session session = this.sessionFactory.getCurrentSession();
		try{
			misMatchProductsList = session.createQuery("from MisMatchProducts").list();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getMisMatchproducts() method in MisMatchProductsDAOImpl Class :- End");
		return misMatchProductsList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MisMatchProducts> getMisMatchproductsToIncompleteOrders() {
		logger.info("getMisMatchproductsToIncompleteOrders() method in MisMatchProductsDAOImpl Class :- Start");
		List<MisMatchProducts> misMatchProductsList = new ArrayList<MisMatchProducts>();
		Session session = this.sessionFactory.getCurrentSession();
		try{
			//misMatchProductsList = session.createQuery("from MisMatchProducts").list();
			misMatchProductsList = session.createQuery("from MisMatchProducts where status=:status")
					.setParameter("status", StringConstatns.notPicked).list();

		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info("getMisMatchproductsToIncompleteOrders() method in MisMatchProductsDAOImpl Class :- End");
		return misMatchProductsList;
	}	

}
