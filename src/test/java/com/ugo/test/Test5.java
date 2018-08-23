/**
 * 
 */
package com.ugo.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.ugo.dto.MD5Util;
import com.ugo.service.RefundService;

/**
 * @author sunshangfeng
 *
 */
public class Test5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = RefundService.class.getClassLoader().getResource("") .getPath(); 
        String removeStr = "/WEB-INF/classes/WEB-INF";
        String removeStr1 = "/target/test-classes";
        
        String replacePath = path.replace(removeStr1, "");
        System.out.println("自动退款--当前路径-------"+replacePath);
        
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format( now );
        System.out.println("当前日期--"+format);
        try {
			String versionTime = String.valueOf(dateFormat.parse(format).getTime()/1000);
			System.out.println("时间戳"+versionTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        BigDecimal scale = new BigDecimal(744.5).setScale(0, BigDecimal.ROUND_HALF_UP);
        int i =Integer.parseInt(scale+"");
        System.out.println(i-1);
        
        String a = "鱼香肉丝.jpg";
       String b =  UUID.randomUUID().toString().replaceAll("-","")+a.substring(a.lastIndexOf("."));
       System.out.println(b);
       String url =  "/ugo/apps/apks/main-release-v6-2018-07-26_release.apk";
       String md5sum = MD5Util.md5sum(url);
       System.out.println(md5sum);
        
	}

}
