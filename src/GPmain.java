import java.util.LinkedList;


public class GPmain extends GPparam {

	/**
	 * @param args
	 * @return 
	 */
	GPgridworld gridworld;
	GPprocess   process;
	LinkedList<GPprog> progPool;
	LinkedList<GPfitness> progFitPool;
	
	public GPmain() {
		gridworld = new GPgridworld();
		process   = new GPprocess(gridworld);
		progPool  = new LinkedList<GPprog>();
		progFitPool = new LinkedList<GPfitness>();
		
		for(int i = 0; i < population; i++) {
			GPprog prog = new GPprog(gridworld);
			gridworld.randGrid();
			prog.setGridXY(gridworld.randGridX(), gridworld.randGridY());
			progPool.add(prog);
			progFitPool.add(new GPfitness());
		}
		
	}
	
	public void GPgenerationLoop() {
		for(int generation = 0; generation <= numGeneration; generation++) {
			for(int i = 0; i < population; i++) {
				GPprog prog = progPool.get(i);
				prog.executeAction();
				progFitPool.get(i).reportProgPosition(prog.atGridX(), prog.atGridY());
			}
			
			// statistic
			int sumFitness = 0;
			int maxFitness = -1;
			for(int i = 0; i < population; i++) {
				sumFitness += progFitPool.get(i).reportProgFitness();
				if(maxFitness < progFitPool.get(i).reportProgFitness())
					maxFitness = progFitPool.get(i).reportProgFitness();
			}
			
			System.out.printf("Generation[%3d]: maxFitness: %3d, sumFitness: %3d, avgFitness: %.2f\n",
					generation, maxFitness, sumFitness, ((double)sumFitness/(double)population));
			
			process.nextGeneration(progPool, progFitPool);
			for(int i = 0; i < population; i++)
				progFitPool.get(i).reinitProgFitness();
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPmain gp = new GPmain();
		gp.GPgenerationLoop();
	}

}
