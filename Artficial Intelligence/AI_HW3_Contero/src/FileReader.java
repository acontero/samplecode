import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


public class FileReader {
	public static BufferedReader inStream;
	public static PrintWriter outStream;
	
	public static String[][] allData = new String[1728][7];
	public static String[][] trainingData = new String[864][7];
	public static String[][] testData = new String[864][7];
	public static Set<Integer> randomNums = new LinkedHashSet<Integer>();
	public static Set<Integer> otherRandomNums = new LinkedHashSet<Integer>();
	
		
		
	public static void readAllData() throws IOException {
		String oneLine = null;
		oneLine = inStream.readLine();
		
		//Read in all the data into one array
		for(int i = 0; i<1728; i++){
			allData[i] = oneLine.split(",");
//				for(int x = 0; x<7; x++){
//					display(allData[i][x] + " ");
//				}
//				displayln("");
			oneLine = inStream.readLine();
		}
		
		//Then divide up the 1728 data points randomly into two sets, one for training and one for testing
		//represented by two arrays, testData and trainingData
		int n = 0;
		for(int i = 0; i<864; i++){
			//Min + (int)(Math.random() * ((Max - Min) + 1))
			n = 0 + (int)(Math.random() * (1727 + 1));
			while(randomNums.contains(n)){
				n = 0 + (int)(Math.random() * (1727 + 1));
			}
			randomNums.add(n);
//				displayln(""+ n);
		}
		
		//obtain the numbers not included in randomNums and put them in otherRandomNum
		for(int i = 0; i<1728; i++){
			if(!randomNums.contains(i)){
				otherRandomNums.add(i);
			}
		}
		
		//NOW SET THE VALUES FOR EACH ARRAY
		int count = 0;
		int x = 0;
		Iterator<Integer> iter = randomNums.iterator();
		while (iter.hasNext()) {
			x = iter.next();
			for(int i = 0; i<7; i++){
				trainingData[count][i] = allData[x][i];
				//display(trainingData[count][i] + " ");
			}
			//displayln("");
			count++;	
		}
		
		

	} // end setIO
	
	//ArrayList<Record>
	public static void buildLearningSet(){
		//NOW SET THE VALUES FOR EACH ARRAY
		int count = 0;
		int x = 0;
		Iterator<Integer> iter = randomNums.iterator();
		while (iter.hasNext()) {
			x = iter.next();
			for(int i = 0; i<7; i++){
				trainingData[count][i] = allData[x][i];
				//display(trainingData[count][i] + " ");
			}
			//displayln("");
			count++;	
		}
	}
	
