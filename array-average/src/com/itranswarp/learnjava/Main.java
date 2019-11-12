package com.itranswarp.learnjava;

/**
 * 二维数组
 */
public class Main {
	public static void main(String[] args) {
		// 用二维数组表示的学生成绩:
		int[][] scores = { //
				{ 82, 90, 91 }, //
				{ 68, 72, 64 }, //
				{ 95, 91, 89 }, //
				{ 67, 52, 60 }, //
				{ 79, 81, 85 }, //
		};
		// TODO:
		double average = getAverage(scores);
		if (Math.abs(average - 77.733333) < 0.000001) {
			System.out.println("测试成功");
		} else {
			System.out.println("测试失败");
		}
	}
	
	
	
	public static double getAverage(int [][] scores) {
		double average = 0;
		int r = scores.length;
		int c = scores[0].length;
		
		for(int i = 0;i<r;i++)
			for (int j=0;j<c;j++)
				average += scores[i][j];
		
		average /= r*c;
		System.out.println(average);
		return average;
		
		
	}
	
}
