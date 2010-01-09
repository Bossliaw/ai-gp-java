import java.util.LinkedList;


public interface GPprocessAPI {
	
	LinkedList<GPprog> naturalSelection(LinkedList<GPprog> currGPprog);
	GPprog 			   crossover(GPprog father, GPprog mother);
	GPprog             mutation(GPprog abnormal);
	
	
}
