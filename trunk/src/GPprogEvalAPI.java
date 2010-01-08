
public interface GPprogEvalAPI extends GPprogLangAPI {
	
	int evalArg1st = 1;
	int evalArg2nd = 2;
	int evalArg3rd = 3;
	int opExec     = 0;
	
	int subtree_substringTail(int substringHead);
	
	Action evaluate();
}
