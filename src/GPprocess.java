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
		LinkedList<Integer> fatherGene = father.getCode();
		LinkedList<Integer> motherGene = mother.getCode();
		int fatherGeneLength = fatherGene.size();
		int motherGeneLength = motherGene.size();
		
		//father.getEval().initArraylizeCode();
		//mother.getEval().initArraylizeCode();
		
		/*
		// father choosing random process (improvement because high probability to choose leaf nodes)
		int [] InternalIndices = new int [numInternalCollect];
		for(int i = 0; i < numInternalCollect; i++)
			for(int k = rand(fatherGeneLength); k < fatherGeneLength; k++) {
				Integer node = father.getEval().getNode(k);
				if(father.getEval().getType(node) == Type.Operator) {
					InternalIndices[i] = k;
					break;
				}
			}// end random search and collect (destroy...(wrong
		// end collect internal for loop
		int numTerminalCollect = (int)(((double)numInternalCollect)*BranchLeavesRatio);
		int [] TerminalIndices = new int [numTerminalCollect];
		for(int i = 0; i < numTerminalCollect; i++)
			for(int k = rand(fatherGeneLength); k < fatherGeneLength; k++) {
				Integer node = father.getEval().getNode(k);
				if(father.getEval().getType(node) != Type.Operator) {
					TerminalIndices[i] = k;
					break;
				}
			}// end random search and collect (destroy...(wrong
		// end collect terminal for loop
		int randpickFromBranchLeavesCollection = rand(numInternalCollect+numTerminalCollect);
		
		// final pick
		int finalpickFatherSubtreeHead;
		if(randpickFromBranchLeavesCollection >= numInternalCollect)
			finalpickFatherSubtreeHead = TerminalIndices[randpickFromBranchLeavesCollection - numInternalCollect];
		else
			finalpickFatherSubtreeHead = InternalIndices[randpickFromBranchLeavesCollection];
		*/
		int randpickFatherSubtreeHead = rand(fatherGeneLength);
		int randpickMotherSubtreeHead = rand(motherGeneLength);
		int randpickFatherSubtreeTail = father.getEval().subtree_substringTail(randpickFatherSubtreeHead);
		int randpickMotherSubtreeTail = mother.getEval().subtree_substringTail(randpickMotherSubtreeHead);
		
		// father's subtree replaces mother's subtree
		LinkedList<Integer> fatherSubtree = 
			new LinkedList<Integer>(fatherGene.subList(randpickFatherSubtreeHead, randpickFatherSubtreeTail+1));
		
		LinkedList<Integer> childGene = new LinkedList<Integer>();
		childGene.addAll(motherGene);
		for(int i = randpickMotherSubtreeHead; i < (randpickMotherSubtreeTail+1); i++)
			childGene.remove(randpickMotherSubtreeHead);
		childGene.addAll(randpickMotherSubtreeHead, fatherSubtree);
		
		GPprog child = new GPprog(gridworld, childGene);
		return child;
	}

	@Override
	public GPprog mutation(GPprog abnormal) {
		// TODO Auto-generated method stub
		int sub_head, sub_tail;
		
		GPprogInit init = new GPprogInit(10, 3, false);
		LinkedList<Integer> mutation_code = new LinkedList<Integer>();		//mutation code
		mutation_code = init.generate();
		
		//get head,end
		sub_head = rand(abnormal.getCode().size());
		sub_tail = abnormal.getEval().subtree_substringTail(sub_head);

		for(int i = sub_head; i <= sub_tail; i++)								//merge tree
			abnormal.getCode().remove(sub_head);
		abnormal.getCode().addAll(sub_head, mutation_code);
		
		return abnormal;
	}

	@Override
	public LinkedList<GPprog> nextGeneration(LinkedList<GPprog> currGeneration, LinkedList<GPfitness> progFitPool) {
		// TODO Auto-generated method stub
		int population = currGeneration.size();
		int survival_population = (int) (((float)population) * survivalRate);
		int newbirth_population = population - survival_population;
		
		// natural selection
		LinkedList<GPprog> nextGen = naturalSelection(currGeneration, progFitPool);
		
		// mutation operation (by random test)
		for(int i = 0; i < survival_population; i++)
			if(Math.random() < mutationProb) {
				GPprog mutated = mutation(nextGen.get(i));
				nextGen.set(i, mutated);
			}
		
		// crossover operation
		for(int i = 0; i < newbirth_population; i++) {
			int randpickFather = rand(survival_population);
			int randpickMother = rand(survival_population);
			GPprog child = crossover(nextGen.get(randpickFather), nextGen.get(randpickMother));
			nextGen.add(child);
		}

		// reinitialize program fitness
		for(int i = 0; i < population; i++)
			progFitPool.get(i).reinitProgFitness();
		
		return nextGen;
	}

}
