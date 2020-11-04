/**
 * 
 */
package com.side.users.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.side.users.IService.ISideUserService;
import com.side.users.dto.SideUserDto;
import com.side.users.pojo.SideUser;

/**
 * @author gmc
 * 
 */
@RestController
@RequestMapping("/users")
public class UserController extends SideBaseController {
	
	@Qualifier("sideUserService")
	@Autowired
	private ISideUserService sideUserService;

	@GetMapping("/getUserList")
	public ResultDto<SideUserDto> getUserList(SideUserDto dto, @RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize")int pageSize){
		
		ResultDto<SideUserDto> resultDto = new ResultDto<SideUserDto>();
		PageMode<SideUserDto> pageMode = null;
		
		try {
			pageMode = sideUserService.findSystemUserBySQL(dto, pageNumber, pageSize);
			
			if(pageMode != null) {
				resultDto.setRetCode(SideConstant.SUCCESS);
				resultDto.setRetMsg(SideConstant.SUCCESS_MSG);
				resultDto.setPageMode(pageMode);
			} else {
				resultDto.setRetCode(SideConstant.QUERY_EMPTY);
				resultDto.setRetMsg(SideConstant.QUERY_EMPTY_MSG);
			}
			
		} catch(Exception e) {
			resultDto.setRetCode(SideConstant.FAIL);
			resultDto.setRetMsg(SideConstant.FAIL_MSG);
			e.printStackTrace();
		}
		
		return resultDto;
	}
	
	@PostMapping("/userEditer")
	public ResultDto<SideUserDto> userEdit(@RequestBody SideUser user){
		
		ResultDto<SideUserDto> resultDto = new ResultDto<SideUserDto>();
		
		PageMode<SideUserDto> pageMode = null;
		
		SideUserDto dto = new SideUserDto();
		
		try {
			if(user != null && user.getUserId() != null) {
				user.setLastUpdateBy(getUserInfo().getUser().getUserId());
				user.setLastUpdateDate(new Date());
				user.getAccount().setLastUpdateBy(user.getCreateBy());
				user.getAccount().setLastUpdateDate(new Date());
				String password = user.getAccount().getAccPassword();
				if(StringUtils.isNotEmpty(password) && password.length() < 60) {
					user.getAccount().setAccPassword(new BCryptPasswordEncoder().encode(password));
				}
			} else {
				user.setCreateBy(getUserInfo().getUser().getUserId());
				user.setCreateDate(new Date());
				user.getAccount().setCreateBy(user.getCreateBy());
				user.getAccount().setCreateDate(new Date());
				String password = user.getAccount().getAccPassword();
				if(StringUtils.isNotEmpty(password)) {
					user.getAccount().setAccPassword(new BCryptPasswordEncoder().encode(password));
				}
			}
			user.getAccount().setUserId(user);
			sideUserService.update(user);
			
			pageMode = sideUserService.findSystemUserBySQL(dto, 1, 10);
			
			if(pageMode != null) {
				resultDto.setRetCode(SideConstant.SUCCESS);
				resultDto.setRetMsg(SideConstant.SUCCESS_MSG);
				resultDto.setPageMode(pageMode);
			} else {
				resultDto.setRetCode(SideConstant.QUERY_EMPTY);
				resultDto.setRetMsg(SideConstant.QUERY_EMPTY_MSG);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			resultDto.setRetCode(SideConstant.FAIL);
			resultDto.setRetMsg(SideConstant.FAIL_MSG);
		}
		
		
		return resultDto;
	}
	
	/**
	 * 根据ID获取用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping("/userInfo")
	public ResultDto<SideUser> getUserInfo(@RequestParam("userId") Long userId){
		
		ResultDto<SideUser> resultDto = new ResultDto<SideUser>();
		try {
			SideUser user = sideUserService.get(SideUser.class, userId);
			List<SideUser> record = new ArrayList<SideUser>();
			if(user != null) {
//				System.out.println("当前账号编码:"+user.getAccount().getAccCode());
				record.add(user);
				resultDto.setRecord(record);
				resultDto.setRetCode(SideConstant.SUCCESS);
				resultDto.setRetMsg(SideConstant.SUCCESS_MSG);
			} else {
				resultDto.setRetCode(SideConstant.QUERY_EMPTY);
				resultDto.setRetMsg(SideConstant.QUERY_EMPTY_MSG);
			}
			
		} catch (Exception e) {
			resultDto.setRetCode(SideConstant.FAIL);
			resultDto.setRetMsg(SideConstant.FAIL_MSG);
			e.printStackTrace();
		}
		
		return resultDto;
	}
	
	@DeleteMapping("/delete")
	public ResultDto<SideUser> deleteUser(@RequestParam("userId") Long userId){
		ResultDto<SideUser> resultDto = new ResultDto<SideUser>();
		try {
			SideUser user = sideUserService.get(SideUser.class, userId);
			
			if(user != null) {
				sideUserService.delete(user);
				resultDto.setRetCode(SideConstant.SUCCESS);
				resultDto.setRetMsg(SideConstant.SUCCESS_MSG);
			} else {
				resultDto.setRetCode(SideConstant.QUERY_EMPTY);
				resultDto.setRetMsg(SideConstant.QUERY_EMPTY_MSG);
			}
			
		} catch (Exception e) {
			resultDto.setRetCode(SideConstant.FAIL);
			resultDto.setRetMsg(SideConstant.FAIL_MSG);
			e.printStackTrace();
		}
		
		return resultDto;
	}
	
}
