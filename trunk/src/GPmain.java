
public class GPmain {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GPgridworld gridworld;
		GPprog      prog;
		gridworld = new GPgridworld();
		prog      = new GPprog(gridworld);
		prog.setGridXY(3, 3);
		prog.executeAction();
	}

}
