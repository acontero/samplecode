
public class Gameboard implements Comparable<Gameboard>{

	private Gameboard parent;
	private int costThusFar; //g(n)
	private int estimatedCostToGoal; //h(n)
	private int evalFxn; //f(n)
	private int[][] state = new int[3][3];
	private int[] spaceLocation = new int[2];
	
	public Gameboard(){
		this.parent = null;
		this.costThusFar = 0;
		this.estimatedCostToGoal = 0;
		this.spaceLocation = new int[2];
	}
	
	public Gameboard(Gameboard parentNode, int g, int h, int[][] array){
		this.parent = parentNode;
		this.costThusFar = g;
		this.estimatedCostToGoal = h;
		this.state = array;
		this.spaceLocation = new int[2];
	}
	
//	public int getNodeNumber(){
//		return this.nodeNumber;
//	}
	
	public Gameboard getParent(){
		return this.parent;
	}
	
	public int getEvalFxn(){
		this.evalFxn = this.costThusFar + this.estimatedCostToGoal;
		return this.evalFxn;
	}
	
	public int getCostThusFar(){
		return this.costThusFar;
	}
	
	public int getEstimatedCostToGoal(){
		return this.estimatedCostToGoal;
	}
	
	public int[][] getState(){
		return this.state;
	}
	
	public void setParent(Gameboard parentNode) {
		this.parent = parentNode;
	}
	
	public int[] getSpaceLocation() {
		return this.spaceLocation;
	}
	
	
	public void setSpaceLocation(int x,int y){
		int[] temp = {x,y};
		this.spaceLocation = temp;
	}
	
	public Gameboard updateCostThusFar(int g){
		this.costThusFar = g;
		this.evalFxn = this.costThusFar + this.estimatedCostToGoal;
		return this;
	}

	public int compareTo(Gameboard node){ //return the smaller f(n)
        if(this.evalFxn > node.evalFxn) return 1; 
        if(this.evalFxn < node.evalFxn) return -1; 
        return 0;
    }
	
	public void printState(){
		for(int r = 0; r<3; r++){
			for(int c = 0; c<3; c++){
				System.out.print(this.state[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	

	
}
