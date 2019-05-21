/**
 * 
 */
package com.ltts.picktolight.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author 90001332
 *
 */
@Entity
@Table(name="apkfiledetails")
public class APKFileDetails {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String apkname;
	
	private String version;
	
	private String datetime;
	
	@Transient
	private String existingversion;
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the apkname
	 */
	public String getApkname() {
		return apkname;
	}

	/**
	 * @param apkname the apkname to set
	 */
	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the datetime
	 */
	public String getDatetime() {
		return datetime;
	}

	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	/**
	 * @return the existingversion
	 */
	public String getExistingversion() {
		return existingversion;
	}

	/**
	 * @param existingversion the existingversion to set
	 */
	public void setExistingversion(String existingversion) {
		this.existingversion = existingversion;
	}

}
