
public interface GPprogAPI extends GPprogLangAPI {

	boolean sensor(int word);
	void    executeAction(GPfitness progFit);
	int     atGridX();
	int     atGridY();
	void    setGridXY(int x, int y);
	
}
