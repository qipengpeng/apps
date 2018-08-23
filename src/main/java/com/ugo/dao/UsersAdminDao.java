/**
 * 
 */
package com.ugo.dao;

import org.apache.ibatis.annotations.Param;

import com.ugo.entity.UsersAdmin;

/**
 * @author sunshangfeng
 *后台登录接口
 */
public interface UsersAdminDao {
	
		//添加用户  
		void  addUser(UsersAdmin usersAdmin);
		//根据用户名和密码查询用户  
	    //注解的两个参数会自动封装成map集合，括号内即为键 
		UsersAdmin findUserByNameAndPwd(@Param("userName")String userName, @Param("password")String password);  
		int getId(UsersAdmin user);
		
		int getUserCount(UsersAdmin user);
}
