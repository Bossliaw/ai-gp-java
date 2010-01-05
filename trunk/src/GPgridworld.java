
public class GPgridworld implements GPgridworldobj {

	int gridworld [][] = {
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
	
	int getGridObj(int x, int y)
	{
		return gridworld[x][y];
	}
	
}
