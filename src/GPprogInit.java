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
	}
	
	public setGenExpProb(double actionProb, double sensorProb, double boolProb) {
		genExpProb[0] = actionProb;
		genExpProb[1] = sensorProb;
		genExpProb[2] = boolProb;
	}
	
	public setActionProb(double moveNprob, double moveEprob, double moveWprob) {
		genActionProb[0] = moveNprob;
		genActionProb[1] = moveEprob;
		genActionProb[2] = moveWprob;
	}
	
	public setOpExpProb(double notprob, double orporb, double andprob) {
		
	}
	
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
			grammarstack.push(action);
		else if(rand01 >= interval[0] && rand01 < interval[1])
			grammarstack.push(sensor);
		else if(rand01 >= interval[1] && rand01 < interval[2])
			grammarstack.push(bool);
		else // rand01 >= interval[2] && rand01 < 1
			grammarstack.push(op_exp);
	}
	
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
