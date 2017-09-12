/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ltts.picktolight.domain.OrderHistory;
import com.ltts.picktolight.domain.ProductBarcode;
import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */

@Repository
public class OrderHistoryDAOImpl implements OrderHistoryDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderHistoryDAOImpl.class);

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

		/**
		 * Saving orders in another table to maintain history
		 * @param orderHistory POJO class object that holds the details of an order
		 */
	@Override
	public void addOrderHistory(List<OrderHistory> orederHistoryList) {
		
		logger.info("addOrderHistory() method of OrderHistoryDAOImpl start");
		Session session = this.sessionFactory.getCurrentSession();
		
		try{
			
		//Query to save info related to order
		for(OrderHistory product:orederHistoryList){
			
			@SuppressWarnings("unchecked")
			List<OrderHistory> list = session.createCriteria(OrderHistory.class)
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
				sBuffer.append("update OrderHistory p set p.quantity=:quantity,");
				sBuffer.append("p.color=:color,p.description=:description,p.serialNo=:serialNo,p.unitdDesc=:unitdDesc,");
				sBuffer.append("p.pickerOrder=:pickerOrder,p.iDesc=:iDesc,p.installation=:installation where p.id=:id");
				
				String query=sBuffer.toString();
				session.createQuery(query)
				.setParameter("quantity", product.getQuantity())
				.setParameter("color", product.getColor())
				.setParameter("description", product.getDescription())
				.setParameter("serialNo", product.getSerialNo())
				.setParameter("unitdDesc", product.getUnitdDesc())
				.setParameter("pickerOrder", product.getPickerOrder())
				.setParameter("iDesc", product.getiDesc())
				.setParameter("installation", product.getInstallation())
				.setParameter("id", list.get(0).getId())
				.executeUpdate();
			}
		}
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			
		}
		logger.info("addOrderHistory() method of OrderHistoryDAOImpl End");
	}

	/**
	 * To update the status of products picked into PICKED
	 * @param orederHistory POJO class object which holds information 
	 * related to products of an order
	 * @return number of products updated
	 */
	@Override
	public int updateOrderHistory(OrderHistory orederHistory) {
		
		logger.info("updateOrderHistory() method of OrderHistoryDAOImpl start");
		Session session = this.sessionFactory.getCurrentSession();
		int i = 0;
		
		try{
			
			//this query updates the status of products picked into PICKED
		i = session.createQuery("update OrderHistory oh set oh.status='"+StringConstatns.picked+"',oh.pickEndTime='" +orederHistory.getPickEndTime()
						+ "' where oh.orderid='" + orederHistory.getOrderid() + "' and oh.barcode='" + orederHistory.getBarcode() + "'")
				.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			
		}
		System.out.println(i + " rows updated");
		logger.info("updateOrderHistory() method of OrderHistoryDAOImpl End");
		return i;
	}

}
