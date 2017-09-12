/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.LowInventoryInfo;

/**
 * @author 90001332
 *
 */
public interface LowInventoryInfoDAO {
	
	String saveLowInventoryInfo(LowInventoryInfo lowInventoryInfo);
	
	List<LowInventoryInfo> getLowInventoryInfo(LowInventoryInfo lowInventoryInfo);
	
	String updateLowInventoryInfo(List<LowInventoryInfo> lowInventoryInfo);
	
	List<LowInventoryInfo> getLowInventoryDetails(LowInventoryInfo prodInfo);

}
