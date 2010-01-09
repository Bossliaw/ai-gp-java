import java.util.LinkedList;


public class GPprocess extends GPprocessParam implements GPprocessAPI, GPprogLangAPI {
	
	private GPgridworld gridworld;
	
	public GPprocess(GPgridworld gridworld)
	{
		super();
		this.gridworld = gridworld;
	}
	
	private int rand(int upperlim)
	{
		return (int)(Math.random()*((double)(upperlim)));
	}
	
	@Override
	public LinkedList<GPprog> naturalSelection(LinkedList<GPprog> currProgs, LinkedList<GPfitness> progFit) {
		// implement paper's tournament selection
		// TODO Auto-generated method stub
		LinkedList<GPprog> survivalProgs = new LinkedList<GPprog>();
		int everyNprog = FitSampleNum;
		int population = currProgs.size();
		int survival_population = (int) (((float)population) * survivalRate);
		
		// collect mostfit individual until arrive survival population
		for(int i = 0; i < survival_population; i++) {
			// finding mostfit individual per everyNprog
			int mostFit = -1;
			int mostFitPick = -1;
			for(int k = 0; k < everyNprog; k++) {
				int randPick = rand(population);
				int randPickFit  = progFit.get(randPick).reportProgFitness();
				if(mostFit < randPickFit) {
					mostFit = randPickFit;
					mostFitPick = randPick;
				}
			}
			//System.out.printf("%3d: mostFit %d\n",i, mostFit);
			survivalProgs.add(currProgs.get(mostFitPick));
		}
		
		return survivalProgs;
	}
	
	@Override
	public GPprog crossover(GPprog father, GPprog mother) {
		// TODO Auto-generated method stub
		LinkedList<Integer> fatherGene = father.getProg();
		LinkedList<Integer> motherGene = mother.getProg();
		GPprogEval fatherEval = new GPprogEval(father);
		GPprogEval motherEval = new GPprogEval(mother);
		int fatherGeneLength = fatherGene.size();
		int motherGeneLength = motherGene.size();
		int randpickFatherSubtreeHead = rand(fatherGeneLength);
		int randpickMotherSubtreeHead = rand(motherGeneLength);
		
		// father's subtree replaces mother's subtree		
		int randpickFatherSubtreeTail = fatherEval.subtree_substringTail(randpickFatherSubtreeHead);
		int randpickMotherSubtreeTail = motherEval.subtree_substringTail(randpickMotherSubtreeHead);
		
		LinkedList<Integer> fatherSubtree = 
			new LinkedList<Integer>(fatherGene.subList(randpickFatherSubtreeHead, randpickFatherSubtreeTail+1));
		
		LinkedList<Integer> childGene = motherGene;
		for(int i = randpickMotherSubtreeHead; i < (randpickMotherSubtreeTail+1); i++)
			childGene.remove(randpickMotherSubtreeHead);
		childGene.addAll(randpickMotherSubtreeHead, fatherSubtree);
		
		GPprog child = new GPprog(gridworld, childGene);
		gridworld.randGrid();
		child.setGridXY(gridworld.randGridX(), gridworld.randGridY());
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
		sub_head = rand(abnormal_code.size());
		sub_end = subtree.subtree_substringTail(sub_head);

		for(int i = sub_head; i<=sub_end; i++)								//merge tree
			abnormal_code.remove(sub_head);
		abnormal_code.addAll(sub_head, mutation_code);
		
		GPprog mutate = new GPprog(gridworld, abnormal_code);
		gridworld.randGrid();
		mutate.setGridXY(gridworld.randGridX(), gridworld.randGridY());
		return mutate;
	}

	@Override
	public LinkedList<GPprog> nextGeneration(LinkedList<GPprog> currGeneration, LinkedList<GPfitness> progFitPool) {
		// TODO Auto-generated method stub
		int population = currGeneration.size();
		int survival_population = (int) (((float)population) * survivalRate);
		LinkedList<GPprog> nextGen = naturalSelection(currGeneration, progFitPool);
		int newbirth_population = population - survival_population;
		
		// mutation  operation (by random test)
		for(int i = 0; i < survival_population; i++)
			if(Math.random() < mutationProb) {
				GPprog abnormal = mutation(nextGen.get(i));
				nextGen.set(i, abnormal);
			}
		
		// crossover operation
		for(int i = 0; i < newbirth_population; i++) {
			int randpickFather = rand(survival_population);
			int randpickMother = rand(survival_population);
			GPprog child = crossover(nextGen.get(randpickFather), nextGen.get(randpickMother));
			nextGen.add(child);
		}
		for(int i = 0; i < population; i++)
			progFitPool.get(i).reinitProgFitness();
		
		return nextGen;
	}

}
