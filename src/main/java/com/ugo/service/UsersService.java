/**
 * 
 */
package com.ugo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ugo.dao.UsersDao;
import com.ugo.entity.Users;

/**
 * @author sunshangfeng
 *
 */
@Service
public class UsersService {
	@Autowired
	private UsersDao usersDao;
	@Transactional
	public void addUser(Users users) {
		int user = usersDao.getUser(users.getOpenId());
		if(user==0) {
			usersDao.addUser(users);
		}else {
			usersDao.updateUser(users);
		}
	}
	@Transactional
	public void updateUser(Users users) {
		usersDao.updateUser(users);
	}
}