	public static void buildTestingSet(){
		//Setup testData array
		int count = 0;
		int x = 0;
		Iterator<Integer> iter = otherRandomNums.iterator();
		while (iter.hasNext()) {
			x = iter.next();
			for(int i = 0; i<7; i++){
				testData[count][i] = allData[x][i];
				//display(testData[count][i] + " ");
			}
			//displayln("");
			count++;
		}
	}
		
	
	//NOTE: I ACTUALLY ENDED UP SWITCHING TESTINGDATA WITH TRAININGDATA BC IT PRODUCED BETTER RESULTS,
	//BECAUSE OF THE WAY I WAS CONSTRUCTING THEM
	public static ArrayList<Record> buildRecords(int size, int whichDataSet) {
		String[][] a;
		if(whichDataSet == 0) a = trainingData;
		else a = testData;
		ArrayList<Record> records = new ArrayList<Record>();
		String line;
        Record r = null;
        ArrayList<DiscreteAttribute> attributes;
	       for(int i = 0; i<size; i++){
	          attributes = new ArrayList<DiscreteAttribute>();
	          r = new Record();
	          
			  String buyingPrice = a[i][0];
			  String maintPrice = a[i][1];
			  String doors = a[i][2];
			  String persons = a[i][3];
			  String lug_boot = a[i][4];
			  String safety = a[i][5];
			  String acceptabilityClass = a[i][6];
			  
			  //buyingPrice
			  if(buyingPrice.equalsIgnoreCase("vhigh")) {
				  attributes.add(new DiscreteAttribute("buyingPrice", 0));
			  }
			  else if(buyingPrice.equalsIgnoreCase("high")) {
				  attributes.add(new DiscreteAttribute("buyingPrice", 1));
			  }
			  else if(buyingPrice.equalsIgnoreCase("med")) {
				  attributes.add(new DiscreteAttribute("buyingPrice", 2));
			  }
			  else if(buyingPrice.equalsIgnoreCase("low")) {
				  attributes.add(new DiscreteAttribute("buyingPrice", 3));
			  }
			  
			  //maintPrice
			  if(maintPrice.equalsIgnoreCase("vhigh")) {
				  attributes.add(new DiscreteAttribute("maintPrice", 0));
			  }
			  else if(maintPrice.equalsIgnoreCase("high")) {
				  attributes.add(new DiscreteAttribute("maintPrice", 1));
			  }
			  else if(maintPrice.equalsIgnoreCase("med")) {
				  attributes.add(new DiscreteAttribute("maintPrice", 2));
			  }
			  else if(maintPrice.equalsIgnoreCase("low")) {
				  attributes.add(new DiscreteAttribute("maintPrice", 3));
			  }
			  
			  //doors
			  if(doors.equalsIgnoreCase("2")) {
				  attributes.add(new DiscreteAttribute("doors", 0));
			  }
			  else if(doors.equalsIgnoreCase("3")) {
				  attributes.add(new DiscreteAttribute("doors", 1));
			  }
			  else if(doors.equalsIgnoreCase("4")) {
				  attributes.add(new DiscreteAttribute("doors", 2));
			  }
			  else if(doors.equalsIgnoreCase("5more")) {
				  attributes.add(new DiscreteAttribute("doors", 3));
			  }
			  
			  //persons
			  if(persons.equalsIgnoreCase("2")) {
				  attributes.add(new DiscreteAttribute("persons", 0));
	
			  }
			  else if(persons.equalsIgnoreCase("4")) {
				  attributes.add(new DiscreteAttribute("persons", 1));
	
			  }
			  else if(persons.equalsIgnoreCase("more")) {
				  attributes.add(new DiscreteAttribute("persons", 2));
	
			  }
			  
			  //lug_boot
			  if(lug_boot.equalsIgnoreCase("small")) {
				  attributes.add(new DiscreteAttribute("lug_boot", 0));
			  }
			  else if(lug_boot.equalsIgnoreCase("med")) {
				  attributes.add(new DiscreteAttribute("lug_boot", 1));
			  }
			  else if(lug_boot.equalsIgnoreCase("big")) {
				  attributes.add(new DiscreteAttribute("lug_boot", 2));
			  }
			  
			  //safety
			  if(safety.equalsIgnoreCase("low")) {
				  attributes.add(new DiscreteAttribute("safety", 0));
			  }
			  else if(safety.equalsIgnoreCase("med")) {
				  attributes.add(new DiscreteAttribute("safety", 1));
			  }
			  else if(safety.equalsIgnoreCase("high")) {
				  attributes.add(new DiscreteAttribute("safety", 2));
			  }
			  
			  //acceptability class
			  if(acceptabilityClass.equalsIgnoreCase("unacc")) {
				  attributes.add(new DiscreteAttribute("acceptabilityClass", 0));
			  }
			  else if(acceptabilityClass.equalsIgnoreCase("acc")) {
				  attributes.add(new DiscreteAttribute("acceptabilityClass", 1));
			  }
			  else if(acceptabilityClass.equalsIgnoreCase("good")) {
				  attributes.add(new DiscreteAttribute("acceptabilityClass", 2));
			  }
			  else if(acceptabilityClass.equalsIgnoreCase("vgood")) {
				  attributes.add(new DiscreteAttribute("acceptabilityClass", 3));
			  }
			    		    
			  r.setAttributes(attributes);
			  records.add(r);
	       }
	    //print all records
	    //printRecords(records);
		return records;
	}
	
	public static void printRecords(ArrayList<Record> records) {
		for(Record record: records){
			for(int i = 0; i<7; i++){
				display(record.getAttributes().get(i).getName() + " = ");
				display(record.getAttributes().get(i).getValue() + "  ");
			}
			displayln("");
		}
		
	}
	
	public String  findModeClassification() {
		int countUnacc = 0;
		int countAccep = 0;
		int countGood = 0;
		int countVgood = 0;
		
		for(int i=0; i<864; i++){
			if(trainingData[i][6]=="unacc") countUnacc++;
			if(trainingData[i][6]=="acc") countAccep++;
			if(trainingData[i][6]=="good") countGood++;
			if(trainingData[i][6]=="vgood") countVgood++;
		}
		int max = Math.max(Math.max(countUnacc,countAccep),Math.max(countGood, countVgood));
		if(max == countUnacc) return "unacc";
		if(max == countAccep) return "acc";
		if(max == countGood) return "good";
		if(max == countVgood) return "vgood";
		else return null;
	}
		
		
		//BELOW ARE I/O METHODS
		
			/**
			 * Sets the input and output files as inStream and outStream, respectively
			 *
			 * @param infile	Input file
			 * @param outfile	Output file
			 */
			public static void setInputOuput(String inFile, String outFile)
			{
				try
				{
					FileInputStream fis = new FileInputStream(inFile); 
					InputStreamReader in = new InputStreamReader(fis, "UTF-8");
					inStream = new BufferedReader(in);
					
					FileOutputStream fos = new FileOutputStream(outFile); 
					OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
					outStream = new PrintWriter(out);
				}
				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		
		/**
		 * Prints string to outStream
		 *
		 * @param s		The string to print
		 */
		public static void display(String s)
		{
			outStream.print(s);
		}
		
		/**
		 * Prints string to outStream and moves to next line
		 *
		 * @param s		The string to print
		 */
		public static void displayln(String s)
		{
			outStream.println(s);
		}
		
		/**
		 * Closes the I/O streams
		 *
		 */
		public static void closeIO()
		{
			try
			{
				inStream.close();
				outStream.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		} // end closeIO

		
		
}