package com.ugo.test;


import java.util.Random;




public class Test6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String phonelist = "13522334959";
		String s = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
		String i= String.valueOf((new Random().nextInt(8999) + 1000));
		System.out.println((new Random().nextInt(8999) + 1000));
		System.out.println(System.currentTimeMillis());
	}

}
