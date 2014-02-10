import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Tree {
	public static Node DTL(ArrayList<Record> examples, ArrayList<String> attrMap, Node root){
		if(examples.size()<=0){
			root.setTestAttribute(mode(root.getParent().getData()));
			return root;
		}
		DiscreteAttribute classification = haveSameClassification(examples);
		if(classification!=null){
			root.setTestAttribute(classification);
			return root;
		}
		if(Main.usedAttributes.size()==6){
			root.setTestAttribute(mode(root.getParent().getData()));
			return root; 
		}
		
		
		int bestAttribute = -1;
		double bestGain = 0;
		
		root.setEntropy(Entropy.calculateEntropy(root.getData(), root));
		if(root.getEntropy() == 0){
			root.setTestAttribute(mode(examples));
			return root;
		}
		
		for(int i = 0; i < 6; i++) {
			if(!Main.isAttributeUsed(i)) {
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				
				for(int j = 0; j < Main.setSizeByIndex(i); j++) {
					ArrayList<Record> subset = subset(root, i, j);
					if(subset.size()!=0) setSizes.add(subset.size());
					//System.out.println("Subset Size = " + subset.size());
					
					if(subset.size() != 0) {
						entropy = Entropy.calculateEntropy(subset, root); //for value j
						entropies.add(entropy);
					}
				}
				
				double gain = Entropy.calculateGain(root.getEntropy(), entropies, setSizes, root.getData().size());
				
				if(gain > bestGain) {
					bestAttribute = i;
					bestGain = gain;
				}
			}
		}
		if(bestAttribute != -1) {
			int setSize = Main.setSize(Main.attrMap.get(bestAttribute));
			if(root.getTestAttribute().getName().equals("")) root.setTestAttribute(new DiscreteAttribute(Main.attrMap.get(bestAttribute), 88));
			root.setTestAttribute(new DiscreteAttribute(Main.attrMap.get(bestAttribute), 999)); //*********
			root.children = new Node[setSize];
			root.setUsed(true);
			Main.usedAttributes.add(bestAttribute);
			
			for (int j = 0; j< setSize; j++) {
				root.children[j] = new Node();
				root.children[j].setParent(root);
				root.children[j].setData(subset(root, bestAttribute, j));
				root.children[j].getTestAttribute().setName(Main.getLeafNames(bestAttribute, j));
				root.children[j].getTestAttribute().setValue(j);
			}

			for (int j = 0; j < setSize; j++) {
				DTL(root.children[j].getData(), attrMap , root.children[j]);
			}
			System.out.println("Best Attribute: " + Main.attrMap.get(bestAttribute));
			root.setData(null);
		}
		else {
			return root;
		}
		
		return root;
	}

	public static ArrayList<Record> subset(Node root, int attr, int value) {
		ArrayList<Record> subset = new ArrayList<Record>();

		for(int i = 0; i < root.getData().size(); i++) {
			Record record = root.getData().get(i);

			if(record.getAttributes().get(attr).getValue() == value)
				subset.add(record);
		}
		return subset;
	}
	
	public static DiscreteAttribute mode(ArrayList<Record> examples) {
		DiscreteAttribute classification = null;
		int countUnacc = 0;
		int countAccep = 0;
		int countGood = 0;
		int countVgood = 0;
		
		for(int i = 0; i<examples.size(); i++){
			int x = examples.get(i).getAttributes().get(6).getValue();
			if(x == 0) countUnacc++;  
			if(x == 1) countAccep++;
			if(x == 2) countGood++;
			if(x == 3) countVgood++;
		}
		
		int max = Math.max(Math.max(countUnacc,countAccep),Math.max(countGood, countVgood));
		if(max == countUnacc) classification = new DiscreteAttribute("unacc",0);
		if(max == countAccep) classification = new DiscreteAttribute("accep",1);
		if(max == countGood) classification = new DiscreteAttribute("good",2);
		if(max == countVgood) classification = new DiscreteAttribute("vgood",3);
		
		return classification;
	}
	
	public static DiscreteAttribute haveSameClassification(ArrayList<Record> examples) {
		DiscreteAttribute classification = examples.get(0).getAttributes().get(6);
		for(int i = 1; i<examples.size(); i++){
			if(examples.get(i).getAttributes().get(6) != classification) return null;
		}
		return classification;
	}
}

