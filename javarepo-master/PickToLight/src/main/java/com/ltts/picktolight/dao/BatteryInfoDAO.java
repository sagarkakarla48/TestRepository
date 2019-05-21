/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.BatteryInfo;

/**
 * @author 90001332
 *
 */
public interface BatteryInfoDAO {

	public int updateBatteryLevel(BatteryInfo batteryInfo);
	
	public void saveBatteryLevels(List<BatteryInfo> batteryLevelList);
	
	public List<BatteryInfo> getBattery();
	
}
