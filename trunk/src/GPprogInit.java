import java.util.LinkedList;
import java.util.Stack;

public class GPprogInit implements GPprogInitAPI {
		
	private LinkedList<Integer> gen_code = new LinkedList<Integer>();
	private Stack<Integer> grammarstack = new Stack<Integer>();
	
	private int numMinNodes = 10;
	private int numMaxNodes = 100;
	private boolean rootIsInternal = true;
	private int nodeCount;
	
	private double genExpProb    [] = new double [3];
	private double genSensorProb [] = new double [7];
	private double genActionProb [] = new double [3];
	private double genOpExpProb  [] = new double [3];
	private double genBoolProb   [] = new double [1];
	
	public GPprogInit(int maxNumNodes, int minNumNodes, boolean rootIsInternal){
		this.numMaxNodes = maxNumNodes;
		this.numMinNodes = minNumNodes;
		this.rootIsInternal = rootIsInternal;
		
		for(int i = 0; i < genExpProb.length; i++)
			genExpProb[i] = 1/(genExpProb.length+1);
		for(int i = 0; i < genSensorProb.length; i++)
			genSensorProb[i] = 1/(genSensorProb.length+1);
		for(int i = 0; i < genActionProb.length; i++)
			genActionProb[i] = 1/(genActionProb.length+1);
		for(int i = 0; i < genOpExpProb.length; i++)
			genOpExpProb[i] = 1/(genOpExpProb.length+1);
		for(int i = 0; i < genBoolProb.length; i++)
			genBoolProb[i] = 1/(genBoolProb.length+1);
		
	}

// ================================================
	
	public void setGenExpProb(double actionProb, double sensorProb, double boolProb) {
		genExpProb[0] = actionProb;
		genExpProb[1] = sensorProb;
		genExpProb[2] = boolProb;
	}
	
	public void setActionProb(double moveNprob, double moveEprob, double moveWprob) {
		genActionProb[0] = moveNprob;
		genActionProb[1] = moveEprob;
		genActionProb[2] = moveWprob;
	}
	
	public void setOpExpProb(double notprob, double orporb, double andprob) {
		genOpExpProb[0] = notprob;
		genOpExpProb[1] = orporb;
		genOpExpProb[2] = andprob;
	}

	public void setBoolProb(double Tprob) {
		genBoolProb[0] = Tprob;
	}
	
	public void setSensorProb(double np, double ep, double wp, double sp, double nep, double nwp, double sep) {
		genSensorProb[0] = np;
		genSensorProb[1] = ep;
		genSensorProb[2] = wp;
		genSensorProb[3] = sp;
		genSensorProb[4] = nep;
		genSensorProb[5] = nwp;
		genSensorProb[6] = sep;
	}
	
// ================================================
	
	private boolean IsKeyWord(int symbol) {
		return (symbol >= KeyWordLowerBound && symbol <= KeyWordUpperBound);
	}
	
	private boolean IsNonTerm(int symbol) {
		return (symbol > KeyWordUpperBound && symbol <= bool);
	}
	
	private void pushExp() {
		grammarstack.push(exp);
		nodeCount++;
	}
	
// ================================================
	
	private void genExp() {
		double rand01 = Math.random();
		double op_expProb = 1 - (genExpProb[3]);
		double interval[] = new double [3];
		
		if(nodeCount >= numMaxNodes) {
			double shareOpExpProb = op_expProb/3;
			interval[0] = shareOpExpProb;
			interval[1] = 2*shareOpExpProb;
			interval[2] = 3*shareOpExpProb; // should equal to 1
		//  interval[2] = 1;
		}
		else {
			interval[0] = genExpProb[0];
			interval[1] = genExpProb[0] + genExpProb[1];
			interval[2] = genExpProb[0] + genExpProb[1] + genExpProb[2];
		}
		
		if(rand01 < interval[0])
			grammarstack.push(action);
		else if(rand01 >= interval[0] && rand01 < interval[1])
			grammarstack.push(sensor);
		else if(rand01 >= interval[1] && rand01 < interval[2])
			grammarstack.push(bool);
		else // rand01 >= interval[2] && rand01 < 1
			grammarstack.push(op_exp);
	}
	
	private void genBool() {
		double rand01 = Math.random();
		double interval[] = new double [1];
		
		interval[0] = genBoolProb[0];
		
		if(rand01 < interval[0])
			grammarstack.push(T);
		else // rand01 >= interval[2] && rand01 < 1
			grammarstack.push(F);
	}
	
	private void genAction()
	{
		double rand01 = Math.random();
		double op_expProb = 1 - (genActionProb[3]);
		double interval[] = new double [3];
		
		interval[0] = genActionProb[0];
		interval[1] = genActionProb[0] + genActionProb[1];
		interval[2] = genActionProb[0] + genActionProb[1] + genActionProb[2];
		
		if(rand01 < interval[0])
			grammarstack.push(moveN);
		else if(rand01 >= interval[0] && rand01 < interval[1])
			grammarstack.push(moveE);
		else if(rand01 >= interval[1] && rand01 < interval[2])
			grammarstack.push(moveW);
		else // rand01 >= interval[2] && rand01 < 1
			grammarstack.push(moveS);
	}
	
	private void genSensor() {
		double rand01 = Math.random();
		double op_expProb = 1 - (genActionProb[3]);
		double interval[] = new double [7];
		
		interval[0] = genSensorProb[0];
		for(int i = 1; i < interval.length; i++)
			interval[i] = (interval[i-1] + genSensorProb[i]);
		
		if(rand01 < interval[0]) grammarstack.push(n);
		else 
		if(rand01 < interval[1]) grammarstack.push(e);
		else 
		if(rand01 < interval[2]) grammarstack.push(w);
		else 
		if(rand01 < interval[3]) grammarstack.push(s);
		else 
		if(rand01 < interval[4]) grammarstack.push(ne);
		else
		if(rand01 < interval[5]) grammarstack.push(nw);
		else 
		if(rand01 < interval[6]) grammarstack.push(se);
		else 
								 grammarstack.push(sw);
			
	}
	
// ================================================
	
	public LinkedList<Integer> generate(){
		
		// push initial stack
		if(rootIsInternal) {
			for(int i = 0; i < numMinNodes-1; i++)
				grammarstack.push(exp);
			grammarstack.push(op_exp);
		}
		else
			for(int i = 0; i < numMinNodes; i++)
				grammarstack.push(exp);
		
		nodeCount = numMinNodes;
		
		// generate start
		while(!grammarstack.empty()){
			//<exp> -> <op-exp> | <action> | <sensor>
			switch(grammarstack.pop()) {
			case exp:     genExp();    break; // <exp>    -> <op-exp> | <action> | <sensor>
			case bool:    genBool();   break; // <bool>   -> T | F
			case action:  genAction(); break; // <action> -> moveE | moveW | moveN | moveS
			case sensor:  genSensor(); break; // <sensor> -> n | s | w | e | ne | se | nw | sw
			case op_exp:  genOp_exp(); break;
			case if_exp:  genIf_exp(); break;
			case or_exp:  genOr_exp(); break;
			case not_exp: genNotExp(); break;
			case and_exp: genAndExp(); break;
			default:      break;
			}
			
		}
	}

		System.out.print(gen_code.clone()+"\n");
		
		return gen_code;
	}

}
