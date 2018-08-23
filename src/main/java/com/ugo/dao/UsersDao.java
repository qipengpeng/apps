/**
 * 
 */
package com.ugo.dao;

import com.ugo.entity.Users;

/**
 * @author sunshangfeng
 *
 */
public interface UsersDao {
	void addUser(Users users);
	
	/**
	 * 保存用户信息
	 * */
	int getUser(String openId);
	void updateUser(Users users);
	
	/**
	 * 获取用户ID
	 * */
	int getUserId(String openId);
}
