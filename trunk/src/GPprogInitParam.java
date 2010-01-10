
public class GPprogInitParam {

	int     maxNumProgNodes = 100;
	int     minNumProgNodes = 15;
	boolean rootIsInternal = true;
	
	// random program generate parameters
	boolean probUserDefine = true;
	// <exp> -> <op-exp> | <action> | <sensor> | <boolean>
	double actionProb = 0.15;
	double sensorProb = 0.15;
	double boolProb   = 0.00;
    double opExpProb  = 0.70;
	double genExpProb [] = { actionProb, sensorProb, boolProb };
    
	// <bool>    ->  T | F
	double Tprob      = 0.5;
    double Fprob      = 0.5;
	double genBoolProb [] = { Tprob };
    
	// <action>  ->  moveE | moveW | moveN | moveS 
	double moveNprob  = 0.25;
	double moveEprob  = 0.25;
	double moveWprob  = 0.25;
    double moveSprob  = 0.25;
    double genActionProb [] = { moveNprob, moveEprob, moveWprob };
    
    // <sensor>  ->  n | s | w | e | ne | se | nw | sw 
    double nProb  = 0.125;
	double eProb  = 0.125;
	double wProb  = 0.125;
	double sProb  = 0.125;
	double neProb = 0.125;
	double nwProb = 0.125;
	double seProb = 0.125;
	double swProb = 0.125;
	double genSensorProb [] = { nProb, eProb, wProb, sProb, neProb, nwProb, seProb };
	
	// <op-exp>  -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
	double notProb = 0.25;
	double andProb = 0.25;
	double orProb  = 0.25;
	double ifProb  = 0.25;
	double genOpExpProb [] = { notProb, orProb, andProb };

	public GPprogInitParam() {
		
	}
	
	public GPprogInitParam(int maxNodes, int minNodes, boolean rootIsInternal) {
		this.maxNumProgNodes = maxNodes;
		this.minNumProgNodes = minNodes;
		this.rootIsInternal  = rootIsInternal;
		
	}
}
