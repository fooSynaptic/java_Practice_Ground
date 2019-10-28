
public class SalaryIncome implements Income{
	protected double income;

	// TODO
	public SalaryIncome(int imcome) {
		this.income = income;
	}

	
	public double getTax() {
		return (this.income-5000.) *0.1;
	}
}
