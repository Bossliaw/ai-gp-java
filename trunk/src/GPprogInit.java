import java.util.LinkedList;
import java.util.Stack;

public class GPprogInit extends GPprogInitParam implements GPprogInitAPI {
		
	private LinkedList<Integer> gen_code = new LinkedList<Integer>();
	private Stack<Integer> grammarstack = new Stack<Integer>();
	private int nodeCount;
	
	public GPprogInit(int maxNodes, int minNodes, boolean rootInternal) {
		super(maxNodes, minNodes, rootInternal);
		if(!probUserDefine) {
			for(int i = 0; i < genExpProb.length; i++)
				genExpProb[i]    = 1.0/(double)(genExpProb.length+1);
			for(int i = 0; i < genSensorProb.length; i++)
				genSensorProb[i] = 1.0/(double)(genSensorProb.length+1);
			for(int i = 0; i < genActionProb.length; i++)
				genActionProb[i] = 1.0/(double)(genActionProb.length+1);
			for(int i = 0; i < genOpExpProb.length; i++)
				genOpExpProb[i]  = 1.0/(double)(genOpExpProb.length+1);
			for(int i = 0; i < genBoolProb.length; i++)
				genBoolProb[i]   = 1.0/(double)(genBoolProb.length+1);
		}
	}
	
	public GPprogInit() {
		if(!probUserDefine) {
			for(int i = 0; i < genExpProb.length; i++)
				genExpProb[i]    = 1.0/(double)(genExpProb.length+1);
			for(int i = 0; i < genSensorProb.length; i++)
				genSensorProb[i] = 1.0/(double)(genSensorProb.length+1);
			for(int i = 0; i < genActionProb.length; i++)
				genActionProb[i] = 1.0/(double)(genActionProb.length+1);
			for(int i = 0; i < genOpExpProb.length; i++)
				genOpExpProb[i]  = 1.0/(double)(genOpExpProb.length+1);
			for(int i = 0; i < genBoolProb.length; i++)
				genBoolProb[i]   = 1.0/(double)(genBoolProb.length+1);
		}
	}
	
// ================================================
/*
	private boolean IsKeyWord(int symbol) {
		return (symbol >= KeyWordLowerBound && symbol <= KeyWordUpperBound);
	}
	
	private boolean IsNonTerm(int symbol) {
		return (symbol > KeyWordUpperBound && symbol <= bool);
	}
*/
	private void pushExp() {
		grammarstack.push(exp);
		nodeCount++;
	}
	
// ================================================
	
	private void genExp() {
		double rand01 = Math.random();
		double op_expProb = 1 - (genExpProb[0] + genExpProb[1] + genExpProb[2]);
		double interval[] = new double [3];
		
		if(nodeCount >= maxNumProgNodes) {
			double shareOpExpProb = op_expProb/3;
			interval[0] = genExpProb[0] + shareOpExpProb;
			interval[1] = genExpProb[0] + genExpProb[1] + 2*shareOpExpProb;
			interval[2] = genExpProb[0] + genExpProb[1] + genExpProb[2] + 3*shareOpExpProb; // should equal to 1
		//  interval[2] = 1;
		}
		else if(nodeCount < minNumProgNodes) {
			interval[0] = 0.0;
			interval[1] = 0.0;
			interval[2] = 0.0;
		}
		else {
			interval[0] = genExpProb[0];
			interval[1] = genExpProb[0] + genExpProb[1];
			interval[2] = genExpProb[0] + genExpProb[1] + genExpProb[2];
		}
		
		if(rand01 < interval[0]) grammarstack.push(action);
		else 
		if(rand01 < interval[1]) grammarstack.push(sensor);
		else 
		if(rand01 < interval[2]) grammarstack.push(bool);
		else 
		/* rand01 < 1.0       */ grammarstack.push(op_exp);
	}
	
	private void genBool() {
		double rand01 = Math.random();
		double interval[] = new double [1];
		
		interval[0] = genBoolProb[0];
		
		if(rand01 < interval[0])
			gen_code.add(T);
		else // rand01 >= interval[2] && rand01 < 1
			gen_code.add(F);
	}
	
