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

/**
 * @author 90001332
 *
 */

@Entity
@Table(name="orders_history")
public class OrderHistory {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="order_id")
	private String orderid;
	@Column(name="barcode")
	private String barcode;
	@Column(name="product_quantity")
	private String quantity;
	@Column(name="color")
	private String color;
	@Column(name="status")
	private String status="Not Picked";
	@Column(name="force_complete_status")
	private String forceCompleteStatus;

	@Column(name="pick_start_time")
	private String pickStartTime;
	@Column(name="pick_end_time")
	private String pickEndTime;
	
	@Column(name="description")
	private String description;
	@Column(name="super_market_desc")
	private String superMarketDesc;
	@Column(name="serial_no")
	private String serialNo;
	@Column(name="unitd_desc")
	private String unitdDesc;
	@Column(name="picker_order")
	private String pickerOrder;
	@Column(name="i_desc")
	private String iDesc;
	@Column(name="installation")
	private String installation;

	
	
		
	
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
	 * @return the pickStartTime
	 */
	public String getPickStartTime() {
		return pickStartTime;
	}

	/**
	 * @param pickStartTime the pickStartTime to set
	 */
	public void setPickStartTime(String pickStartTime) {
		this.pickStartTime = pickStartTime;
	}

	/**
	 * @return the pickEndTime
	 */
	public String getPickEndTime() {
		return pickEndTime;
	}

	/**
	 * @param pickEndTime the pickEndTime to set
	 */
	public void setPickEndTime(String pickEndTime) {
		this.pickEndTime = pickEndTime;
	}

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
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
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

	/**
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the unitdDesc
	 */
	public String getUnitdDesc() {
		return unitdDesc;
	}

	/**
	 * @param unitdDesc the unitdDesc to set
	 */
	public void setUnitdDesc(String unitdDesc) {
		this.unitdDesc = unitdDesc;
	}

	/**
	 * @return the pickerOrder
	 */
	public String getPickerOrder() {
		return pickerOrder;
	}

	/**
	 * @param pickerOrder the pickerOrder to set
	 */
	public void setPickerOrder(String pickerOrder) {
		this.pickerOrder = pickerOrder;
	}

	/**
	 * @return the iDesc
	 */
	public String getiDesc() {
		return iDesc;
	}

	/**
	 * @param iDesc the iDesc to set
	 */
	public void setiDesc(String iDesc) {
		this.iDesc = iDesc;
	}

	/**
	 * @return the installation
	 */
	public String getInstallation() {
		return installation;
	}

	/**
	 * @param installation the installation to set
	 */
	public void setInstallation(String installation) {
		this.installation = installation;
	}
	
		
}
