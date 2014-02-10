import java.io.*;
import java.util.*;

/**
 * This program implements the Decision Tree Learning Algorithm (DTL).
 * It does this with the help of calculating entropy and gain as well as the mode classification in the data pool.
 * 
 * @author Angelica Contero
 * @version HW3 - FALL 2013
 * @since  10/4/13
 */

public class Main {
	public static FileReader fr = new FileReader();
	public static ArrayList<String> attrMap;
	public static ArrayList<Integer> usedAttributes = new ArrayList<Integer>();
	public static ArrayList<Record> examples20 = new ArrayList<Record>();
	public static ArrayList<Record> examples40 = new ArrayList<Record>();
	public static ArrayList<Record> examples60 = new ArrayList<Record>();
	public static ArrayList<Record> examples80 = new ArrayList<Record>();
	public static ArrayList<Record> examplesTest = new ArrayList<Record>();
	
 	
	public static void main(String[] argv) throws IOException{
		FileReader.setInputOuput(argv[0], argv[1]);
		FileReader.readAllData();
		FileReader.buildLearningSet();
		FileReader.buildTestingSet();
		
		populateAttrMap();
		//build records from the training data array
		examples20 = FileReader.buildRecords(173,0);
		examples40 = FileReader.buildRecords(346,0);
		examples60 = FileReader.buildRecords(518,0);
		examples80 = FileReader.buildRecords(691,0);
		examplesTest = FileReader.buildRecords(864,1);
		
//		System.out.println("size of examples20="+examples20.size());
//		System.out.println("size of examples20="+examples40.size());
//		System.out.println("size of examples20="+examples60.size());
//		System.out.println("size of examples20="+examples80.size());
//		System.out.println("size of examples20="+examplesTest.size());
		
		if(examples20.size()==0){
			System.out.println("ErrorOccurred");
			return;
		}
		
		double accuracy1 = runAlgorithm(examples20,examplesTest);
		double accuracy2 = runAlgorithm(examples40,examplesTest);
		double accuracy3 = runAlgorithm(examples60,examplesTest);
		double accuracy4 = runAlgorithm(examples80,examplesTest);
		
		displayln("Accuracy for 20% = " + accuracy1);
		displayln("Accuracy for 40% = " + accuracy2);
		displayln("Accuracy for 60% = " + accuracy3);
		displayln("Accuracy for 80% = " + accuracy4);
		
		fr.closeIO();
	}
	
	public static double runAlgorithm(ArrayList<Record> examples, ArrayList<Record> testingData) {
		Tree t = new Tree();
		String x = fr.findModeClassification();
		//System.out.println(x); //print mode to console

		DiscreteAttribute test = t.mode(examples);
		System.out.println("Mode is " + test.getName());
		
		Node root = new Node();
		for(Record example: examples){
			root.getData().add(example);
		}
		root.getTestAttribute().setValue(999);
		
		//CLEAR THE SLATE BEFORE RUNNING DTL ALGORITHM
		usedAttributes = new ArrayList<Integer>();
		Tree.DTL(examples, attrMap, root);
		
		
		int count = 0;
		double result = 0;
		boolean prediction;
		for(int i = 0; i<testingData.size(); i++){
			//GET PREDICTION FOR TEST RECORD i
			ArrayList<Record> testRecord = new ArrayList<Record>();
			Record p = testingData.get(i);
			testRecord.add(p);
			FileReader.printRecords(testRecord);
			prediction = traverseTree(testingData.get(i),root);
			if(prediction==true) count++;
			displayln("Prediction was: " + prediction);
			displayln("Count is now = " + count);
		}
		displayln("TOTAL COUNT=" + count);
		result = (count/864.0);
		displayln("Count/864="+ result);
		return result;
		//printTree(root);
		
	}

	public static void populateAttrMap() {
		attrMap = new ArrayList<String>();
		attrMap.add("buyingPrice");
		attrMap.add("maintPrice");
		attrMap.add("doors");
		attrMap.add("persons");
		attrMap.add("lug_boot");
		attrMap.add("safety");
//		attrMap.add("accessabilityClass");
	}
	
    private static int temp = 0;
    private static String answer = "";
    private static String currentAtt = "";
  
    public static boolean traverseTree(Record r, Node root) {
    		if(getLeafNames(6,root.getTestAttribute().getValue())!=null)
            answer = getLeafNames(6,root.getTestAttribute().getValue());
      		
     		if(root.children != null) {
     			double nodeValue = 0;
     			for(int i = 0; i < r.getAttributes().size(); i++)
     				if(r.getAttributes().get(i).getName().equalsIgnoreCase(root.getTestAttribute().getName())) {
     					nodeValue = r.getAttributes().get(i).getValue();
     					break;
     				}

     			traverseTree(r, root.children[(int) nodeValue]);
     		}

		while(temp < 1){
			System.out.println("Classification Prediction: ");
			if(root.getTestAttribute().getValue() == 0) {
				System.out.println("unacc");
				answer = "unacc";
			}
			else if(root.getTestAttribute().getValue() == 1) {
				System.out.println("acc");
				answer = "acc";
			}
			else if(root.getTestAttribute().getValue() == 2) {
				System.out.println("good");
				answer = "good";
			}
			else if(root.getTestAttribute().getValue() == 3) {
				System.out.println("vgood");
				answer = "vgood";
			}
			else{
				System.out.println("nothing");
				answer = "";
			}
			temp++;
		}
		
		String correctAnswer = getLeafNames(6, r.getAttributes().get(6).getValue());
		displayln("Answer is supposed to be: "+correctAnswer);
		displayln("Answer is actually: " + answer);
		
		if(answer.equalsIgnoreCase(correctAnswer)){
			return true;
		}
		else return false;
	}
	
