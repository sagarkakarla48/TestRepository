/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;

import com.ltts.picktolight.domain.LowInventoryInfo;

/**
 * @author 90001332
 *
 */
public interface LowInventoryInfoService {

	String saveLowInventoryInfo(LowInventoryInfo lowInventoryInfo);
	
	List<LowInventoryInfo> getLowInventoryInfo(LowInventoryInfo lowInventoryInfo);
	
	String updateLowInventoryInfo(List<LowInventoryInfo> lowInventoryInfo);
	
	List<LowInventoryInfo> getLowInventoryDetails(LowInventoryInfo prodInfo);
}
