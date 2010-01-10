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
			progPool.add(prog);
			progFitPool.add(new GPfitness());
		}
		
	}
	
	public void GPgenerationLoop() {
		for(int generation = 0; generation <= numGeneration; generation++) {
			
			// test fitness
			for(int i = 0; i < population; i++) {
				GPprog prog = progPool.get(i);
				GPfitness progfit = progFitPool.get(i);
				for(int k = 0; k < numSwitchPos; k++) {
					gridworld.randGrid();
					prog.setGridXY(gridworld.randGridX(), gridworld.randGridY());
					prog.executeAction(progfit);
					
				}
			}
			
			// statistic
			int sumFitness = 0;
			int maxFitness = -1;
			int mostFitIndividual = -1;
			for(int i = 0; i < population; i++) {
				sumFitness += progFitPool.get(i).reportProgFitness();
				if(maxFitness < progFitPool.get(i).reportProgFitness()) {
					mostFitIndividual = i;
					maxFitness = progFitPool.get(i).reportProgFitness();
				}
			}
			
			System.out.printf("Generation[%3d]: maxFitness: %2d, mostFitRuns: %2d, sumFitness: %3d, avgFitness: %.2f ",
					generation, maxFitness, progPool.get(mostFitIndividual).getActualRun(),
					sumFitness, ((double)sumFitness/(double)population));
			
			gen_max[generation] = maxFitness;
			gen_avg[generation] =sumFitness/population;
			
			System.out.print("mostfit gene: ");
			progPool.get(mostFitIndividual).dumpProgGene();
			
			// next generation
			progPool = process.nextGeneration(progPool, progFitPool);
			
		}// end generation loop
		
		new GPchart(numGeneration, gen_max, gen_avg);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPmain gp = new GPmain();
		gp.GPgenerationLoop();

	}

}
