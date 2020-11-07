/**
 * 
 */
package com.side.landlord.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

import com.side.users.pojo.SideUser;

/**
 * @author gmc
 * @see 房东实体类
 */

@DynamicUpdate
@Entity
@Table(name="side_landlord")
public class Landlord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="landlord_id", unique = true, nullable = false)
	private Long landlordId;
	
	/**
	 * 房东编码
	 */
	@Column(name="landlord_code", nullable=false)
	private String landlordCode;
	
	/**
	 * 关联用户
	 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(insertable=true, nullable=false)
	private SideUser user;
	
	/**
	 * 信用积分
	 */
	@Column(name="credit_score", nullable=false)
	private Integer creditScore;
	
	/**
	 * 证件号码
	 */
	@Column(name="id_card",nullable=true)
	private String idCard;
	
	/**
	 * 证件类型
	 */
	@Column(name="id_card_type", nullable=true)
	private Integer idCardType;
	
	/**
	 * 证件照（正面）
	 */
	@Column(name="card_photo_first", nullable=true)
	private Byte[] cardPhotofirst;
	
	/**
	 * 证件照（反面）
	 */
	@Column(name="card_photo_second", nullable=true)
	private Byte[] cardPhotosecond;
	
	/**
	 * 审核状态
	 */
	@Column(name="audit_flag", nullable=true)
	private Integer auditFlag;
	
	/**
	 * 创建人
	 */
	@Column(name="create_by", nullable=false)
	private Long createBy;
	
	/**
	 * 创建日期
	 */
	@Column(name="create_date", nullable=false)
	private Date createDate;
	
	/**
	 * 更新人
	 */
	@Column(name="last_update_by", nullable=true)
	private Long lastUpdateBy;
	
	/**
	 * 更新日期
	 */
	@Column(name="last_update_date", nullable=true)
	private Date lastUpdateDate;
	
	@Transient
	private String formatCreateDate = "";
	
	@Transient
	private String isAudited = "";

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

	public String getFormatCreateDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.format(this.getCreateDate());
		} catch(Exception e) {
			return format.format(new Date());
		}
	}

	public void setFormatCreateDate(String formatCreateDate) {
		this.formatCreateDate = "";
	}

	public String getIsAudited() {
		if(!ObjectUtils.isEmpty(this.getAuditFlag())) {
			if(this.getAuditFlag() == 1) {
				isAudited = "已审核";
			} else {
				isAudited = "未审核";
			}
		} else {
			isAudited = "未审核";
		}
		return isAudited;
	}

	public void setIsAudited(String isAudited) {
		this.isAudited = "";
	}

}
