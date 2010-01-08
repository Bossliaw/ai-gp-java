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

public class GPprog implements GPprogLangAPI, GPprogGridAPI
{

	private GPgridworld gridworld;
	private int atGridx;
	private int atGridy;
	private LinkedList<Integer> prog;
	
	private GPprogInit progInit;
	private GPprogEval progEval;
	
	public GPprog(GPgridworld gridworld)
	{
		this.gridworld = gridworld;
		progInit = new GPprogInit();
		progEval = new GPprogEval(this);
		
		// test
		prog = new LinkedList<Integer>();
		prog.add(NOT);
		prog.add(AND);
		prog.add(IF);
		prog.add(ne);
		prog.add(IF);
		prog.add(se);
		prog.add(moveS);
		prog.add(moveW);
		prog.add(moveN);
		prog.add(NOT);
		prog.add(NOT);
		prog.add(e);
		
		// generate random program
		// prog = progInit.generate();
	}

	
	public LinkedList<Integer> getProg()
	{
		return prog;
	}
	
	@Override
	public void executeAction() {
		// TODO Auto-generated method stub
		Action action = progEval.evaluate();
		switch(action) {
		case moveN:
		case moveS:
		case moveE:
		case moveW:
		case idle:
		}
	}

	@Override
	public boolean sensor(int word) {
		// TODO Auto-generated method stub
		switch(word) {
		case n:
			return gridworld.getGridObj(atGridx, atGridy-1);
		case s:
			return gridworld.getGridObj(atGridx, atGridy+1);
		case w:
			return gridworld.getGridObj(atGridx-1, atGridy);
		case e:
			return gridworld.getGridObj(atGridx+1, atGridy);
		default:
			return false;
		}
	}

}
