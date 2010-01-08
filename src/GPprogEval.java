import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;


public class GPprogEval implements GPprogEvalAPI, GPprogLangAPI {

	// computer stack, register
	private boolean retVal;
	private GPprog prog;
	private int progCounter = 0;
	private Stack<Integer> sysStack; // store operator wordcode
	private Stack<Integer> dfaStack; // store operator state machine
	
	public GPprogEval(GPprog prog)
	{
		this.prog = prog;
	}
	
	private Type getType(Integer wordcode) {
		// TODO Auto-generated method stub
		if(wordcode < KeyWordLowerBound || wordcode > KeyWordUpperBound)
			return Type.Invaild;
		else
		if(wordcode >= F && wordcode <= T)
			return Type.Boolean;
		else
		if(wordcode >= moveN && wordcode <= moveE)
			return Type.Action;
		else
		if(wordcode >= n && wordcode <= se)
			return Type.Sensor;
		else
		if(wordcode >= IF && wordcode <= AND)
			return Type.Operator;
		else
			return null;
	}

	private void nextState(int nextS)
	{
		// update execState
		dfaStack.pop();
		dfaStack.push(opExec);
	}
	
	private void evalArg()
	{
		// push next program keyword for evaluation
		int nextKw = progIter.next();
		if(getType(nextKw) == Type.Operator) {
			dfaStack.push(evalArg1st);
		}
		sysStack.push(nextKw);
	}
	
	private void endProcedure()
	{
		// operation completed, pop
		sysStack.pop();
		dfaStack.pop();
	}
	
	
	private void opNot(Integer execState) {
		switch(execState.intValue()) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			evalArg();
			break;
		case opExec:	// execute operation
			// execute NOT operation
			if(retVal) retVal = false;
			else       retVal = true;
			endProcedure();
			break;
		}
		
	}
	
	private void opOr(Integer execState) {
		switch(execState.intValue()) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			evalArg();
			break;
		case opExec:     // execute operation
			if(retVal) endProcedure();
			else       nextState(evalArg2nd);
			break;
		case evalArg2nd: // evaluate second argument
			evalArg();
			endProcedure();
			break;
		}
	}
	
	private void opAnd(Integer execState) {
		switch(execState.intValue()) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			evalArg();
			break;
		case opExec: // execute operation
			if(!retVal) endProcedure();
			else        nextState(evalArg2nd);
			break;
		case evalArg2nd:	
			evalArg();
			endProcedure();
			break;
		}
	}
	
	@Override
	public Action evaluate() {
		// TODO Auto-generated method stub
		progCounter = 0;
		sysStack = new Stack<Integer>();
		dfaStack = new Stack<Integer>();
		
		sysStack.push(progIter.next());
		dfaStack.push(new Integer(0));
		
		int keyword = n;
		while(!sysStack.isEmpty()) {
			keyword = sysStack.peek();
			switch(keyword) {
			case NOT: opNot(dfaStack.peek()); break;
			case IF:	
			case OR:  opOr(dfaStack.peek()); break;
			case AND: break;
			case n:
			case w:
			case s:
			case e:
			case ne:
			case se:
			case sw:
			case nw:
				retVal = prog.sensor(keyword); 
				break;
			case moveN:
			case moveS:
			case moveW:
			case moveE:
				sysStack.clear();
				dfaStack.clear();
				break;
			default: break;
			}
		}
		
		// check last keyword
		if(getType(keyword) == Type.Sensor)
			return Action.idle;
		else
		if(getType(keyword) == Type.Action) {
			switch(keyword) {
			case moveN: return Action.moveN;
			case moveS: return Action.moveS;
			case moveW: return Action.moveW;
			case moveE: return Action.moveW;
			default:    return null;
			}
		}
		else return null;
			
	}

}
