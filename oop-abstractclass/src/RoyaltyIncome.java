
/**
 * 稿费收入税率是20%
 */
public class RoyaltyIncome extends Income {
	protected double income;
	// TODO

	public RoyaltyIncome(int income) {
		this.income = (double)income;
	}
	
	@Override
	public double getTax() {
		return (double)this.income * 0.2;
	}
	
}

