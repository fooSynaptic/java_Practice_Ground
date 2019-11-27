

import java.util.*;

/**
This program uses a set to print all unique words in `system.in`
author: hujiaxin
**/


public class setTest{
	public static void main(String[] args) {
		Set<String> words = new HashSet<>();
		long totalTime = 0;
		
		
		try (Scanner in = new Scanner(System.in)){
			while (in.hasNext()) {
				String word = in.next();
				long callTime = System.currentTimeMillis();
				words.add(word);
				
				callTime = System.currentTimeMillis() - callTime;
				totalTime += callTime;
				
			}
		}
		
		Iterator<String> iter = words.iterator();
		for (int i=1; i<20 && iter.hasNext(); i++)
			System.out.println(iter.next());
	
		System.out.println("...");
		System.out.println(words.size() + " distinct words." + totalTime + "Milliseconds. ");
		
		treeSetTest();
		
	}
	
	public static void treeSetTest() {
		SortedSet<String> sorter = new TreeSet<>();
		sorter.add("Bob");
		sorter.add("Amy");
		sorter.add("Carl");
		for (String s: sorter)
			System.out.println(s);
		
		
		
		
	}
}


