/**
 * 
 */
package com.ltts.picktolight.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltts.picktolight.dao.LowInventoryInfoDAO;
import com.ltts.picktolight.domain.LowInventoryInfo;

/**
 * @author 90001332
 *
 */
@Service
public class LowInventoryInfoServiceImpl implements LowInventoryInfoService {

	private LowInventoryInfoDAO lowInventoryInfoDao;

	public void setLowInventoryInfoDao(LowInventoryInfoDAO lowInventoryInfoDao) {
		this.lowInventoryInfoDao = lowInventoryInfoDao;
	}

	@Override
	@Transactional
	public String saveLowInventoryInfo(LowInventoryInfo lowInventoryInfo) {
		return lowInventoryInfoDao.saveLowInventoryInfo(lowInventoryInfo);
	}

	@Override
	@Transactional
	public List<LowInventoryInfo> getLowInventoryInfo(LowInventoryInfo lowInventoryInfo) {
		return lowInventoryInfoDao.getLowInventoryInfo(lowInventoryInfo);
	}

	@Override
	@Transactional
	public String updateLowInventoryInfo(List<LowInventoryInfo> lowInventoryInfo) {
		return lowInventoryInfoDao.updateLowInventoryInfo(lowInventoryInfo);
	}

	@Override
	@Transactional
	public List<LowInventoryInfo> getLowInventoryDetails(LowInventoryInfo prodInfo) {
		return lowInventoryInfoDao.getLowInventoryDetails(prodInfo);
	}
	
}
