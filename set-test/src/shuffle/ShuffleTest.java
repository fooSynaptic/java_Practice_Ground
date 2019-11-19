package shuffle;

import java.util.*;

public class ShuffleTest{
	public static void main(String[] args){
		List<Integer> numbers = new ArrayList<>();
		for(int i = 1; i< 50; i++)
			numbers.add(i);

		Collections.shuffle(numbers);
		
		int element = 999999;
		int idx = Collections.binarySearch(numbers, element);
		
		System.out.println(idx);
		System.out.println(-idx-1);
		
		if (idx<0)
			numbers.add(-idx-1, element);
		 
		
		List<Integer> winningCombination = numbers.subList(50-10, 50);
		Collections.sort(winningCombination);
		System.out.println(winningCombination);
	}
}