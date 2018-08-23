/**
 * 
 */
package com.ugo.service;


import org.springframework.stereotype.Service;

/**
 * @author sunshangfeng
 *
 */
@Service
public class RefundService {
	
	public String getPath() {
		 //获得当前目录
        String path = RefundService.class.getClassLoader().getResource("") .getPath(); 
        String removeStr = "/WEB-INF/classes";
        String replacePath = path.replace(removeStr, "");
        System.out.println("自动退款--当前路径-------"+replacePath);
        return replacePath;
	}

}
