/**
 * 
 */
package com.side.users.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.mysql.jdbc.StringUtils;
import com.side.authorization.IDao.IUserRoleDao;
import com.side.authorization.pojo.SideUserRole;
import com.side.basic.baseServiceImpl.SideBasicServiceImpl;
import com.side.basic.common.utils.DetachedCriteriaTS;
import com.side.basic.common.utils.PageMode;
import com.side.role.IRoleDao.IRoleDao;
import com.side.role.pojo.SideRole;
import com.side.users.IDao.ISideAccountDao;
import com.side.users.IDao.ISideUserDao;
import com.side.users.IService.ISideUserService;
import com.side.users.dto.SideUserDto;
import com.side.users.pojo.Account;
import com.side.users.pojo.SideUser;
import com.side.users.sql.UserSQL;

/**
 * @author gmc
 *
 */
@Service("sideUserService")
public class SideUserServiceImpl extends SideBasicServiceImpl<SideUser> implements ISideUserService {

	@Resource
	private ISideUserDao sideUserDao;
	
	@Resource
	private ISideAccountDao sideAccountDao;
	
	@Resource
	private IRoleDao roleDao;
	
	@Resource
	private IUserRoleDao userRoleDao;
	
	@Override
	public SideUser findSideUserByCode(String code) {
		
		SideUser adminUser = null;
		
		DetachedCriteriaTS<SideUser> criteria = new DetachedCriteriaTS<SideUser>(SideUser.class);
		
		if(!StringUtils.isNullOrEmpty(code)) {
			criteria.add(Restrictions.eq("userCode", code));
			adminUser = sideUserDao.find(criteria);
		}
		
		return adminUser;
	}

	@Override
	public List<SideUser> findAllUser(SideUserDto dto) throws Exception {
		List<SideUser> list = null;
		
		DetachedCriteriaTS<SideUser> criteria = new DetachedCriteriaTS<SideUser>(SideUser.class);	
		
		if(dto.getAccount() != null) {
			Account account = sideAccountDao.get(Account.class, dto.getAccount());
			criteria.add(Restrictions.eq("account", account));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getUserCode())) {
			criteria.add(Restrictions.eq("userCode", dto.getUserCode()));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getUserName())) {
			criteria.add(Restrictions.like("userName", dto.getUserName(), MatchMode.ANYWHERE));
		}
		
		if(dto.getUserStatus() != null) {
			criteria.add(Restrictions.eq("userStatus", dto.getUserStatus()));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getSearchKey())) {
			criteria.add(Restrictions.or(Restrictions.like("userCode", dto.getSearchKey(), MatchMode.ANYWHERE), 
										 Restrictions.like("userName", dto.getSearchKey(), MatchMode.ANYWHERE)));
		}
		
		list = sideUserDao.findAll(criteria);
		
		return list;
	}

	@Override
	public PageMode<SideUser> findUserForPages(SideUserDto dto, int pageNumber, int pageSize) throws Exception {
		
		DetachedCriteriaTS<SideUser> criteria = new DetachedCriteriaTS<SideUser>(SideUser.class);
		if(dto.getAccount() != null) {
			Account account = sideAccountDao.get(Account.class, dto.getAccount());
			criteria.add(Restrictions.eq("account", account));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getUserCode())) {
			criteria.add(Restrictions.eq("userCode", dto.getUserCode()));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getUserName())) {
			criteria.add(Restrictions.like("userName", dto.getUserName(), MatchMode.ANYWHERE));
		}
		
		if(dto.getUserStatus() != null) {
			criteria.add(Restrictions.eq("userStatus", dto.getUserStatus()));
		}
		
		if(!StringUtils.isNullOrEmpty(dto.getSearchKey())) {
			criteria.add(Restrictions.or(Restrictions.like("userCode", dto.getSearchKey(), MatchMode.ANYWHERE), 
										 Restrictions.like("userName", dto.getSearchKey(), MatchMode.ANYWHERE)));
		}
		
		return sideUserDao.findForPage(criteria, pageNumber, pageSize);
	}

	@Override
	@Transactional
	public void userEditer(SideUser user) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		//新增
		if(user != null && user.getUserId() == null) {
			if(user.getAccount() != null) {
				String tmpPwd = user.getAccount().getAccPassword();
				user.getAccount().setAccPassword(encoder.encode(tmpPwd));
				sideUserDao.saveOrUpdate(user);
			}
		} else { //修改
			SideUser old = sideUserDao.get(SideUser.class, user.getUserId());
			BeanUtils.copyProperties(user, old, 
					"userId", "userCode", "createDate", "lastUpdateDate", "createBy", "lastUpdateBy", 
					"account.accountId", "account.accCode", "account.userId", "account.createDate", "account.createBy",
					"account.lastUpdateDate", "account.lastUpdateBy");
			if(user.getAccount() != null && user.getAccount().getAccPassword() != null) {
				if(!encoder.matches(user.getAccount().getAccPassword(), old.getAccount().getAccPassword())) {
					old.getAccount().setAccPassword(encoder.encode(user.getAccount().getAccPassword()));
				}
				old.getAccount().setLastUpdateBy(user.getUserId());
				old.getAccount().setLastUpdateDate(new Date());
			}
			old.setLastUpdateBy(user.getUserId());
			old.setLastUpdateDate(new Date());
			sideUserDao.saveOrUpdate(old);
		}
		
	}

	@Override
	public PageMode<SideUserDto> findSystemUserBySQL(SideUserDto dto, int pageNumber, int pageSize) throws Exception {
		
		PageMode<SideUserDto> pageMode = null;
		StringBuffer sb = new StringBuffer(500);
		sb.append(UserSQL.FIND_SYSTEM_USER_BYSQL);
		Map<String, String> params = new HashMap<String, String>();
		
		if(!StringUtils.isNullOrEmpty(dto.getSearchKey())) {
			sb.replace(sb.indexOf("$1"), sb.indexOf("$1")+2, " and (a.usercode like ? or a.username like ? or r.roleName like ?)");
			params.put("usercode", "%" + dto.getSearchKey() + "%");
			params.put("username", "%" + dto.getSearchKey() + "%");
			params.put("roleName", "%" + dto.getSearchKey() + "%");
		} else {
			sb.replace(sb.indexOf("$1"), sb.indexOf("$1")+2, "");
		}
		
		pageMode = sideUserDao.findBySQL(sb.toString(), params, pageNumber, pageSize, SideUserDto.class);
		
		return pageMode;
	}

	@Override
	@Transactional
	public void userRegist(SideUser user) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		if (ObjectUtils.isEmpty(user)) {
			throw new Exception("参数异常:[user]为空");
		}
		String tmpPwd = user.getAccount().getAccPassword();
		user.getAccount().setAccPassword(encoder.encode(tmpPwd));
		sideUserDao.saveOrUpdate(user);
		
		DetachedCriteriaTS<SideUser> criteria = new DetachedCriteriaTS<SideUser>(SideUser.class);
		DetachedCriteriaTS<SideRole> roleCriteria = new DetachedCriteriaTS<SideRole>(SideRole.class);
		criteria.add(Restrictions.eq("userCode", user.getUserCode()));
		roleCriteria.add(Restrictions.eq("roleCode", "tenant"));
		SideUser newUser = sideUserDao.find(criteria);
		SideRole role = roleDao.find(roleCriteria);
		SideUserRole userRole = new SideUserRole();
		userRole.setUserId(newUser);
		userRole.setRoleId(role);
		userRoleDao.saveOrUpdate(userRole);
	}


}
