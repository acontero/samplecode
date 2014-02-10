import java.util.ArrayList;

public class Entropy {	
	public static double calculateEntropy(ArrayList<Record> data, Node root) {
		if(data.size() == 0) {
			// nothing to do
			return 0;
		}
		if(root.getTestAttribute().getValue()==999) return 1;
		
		
		double entropy = 0;
		
		for(int i = 0; i < Main.setSizeByIndex(6); i++) {
			int count = 0;
		
			for(int j = 0; j < data.size(); j++) {
				Record record = data.get(j);
				
				if(record.getAttributes().get(6).getValue() == i) {
					count++;
				}
			}
		
			double probability = count / (double)data.size();
			double sum = 0;
			if(count > 0) {
				sum += probability * (Math.log(probability) / Math.log(2));
				entropy = entropy - sum;
			}
		}
		
		return entropy;
	}
	
	public static double calculateGain(double rootEntropy, ArrayList<Double> subEntropies, ArrayList<Integer> setSizes, int data) {
		double gain = rootEntropy; 
		double sum = 0;
		
		for(int i = 0; i < subEntropies.size(); i++) {
			sum += ((setSizes.get(i) / (double)data) * subEntropies.get(i));
		}
		
		return (gain-sum);
	}
}
