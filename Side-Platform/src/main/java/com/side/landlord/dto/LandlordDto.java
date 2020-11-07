/**
 * 
 */
package com.side.landlord.dto;

import java.util.Date;

import com.side.basic.dto.BasicDto;
import com.side.users.pojo.SideUser;

/**
 * @author gmc
 *
 */
public class LandlordDto extends BasicDto{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键
	 */
	private Long landlordId;
	
	/**
	 * 房东编码
	 */
	private String landlordCode;
	
	/**
	 * 关联用户
	 */
	private SideUser user;
	
	/**
	 * 信用积分
	 */
	private Integer creditScore;
	
	/**
	 * 证件号码
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

	public Long getLandlordId() {
		return landlordId;
	}

	public void setLandlordId(Long landlordId) {
		this.landlordId = landlordId;
	}

	public String getLandlordCode() {
		return landlordCode;
	}

	public void setLandlordCode(String landlordCode) {
		this.landlordCode = landlordCode;
	}

	public SideUser getUser() {
		return user;
	}

	public void setUser(SideUser user) {
		this.user = user;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
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

	public Integer getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(Integer auditFlag) {
		this.auditFlag = auditFlag;
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

}
