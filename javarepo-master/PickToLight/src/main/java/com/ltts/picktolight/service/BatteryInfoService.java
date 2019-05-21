/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;
import java.util.Map;

import com.ltts.picktolight.domain.BatteryInfo;
import com.ltts.picktolight.domain.UploadProducts;

/**
 * @author 90001332
 *
 */
public interface BatteryInfoService {
	
	public Map<String, String> updateBatteryLevel(BatteryInfo batteryInfo);
	
	public void saveBatteryLevels(List<UploadProducts> uploadProductList);
	
	public List<BatteryInfo> getBattery();
	
}