	private void genAction()
	{
		double rand01 = Math.random();
		double interval[] = new double [3];
		
		interval[0] = genActionProb[0];
		interval[1] = genActionProb[0] + genActionProb[1];
		interval[2] = genActionProb[0] + genActionProb[1] + genActionProb[2];
		
		if(rand01 < interval[0])
			gen_code.add(moveN);
		else if(rand01 >= interval[0] && rand01 < interval[1])
			gen_code.add(moveE);
		else if(rand01 >= interval[1] && rand01 < interval[2])
			gen_code.add(moveW);
		else // rand01 >= interval[2] && rand01 < 1
			gen_code.add(moveS);
	}
	
	private void genSensor() {
		double rand01 = Math.random();
		double interval[] = new double [7];
		
		interval[0] = genSensorProb[0];
		for(int i = 1; i < interval.length; i++)
			interval[i] = (interval[i-1] + genSensorProb[i]);
		
		if(rand01 < interval[0]) gen_code.add(n);
		else 
		if(rand01 < interval[1]) gen_code.add(e);
		else 
		if(rand01 < interval[2]) gen_code.add(w);
		else 
		if(rand01 < interval[3]) gen_code.add(s);
		else 
		if(rand01 < interval[4]) gen_code.add(ne);
		else
		if(rand01 < interval[5]) gen_code.add(nw);
		else 
		if(rand01 < interval[6]) gen_code.add(se);
		else 
								 gen_code.add(sw);
			
	}
	
// ================================================
	
	
	private void genOp_exp(){	
		double rand01 = Math.random();
		double interval[] = new double [3];
		
		interval[0] = genOpExpProb[0];
		interval[1] = genOpExpProb[0] + genOpExpProb[1];
		interval[2] = genOpExpProb[0] + genOpExpProb[1] + genOpExpProb[2];
		
		if(rand01 < interval[0])
			grammarstack.push(not_exp);
		else if(rand01 >= interval[0] && rand01 < interval[1])
			grammarstack.push(and_exp);
		else if(rand01 >= interval[1] && rand01 < interval[2])
			grammarstack.push(or_exp);
		else // rand01 >= interval[2] && rand01 < 1
			grammarstack.push(if_exp);
	}
		
	private void genIf_exp(){
		pushExp();
		pushExp();
		pushExp();
		gen_code.add(IF);
	}
	
	private void genOr_exp(){
		pushExp();
		pushExp();
		gen_code.add(OR);
	}
	
	private void genNotExp(){
		pushExp();
		gen_code.add(NOT);
	}
	
	private void genAndExp(){
		pushExp();
		pushExp();
		gen_code.add(AND);
	}

	public LinkedList<Integer> generate(){
		
		// push initial stack
		if(rootIsInternal)
			grammarstack.push(op_exp);
		else
			grammarstack.push(exp);
		
		nodeCount = 1;
		
		// generate start
		while(!grammarstack.empty()){
			//<exp> -> <op-exp> | <action> | <sensor>
			switch(grammarstack.pop()) {
			case exp:     genExp();    break; // <exp>    -> <op-exp> | <action> | <sensor>
			case bool:    genBool();   break; // <bool>   -> T | F
			case action:  genAction(); break; // <action> -> moveE | moveW | moveN | moveS
			case sensor:  genSensor(); break; // <sensor> -> n | s | w | e | ne | se | nw | sw
			case op_exp:  genOp_exp(); break; // <op-exp> -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
			case if_exp:  genIf_exp(); break; // <if-exp> -> IF <exp> <exp> <exp>
			case or_exp:  genOr_exp(); break; // <or-exp> -> OR <exp> <exp>
			case not_exp: genNotExp(); break; // <not-exp> -> NOT <exp>
			case and_exp: genAndExp(); break; // <and-exp> -> AND <exp> <exp>
			default:      break;
			}
		}

		/*
		for(int i = 0; i < gen_code.size(); i++)
			System.out.print(KeywordString[gen_code.get(i)]+" ");
		System.out.print("\n");
		*/
		return gen_code;
	}

}
