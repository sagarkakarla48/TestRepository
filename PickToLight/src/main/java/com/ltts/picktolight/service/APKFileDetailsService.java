/**
 * 
 */
package com.ltts.picktolight.service;

import com.ltts.picktolight.domain.APKFileDetails;

/**
 * @author 90001332
 *
 */
public interface APKFileDetailsService {
	
	
	public String saveAPKDetails(APKFileDetails apkFileDetails);
	
	public APKFileDetails getAPKDetails(APKFileDetails apkFileDetails);

}
