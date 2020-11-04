/**
 * 
 */
package com.side.tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.side.basic.common.utils.PageMode;
import com.side.basic.constant.SideConstant;
import com.side.basic.controller.SideBaseController;
import com.side.basic.dto.ResultDto;
import com.side.tenant.ITenantService.ITenantService;
import com.side.tenant.dto.TenantDto;
import com.side.tenant.pojo.Tenant;

/**
 * @author gmc
 *
 */

@RestController
@RequestMapping("/tenant/service")
public class SideTenantController extends SideBaseController {
	
	@Qualifier("tenantService")
	@Autowired
	private ITenantService tenantService;
	
	/**
	 * url http://localhost:8801/side/tenant/service/list
	 * @param dto
	 * @return
	 */
	@GetMapping("/list")
	public ResultDto<Tenant> list(TenantDto dto, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize")int pageSize) {
		
		ResultDto<Tenant> result = null;
		try {
			PageMode<Tenant> tenants = tenantService.findPageByDto(dto);
			result = new ResultDto<Tenant>(SideConstant.SUCCESS, SideConstant.SUCCESS_MSG, tenants);
		} catch(Exception e) {
			result = new ResultDto<Tenant>(SideConstant.SUCCESS, SideConstant.SUCCESS_MSG);
		}
		
		return result;
	}
	
	@PostMapping("/editTenant")
	public ResultDto<Tenant> editTenant(@RequestBody Tenant tenant){
		
		ResultDto<Tenant> result = null;
		try {
			if(tenant.getTenantId() != null) {
				tenant.setLastUpdateBy(this.getUserInfo().getUser().getUserId());
			}
			tenantService.editTenant(tenant);
			result = new ResultDto<>(SideConstant.SUCCESS, SideConstant.SUCCESS_MSG);
		} catch(Exception e) {
			result = new ResultDto<>(SideConstant.FAIL, SideConstant.FAIL_MSG);
		}
		
		return result;
	}

}
