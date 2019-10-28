
public class SalaryIncome extends Income {
	protected double income;
	// TODO
	
	
	public SalaryIncome(int income) {
		this.income = income;
	}



	@Override
	public double getTax() {
		return (this.income-5000.)*0.1;
	}

	
}