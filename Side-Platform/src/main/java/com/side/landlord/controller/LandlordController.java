/**
 * 
 */
package com.side.landlord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.side.basic.common.utils.PageMode;
import com.side.basic.constant.SideConstant;
import com.side.basic.controller.SideBaseController;
import com.side.basic.dto.ResultDto;
import com.side.landlord.IService.ILandlordService;
import com.side.landlord.dto.LandlordDto;
import com.side.landlord.pojo.Landlord;

/**
 * @author gmc
 * @see 房东请求控制器
 */

@RestController
@RequestMapping("/landlord")
public class LandlordController extends SideBaseController {
	
	@Autowired
	private ILandlordService landlordService;
	
	/**
	 * 获取房东列表
	 * @param dto
	 * @return
	 */
	@GetMapping("/list")
	public ResultDto<Landlord> getList(LandlordDto dto) {
		
		ResultDto<Landlord> result = null;
		
		try {
			PageMode<Landlord> list = landlordService.getList(dto);
			result = new ResultDto<>(SideConstant.SUCCESS, SideConstant.SUCCESS_MSG, list);
		} catch(Exception e) {
			e.printStackTrace();
			result = new ResultDto<>(SideConstant.FAIL, SideConstant.FAIL_MSG);
		}
		
		return result;
	}
	
	@PostMapping("/audit")
	public ResultDto<Landlord> editLandlord(@RequestBody Landlord landlord) {
		
		ResultDto<Landlord> result = null;
		boolean editFlag = false;
		try {
			editFlag = landlordService.editLandlord(landlord);
			if(!editFlag) {
				result = new ResultDto<Landlord>(SideConstant.FAIL, SideConstant.FAIL_MSG);
			} else {
				result = new ResultDto<Landlord>(SideConstant.SUCCESS, SideConstant.SUCCESS_MSG);
			}
		} catch(Exception e) {
			e.printStackTrace();
			result = new ResultDto<Landlord>(SideConstant.FAIL, SideConstant.FAIL_MSG);
		}
		
		return result;
	}

}
