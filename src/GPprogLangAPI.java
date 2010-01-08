import java.util.List;

// KeyWord
public interface GPprogLangAPI {
	
	Integer moveN = new Integer(0);
	Integer moveS = new Integer(1);
	Integer moveW = new Integer(2);
	Integer moveE = new Integer(3);
	Integer n = new Integer(4);
	Integer s = new Integer(5);
	Integer w = new Integer(6);
	Integer e = new Integer(7);
	Integer nw = new Integer(8);
	Integer ne = new Integer(9);
	Integer sw = new Integer(10);
	Integer se = new Integer(11);
	Integer IF = new Integer(12);
	Integer OR = new Integer(13);
	Integer NOT = new Integer(14);
	Integer AND = new Integer(15);
	
	enum Type    { Action, Sensor, Operator };

	enum Action  { moveN, moveS, moveW, moveE, idle };
	
	Type getType();
	
}
