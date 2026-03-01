/**
 * Abstract base class representing income from various sources.
 * 
 * This class defines the common interface for all income types,
 * requiring each concrete subclass to implement its own tax calculation.
 * 
 * @author Student
 * @version 1.0
 */
public abstract class Income {

	/**
	 * Calculates the tax amount for this income.
	 * 
	 * @return the calculated tax amount
	 */
	public abstract double getTax();
	
	/**
	 * Gets the income amount before tax.
	 * 
	 * @return the income amount
	 */
	public abstract double getIncomeAmount();
}
