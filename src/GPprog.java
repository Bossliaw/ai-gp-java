import java.util.LinkedList;

/*
 * 
 * program grammar rules
<exp>    -> <op-exp> | <action> | <sensor> | <boolean>
<boolean> -> { T | F }
<action> -> ( moveE | moveW | moveN | moveS )
<sensor> -> ( n | s | w | e | ne | se | nw | sw )
<op-exp> -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
<and-exp> -> AND <exp> <exp>
<if-exp> -> IF <exp> <exp> <exp> 
<or-exp> -> OR <exp> <exp> 

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

public class GPprog implements GPprogLangAPI, GPprogAPI
{

	private GPgridworld gridworld;
	private LinkedList<Integer> prog;
	private GPprogEval progEval;
	
	private int atGridx = -1;
	private int atGridy = -1;
	
	public GPprog(GPgridworld gridworld, int maxNumNodes, int minNumNodes, boolean rootIsInternal)
	{
		this.gridworld = gridworld;
		GPprogInit progInit = new GPprogInit(maxNumNodes, minNumNodes, rootIsInternal);
		progEval = new GPprogEval(this);
		
		// generate random program
		prog = progInit.generate();
	}

	public GPprog(GPgridworld gridworld, LinkedList<Integer> progGene)
	{
		this.gridworld = gridworld;
		progEval = new GPprogEval(this);
		prog     = progGene;
	}
	
	public LinkedList<Integer> getProg()
	{
		return prog;
	}
	
	@Override
	public void executeAction() {
		
		
		// TODO Auto-generated method stub
		progEval.subtree_substringTail(0); // grammar check
		Action action = progEval.evaluate();
		switch(action) {
		case moveN: if(sensor(n)) atGridy--; break;
		case moveS: if(sensor(s)) atGridy++; break;
		case moveE: if(sensor(e)) atGridx++; break;
		case moveW: if(sensor(w)) atGridx--; break;
		case idle: break;
		}
		System.out.printf("%d, %d\n", atGridx, atGridy);	
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
		case ne:
			return gridworld.getGridObj(atGridx+1, atGridy-1);
		case se:
			return gridworld.getGridObj(atGridx+1, atGridy+1);
		case nw:
			return gridworld.getGridObj(atGridx-1, atGridy+1);
		case sw:
			return gridworld.getGridObj(atGridx-1, atGridy-1);
		default:
			return false;
		}
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
	public void setGridXY(int x, int y) {
		// TODO Auto-generated method stub
		atGridx = x;
		atGridy = y;
	}

}
