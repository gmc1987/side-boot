/**
 * 
 */
package com.side.tenant.ITenantService;

import com.side.basic.IbaseService.ISideBasicService;
import com.side.basic.common.utils.PageMode;
import com.side.tenant.dto.TenantDto;
import com.side.tenant.pojo.Tenant;

/**
 * @author gmc
 *
 */
public interface ITenantService extends ISideBasicService<Tenant> {

	/**
	 * 根据dto对象查询数据
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageMode<Tenant> findPageByDto(TenantDto dto) throws Exception;
	
	/**
	 * 保存租户信息
	 * @param tenant
	 * @throws Exception
	 */
	public void editTenant(Tenant tenant) throws Exception;
	 
}
