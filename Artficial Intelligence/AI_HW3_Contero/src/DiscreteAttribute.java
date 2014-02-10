public class DiscreteAttribute extends Attribute {
	//Buying Price
	public static final int vhighBuy = 0;
	public static final int highBuy = 1;
	public static final int medBuy = 2;
	public static final int lowBuy = 3;
	
	//Maint Price
	public static final int vhighMaint = 0;
	public static final int highMaint = 1;
	public static final int medMaint = 2;
	public static final int lowMaint = 3;
	
	//Doors
	public static final int twoDoors = 0;
	public static final int threeDoors = 1;
	public static final int fourDoors = 2;
	public static final int fiveMoreDoors = 3;
	
	//persons
	public static final int twoPerson = 0;
	public static final int fourPerson = 1;
	public static final int morePerson = 2;
	
	//lug_boot
	public static final int smallLug = 0;
	public static final int medLug = 1;
	public static final int bigLug = 2;
	
	//safety
	public static final int lowSafety = 0;
	public static final int medSafety = 1;
	public static final int highSafety = 2;
	
	//acceptability class
	public static final int unacc = 0;
	public static final int accep = 1;
	public static final int good = 2;
	public static final int vgood = 3;
	
	enum buyingPrice {
		vhighBuy,
		highBuy,
		medBuy,
		lowBuy
	}
	
	enum maintPrice {
		vhighMaint,
		highMaint,
		medMaint,
		lowMaint
	}
	
	enum doors {
		twoDoors,
		threeDoors,
		fourDoors,
		fiveMoreDoors,
	}
	
	enum persons {
		twoPerson,
		fourPerson,
		morePerson
	}
	
	enum lug_boot {
		smallLug,
		medLug,
		bigLug
	}
	
	enum safety {
		lowSafety,
		medSafety,
		highSafety
	}
	
	enum acceptabilityClass {
		unacc,
		acc,
		good,
		vgood
	}

	public DiscreteAttribute(String name, int value) {
		super(name, value);
	}

	public DiscreteAttribute(String name, String value) {
		super(name, value);
	}
}