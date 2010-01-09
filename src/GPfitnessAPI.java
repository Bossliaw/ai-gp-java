
public interface GPfitnessAPI {
	
	boolean o = true;
	boolean x = false;
	
    boolean path_markInit [][] = {
			   { x, x, x, x, x, x, x, x, x, x },
			   { x, o, o, o, x, x, o, o, o, x },
			   { x, o, x, o, x, x, o, x, o, x },
			   { x, o, x, o, o, o, o, x, o, x },
			   { x, o, x, x, x, x, x, x, o, x },
			   { x, o, x, x, x, x, x, x, o, x },
			   { x, o, x, o, o, o, o, x, o, x },
			   { x, o, x, o, x, x, o, x, o, x },
			   { x, o, o, o, x, x, o, o, o, x },
			   { x, x, x, x, x, x, x, x, x, x }
			};
	
	void reportProgPosition(int atGridx, int atGridy);
	int  reportProgFitness();
	void reinitProgFitness();
}
