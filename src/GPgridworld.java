
public class GPgridworld implements GPgridworldobj {

	boolean gridworld [][] = {
	   { x, x, x, x, x, x, x, x, x, x },
	   { x, o, o, o, x, x, o, o, o, x },
	   { x, o, o, o, x, x, o, o, o, x },
	   { x, o, o, o, o, o, o, o, o, x },
	   { x, o, o, o, o, o, o, o, o, x },
	   { x, o, o, o, o, o, o, o, o, x },
	   { x, o, o, o, o, o, o, o, o, x },
	   { x, o, o, o, x, x, o, o, o, x },
	   { x, o, o, o, x, x, o, o, o, x },
	   { x, x, x, x, x, x, x, x, x, x }
	};
	
	boolean getGridObj(int x, int y)
	{
		return gridworld[y][x];
	}
	
}
