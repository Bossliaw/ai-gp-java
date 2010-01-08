
public interface GPprogAPI extends GPprogLangAPI {

	boolean sensor(int word);
	void    executeAction();
	int     atGridX();
	int     atGridY();
	void    setGridXY(int x, int y);
	
}
