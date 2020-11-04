/**
 * 
 */
package com.side.tenant.tenantServiceImpl;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.side.basic.baseServiceImpl.SideBasicServiceImpl;
import com.side.basic.common.utils.DetachedCriteriaTS;
import com.side.basic.common.utils.PageMode;
import com.side.tenant.ITenantDao.ITenantDao;
import com.side.tenant.ITenantService.ITenantService;
import com.side.tenant.dto.TenantDto;
import com.side.tenant.pojo.Tenant;

/**
 * @author gmc
 * @see 租客业务实现接口
 */
@Service("tenantService")
public class TenantServiceImpl extends SideBasicServiceImpl<Tenant> implements ITenantService {

	@Autowired
	private ITenantDao tenantDao;

	@Override
	public PageMode<Tenant> findPageByDto(TenantDto dto) throws Exception {
		PageMode<Tenant> pageMode = null;
		DetachedCriteriaTS<Tenant> criteria = new DetachedCriteriaTS<Tenant>(Tenant.class);
		criteria.getCriteria().createAlias("user", "user");
		if(!StringUtils.isEmpty(dto.getSearchKey())) {
			criteria.add(Restrictions.or(Restrictions.like("tenantCode", dto.getSearchKey(), MatchMode.ANYWHERE), 
										 Restrictions.like("idCard", dto.getSearchKey(), MatchMode.ANYWHERE),
										 Restrictions.like("user.userName", dto.getSearchKey(), MatchMode.ANYWHERE),
										 Restrictions.like("user.userCode", dto.getSearchKey(), MatchMode.ANYWHERE)
										 ));
		}
		criteria.add(Restrictions.eq("auditFlag", dto.getAuditFlag()));
		pageMode = tenantDao.findForPage(criteria, dto.getPageNumber(), dto.getPageSize());
		return pageMode;
	}

	@Override
	@Transactional
	public void editTenant(Tenant tenant) throws Exception {
		if (tenant.getTenantId() != null) {
			tenant.setLastUpdateDate(new Date());
		}
		tenantDao.saveOrUpdate(tenant);
	}
	
	@Transactional
	public void delTenant(Tenant tenant) throws Exception {
		if(ObjectUtils.isEmpty(tenant)) {
			throw new Exception("参数错误");
		}
		tenantDao.delete(tenant);
	}

}
