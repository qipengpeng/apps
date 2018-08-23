package com.ugo.test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Test {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		 String URL="jdbc:mysql://127.0.0.1:3306/ug_test?useUnicode=true&amp;characterEncoding=utf-8";
		          String USER="root";
		          String PASSWORD="root";
		        //1.加载驱动程序
		         Class.forName("com.mysql.jdbc.Driver");
		        //2.获得数据库链接
		         Connection conn=(Connection) DriverManager.getConnection(URL, USER, PASSWORD);
		         //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
		          Statement st=(Statement) conn.createStatement();
		          ResultSet rs=st.executeQuery("select * from admin_users");
		          //4.处理数据库的返回结果(使用ResultSet类)
		          while(rs.next()){
		              System.out.println(rs.getString("name")+" "
		                            +rs.getString("password"));
		          }
		          
		          //关闭资源
		          rs.close();
		          st.close();
		          conn.close();
	}

}
