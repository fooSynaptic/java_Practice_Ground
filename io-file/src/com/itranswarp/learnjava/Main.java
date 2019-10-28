package com.itranswarp.learnjava;

import java.io.File;
import java.io.IOException;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Main {

	public static void main(String[] args) throws IOException {
		File currentDir = new File(".");
		listDir(currentDir.getCanonicalFile(), " ");
	}

	static void listDir(File dir, String ind2 ) {
		// TODO: 递归打印所有文件和子文件夹的内容
		String ind = ind2  + ind2  + ind2 + ind2 ;
		File[] fs = dir.listFiles();
		if (fs != null) {
			for (File f : fs) {
				System.out.println(ind2 + f.getName());
				if (f.isDirectory()) {
					System.out.println(ind2 + f.getName() + "/");
					listDir(f, ind);
				}
				
				
			}
		}
	}
}
