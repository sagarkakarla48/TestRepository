/**
 * 
 */
package com.ltts.picktolight.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author 90001332
 *
 */
@Entity
@Table(name="lowinventoryinfo")
public class LowInventoryInfo {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="quantity")
	private String quantity;
	
	@Column(name="low_stock")
	private boolean lowStock=Boolean.TRUE;
	
	@Column(name="datetime")
	private String datetime;
	
	@Column(name="super_market_desc")
	private String superMarketDesc;
	
	@Column(name="updated_datetime")
	private String updateddatetime;
	
	@Transient
	private String orderId;
	
	@Transient
	private String description;
	
	@Column(name="rack_id")
	private String rackId;
	
	@Transient
	private String productName;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="uploadProductsid")
	private UploadProducts uploadProducts;

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
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	/**
	 * @return the lowStock
	 */
	public boolean isLowStock() {
		return lowStock;
	}

	/**
	 * @param lowStock the lowStock to set
	 */
	public void setLowStock(boolean lowStock) {
		this.lowStock = lowStock;
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
	 * @return the superMarketDesc
	 */
	public String getSuperMarketDesc() {
		return superMarketDesc;
	}

	/**
	 * @param superMarketDesc the superMarketDesc to set
	 */
	public void setSuperMarketDesc(String superMarketDesc) {
		this.superMarketDesc = superMarketDesc;
	}

	/**
	 * @return the updateddatetime
	 */
	public String getUpdateddatetime() {
		return updateddatetime;
	}

	/**
	 * @param updateddatetime the updateddatetime to set
	 */
	public void setUpdateddatetime(String updateddatetime) {
		this.updateddatetime = updateddatetime;
	}

	/**
	 * @return the uploadProducts
	 */
	public UploadProducts getUploadProducts() {
		return uploadProducts;
	}

	/**
	 * @param uploadProducts the uploadProducts to set
	 */
	public void setUploadProducts(UploadProducts uploadProducts) {
		this.uploadProducts = uploadProducts;
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

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the rackId
	 */
	public String getRackId() {
		return rackId;
	}

	/**
	 * @param rackId the rackId to set
	 */
	public void setRackId(String rackId) {
		this.rackId = rackId;
	}
	
}
