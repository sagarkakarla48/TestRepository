/**
 * 
 */
package com.ltts.picktolight.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author 90001334
 *
 */

@Entity
@Table(name="product_rackid")
public class UploadProducts {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="rack_num")
	private String rackNum;
	
	@Column(name="description")
	private String description;
	
	@Column(name="super_description")
	private String superDescription;
	
	@Column(name="datetime")
	private String datetime;
	
	@Column(name="pick_order")
	private String pickOrder;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="uploadProducts")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ProductBarcode> productBarcode;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="uploadProducts")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<LowInventoryInfo> lowInventory;
	
	
	
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the rackNum
	 */
	public String getRackNum() {
		return rackNum;
	}
	/**
	 * @param rackNum the rackNum to set
	 */
	public void setRackNum(String rackNum) {
		this.rackNum = rackNum;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ProductBarcode> getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(List<ProductBarcode> productBarcode) {
		this.productBarcode = productBarcode;
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
	 * @return the superDescription
	 */
	public String getSuperDescription() {
		return superDescription;
	}
	/**
	 * @param superDescription the superDescription to set
	 */
	public void setSuperDescription(String superDescription) {
		this.superDescription = superDescription;
	}
	/**
	 * @return the pickOrder
	 */
	public String getPickOrder() {
		return pickOrder;
	}
	/**
	 * @param pickOrder the pickOrder to set
	 */
	public void setPickOrder(String pickOrder) {
		this.pickOrder = pickOrder;
	}
	/**
	 * @return the lowInventory
	 */
	public List<LowInventoryInfo> getLowInventory() {
		return lowInventory;
	}
	/**
	 * @param lowInventory the lowInventory to set
	 */
	public void setLowInventory(List<LowInventoryInfo> lowInventory) {
		this.lowInventory = lowInventory;
	}

	
}
