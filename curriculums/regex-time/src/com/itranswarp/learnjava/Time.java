package com.itranswarp.learnjava;
import java.util.regex.*;

/**
 * Learn Java from https://www.liaoxuefeng.com/
 * 
 * @author liaoxuefeng
 */
public class Time {

	/**
	 * 从"21:05:19"中提取时，分，秒，否则抛出IllegalArgumentException
	 */
	public static int[] parseTime(String s) {
		// FIXME:
		if (s==null) throw new IllegalArgumentException();
		if (s.length()==0) throw new IllegalArgumentException();
		
		int ans[] = new int[3];
		//Pattern pattern = Pattern.compile("(//d{2}):(//d{2}):(//d{2})");
		Pattern pattern = Pattern.compile("([0-1][0-9]|2[0-4]):([0-6][0-9]):([0-6][0-9])");
		Matcher matcher = pattern.matcher(s);
		
		if (matcher.matches()) {
			ans[0] = Integer.parseInt(matcher.group(1));
			ans[1] = Integer.parseInt(matcher.group(2));
			ans[2] = Integer.parseInt(matcher.group(3));
			System.out.println(ans.toString());
			//return {hour, min, sec};

			
		}
		else
			throw new IllegalArgumentException();


			
		
		return ans;
	}

}
