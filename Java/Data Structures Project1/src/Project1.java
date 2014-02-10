import java.io.*;
import java.util.*;

public class Project1 {

	/**
	 * @author      Angelica Contero 
	 * @version     CS313 Project       (current version number of this program)                  
	 * @since       2012-10-24          (the version of the package this class was first added to)
	 */
	
	/**
	 * The design of my program was based on reading in large numbers backwards. 
	 * This would allow me to navigate the linked list for arithmetic the way one does on paper
	 * from right to left (or ones digits to tens digits to hundreds digits, etc.)
	 * When I print out to the console, I copy the linked list into a temporary list and reverse the 
	 * temporary one, thereby leaving the original list unadulterated. 
	 * 
	 * Also of note, while calculating the product, you'll notice that I use my sum function to keep 
	 * adding up the partial sums at each step of the way, constantly updating my answer until the 
	 * multiplication is complete.
	 */
	public static void main(String[] args) {
		if (args.length==0) System.out.println ("No file specified.");
		else{
			FileReader theFile;
			BufferedReader inFile;
			String oneLine;
			int s; 
			try{
				theFile = new FileReader(args[0]);
				inFile = new BufferedReader(theFile);
				while((oneLine = inFile.readLine()) !=null){
					String firstNumbers[] = oneLine.split(",");
					s=0;
					LargeNumbers x = new LargeNumbers();
					for (int i = firstNumbers.length-1; i>=0; i--){
						s= s + Integer.parseInt(firstNumbers[i]);
						x.append(Integer.parseInt(firstNumbers[i]));
					}	
					System.out.println("The first input number is: ");
					x.printReverse();
					
					if ((oneLine = inFile.readLine()) != null){
						String secondNumbers[] = oneLine.split(",");
						s=0;
						LargeNumbers y = new LargeNumbers();
						for (int i = secondNumbers.length-1; i>=0; i--){
							s= s + Integer.parseInt(secondNumbers[i]);
							y.append(Integer.parseInt(secondNumbers[i]));
						}	
						System.out.println("The second input number is: ");
						y.printReverse();
						
						LargeNumbers n = new LargeNumbers ();
						n = sum(x,y);
						System.out.println("The sum of these two inputs is: ");
						n.printReverse();
						LargeNumbers p = new LargeNumbers ();
						p = product(x,y);
						System.out.println("The product of these two inputs is: ");
						p.printReverse();
						System.out.println();
						inFile.readLine();
					}//end if loop
				}//end while loop
			}//end try loop
			catch (Exception e){ 
				System.out.println(e);
			}
		}//else
	}//end main

	/**
	 * Calculates sum of 2 LargeNumbers.
	 *
	 * Use {@link #sum(LargeNumber, LargeNumber)} to calculate sum.
	 *
	 * @param x			  First LargeNumber
	 * @param y			  Second LargeNumber
	 *
	 * @return            sum as a new LargeNumber
	 */
	public static LargeNumbers sum(LargeNumbers x, LargeNumbers y){
		Node p,q;
		p = x.first.getNext();
		q = y.first.getNext();
		Integer carryover = 0;
		LargeNumbers sum = new LargeNumbers();
		while (!((p == null) && (q == null))){ //while both nodes are not null, keep adding
			Integer tempsum = 0;
			if (p == null){
				tempsum = q.getElement() + carryover;
				q = q.getNext();
			}
			else if (q == null){
				tempsum = p.getElement() + carryover;
				p = p.getNext();
			}
			else{
				tempsum = p.getElement() + q.getElement() + carryover;
				p = p.getNext();
				q = q.getNext();
			}
			
			//how to handle tempsum:
			if(tempsum >999){
				carryover = tempsum/1000;
				tempsum = tempsum % 1000;
			}
			//reset carryover to 0 if there's is nothing to carry over
			else carryover = 0;
	
			sum.append(tempsum); 
		}
		if(carryover!=0) sum.append(carryover);
		return sum;
	}
	
	/**
	 * Calculates product of 2 LargeNumbers.
	 *
	 * Use {@link #product(LargeNumber, LargeNumber)} to calculate sum.
	 *
	 * @param x			  First LargeNumber
	 * @param y			  Second LargeNumber
	 *
	 * @return            product as a new LargeNumber
	 */
	public static LargeNumbers product(LargeNumbers x, LargeNumbers y){
		int count = 0;
		Node p,q;
		q = y.first.getNext();
		LargeNumbers product = new LargeNumbers();
		LargeNumbers sum = new LargeNumbers();
		while (q!=null){ //while bottom multiplier node is not null, keep multiplying
			LargeNumbers partialProduct = new LargeNumbers();
			//insert 0 place holders to end if needed
			if (count>0){
				for(int i=1; i<=count; i++){
					partialProduct.append(0);
				}
			}
			p = x.first.getNext();
			Integer tempprod = 0;
			while(p!=null){//while top mutiplicand node is not null, keep multiplying
				tempprod = q.getElement()*p.getElement();
				//how to handle tempprod:
				partialProduct.append(tempprod); 
				p = p.getNext();
			}
			sum = sum(partialProduct,sum);
			q = q.getNext();
			count++;	
		}
		
		Node n = sum.first.getNext();
		Boolean zero = false;
		//check if product is all 0's
		while (n!=null){
			if (n.getElement() == 0){
				zero = true;
				n = n.getNext();
			}
			else{
				zero = false;
				break;
			}
		}
		if (zero == true){
			product = new LargeNumbers();
			product.append(0);
		}
		else product = sum;
		return product;
	}
}//end class
