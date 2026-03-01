/**
 * Demonstrates polymorphism with abstract classes.
 * 
 * This program calculates total tax for a person with multiple
 * income sources (salary and royalties) using the Income abstraction.
 * 
 * @author Student
 * @version 1.0
 */
public class Main {

	/**
	 * Main entry point.
	 * 
	 * @param args command line arguments (not used)
	 */
	public static void main(String[] args) {
		// Create income sources for a person with both salary and royalties
		Income[] incomes = new Income[] {
			new SalaryIncome(7500),    // Monthly salary
			new RoyaltyIncome(12000)   // Book royalties
		};
		
		double totalTax = calculateTotalTax(incomes);
		
		System.out.println("Total tax to pay: " + totalTax);
	}
	
	/**
	 * Calculates total tax across multiple income sources.
	 * 
	 * This method demonstrates polymorphism - it works with any
	 * subclass of Income without knowing the specific type.
	 * 
	 * @param incomes array of Income objects
	 * @return total tax amount
	 */
	public static double calculateTotalTax(Income[] incomes) {
		double total = 0.0;
		
		for (Income income : incomes) {
			total += income.getTax();
		}
		
		return total;
	}
}
