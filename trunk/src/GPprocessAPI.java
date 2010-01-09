import java.util.LinkedList;


public interface GPprocessAPI {
	
	LinkedList<GPprog> naturalSelection(LinkedList<GPprog> currGPprog, LinkedList<GPfitness> progFit);
	GPprog 			   crossover(GPprog father, GPprog mother);
	GPprog             mutation(GPprog abnormal);
	
	LinkedList<GPprog> nextGeneration(LinkedList<GPprog> currGeneration, LinkedList<GPfitness> progFit);
}
