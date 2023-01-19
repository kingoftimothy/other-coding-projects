// A simple symbol table for a postscript interpreter.
// (c) 2001,1996, 2001 duane a. bailey
 
import structure5.*;
import java.util.Iterator;
/**
 * A simple table of symbols for a postscript interpreter.
 * This particular implementation is very expensive, and we'll see
 * improved implementations in the latter part of the semester.
 * @author duane a. bailey
 */
public class SymbolTable {
	/**
	 * A list of string-value associations; representation of a
	 * "symbol table".
	 */
	private List<Association<String, Token>> table;     // the table is a list of associations.

	/**
	 * Constructs an empty symbol table.
	 */
	public SymbolTable() {
		table = new DoublyLinkedList<Association<String, Token>>();
	}

	/**
	 * Checks for an entry associated with a particular string.
	 * @param symbol a string potentially associated with a value.
	 * @return true if (and only if) the symbol has an associated value
	 * in this table.
	 */
	public boolean contains(String symbol) {
		Association<String, Token> a =
			new Association<String, Token>(symbol, null);
		return table.contains(a);
	}

	/**
	 * Adds a string-value association to the table.
	 * Assumes that the symbol is not null.  If string has an associated
	 * value already, it is removed before adding the new association.
	 * @param symbol a non-null string
	 * @param value a Token associated with a string.
	 */
	public void add(String symbol, Token value) {
		Association<String, Token> a =
			new Association<String, Token>(symbol, value);
		if (table.contains(a)) {
			table.remove(a);
		}
		
		table.addFirst(a);
	}

	/**
	 * Gets a value associated with a string, from the symbol table.
	 * @param symbol the string whose value is sought
	 * @return the Token associated with the string, or null, if none.
	 */
	public Token get(String symbol) {
		Association<String, Token> a =
			new Association<String, Token>(symbol, null);
		if (table.contains(a)) {
			a = (Association<String, Token>)table.remove(a);
			table.addFirst(a);
			return a.getValue();
		} else {
			return null;
		}
	}

	/**
	 * Removes a value associated with the a string.
	 * @param symbol a string possibly keyed to a value in the symbol table.
	 * @return the token associated with the string
	 */
	public Token remove(String symbol) {
		Association<String, Token> a =
			new Association<String, Token>(symbol, null);
	
		if (table.contains(a)) {
			a = table.remove(a);
			return a.getValue();
		} else {
			return null;
		}
	}

	/**
	 * Returns a string representation of the symbol table.
	 * @return a string representation of the symbol table.
	 */
	public String toString() {
		Iterator<Association<String, Token>> i = table.iterator();
		String result = "";
		while (i.hasNext()) {
			Association<String, Token> a = i.next();
			result = result + a.getKey() + "=" + a.getValue() + "\n";
		}
		return result;
	}

	/**
	 * An example method that makes use of a symbol table.
	 */
	public static void main(String[] args) {
		SymbolTable table = new SymbolTable();
		
		// sometime later:
		table.add("pi", new Token(3.141592653));

		// sometime even later:
		if (table.contains("pi")) {
			Token token = table.get("pi");
			System.out.println(token.getNumber());
		}
	}
}
