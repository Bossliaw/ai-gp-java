public interface GPprogLang {
	
	enum Type    { Action, Sensor, Operator };
	enum Keyword { moveN, moveS, moveW, moveE, s, w, n, e, ne, nw, se, sw, IF, AND, OR, NOT };

	enum Action  { moveN, moveS, moveW, moveE, idle };
	
	
	Action  eval();
	
	
}


