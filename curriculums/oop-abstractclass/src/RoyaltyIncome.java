/**
 * Represents royalty income (e.g., from books, patents) with tax calculation.
 * 
 * Royalty income typically has different tax treatment compared to salary.
 * This implementation uses a flat 20% tax rate.
 * 
 * @author Student
 * @version 1.0
 */
public class RoyaltyIncome extends Income {

	/** The gross royalty income amount */
	private final double income;
	
	/** Tax rate for royalty income */
	private static final double TAX_RATE = 0.2;

	/**
	 * Constructs a RoyaltyIncome instance.
	 * 
	 * @param income the gross royalty amount
	 */
	public RoyaltyIncome(double income) {
		this.income = income;
	}

	/**
	 * Calculates tax based on royalty income.
	 * 
	 * @return the calculated tax amount
	 */
	@Override
	public double getTax() {
		return this.income * TAX_RATE;
	}
	
	/**
	 * Gets the gross royalty income.
	 * 
	 * @return the royalty amount
	 */
	@Override
	public double getIncomeAmount() {
		return this.income;
	}
}
