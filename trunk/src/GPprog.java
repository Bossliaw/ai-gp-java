import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/*
 * 
 * program grammar rules
<exp>    -> <op-exp> | <action> | <sensor>
<action> -> ( moveE | moveW | moveN | moveS ) #eval_term (terminate: system stack problem)
<sensor> -> ( n | s | w | e | ne | se | nw | sw )#eval_sensor (boolean value replacement)
<op-exp> -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
<and-exp> -> AND <exp> <exp> #eval_and, return value
<if-exp> -> IF <exp> <exp> <exp> #eval_if
<or-exp> -> OR <exp> <exp> #eval_or

program data structure
1. tree
2. linked-list
	��{���ݦ��@�ӶW���r��, �@�Ӥ@��node��
	(pushdown automata -1. �ۤv�gstack (selected)
	                    2. recursive call(system stack) (�n��) (but action symbol termination problem)

program generation
1. prevent unlimited generation / funciton call(stack overflow)
=> set upper limit
   pros: increase performance, space reserved
   cons: unknown solution quality
2. generating method (random)
   -

   IF(AND(se NOT(north))OR(s, sw) se)
   IF AND se NOT north OR s sw se   
        
pushdown automata
            <exp>
      <exp> <exp> <exp>
      <exp> <exp> <exp>
<exp> <exp> <exp> <exp>
 ?      IF   AND    se
 
system stack
                               NOT
             AND    AND bool   AND bool
 ___   IF    IF     IF         IF        
 
 

 0. |
 1. IF
 2. IF AND
 3. IF AND se
 4. IF AND 
 system stack, DFS stack (use linked-list)
 0. |
 top = "", do nothing, read IF, push IF
 1. IF
 top = IF, <do op>, read AND, push AND
 2. IF AND
 top = AND, <do op>, read OR, push OR
 3. IF AND OR
 top = OR, <do op>, read n, push n
 4. IF AND OR n 
 top = n, <do sensor>, pop n, <push valStack>, top = OR, <do op>, read ne, push ne
 5. IF AND OR ne
 top = ne, <do sensor>, pop ne, <push valStack>, top = OR, <do op> pop OR, top = AND, <do op>, read NOT, push NOT
 6. IF AND NOT
 top = NOT, <do op>, 
 7. IF AND NOT e
 8. IF AND NOT
 
 read IF, 
 
 
 value stack
 0. |
 1. |
 2. |
 3. |
 4. |
 5. n
 6. OR(n, ne)
 7. n ne
 8. n ne e
 */

public class GPprog implements GPprogLang, GPprogGridAPI {

	private GPgridworld gridworld;
	private int atGridx;
	private int atGridy;
	private LinkedList<Keyword> prog;
	private Iterator<Keyword> progIter;
	
	// computer stack, register
	private int retVal; 
	private Stack<Keyword> sysStack = new Stack<Keyword>();
	private Stack<Integer> dfaStack = new Stack<Integer>();
	
	public GPprog(GPgridworld gridworld)
	{
		this.gridworld = gridworld;
		
		prog = new LinkedList<Keyword>();
		prog.add(Keyword.NOT);
		prog.add(Keyword.AND);
		prog.add(Keyword.IF);
		prog.add(Keyword.ne);
		prog.add(Keyword.IF);
		prog.add(Keyword.se);
		prog.add(Keyword.moveS);
		prog.add(Keyword.moveW);
		prog.add(Keyword.moveN);
		prog.add(Keyword.NOT);
		prog.add(Keyword.NOT);
		prog.add(Keyword.e);
		// generate random program
	}
	
	
	
	private Type getType(final Keyword kw)
	{
		switch(kw) {
			case IF:
			case OR:
			case AND:
			case NOT:
				return Type.Operator;
			case ne:
			case se:
			case nw:
			case sw:
			case n:
			case s:
			case w:
			case e:
				return Type.Sensor;
			case moveN:
			case moveS:
			case moveW:
			case moveE:
				return Type.Action;
		}
		return null;
	}
	
	private void opNot(Integer execState) {
		switch(execState.intValue()) {
		case 0: // evaluate first argument
			// 
			// update execState to 1
			Integer newState = dfaStack.pop().intValue();
			dfaStack.push(newState);
			sysStack.push(progIter.next());
			break;
		case 1:
		}
		
	}
	
	
	
	@Override
	public Action evaluate() {
		// TODO Auto-generated method stub
		progIter = prog.iterator();
		
		sysStack.push(progIter.next());
		dfaStack.push(new Integer(0));
		
		while(!sysStack.isEmpty()) {
			Keyword kw = sysStack.peek();
			switch(kw) {
			case IF:
					
			case OR:
					
			case NOT: opNot(dfaStack.peek()); break;
			case AND:
					
				}
			}

				
			
		}
		
		return null;
	}

	@Override
	public int atGridX() {
		// TODO Auto-generated method stub
		return atGridx;
	}

	@Override
	public int atGridY() {
		// TODO Auto-generated method stub
		return atGridy;
	}

	@Override
	public void setXY(int x, int y) {
		// TODO Auto-generated method stub
		atGridx = x;
		atGridy = y;
	}



	@Override
	public void generate() {
		// TODO Auto-generated method stub
		
	}

}
