/**
 * 
 */
package com.side.landlord.IService;

import com.side.basic.IbaseService.ISideBasicService;
import com.side.basic.common.utils.PageMode;
import com.side.landlord.dto.LandlordDto;
import com.side.landlord.pojo.Landlord;

/**
 * @author gmc
 * @see 房东服务接口
 */
public interface ILandlordService extends ISideBasicService<Landlord> {

	/**
	 * 根据条件查询房东列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageMode<Landlord> getList(LandlordDto dto) throws Exception;
	
	/**
	 * 编辑房东信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean editLandlord(Landlord landlord) throws Exception;
	
}
