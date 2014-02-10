abstract public class Attribute {
	public String name;
	public int value;
	public boolean isUnknown;
	private double surrogate;
	
	public Attribute(String name, int value) {
		this.name = name;
		this.value = value;
		isUnknown = false;
	}
	
	public Attribute(String name, String value) {
		this.name = name;
		try {
			this.value = Integer.valueOf(value);
			this.isUnknown = false;

		}
		catch(NumberFormatException nfe) {
			this.value = -1;
			this.isUnknown = true;
		}
		
		surrogate = -1;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setUnknown(boolean isUnknown) {
		this.isUnknown = isUnknown;
	}

	public boolean isUnknown() {
		return isUnknown;
	}
}