import java.util.List;


public class Result {

	public int numOfNodesExpanded;
	public long totalTime;
	public int g;
	public List<Gameboard> sequenceOfNodes;
	public int stepsInSolution = 0;
	public int levelOfDifficulty =0;

	public Result(){
		numOfNodesExpanded = 0;
		totalTime = 0;
		int g = 0;
	}
	
}
