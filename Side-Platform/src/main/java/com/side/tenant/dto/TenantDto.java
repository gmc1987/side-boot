/**
 * 
 */
package com.side.tenant.dto;

import java.io.Serializable;
import java.util.Date;

import com.side.basic.dto.BasicDto;

/**
 * @author gmc
 * @see 参数传递对象
 */
public class TenantDto extends BasicDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long tenantId;
	
	/**
	 * 租户编码
	 */
	private String tenantCode;
	
	/**
	 * 关联用户
	 */
	private Long userId;
	
	/**
	 * 信用评分
	 */
	private Double trustNum;
	
	/**
	 * 证件号
	 */
	private String idCard;
	
	/**
	 * 证件类型
	 */
	private Integer idCardType;
	
	/**
	 * 证件照（正面）
	 */
	private Byte[] cardPhotofirst;
	
	/**
	 * 证件照（反面）
	 */
	private Byte[] cardPhotosecond;
	
	/**
	 * 审核状态
	 */
	private Integer auditFlag;
	
	/**
	 * 创建人
	 */
	private Long createBy;
	
	/**
	 * 创建日期
	 */
	private Date createDate;
	
	/**
	 * 更新人
	 */
	private Long lastUpdateBy;
	
	/**
	 * 更新日期
	 */
	private Date lastUpdateDate;

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getTrustNum() {
		return trustNum;
	}

	public void setTrustNum(Double trustNum) {
		this.trustNum = trustNum;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(Integer idCardType) {
		this.idCardType = idCardType;
	}

	public Byte[] getCardPhotofirst() {
		return cardPhotofirst;
	}

	public void setCardPhotofirst(Byte[] cardPhotofirst) {
		this.cardPhotofirst = cardPhotofirst;
	}

	public Byte[] getCardPhotosecond() {
		return cardPhotosecond;
	}

	public void setCardPhotosecond(Byte[] cardPhotosecond) {
		this.cardPhotosecond = cardPhotosecond;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
	}

}
