/**
 * 
 */
package com.side.users.IService;

import java.util.List;

import com.side.basic.IbaseService.ISideBasicService;
import com.side.basic.common.utils.PageMode;
import com.side.users.dto.SideUserDto;
import com.side.users.pojo.SideUser;

/**
 * @author gmc
 *
 */
public interface ISideUserService extends ISideBasicService<SideUser>{

	/**
	 * 根据用户编码查询用户信息
	 * @param code
	 * @return
	 */
	public SideUser findSideUserByCode(String code);
	
	/**
	 * 查询所有用户
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List<SideUser> findAllUser(SideUserDto dto) throws Exception;
	
	/**
	 * 分页查询用户信息
	 * @param dto
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageMode<SideUser> findUserForPages(SideUserDto dto, int pageNumber, int pageSize) throws Exception;
	
	/**
	 * 用户编辑(新增/修改)
	 * @param user
	 * @throws Exception
	 */
	public void userEditer(SideUser user) throws Exception;
	
	/**
	 * 根据SQL查询系统用户
	 * @param dto 查询实体
	 * @param pageNumber 当前记录
	 * @param pageSize	当前页数
	 * @return
	 * @throws Exception
	 */
	public PageMode<SideUserDto> findSystemUserBySQL(SideUserDto dto, int pageNumber, int pageSize) throws Exception;
	
	/**
	 * 用户注册
	 * @param user
	 * @throws Exception
	 */
	public void userRegist(SideUser user) throws Exception;
	
}
