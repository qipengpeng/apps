package com.ugo.test;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String val = "";  
        Random random = new Random();        
        //length为几位密码 
        for(int i = 0; i < 10; i++) {          
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        System.out.println(val);
        int i= (int) ((Math.random()*9+1)*1000);
        System.out.println((int)((Math.random()*9+1)*1000)); 
        Random rad = new Random();
        int nextInt = rad.nextInt(6);
        System.out.println(nextInt);
	}

}
