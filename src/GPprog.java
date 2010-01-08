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
	把程式看成一個超長字串, 一個一個node看
	(pushdown automata -1. 自己寫stack (selected)
	                    2. recursive call(system stack) (好做) (but action symbol termination problem)

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

	private int atGridx;
	private int atGridy;
	private LinkedList<Keyword> prog;
	
	private boolean returnVal;
	private boolean 
	private Stack<Keyword> simsysStack = new Stack<Keyword>();
	
	public GPprog()
	{
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
	
	private void execOperator(Keyword kw) {
		switch(kw) {
		case IF:
			if(valStack.capacity() >= 3) {
				valStack
			}
		case OR:
		case NOT:
		case AND:
		}
	}
	
	@Override
	public Action eval() {
		// TODO Auto-generated method stub
		Iterator<Keyword> iter = prog.iterator();
		while(iter.hasNext()) {
			if(!dfsStack.isEmpty()) {
				Keyword kw = dfsStack.peek();
				switch(getType(kw)) {
				case Operator:
					execOperator(kw);
					break;
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

}
