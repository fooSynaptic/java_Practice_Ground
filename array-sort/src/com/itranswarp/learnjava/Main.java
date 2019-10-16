package com.itranswarp.learnjava;

import java.util.Arrays;

/**
 * 降序排序
 */
public class Main {

	
	
	public static void main(String[] args) {
		int[] ns = { 28, 12, 89, 73, 65, 18, 96, 50, 8, 36 };
		// 排序前:
		System.out.println(Arrays.toString(ns));
		int n = ns.length;
		
		//boolean sorted = true;
		for (boolean sorted = false; sorted = !sorted; n--) {
			for (int j=0;j<n-1;j++) {
				if (ns[j] > ns[j+1]) {
					int tmp = ns[j];
					ns[j] = ns[j+1];
					ns[j+1] = tmp;
					sorted = false;
				}
				
			}
		}
		
		
		
		int m = ns.length;
		for (int i = 0; i< m/2; i++) {
			int tmp = ns[i];
			ns[i] = ns[m-i-1];
			ns[m-i-1] = tmp;
		}
		
		System.out.println(Arrays.toString(ns));
		if (Arrays.toString(ns).equals("[96, 89, 73, 65, 50, 36, 28, 18, 12, 8]")) {
			System.out.println("测试成功");
		} else {
			System.out.println("测试失败");
		}
	}
}
