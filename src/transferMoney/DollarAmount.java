package transferMoney;

public class DollarAmount implements Comparable<DollarAmount>{

	private double value;
	
	public DollarAmount(int value) {
		
		this.value = value;
	}

	@Override
	public int compareTo(DollarAmount o) {
		
		return value > o.getValue() ? 1 : (value < o.getValue() ? -1 : 0);
	}

	public double getValue() {
		return value;
	}
	
	public void setValue(double d) {
		
		this.value += d;
	}
}
