/**
 * 
 */
package com.side.authorization.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.side.role.pojo.SideRole;
import com.side.users.pojo.SideUser;

/**
 * @author gmc
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(value= {"hibernateLazyInitializer", "handler"})
@Entity
@DynamicUpdate
@Table(name="side_user_role")
public class SideUserRole implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer userRoleId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name = "userId", nullable=false)
	private SideUser userId;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name = "roleId", nullable=false)
	private SideRole roleId;
	

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public SideUser getUserId() {
		return userId;
	}

	public void setUserId(SideUser userId) {
		this.userId = userId;
	}

	public SideRole getRoleId() {
		return roleId;
	}

	public void setRoleId(SideRole roleId) {
		this.roleId = roleId;
	}
}
