package com.itranswarp.learnjava;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		String a = "12";
		String b = "d9";
		// TODO: 捕获异常并处理
		try {
		int c = stringToInt(a);
		int d = stringToInt(b);
		System.out.println(c * d);
		}
		catch(NumberFormatException e){
			System.out.println(e+"-------字符串非全为数字");
		}
		finally{
			System.out.println("End");
		}
	}

	static int stringToInt(String s) {
		return Integer.parseInt(s);
	}
}
