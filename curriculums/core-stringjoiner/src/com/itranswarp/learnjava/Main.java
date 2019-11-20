package com.itranswarp.learnjava;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringJoiner;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) {
		String[] fields = { "name", "position", "salary" };
		String table = "employee";
		String select = buildSelectSql(table, fields);
		System.out.println(select);
		System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "测试成功" : "测试失败");
	
		BigInteger bi = new BigInteger("1234567890");
		System.out.println(bi.pow(5)); 
		
		BigDecimal d1 = new BigDecimal("123.45");
		BigDecimal d2 = new BigDecimal("123.4500");
		BigDecimal d3 = new BigDecimal("1234500");
		System.out.println(d1.scale()); // 2,两位小数
		System.out.println(d2.scale()); // 4
		System.out.println(d3.scale()); // 0
		Math.abs(-100); // 100
		Math.abs(-7.8); // 7.8
	}

	static String buildSelectSql(String table, String[] fields) {
		// TODO:
		StringJoiner ans = new StringJoiner(", ");
		String res = "SELECT ";
		for (String x: fields)
			ans.add(x);
		
		res += ans.toString();
		res += " FROM employee";
		return res;
	}

}




