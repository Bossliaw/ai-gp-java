
public class GPfitness implements GPfitnessAPI {

	private boolean path_mark [][];
	int fitness = 0;
	
	public GPfitness() {
		path_mark = new boolean [path_markInit.length][path_markInit[0].length];
		reinitPathMark();
	}
	
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
			path_mark[atGridy][atGridx] = false;
		}
	}

	@Override
	public void reinitProgFitness() {
		// TODO Auto-generated method stub
		reinitPathMark();
		fitness = 0;
	}
	
	public void reinitPathMark() {
		for(int i = 0; i < path_mark.length; i++)
			for(int k = 0; k < path_mark[i].length; k++)
				path_mark[i][k] = path_markInit[i][k];
	}

}
