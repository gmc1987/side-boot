/**
 * 
 */
package com.side.menus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.side.basic.SideException.SideCustException;
import com.side.basic.common.josn.JsonTools;
import com.side.menus.IService.ISideMenuService;
import com.side.menus.dto.MenuDto;
import com.side.menus.pojo.SideMenus;
import com.side.users.IService.ISideUserService;
import com.side.users.pojo.SideUser;

/**
 * @author gmc
 * 
 */

@RestController
@RequestMapping("/menu")
public class SideMenuController {
	
	@Qualifier("sideMenuService")
	@Autowired
	private ISideMenuService sideMenuService;
	
	@Qualifier("sideUserService")
	@Autowired
	private ISideUserService sideUserService;
	
	@GetMapping("search")
	public Map<String, Object> findMenus(MenuDto dto){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SideMenus> mode;
		try {
			mode = sideMenuService.findMenuByKey(dto);
			result.put("success", true);
			result.put("data", JsonTools.obj2Json(mode));
			return result;
		} catch (SideCustException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("data", null);
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("data", null);
			return result;
		}
		
	}
	
	@PutMapping("editMenuService")
	public Map<String, Object> menuEdit(MenuDto dto){
		Map<String, Object> result = new HashMap<String, Object>();
		List<SideMenus> mode;
		try {
			SideUser user = sideUserService.findSideUserByCode((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			if(user != null) {
				dto.setCurrentUser(String.valueOf(user.getUserId()));
			}
			sideMenuService.editMenuByDto(dto);
			
			dto.setKey("");
			dto.setMenuCode("");
			dto.setMenuName("");
			dto.setMenuPath("");
			if(dto.getParentMenu() != null) {
				dto.setParentId(String.valueOf(dto.getParentMenu()));
			}
			
			mode = sideMenuService.findMenuByKey(dto);
			result.put("success", true);
			result.put("data", JsonTools.obj2Json(mode));
			return result;
			
		} catch (SideCustException e) {
			result.put("success", false);
			result.put("data", null);
			return result;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("data", null);
			return result;
		}
	}
	
	@DeleteMapping("delMenuService")
	public Map<String, Object> menuDelete(MenuDto dto){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			sideMenuService.delMenuByDto(dto);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch (SideCustException e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "删除失败");
		}
		return result;
	}

}
