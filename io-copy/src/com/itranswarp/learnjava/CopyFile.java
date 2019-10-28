package com.itranswarp.learnjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class CopyFile {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Usage:\n  java CopyFile.java <source> <target>");
			System.exit(1);
		}
		copy(args[0], args[1]);
	}
	
	
	
	public static String readAsString(InputStream input) throws IOException {
		int n;
		StringBuilder sb = new StringBuilder();
		while ((n = input.read())!=-1) {
			sb.append((char) n);
		}
		return sb.toString();
		}
	

	static void copy(String source, String target) throws IOException {
		// 友情提示：测试时请使用无关紧要的文件
		// TODO:
		// read
		String s;
		try (InputStream input = new FileInputStream(source)) {
            s = readAsString(input);
            System.out.println("read ok!");
	}
		
		
		try (OutputStream output = new FileOutputStream(target)) {
	        output.write(s.getBytes("UTF-8")); 
	    } // 编译器在此自动为我们写入finally并调用close()
}
}
