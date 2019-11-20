
/**
 * 稿费收入税率是20%
 */
public class RoyaltyIncome implements Income{
	protected double income;
	// TODO
	
	public RoyaltyIncome(int income) {
		this.income = income;
	}
	
	public double getTax() {
		return this.income * 0.2;
	}

}

