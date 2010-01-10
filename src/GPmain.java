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
	
	int genMaxFit [] = new int [numGeneration+1];
	double genAvgFit [] = new double [numGeneration+1];
	
	public GPmain() 
	{
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
	public void generationLoop() 
	{
		for(int generation = 0; generation <= numGeneration; generation++) {
			
			// test fitness
			for(int i = 0; i < population; i++) {
				GPprog prog = progPool.get(i);
				GPfitness progfit = progFitPool.get(i);
				for(int k = 0; k < numSwitchPos; k++) {
					gridworld.randGrid();
					prog.setGridXY(gridworld.randGridX(), gridworld.randGridY());
					prog.executeAction(progfit);
					progfit.reinitPathMark();
				}
			}
			
			statistic(generation);

			// next generation
			progPool = process.nextGeneration(progPool, progFitPool);
			System.gc();
		}// end generation loop
	}
	public void showChart()
	{
		new GPchart(numGeneration, genMaxFit, genAvgFit);
	}
	public void statistic(int generation)
	{
		int sumFitness = 0;
		int maxFitness = -1;
		int mostFitIndividual = -1;
		
		int nodeCount [] = new int [statNumSlots];
		
		for(int i = 0; i < population; i++) {
			for(int k = 0; k < nodeCount.length; k++) {
				int numNodes = progPool.get(i).getCode().size();
				if(numNodes >= k*statInterval+1 && numNodes <= (k+1)*statInterval)
					nodeCount[k]++;
			}
			sumFitness += progFitPool.get(i).reportProgFitness();
			if(maxFitness < progFitPool.get(i).reportProgFitness()) {
				mostFitIndividual = i;
				maxFitness = progFitPool.get(i).reportProgFitness();
			}
		}
		genMaxFit[generation] = maxFitness;
		genAvgFit[generation] = ((double)sumFitness) / ((double)population);
		System.out.printf("Generation[%3d]: maxFitness: %2d, mostFitRuns: %2d, sumFitness: %3d, avgFitness: %.2f ",
				generation, maxFitness, progPool.get(mostFitIndividual).getActualRun(),
				sumFitness, genAvgFit[generation]);
		
		System.out.print("mostfit gene: ");
		progPool.get(mostFitIndividual).dumpProgGene();
		
		System.out.print("node count statistic:\n");
		for(int k = 0; k < nodeCount.length; k++) {
			System.out.printf("%3d ~ %3d: ", (k*statInterval+1), (k+1)*statInterval);
			System.out.printf("%d\n", nodeCount[k]);
		}
		
		System.out.printf("free memory: %d\n", Runtime.getRuntime().freeMemory());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPmain gp = new GPmain();
		gp.generationLoop();
		gp.showChart();
	}

}
