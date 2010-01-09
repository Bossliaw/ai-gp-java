import java.util.LinkedList;


public interface GPprocessAPI {
	
	void               setParameters(double survivalRate, double mutationProb, int FitSampleNum,
									int maxNumNodes, int minNumNodes, boolean rootIsInternal);
	
	LinkedList<GPprog> naturalSelection(LinkedList<GPprog> currGPprog, LinkedList<GPfitness> progFit);
	GPprog 			   crossover(GPprog father, GPprog mother);
	GPprog             mutation(GPprog abnormal);
	
	LinkedList<GPprog> nextGeneration(LinkedList<GPprog> currGeneration, LinkedList<GPfitness> progFit);
}
