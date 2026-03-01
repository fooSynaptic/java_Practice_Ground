/**
 * Represents salary income with tax calculation.
 * 
 * Tax calculation formula: (income - 5000) * 0.1
 * This assumes a 5000 base deduction and 10% tax rate.
 * 
 * @author Student
 * @version 1.0
 */
public class SalaryIncome extends Income {

	/** The gross salary income amount */
	private final double income;
	
	/** Standard deduction amount for salary income */
	private static final double BASE_DEDUCTION = 5000.0;
	
	/** Tax rate applied to taxable income */
	private static final double TAX_RATE = 0.1;

	/**
	 * Constructs a SalaryIncome instance.
	 * 
	 * @param income the gross salary amount
	 */
	public SalaryIncome(double income) {
		this.income = income;
	}

	/**
	 * Calculates tax based on salary income.
	 * 
	 * @return the calculated tax amount (may be 0 if income <= deduction)
	 */
	@Override
	public double getTax() {
		double taxableIncome = Math.max(0, this.income - BASE_DEDUCTION);
		return taxableIncome * TAX_RATE;
	}
	
	/**
	 * Gets the gross salary income.
	 * 
	 * @return the salary amount
	 */
	@Override
	public double getIncomeAmount() {
		return this.income;
	}
}
