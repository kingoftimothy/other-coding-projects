// An Iterator-like class for consuming streams of postscript tokens.
// (c) 2001,1996, 2001 duane a. bailey
// See Token.java for more help.

import structure5.*;
import java.util.Iterator;
import java.util.Scanner;

/**
 * A simple input stream that parses postscript files and generates
 * a stream of {@link Token}s.  By default, the stream is constructed
 * from a structure.ReadStream, but the Reader may also be constructed
 * to read from a List.  This allows the recursive implementation of
 * procedures.
 *
 * @author duane a. bailey
 *
 * modified by Bill J.
 */
public class Reader extends AbstractIterator<Token> {
	/**
	 * True if this Reader is reading from a stream.
	 */
	private boolean isStream;	// true if this is input; else from list
	/**
	 * If isStream, the subordinate ReadStream.
	 */
	private Scanner in;	// ReadStream associated with input
	/**
	 * If !isStream, the subordinate list iterator.
	 */
	private AbstractIterator<Token> listIterator; // list-based streams use an iterator

	/**
	 * Constructs a Reader that reads from the default input stream.
	 */
	public Reader() {
		isStream = true;
		in = new Scanner(System.in);
	}

	/**
	 * Constructs a Reader that reads tokens from a List.
	 * @param l a List containing the tokens to be read.
	 */
	public Reader(List<Token> l) {
		isStream = false;
		listIterator = (AbstractIterator<Token>)l.iterator();
	}

	/**
	 * Constructs a Reader that reads from a single Token.
	 * If the token is a procedure, the tokens are read from the
	 * associated list, otherwise a trivial stream is constructed
	 * that contains the single token.
	 * @param t the source of the token stream
	 */
	public Reader(Token t) {
		isStream = false;
		List<Token> l;
		if (t.isProcedure()) {
			l = t.getProcedure();
		} else {
			l = new SinglyLinkedList<Token>();
			l.add(t);
		}
	
		listIterator = (AbstractIterator<Token>)l.iterator();
	}

	/**
	 * Check for more tokens on input stream.
	 * @return true iff there are more unprocessed tokens on stream.
	 */
	public boolean hasNext() {
		boolean result = false;
		if (isStream) {
			result = in.hasNext();
		} else {
			result = listIterator.hasNext();
		}
	
		return result;
	}

	/**
	 * Not yet implemented.
	 */
	public Token get() {
		Assert.pre(false, "Not implemented.");
		return null;
	}

	/**
	 * If this is a list, resets the reader back to the beginning.
	 * Tokens will be re-read.
	 */
	public void reset() {
		if (!isStream) {
			listIterator.reset();
		}
	}

	/**
	 * If {@link #hasNext()} returns the next token from the input
	 * stream.
	 * @return the next token read from the stream.
	 */
	public Token next() {
		Token result;
		if (isStream) {
			result = readToken();
		} else {
			result = (Token)listIterator.next();
		}
		return result;
	}

	/**
	 * Reads the next token string from an input stream, skipping
	 * past comments
	 * <b>Notice</b> that token strings are separated by spaces.
	 * @return the next token string read from the input stream.
	 */
	private String skipPastComments() {
		String s = null;
		
		// return null if we have no more input
		do {
			if (!in.hasNext()) {
				return null;
			}

			// skip past comments
			s = in.next();
			if (s.equals("%")) {
				in.nextLine();
				s = null;
			}
		} while (s == null);

		return s;
	}
	
	/**
	 * Reads the next token from an input stream.
	 * <b>Notice</b> that tokens are separated by spaces.
	 * @return the next token read from the input stream.
	 */
	private Token readToken() {
		Token result = null;
		String s;

		s = skipPastComments();
		if (s == null) {
			return null;
		}
		
		// exit if we're at end of procedure
		if (s.equals("}")) {
			return null;
		} else if (s.equals("{")) {  // procedure start
			List<Token> l = new DoublyLinkedList<Token>();
			Token t;
			// consume tokens until end of procedure
			for (t = readToken(); t != null; t = readToken()) {
				l.addLast(t);
			}
			result = new Token(l);
		} else if (s.equals("true") || s.equals("false")) { // booleans
			result = new Token(s.equals("true"));
		} else {
			try {
				Double d = Double.valueOf(s); // doubles (numbers)
				result = new Token(d.doubleValue());
			} catch (NumberFormatException e) {
				result = new Token(s);  // all others are symbols
			}
		}
	
		return result;
	}

	/**
	 * A sample method that reads tokens from a Reader and prints
	 * their value to the console.
	 */
	public static void main(String[] args) {
		int i = 0;
		Reader r = new Reader();
		Token t;
		while (r.hasNext()) {
			t = r.next();
			if (t.isSymbol() && // only if symbol:
			    t.getSymbol().equals("quit")) {
				break;
			}
	    
			// process token
			System.out.println(i + ": " + t);
			i++;
		}
	}
}
