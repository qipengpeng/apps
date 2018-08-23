package com.ugo.test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Test2 {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		List<String> cities = Arrays.asList("Milan", 
                "London", 
                "New York", 
	                "San Francisco");
			String citiesCommaSeparated = String.join(",", cities);
			System.out.println(cities);
			System.out.println(citiesCommaSeparated);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			
		     String json = "{\"value\":\"121313\",\"Data\":{\"id\":\"12\",\"name\":\"name\",\"value\":\"vl\" } } ";
		     System.out.println(json);
	}

}
