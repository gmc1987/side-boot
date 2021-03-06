/**
 * 
 */
package com.side.service.auth.service.dataservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.side.service.auth.dao.IAccountRepository;
import com.side.service.auth.pojo.Account;
import com.side.service.auth.pojo.SideUser;

/**
 * @author gmc
 *
 */
@Service("accountService")
public class IAccountService {

	@Autowired
	private IAccountRepository accountRepository;
	
	public Account findByUserId(SideUser userId) {
		return accountRepository.findByUserId(userId);
	}
	
}
