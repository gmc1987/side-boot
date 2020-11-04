/**
 * 
 */
package com.side.authorization.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.side.authorization.IDao.IAuthorizationDao;
import com.side.authorization.IService.IAuthorizationService;
import com.side.authorization.dto.SideAuthorizationDto;
import com.side.authorization.pojo.SideAuthorization;
import com.side.basic.baseServiceImpl.SideBasicServiceImpl;
import com.side.basic.common.utils.DetachedCriteriaTS;
import com.side.menus.IService.ISideMenuService;
import com.side.menus.pojo.SideMenus;
import com.side.role.IRoleService.IRoleService;
import com.side.role.pojo.SideRole;

/**
 * @author gmc
 *
 */
@Service("authorizationService")
public class AuthorizationServiceImpl extends SideBasicServiceImpl<SideAuthorization> implements IAuthorizationService {
	
	@Autowired
	@Qualifier("authorizationDao")
	private IAuthorizationDao authorizationDao;
	
	@Autowired
	@Qualifier("roleService")
	private IRoleService roleService;
	
	@Qualifier("sideMenuService")
	@Autowired
	private ISideMenuService sideMenuService;

	@Override
	public List<SideAuthorization> findAuthorizationByRole(String roleCode) throws Exception {
		List<SideAuthorization> authorizations = null;
		SideRole role = roleService.findRoleByCode(roleCode);
		if(role != null) {
			DetachedCriteriaTS<SideAuthorization> criteria = new DetachedCriteriaTS<SideAuthorization>(SideAuthorization.class);
			criteria.getCriteria().createAlias("menuId", "menu");
			criteria.add(Restrictions.eqOrIsNull("roleId", role));
			criteria.addOrder(Order.asc("menu.menuSort"));
			criteria.addOrder(Order.asc("menu.isParent"));
			authorizations = authorizationDao.findAll(criteria);
		}
		return authorizations;
	}

	@Override
	public List<SideAuthorization> findParentAuthorizationByRole(String roleCode) throws Exception {
		DetachedCriteriaTS<SideAuthorization> criteria = new DetachedCriteriaTS<SideAuthorization>(SideAuthorization.class);
		if(StringUtils.isEmpty(roleCode)) {
			return null;
		}
		criteria.getCriteria().createAlias("roleId", "role");
		criteria.getCriteria().createAlias("menuId", "menu");
		
		criteria.add(Restrictions.eq("role.roleCode", roleCode));
		criteria.add(Restrictions.eq("menu.isParent", 0));
		
		criteria.addOrder(Order.desc("menu.menuSort"));
		
		return authorizationDao.findAll(criteria);
		
	}

	@Override
	@Cache(region="authorization", usage=CacheConcurrencyStrategy.READ_ONLY)
	public List<SideAuthorization> findAuthorizationByRoleAndMenu(SideAuthorizationDto dto) throws Exception {
		
		List<SideAuthorization> authorizations = null;
		SideRole role = null;
		SideMenus menu = null;
		
		if (ObjectUtils.isEmpty(dto)) {
			throw new Exception("参数有误！");
		} else {
			role = roleService.get(SideRole.class, dto.getRoleId());
			menu = sideMenuService.get(SideMenus.class, dto.getMenuId());
			
			if(role != null && menu != null) {
				DetachedCriteriaTS<SideAuthorization> criteria = new DetachedCriteriaTS<SideAuthorization>(SideAuthorization.class);
				criteria.add(Restrictions.eq("roleId", role));
				criteria.add(Restrictions.eq("menuId", menu));
				authorizations = authorizationDao.findAll(criteria);
			}
			
		}
		
		return authorizations;
	}

	@Override
	@Transactional
	public void saveAllAuthorizations(List<SideAuthorizationDto> list) throws Exception {
		List<SideAuthorization> addList = new ArrayList<SideAuthorization>();
		List<SideAuthorization> delList = new ArrayList<SideAuthorization>();
		//过滤新增与删除的数据
		for(SideAuthorizationDto authorization : list) {
			if(StringUtils.isEmpty(authorization.getOperationList()) && authorization.getAuthorizationId() != null) { //删除
				SideAuthorization tmp = authorizationDao.get(SideAuthorization.class, authorization.getAuthorizationId());
				delList.add(tmp);
			} else { //新增
				if(authorization.getAuthorizationId() == null) {
					SideRole role = roleService.get(SideRole.class, authorization.getRoleId());
					SideMenus menu = sideMenuService.get(SideMenus.class, authorization.getMenuId());
					SideAuthorization tmp = new SideAuthorization();
					tmp.setMenuId(menu);
					tmp.setRoleId(role);
					tmp.setOperationList(authorization.getOperationList());
					addList.add(tmp);
				} else { //修改
					SideAuthorization tmp = authorizationDao.get(SideAuthorization.class, authorization.getAuthorizationId());
					tmp.setOperationList(authorization.getOperationList());
					addList.add(tmp);
				}
			}
		}
		//执行删除数据
		if(delList.size() > 0) {
			authorizationDao.deleteAll(delList);
		}
		//执行新增/更新操作
		if(addList.size() > 0) {
			authorizationDao.saveOrUpdateAll(addList);
		}
		
	}
}
