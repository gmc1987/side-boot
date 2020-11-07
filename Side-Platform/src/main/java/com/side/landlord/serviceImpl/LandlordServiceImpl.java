/**
 * 
 */
package com.side.landlord.serviceImpl;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.side.basic.SideException.SideCustException;
import com.side.basic.baseServiceImpl.SideBasicServiceImpl;
import com.side.basic.common.utils.DetachedCriteriaTS;
import com.side.basic.common.utils.PageMode;
import com.side.landlord.IDao.ILandlordDao;
import com.side.landlord.IService.ILandlordService;
import com.side.landlord.dto.LandlordDto;
import com.side.landlord.pojo.Landlord;

/**
 * @author gmc
 *
 */
@Service("landlordService")
public class LandlordServiceImpl extends SideBasicServiceImpl<Landlord> implements ILandlordService {

	@Autowired
	private ILandlordDao landLordDao;

	@Override
	public PageMode<Landlord> getList(LandlordDto dto) throws Exception {
		// TODO Auto-generated method stub
		PageMode<Landlord> result = null;
		DetachedCriteriaTS<Landlord> criteria = new DetachedCriteriaTS<Landlord>(Landlord.class);
		
		if(!StringUtils.isAllEmpty(dto.getSearchKey())) {
			criteria.add(Restrictions.or(Restrictions.like("landlordCode", dto.getSearchKey(), MatchMode.ANYWHERE), 
					 Restrictions.like("idCard", dto.getSearchKey(), MatchMode.ANYWHERE),
					 Restrictions.like("user.userName", dto.getSearchKey(), MatchMode.ANYWHERE),
					 Restrictions.like("user.userCode", dto.getSearchKey(), MatchMode.ANYWHERE)
					 ));
		} 
		criteria.add(Restrictions.eq("auditFlag", dto.getAuditFlag()));
		
		result = landLordDao.findForPage(criteria, dto.getPageNumber(), dto.getPageSize());
		
		return result;
	}

	@Override
	@Transactional
	public boolean editLandlord(Landlord landlord) throws Exception {
		boolean editFlag = false;
		
		if(ObjectUtils.isEmpty(landlord)) {
			throw new SideCustException("参数异常,[landlord]对象不可为空");
		} else {
			landLordDao.saveOrUpdate(landlord);
			editFlag = true;
		}
		
		return editFlag;
	}
}
