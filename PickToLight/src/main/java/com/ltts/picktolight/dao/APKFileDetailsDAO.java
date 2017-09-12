/**
 * 
 */
package com.ltts.picktolight.dao;

import com.ltts.picktolight.domain.APKFileDetails;

/**
 * @author 90001332
 *
 */
public interface APKFileDetailsDAO {
	
	public String saveAPKDetails(APKFileDetails apkFileDetails);
	
	public APKFileDetails getAPKDetails(APKFileDetails apkFileDetails);

}
