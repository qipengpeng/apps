/**
 * 
 */
package com.ugo.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ugo.entity.UsersAdmin;
import com.ugo.service.UsersAdminService;

import ch.qos.logback.classic.Logger;

/**
 * @author sunshangfeng
 *
 */
@Controller
public class UsersAdminController {
	
		private static final Logger logger = (Logger) LoggerFactory.getLogger(UsersAdminController.class);
		@Autowired
		private UsersAdminService usersAdminService;
		
		//初始化启动一次
		//@PostConstruct
		
		/**
		 * @author sunshangfeng
		 *获取商品信息
		 */
		@RequestMapping("/regist")  
	    public String regist(UsersAdmin user,Model model){  
	          
	        System.out.println("用户注册："+user.getUserName()+user.getPassword());  
	          
	        usersAdminService.regist(user);  
	          
	        model.addAttribute("msg", "注册成功");    
	        //注册成功后跳转success.jsp页面  
	        return "success";  
	    }  
		
		@RequestMapping("/login")  
	    public String login(String userName,String password,Model model,HttpServletRequest request){  
	          
	        System.out.println("用户登录："+userName+":"+password);  
	        logger.info("=============登录操作============");
	        UsersAdmin user = usersAdminService.login(userName,password);	         
	        if(user != null){
	        	HttpSession session = request.getSession();
	        	session.setAttribute("userId", user.getId()+"");
	        	request.setAttribute("msg", "登录成功");  
	        }else {
	        	request.setAttribute("msg", "账号或密码错误");  
	        	return "/login";
	        }
	        return "redirect:/PC/getProductsList";
	    }  
		
		
		@RequestMapping(value = "/outLogin")  
	    public  String outLogin(HttpSession session){  
			session.removeAttribute("userId");
			session.invalidate();
			logger.info("用户退出会话域,登录失效");
	        return "/login";  
	    }  
}
