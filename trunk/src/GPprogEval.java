import java.util.Stack;


public class GPprogEval implements GPprogEvalAPI, GPprogLangAPI {

	// computer stack, register
	private boolean retVal = false;
	private GPprog prog;
	private int progCounter = 0;
	private Stack<Integer> sysStack; // store operator wordcode
	private Stack<Integer> dfaStack; // store operator state machine
	
	public GPprogEval(GPprog prog)
	{
		this.prog = prog;
	}
	
	public Integer getNode(int index)
	{
		return prog.getCode().get(index);
	}
	
	private Integer getCurrentInstruction() 
	{
		return prog.getCode().get(progCounter);
	}
	
	public Type getType(Integer wordcode) {
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
		dfaStack.push(nextS);
	}
	
	private void pushCurrInstruction()
	{
		int currKw = getCurrentInstruction();
		//System.out.printf("next instruction = %s\n", KeywordString[currKw]);
		if(getType(currKw) == Type.Operator) {
			dfaStack.push(evalArg1st);
		}
		sysStack.push(currKw);
	}
	
	private void endProcedure()
	{
		// operation completed, pop
		sysStack.pop();
		dfaStack.pop();
	}
	
	
	private void opNot(Integer execState) {
		switch(execState) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			progCounter++;
			pushCurrInstruction();
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
		switch(execState) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			progCounter++;
			pushCurrInstruction();
			break;
		case opExec:     // execute operation
			if(retVal) {
				endProcedure();
				progCounter = subtree_substringTail(progCounter+1);
			}
			else       nextState(evalArg2nd);
			break;
		case evalArg2nd: // evaluate second argument
			endProcedure();
			progCounter++;
			pushCurrInstruction();
			break;
		}
	}
	
	private void opAnd(Integer execState) {
		switch(execState) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			progCounter++;
			pushCurrInstruction();
			break;
		case opExec: // execute operation
			if(retVal) nextState(evalArg2nd);
			else {
				endProcedure();
				progCounter = subtree_substringTail(progCounter+1);
			}
			break;
		case evalArg2nd:	
			endProcedure();
			progCounter++;
			pushCurrInstruction();
			break;
		}
	}
	
	private void opIF(Integer execState) {
		switch(execState) {
		case evalArg1st: // evaluate first argument
			nextState(opExec);
			progCounter++;
			pushCurrInstruction();
			break;
		case opExec: // execute operation
			if(retVal) nextState(evalArg2nd);
			else       nextState(evalArg3rd);
			break;
		case evalArg2nd:	
			endProcedure();
			progCounter++;
			pushCurrInstruction();
			break;
		case evalArg3rd:
			endProcedure();
			progCounter = subtree_substringTail(progCounter+1);
			pushCurrInstruction();
			break;
		}
	}
	
	@Override
	public Action evaluate() {
		// TODO Auto-generated method stub
		progCounter = 0;
		sysStack = new Stack<Integer>();
		dfaStack = new Stack<Integer>();
		
		if(getType(getCurrentInstruction()) == Type.Operator)
			dfaStack.push(evalArg1st);
		
		sysStack.push(getCurrentInstruction());
		
		int keyword = -1;
		while(!sysStack.isEmpty()) {
			keyword = sysStack.peek();
			/*
			System.out.printf("sysStack size = %d, ", sysStack.size());
			System.out.printf("dfaStack size = %d ", dfaStack.size());
			System.out.printf("PC = %d\n", progCounter);
			if(getType(keyword) == Type.Operator)
				System.out.printf("%s, %d\n", KeywordString[keyword], dfaStack.peek());
			else
				System.out.printf("%s\n", KeywordString[keyword]);
			*/
			switch(keyword) {
			case NOT: opNot(dfaStack.peek()); break;
			case IF:  opIF(dfaStack.peek()); break;
			case OR:  opOr(dfaStack.peek()); break;
			case AND: opAnd(dfaStack.peek()); break;
			case n:
			case w:
			case s:
			case e:
			case ne:
			case se:
			case sw:
			case nw:
				retVal = prog.sensor(keyword); 
				sysStack.pop();
				break;
			case T: retVal = true;  sysStack.pop(); break;
			case F: retVal = false; sysStack.pop(); break;
			case moveN:
			case moveS:
			case moveW:
			case moveE:
				sysStack.clear();
				dfaStack.clear();
				break;
			default: break;
			}
			//System.out.printf("retVal = ");
			//System.out.println(retVal);
		}
		
		// check last keyword
		if(getType(keyword) == Type.Sensor || 
		   getType(keyword) == Type.Operator ||
		   getType(keyword) == Type.Boolean)
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

	@Override
	public int subtree_substringTail(int substringHead) {
		// TODO Auto-generated method stub
		int substringTail = substringHead;
		Stack<Boolean> dfsStack = new Stack<Boolean>();
		dfsStack.push(true);

		//System.out.printf("substring head = %d\n", substringHead);
				
		while(!dfsStack.isEmpty()) {
			//System.out.printf("dfsStack size = %d\n", dfsStack.size());
			dfsStack.pop();
			//System.out.printf("substring tail = %d\n", substringTail);
			Integer instruction = prog.getCode().get(substringTail);
			
			if(instruction == NOT) { 
				dfsStack.push(true);
			}
			else
			if(instruction == OR || instruction == AND) {
				dfsStack.push(true);
				dfsStack.push(true);
			}
			else
			if(instruction == IF) {
				dfsStack.push(true);
				dfsStack.push(true);
				dfsStack.push(true);
			}
			substringTail++;
		}
		
		return (substringTail-1);
	}

}
