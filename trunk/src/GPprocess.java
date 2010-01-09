import java.util.LinkedList;


public class GPprocess implements GPprocessAPI {
	
	LinkedList<GPfitness> progFit;
	
	GPgridworld gridworld;
	
	public GPprocess(LinkedList<GPfitness> progFit, GPgridworld gridworld)
	{
		this.progFit = progFit;
		this.gridworld = gridworld;
	}
	
	@Override
	public LinkedList<GPprog> naturalSelection(LinkedList<GPprog> currProgs, int everyNprog, float survivalRate) {
		// implement paper's tournament selection
		// TODO Auto-generated method stub
		LinkedList<GPprog> survivalProgs = new LinkedList<GPprog>();
		int population = currProgs.size();
		int survival_population = (int) (((float)population) * survivalRate);
		
		// collect mostfit individual until arrive survival population
		for(int i = 0; i < survival_population; i++) {
			// finding mostfit individual per everyNprog
			int mostFit = -1;
			int mostFitPick = -1;
			for(int k = 0; k < everyNprog; k++) {
				int randPick = ((int)( Math.random()*((double)(2*population)) )) % population;
				int randPickFit  = progFit.get(randPick).reportProgFitness();
				if(mostFit < randPickFit) {
					mostFit = randPickFit;
					mostFitPick = randPick;
				}
			}
			survivalProgs.add(currProgs.get(mostFitPick));
		}
		
		
		return survivalProgs;
	}
	
	@Override
	public GPprog crossover(GPprog father, GPprog mother) {
		// TODO Auto-generated method stub
		GPprog child = null;
		
		return child;
	}

	@Override
	public GPprog mutation(GPprog abnormal) {
		// TODO Auto-generated method stub
		int sub_head,sub_end;
		
		LinkedList<Integer> abnormal_code = new LinkedList<Integer>();		//initial code
		abnormal_code = abnormal.getProg();
		
		GPprogInit init = new GPprogInit();
		LinkedList<Integer> mutation_code = new LinkedList<Integer>();		//mutation code
		mutation_code = init.generate();
		
		GPprogEval subtree = new GPprogEval(abnormal);						//get head,end
		sub_head = (int)(Math.random()*abnormal_code.size());
		sub_end = subtree.subtree_substringTail(sub_head);

		for(int i = sub_head; i<=sub_end; i++)								//merge tree
			abnormal_code.remove(sub_head);
		abnormal_code.addAll(sub_head, mutation_code);
		
		GPprog mutate = new GPprog(gridworld, abnormal_code);
		return mutate;
	}

	@Override
	public LinkedList<GPprog> nextGeneration(LinkedList<GPprog> currGeneration) {
		// TODO Auto-generated method stub
		return null;
	}

}
