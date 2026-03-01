package com.itranswarp.learnjava;

/**
 * Calculates the average of a 2D array representing student scores.
 * 
 * This program demonstrates:
 * - Working with multi-dimensional arrays
 * - Calculating averages across all elements
 * - Nested loop iteration
 * 
 * @author Student
 * @version 1.0
 */
public class Main {

	/**
	 * Main entry point of the program.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		// Student scores represented as a 2D array
		// Each row represents a student, each column represents a subject
		int[][] scores = { 
				{ 82, 90, 91 },  // Student 1
				{ 68, 72, 64 },  // Student 2
				{ 95, 91, 89 },  // Student 3
				{ 67, 52, 60 },  // Student 4
				{ 79, 81, 85 },  // Student 5
		};
		
		double average = calculateAverage(scores);
		
		// Verify the result with expected value
		if (Math.abs(average - 77.733333) < 0.000001) {
			System.out.println("测试成功 (Test Passed)");
		} else {
			System.out.println("测试失败 (Test Failed)");
		}
	}
	
	/**
	 * Calculates the average of all elements in a 2D integer array.
	 * 
	 * @param scores the 2D array of scores (must not be null or empty)
	 * @return the average value of all elements
	 * @throws IllegalArgumentException if scores is null or empty
	 */
	public static double calculateAverage(int[][] scores) {
		if (scores == null || scores.length == 0) {
			throw new IllegalArgumentException("Scores array must not be null or empty");
		}
		
		double sum = 0;
		int rowCount = scores.length;
		int colCount = scores[0].length;
		int totalElements = rowCount * colCount;
		
		// Sum all elements using nested loops
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				sum += scores[i][j];
			}
		}
		
		double average = sum / totalElements;
		System.out.println("Calculated average: " + average);
		
		return average;
	}
}
