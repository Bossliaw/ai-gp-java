
public class GPfitness implements GPfitnessAPI {

	boolean path_mark [][] = {
			   { x, x, x, x, x, x, x, x, x, x },
			   { x, o, o, o, x, x, o, o, o, x },
			   { x, o, x, o, x, x, o, x, o, x },
			   { x, o, x, o, o, o, o, x, o, x },
			   { x, o, x, x, x, x, x, x, o, x },
			   { x, o, x, o, o, o, o, x, o, x },
			   { x, o, x, o, x, x, o, x, o, x },
			   { x, o, x, o, x, x, o, x, o, x },
			   { x, o, o, o, x, x, o, o, o, x },
			   { x, x, x, x, x, x, x, x, x, x }
			};
	
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
			path_mark[atGridy][atGridx] = true;
		}
	}

}