	public static void printTree(Node root) {
		DiscreteAttribute currentAttribute = root.getTestAttribute();
		if(root.getChildren()==null)return;
				for(int i = 0; i < root.getChildren().length; i++){
					System.out.print("Attribute " + currentAttribute.getName() + " with Value=" + currentAttribute.getValue());
					if(root.getParent()!=null) System.out.print(" and Parent is " + root.getParent().getTestAttribute().getName());
					else System.out.print(" and Parent is RootNode");
					System.out.println("");
					printTree(root.children[i]);
					//System.out.println("Attribute Value " + currentAttribute.getName());
				}
				return;
		}
	
	public static boolean isAttributeUsed(int attribute) {
		if(usedAttributes.contains(attribute)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int setSize(String set) {
		if(set.equalsIgnoreCase("buyingPrice")) {
			return 4;
		}
		else if(set.equalsIgnoreCase("maintPrice")) {
			return 4;
		}
		else if(set.equalsIgnoreCase("doors")) {
			return 4;
		}
		else if(set.equalsIgnoreCase("persons")) {
			return 3;
		}
		else if(set.equalsIgnoreCase("lug_boot")) {
			return 3;
		}
		else if(set.equalsIgnoreCase("safety")) {
			return 3;
		}
		else if(set.equalsIgnoreCase("acceptabilityClass")) {
			return 4;
		}
		return 0;
	}
	
	public static int setSizeByIndex(int a) {
		if(a==0) {
			return 4;
		}
		else if(a==1) {
			return 4;
		}
		else if(a==2) {
			return 4;
		}
		else if(a==3) {
			return 3;
		}
		else if(a==4) {
			return 3;
		}
		else if(a==5) {
			return 3;
		}
		else if(a==6) {
			return 4;
		}
		return 0;
	}
	
	public static String getLeafNames(int attributeNum, int valueNum) {
		//buyingPrice
		if(attributeNum == 0) { 
			if(valueNum == 0) {
				return "vhighBuy";
			}
			else if(valueNum == 1) {
				return "highBuy";
			}
			else if(valueNum == 2) {
				return "medBuy";
			}
			else if(valueNum == 3) {
				return "lowBuy";
			}
		}
		//maintPrice
		else if(attributeNum == 1) {
			if(valueNum == 0) {
				return "vhighMaint";
			}
			else if(valueNum == 1) {
				return "highMaint";
			}
			else if(valueNum == 2) {
				return "medMaint";
			}
			else if(valueNum == 3) {
				return "lowMaint";
			}
		}
		//doors
		else if(attributeNum == 2) {
			if(valueNum == 0) {
				return "twoDoors";
			}
			else if(valueNum == 1) {
				return "threeDoors";
			}
			else if(valueNum == 2) {
				return "fourDoors";
			}
			else if(valueNum == 3) {
				return "fiveMoreDoors";
			}
		}
		//persons
		else if(attributeNum == 3) {
			if(valueNum == 0) {
				return "twoPerson";
			}
			else if(valueNum == 1) {
				return "fourPerson";
			}
			else if(valueNum == 2) {
				return "morePerson";
			}
		}
		//lug_boot
		else if(attributeNum == 4) {
			if(valueNum == 0) {
				return "smallLug";
			}
			else if(valueNum == 1) {
				return "medLug";
			}
			else if(valueNum == 2) {
				return "bigLug";
			}
		}
		//safety
		else if(attributeNum == 5) {
			if(valueNum == 0) {
				return "lowSafety";
			}
			else if(valueNum == 1) {
				return "medSafety";
			}
			else if(valueNum == 2) {
				return "highSafety";
			}
		}		
		//acceptabilityClass
		else if(attributeNum == 6) {
			if(valueNum == 0) {
				return "unacc";
			}
			else if(valueNum == 1) {
				return "acc";
			}
			else if(valueNum == 2) {
				return "good";
			}
			else if(valueNum == 3) {
				return "vgood";
			}
		}		
		
		return null;
	}
	
	
	//BELOW ARE I/O METHODS
	/**
	 * Prints string to outStream
	 *
	 * @param s		The string to print
	 */
	public static void display(String s)
	{
		fr.outStream.print(s);
	}
	
	/**
	 * Prints string to outStream and moves to next line
	 *
	 * @param s		The string to print
	 */
	public static void displayln(String s)
	{
		fr.outStream.println(s);
	}
	
	
}