package com.itranswarp.learnjava;

import java.util.Scanner;

/**
 * switch实现石头/剪子/布并判断胜负
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("please choice:");
		System.out.println(" 1: Rock");
		System.out.println(" 2: Scissors");
		System.out.println(" 3: Paper");
		// 用户输入:
		int choice = (int)(5 + Math.random()*2);
		System.out.println(choice);
		// 计算机随机数 1, 2, 3:
		int random = (int)(1 + Math.random()*2);
		System.out.println(random);
		switch (choice) {
		// TODO:
		case 1:
			System.out.println(random==3);
			break;
		case 2:
			System.out.println(random==1);
			break;
		case 3:
			System.out.println(random==2);
			break;
		default:
			System.out.println("No choice");
		}
	}

}
