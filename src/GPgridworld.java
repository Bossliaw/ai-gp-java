
public class GPgridworld implements GPgridworldobj {

	private boolean gridworld [][] = {
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

	private int availablePosCoord[][] = null;
	private int randAvailableGrid     = 0;
	
	public GPgridworld() {
		int availablePos = 0;
		for(int y = 0; y < gridworld.length; y++)
			for(int x = 0; x < gridworld[y].length; x++)
				if(gridworld[y][x]) availablePos++;
		
		availablePosCoord = new int [availablePos][2];
		int i = 0;
		for(int y = 0; y < gridworld.length; y++)
			for(int x = 0; x < gridworld[y].length; x++)
				if(gridworld[y][x]) {
					availablePosCoord[i][0] = x;
					availablePosCoord[i][1] = y;
					i++;
				}
	}
	
	boolean getGridObj(int x, int y)
	{
		return gridworld[y][x];
	}
	
	void randGrid() {
		randAvailableGrid = ((int)(Math.random()*(float)(availablePosCoord.length)));
	}
	
	int randGridX()
	{
		return availablePosCoord[randAvailableGrid][0];
	}
	int randGridY()
	{
		return availablePosCoord[randAvailableGrid][1];
	}
}
