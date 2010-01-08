/*
 <exp>    -> <op-exp> | <action> | <sensor>
<action> -> ( moveE | moveW | moveN | moveS ) #eval_term (terminate: system stack problem)
<sensor> -> ( n | s | w | e | ne | se | nw | sw )#eval_sensor (boolean value replacement)
<op-exp> -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
<and-exp> -> AND <exp> <exp> #eval_and, return value
<if-exp> -> IF <exp> <exp> <exp> #eval_if
<or-exp> -> OR <exp> <exp> #eval_or
<not-exp>-> NOT <exp> #eval_not
 */
import java.util.LinkedList;


// keyWord + NonTerm
public interface GPprogInitAPI extends GPprogLangAPI {
	
	int exp = 18;
	int op_exp =19;
	int action =20;
	int sensor =21;
	int if_exp =22;
	int and_exp =23;
	int or_exp =24;
	int not_exp = 25;
	
	LinkedList<Integer> generate();
}
