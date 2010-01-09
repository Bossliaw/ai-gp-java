import java.util.LinkedList;


public class GPmain {

	/**
	 * @param args
	 * @return 
	 */
	GPgridworld gridworld;
	LinkedList<GPprog> progPool;
	
	// GP experiment parameters
	int maxGPprog = 2;
	int maxGPprog_nodes = 100;
	int minGPprog_nodes = 10;
	boolean rootIsInternal = true;
	
	public GPmain() {
		
		gridworld = new GPgridworld();
		GPprog prog      = new GPprog(gridworld, maxGPprog_nodes, minGPprog_nodes, rootIsInternal);
		prog.setGridXY(3, 3);
		prog.executeAction();
		
		/*
		for(int i = 0; i < maxGPprog; i++)
			progPool.add(new GPprog(gridworld));*/
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPmain gp = new GPmain();
	}

}
