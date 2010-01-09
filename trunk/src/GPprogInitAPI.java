/*
program grammar rules
<exp>     -> <op-exp> | <action> | <sensor> | <boolean>
<boolean> -> { T | F }
<action>  -> ( moveE | moveW | moveN | moveS )
<sensor>  -> ( n | s | w | e | ne | se | nw | sw )
<op-exp>  -> <if-exp> | <and-exp> | <or-exp> | <not-exp>
<not-exp> -> NOT <exp>
<and-exp> -> AND <exp> <exp>
<if-exp>  -> IF <exp> <exp> <exp> 
<or-exp>  -> OR <exp> <exp> 
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
	int bool    = 26;
	
	LinkedList<Integer> generate();
}
