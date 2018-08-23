/**
 * 
 */
package com.ugo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.UsersAdminDao;
import com.ugo.entity.UsersAdmin;

/**
 * @author sunshangfeng
 *
 */
@Service
public class UsersAdminService {
		@Autowired
		private UsersAdminDao usersAdminDao;
		
		@Transactional
		public void regist(UsersAdmin user) {  
	        // TODO Auto-generated method stub  
	        usersAdminDao.addUser(user);  
	    } 
		
		public UsersAdmin login(String userName, String password) {  
	        // TODO Auto-generated method stub  
	        UsersAdmin nameAndPwd = usersAdminDao.findUserByNameAndPwd(userName,password);
			return nameAndPwd; 
	    } 
		
}
