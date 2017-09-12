/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.OrderHistory;

/**
 * @author 90001332
 *
 */
public interface OrderHistoryDAO {
	
	public void addOrderHistory(List<OrderHistory> orederHistoryList);
	public int updateOrderHistory(OrderHistory orderHistory);

}
