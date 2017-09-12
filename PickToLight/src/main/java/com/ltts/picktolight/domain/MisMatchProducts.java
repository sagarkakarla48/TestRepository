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

import com.ltts.picktolight.util.StringConstatns;

/**
 * @author 90001332
 *
 */

@Entity
@Table(name="mismatchproducts")
public class MisMatchProducts {

	@Id
	@Column(name="id")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)
	//@GeneratedValue(generator="increment")
	//@GenericGenerator(name="increment", strategy = "increment")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="order_id")
	private String orderid;
	@Column(name="barcode")
	private String barcode;
	@Column(name="product")
	private String productId;
	@Column(name="product_quantity")
	private String quantity;
	@Column(name="status")
	private String status=StringConstatns.notPicked;
	@Column(name="datetime")
	private String datetime;
	@Column(name="description")
	private String description;
	@Column(name="super_market_desc")
	private String superMarketDesc;
	@Column(name="force_complete_status")
	private String forceCompleteStatus;
			
	
	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	 * @return the forceCompleteStatus
	 */
	public String getForceCompleteStatus() {
		return forceCompleteStatus;
	}

	/**
	 * @param forceCompleteStatus the forceCompleteStatus to set
	 */
	public void setForceCompleteStatus(String forceCompleteStatus) {
		this.forceCompleteStatus = forceCompleteStatus;
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the orderid
	 */
	public String getOrderid() {
		return orderid;
	}

	/**
	 * @param orderid the orderid to set
	 */
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString(){
		return "id="+id+", quantity="+quantity+",orderid="+orderid;
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
	
}
