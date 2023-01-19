// A class for managing postscript tokens.
// (c) 2001,1996, 2001 duane a. bailey
import structure5.*;
import java.util.Iterator;

/**
 * A class that implements Tokens that might be read from a stream
 * of postscript commands.  There are, basically, four types of tokens:
 * <ul>
 * <li> Numbers - like 3, 1.4, etc.
 * <li> Booleans - true or false,
 * <li> Symbols - pi, myProcedure, quit, etc.
 * <li> Procedures - lists of other tokens to be interpreted at a later time.
 * </ul>
 *
 * Example usage:
 * <p>
 * To read in the tokens of a postscript file, without interpretation,
 * we might do the following:
 *<pre>
 *  public static void {@link Reader#main(String[] args) main(String[] args)}
 *  {
 *      int i=0;
 *      {@link Reader} r = new {@link Reader#Reader() Reader()};
 *      {@link Token} t;
 *      while ({@link Reader#hasNext() r.hasNext()})
 *      {
 *          t = {@link Reader#next() r.next()};
 *          if (t.{@link #isSymbol isSymbol()} &amp;&amp; // only if symbol:
 *              t.{@link #getSymbol getSymbol}().{@link #equals(Object) equals("quit")}) break;
 *          // process token
 *          System.out.println(i+": "+t);
 *          i++;
 *      }
 *  }
 *</pre>
 * @author duane a. bailey
 */
public class Token {
	/**
	 * The kind of token this is. kind should take on
	 * exactly one type from the set of options:
	 * NUMBER_KIND, BOOLEAN_KIND, SYMBOL_KIND, or PROCEDURE_KIND.
	 * This design lets us define one Token class that can
	 * be flexibly used for different logical token types.
	 */
	private int kind;		// type of token
	
	/**
	 * Token is a number.
	 */
	public static final int NUMBER_KIND = 1;
	
	/**
	 * Token is a boolean.
	 */
	public static final int BOOLEAN_KIND = 2;
	
	/**
	 * Token is a symbol.
	 */
	public static final int SYMBOL_KIND = 3;
	
	/**
	 * Token is a procedure.
	 */
	public static final int PROCEDURE_KIND = 4;

	/**
	 * Associated numeric value of token, if isNumber().
	 */
	private double number;	// a double token value
	
	/**
	 * Associated boolean value of token, if isBoolean().
	 */
	private boolean bool;	// a boolean token value
	
	/**
	 * Associated string value of token, if isSymbol().
	 */
	private String symbol;	// name of a symbol
	
	/**
	 * Associated list of token, if isProcedure().
	 */
	private List<Token> procedure;	// a list of tokens for procedures

	/**
	 * Construct a numeric token.
	 *
	 * @param n the numeric value of the token
	 * @post constructs a numeric token with value value
	 */
	public Token(double n) {
		kind = NUMBER_KIND;
		this.number = n;
	}

	/**
	 * Construct a boolean token
	 * @param b the boolean value of the token
	 * @post constructs a boolean token with value bool
	 */
	public Token(boolean b) {
		kind = BOOLEAN_KIND;
		this.bool = b;
	}

	/**
	 * Construct a symbol token
	 *
	 * @param s the string representing the token
	 * @post constructs a symbol token with value symbol
	 */
	public Token(String s) {
		kind = SYMBOL_KIND;
		this.symbol = s;
	}

	/**
	 * Construct a procedure.
	 *
	 * @param proc the list of tokens that make up the procedure.
	 * @post constructs a procedure token with a value that is the
	 *       list of the tokens that define the procedure
	 */
	public Token(List<Token> proc) {
		kind = PROCEDURE_KIND;
		this.procedure = proc;
	}
    
	/**
	 * Return the kind of token.  Great for use in switch statements.
	 * @return integer representing the kind of the token, usually
	 * Token.number, Token.symbol, etc.
	 */
	public int kind() {
		return this.kind;
	}

	/**
	 * Returns true if and only if this token is a number.
	 * @return true iff token is a number.
	 */
	public boolean isNumber() {
		return kind == NUMBER_KIND;
	}

	/**
	 * Returns true if and only if this token is a boolean.
	 * @return true iff token is a boolean.
	 */
	public boolean isBoolean() {
		return kind == BOOLEAN_KIND;
	}

	/**
	 * Returns true if and only if this token is a symbol.
	 * @return true iff token is a symbol.
	 */
	public boolean isSymbol() {
		return kind == SYMBOL_KIND;
	}

	/**
	 * Returns true if and only if this token is a procedure.
	 * @return true iff token is a procedure.
	 */
	public boolean isProcedure() {
		return kind == PROCEDURE_KIND;
	}

	/**
	 * Fetch numeric value of token, provided it's a number.
	 * @return token's numeric value.
	 */
	public double getNumber() {
		Assert.pre(isNumber(), "Is a number.");
		return number;
	}

	/**
	 * Fetch boolean value of token, provided it's a boolean.
	 * @return token's boolean value.
	 */
	public boolean getBoolean() {
		Assert.pre(isBoolean(), "Is a boolean.");
		return bool;
	}

	/**
	 * Fetch string value of token, provided it's a symbol.
	 * @return token's string value.
	 */
	public String getSymbol() {
		Assert.pre(isSymbol(), "Is a string.");
		return symbol;
	}

	/**
	 * Fetch the list of tokens associated with a procedure token.
	 * @return a List of associated token values.
	 */
	public List<Token> getProcedure() {
		Assert.pre(isProcedure(), "Is a procedure.");
		return procedure;
	}

	/**
	 * Returns true if this token has the same value as the other.
	 * (Does not work correctly for procedures, but the behavior is
	 * sufficient for the purposes of our interpreter.)
	 * @param other another token
	 * @return true if and only if this token is equivalent to other
	 */
	public boolean equals(Object other) {
		Token that = (Token)other;
		boolean result = false;
		// if types are different, the tokens are different
		if (this.kind != that.kind) {
			return false;
		}

		// otherwise, compare the contents of the tokens
		switch (this.kind) {
		case NUMBER_KIND:
			result = this.number == that.number;
			break;
		case BOOLEAN_KIND:
			result = this.bool == that.bool;
			break;
		case SYMBOL_KIND:
			result = this.symbol.equals(that.symbol);
			break;
		case PROCEDURE_KIND:
			result = this.procedure == that.procedure;
			break;
		default:
			Assert.fail("unknown token types");
			return false;
		}
	
		return result;
	}

	
	/**
	 * Not implemented.
	 * @return the hashCode for this token (not implemented)
	 */
	public int hashCode() {
		Assert.fail("Not implemented.");
		return 0;
	}

	
	/**
	 * Generates string representation of a token.
	 * @return a string representation of the token.
	 */
	public String toString() {
		String result = "<unknown>";
		switch (kind) {
		case NUMBER_KIND:
			result = "" + number;
			break;
		case BOOLEAN_KIND:
			result = "" + bool;
			break;
		case SYMBOL_KIND:
			result = symbol;
			break;
		case PROCEDURE_KIND:
			result = "{ ";
			// the iterator allows us to visit elements nondestructively
			Iterator<Token> i = procedure.iterator();
			while (i.hasNext()) {
				Token t = i.next();
				result = result + " " + t;
			}
			result = result + " }";
			break;
		default:
			break;
		}
		return result;
	}
}

