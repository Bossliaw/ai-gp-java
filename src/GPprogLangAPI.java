// KeyWord
public interface GPprogLangAPI {
	
	int F = 0;
	int T = 1;
	int moveN = 2;
	int moveS = 3;
	int moveW = 4;
	int moveE = 5;
	int n = 6;
	int s = 7;
	int w = 8;
	int e = 9;
	int nw = 10;
	int ne = 11;
	int sw = 12;
	int se = 13;
	int IF = 14;
	int OR = 15;
	int NOT = 16;
	int AND = 17;
	
	int KeyWordLowerBound = 0;
	int KeyWordUpperBound = 17;
	
	enum Type    { Boolean, Action, Sensor, Operator, Invaild };
	enum Action  { moveN, moveS, moveW, moveE, idle };

	
}
