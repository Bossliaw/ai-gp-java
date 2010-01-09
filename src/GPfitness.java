
public class GPfitness implements GPfitnessAPI {

	private boolean path_mark [][] = path_markInit.clone();
	int fitness = 0;
	
	@Override
	public int reportProgFitness() {
		// TODO Auto-generated method stub
		return fitness;
	}

	@Override
	public void reportProgPosition(int atGridx, int atGridy) {
		// TODO Auto-generated method stub
		if(path_mark[atGridy][atGridx]){
			fitness++;
			//path_mark[atGridy][atGridx] = false;
		}
	}

	@Override
	public void reinitProgFitness() {
		// TODO Auto-generated method stub
		path_mark = path_markInit.clone();
		fitness = 0;
	}
	
	public void reinitPathMark() {
		path_mark = path_markInit.clone();
	}

}
