/**
 * Interpreter.java
 * 
 * An implementation of a basic PostScript interpreter.
 * Creates a Stack of Tokens inputted from System.in to be able
 * to run operations on the Tokens depending on the commands given.
 */

import structure5.*;

public class Interpreter {

	// Creates protected variables for the Stack of Tokens and
	// SymbolTable of user defined symbols
	protected StackVector<Token> stack;
	protected SymbolTable table;

	/**
	* Constructs a StackVector and SymbolTable
	*
	* @post constructs a StackVector of Tokens and SymbolTable
	*/
	public Interpreter() {
		stack = new StackVector<Token>();
		table = new SymbolTable();
	}

	/**
     * A method that loops through stack and puts the values in reverse order in a temporary stack
	 * to print the Tokens in the stack and then loops back to put back the tokens in the right order
	 * in the original stack variable.
     * @pre Stack is either empty or contains Tokens
     * @post stack contains Tokens in the same order as before the program and prints the Tokens in
	 * the right order
     */
	protected void pstack() {
		// initializes size as stack size
		int size = stack.size();

		// loops through stack to print the Tokens and then places the Tokens
		// in temp stack in reverse order
    	Stack<Token> temp = new StackVector<Token>(size);
		for(int i = 0; i < size; i++) {
			// prints top of stack
			System.out.print(stack.peek().toString() + " ");
            temp.push(stack.peek());
			pop();
        }
    
		// loops through temp stack to put stack back in order in original stack
    	int sizeOfTemp = temp.size();
    	for(int i = 0; i < sizeOfTemp; i++) {
			push(temp.peek());
        	temp.pop();
    	}
		System.out.println();
	}

	/**
    * A method that calculates a math operand between new double values in the stack depending
	* on the operator
	* @param oper is a String where it represents the operator used to do math operation
    * @pre There exists two Number token values on the top of the stack and oper should be valid
	* operator.
    * @post The first two Tokens on the stack should be popped and a Token with the valid
	* operation performed should be pushed to the stack
    */
	protected void calculate(String oper) {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Token doesn't have two Tokens to do operator.");
		// asserts pre condition
		Assert.condition(stack.peek().isNumber(), "The Token should be a valid Number.");
		double obj1 = pop().getNumber();

		// asserts pre condition
		Assert.condition(stack.peek().isNumber(), "The Token should be a valid Number.");
		double obj2 = pop().getNumber();
		
		// cases for each operator type
		switch (oper) {
			case "+": push(new Token(obj1 + obj2)); break;
			case "-": push(new Token(obj2 - obj1)); break;
			case "*": push(new Token(obj1 * obj2)); break;
			case "/": push(new Token(obj2 / obj1)); break;
			default:
				// asserts pre condition regarding invalid operator
				System.out.println("Not a valid operator!");
				break;
		}
	}

	/**
    * A method uses the calculate helper method, supplying the + operand to find the sum.
    * @pre There exists two Number token values on the top of the stack and oper should be valid
	* operator.
    * @post The first two Tokens on the stack should be popped and a Token with the valid
	* operation performed should be pushed to the stack
	*/
	protected void add() {
		calculate("+");
	}

	/**
    * A method uses the calculate helper method, supplying the - operand to find the difference.
    * @pre There exists two Number token values on the top of the stack and oper should be valid
	* operator.
	* @post The first two Tokens on the stack should be popped and a Token with the valid
	* operation performed should be pushed to the stack
    */
	protected void sub() {
		calculate("-");
	}

	/**
    * A method uses the calculate helper method, supplying the * operand to find the product.
    * @pre There exists two Number token values on the top of the stack and oper should be valid
	* operator.
    * @post The first two Tokens on the stack should be popped and a Token with the valid
	* operation performed should be pushed to the stack
    */
	protected void mul() {
		calculate("*");
	}

	/**
    * A method uses the calculate helper method, supplying the / operand to find the quotient.
    * @pre There exists two Number token values on the top of the stack and oper should be valid
	* operator.
    * @post The first two Tokens on the stack should be popped and a Token with the valid
	* operation performed should be pushed to the stack
    */
	protected void div() {
		calculate("/");
	}

	/**
    * A method that takes the value on the top of the stack and pushes a duplicate of that value.
    * @pre There exists a token value on the top of the stack.
    * @post a duplicated Token of the token that was on the top of the stack is pushed to the
	* stack.
    */
	protected void dup() {
		// asserts pre condition
		Assert.condition(stack.size() >= 1, "The Stack doesn't have a Token to do operator.");
		push(stack.peek());
	}

	/**
    * A method that swaps the two tokens on the top of the stack.
    * @pre There exists two Number tokens on the top of the stack.
    * @post The two tokens on the top of the stack swap positions
    */
	protected void exch() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to do exchange operation.");
		Token tok1 = pop();
		Token tok2 = pop();
		push(tok1);
		push(tok2);
	}

	/**
    * A method that checks if the top two tokens of the stack are equal and pushes a Boolean Token
    * @pre There exists two Number tokens on the top of the stack.
    * @post The two tokens on the top of the stack are popped and a boolean Token of whether
	* the values are equal is pushed
    */
	protected void eq() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to do the comparison.");
		Token obj1 = pop();
		Token obj2 = pop();
		Token tok = new Token(obj1.equals(obj2));
		push(tok);
	}

	/**
    * A method that checks if the top two tokens of the stack are not equal and pushes a Boolean Token
    * @pre There exists two Number tokens on the top of the stack.
    * @post The two tokens on the top of the stack are popped and a boolean Token of whether
	* the values are not equal is pushed
    */
	protected void ne() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to do the comparison.");
		Token obj1 = pop();
		Token obj2 = pop();
		Token tok = new Token(!obj1.equals(obj2));
		push(tok);
	}

	/**
    * A method that checks if the token below the top token is less than the top token
	* and pushes a Boolean Token
    * @pre There exists two Number tokens on the top of the stack.
    * @post The two tokens on the top of the stack are popped and a boolean Token of whether
	* the the earlier token is less than the token on the top of the stack.
    */
	protected void lt() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to do the comparison.");
		push(new Token(pop().getNumber() < pop().getNumber()));
	}

	/**
    * A method that checks if the token below the top token is greater than the top token
	* and pushes a Boolean Token
    * @pre There exists two Number tokens on the top of the stack.
    * @post The two tokens on the top of the stack are popped and a boolean Token of whether
	* the the earlier token is greater than the token on the top of the stack.
    */
	protected void gt() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to do the comparison.");
		push(new Token(pop().getNumber() > pop().getNumber()));
	}

	/**
    * A method that adds a user-defined symbol to the SymbolTable table.
    * @pre There exists two Token on the top of the stack. The second Token must be a symbol.
    * @post The two tokens on the top of the stack are popped and the Association with the key
	* as the string the user named the symbol and a value of the Token following the symbol
	* is added to the SymbolTable.
    */
	protected void def() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to define symbol.");
		Token tok = pop();
		Assert.condition(stack.peek().isSymbol(), "The Stack doesn't have a valid name for the symbol.");
		String symb = pop().getSymbol();
		table.add(symb, tok);
	}

	/**
    * A method that runs the Token (usually a procedure) on the top of the stack if the Token
	* before it is a boolean that is True.
    * @pre There exists two Token on the top of the stack. The second token should be a 
	* boolean.
    * @post The token on the top is ran through interpret method if previous token is true.
    */
	protected void iff() {
		// asserts pre condition
		Assert.condition(stack.size() >= 2, "The Stack doesn't have two Tokens to run if statement.");
		
		// creates new reader
		Reader secRead = new Reader(pop());

		Assert.condition(stack.peek().isBoolean(), "The next Token is not a Boolean.");
		if (pop().getBoolean()) {
			interpret(secRead);
		}
	}

	/**
    * A method that pops the token on the top of the stack
    * @pre There exists a token to pop off the stack
    * @post pops the token on the top of the stack and returns its value.
	* @return returns the Token that was popped.
    */
	protected Token pop() {
		Assert.condition(!stack.isEmpty(), "The Stack can not be empty.");
		return stack.pop();
	}

	/**
    * A method that pushes a token to the top of the stack
	* @param tok is a Token that is to be pushed onto the stack
    * @pre There exists a Token tok to be pushed
    * @post The Token tok is pushed to the top of the stack.
    */
	protected void push(Token tok) {
		stack.push(tok);
	}

	/**
    * A method that uses the toString method of SymbolTable to print the table.
    * @pre There exists a SymbolTable table to print.
    * @post Prints the Symbol Table.
    */
	protected void ptable() {
		System.out.print(table.toString());
	}

	/**
    * A helper method that is a switch for the various conditions of the symbol 
	* commands called in the interpret method by the Tokens that the user provides.
	* @param tok is a Token that is a Symbol being interpreted.
    * @pre The token tok is a symbol.
    * @post Runs the function that corresponds to the symbol command.
    */
	protected void helperSymbols(Token tok) {
		// asserts pre condition
		Assert.condition(tok.isSymbol(), "The next Token is not a Symbol.");
		
		switch (tok.getSymbol()) {
			case "pstack": pstack(); break;
			case "add": add(); break;
			case "sub": sub(); break;
			case "mul": mul(); break;
			case "div": div(); break;
			case "dup": dup(); break;
			case "exch": exch(); break;
			case "eq": eq(); break;
			case "ne": ne(); break;
			case "pop": pop(); break;
			case "def": def(); break;
			case "ptable": ptable(); break;
			case "if": iff(); break;
			case "lt": lt(); break;
		}
	}

	/**
    * A helper method that is a switch for the various conditions of the symbol
	* commands called in the interpret method by the Tokens that the user provides.
	* @param t is a Token that is a user-defined Symbol being interpreted.
    * @pre The token tok is a user defined symbol.
    * @post Runs the Token (or List of Tokens) associated as the value to the 
	* key of the user defined symbol given.
    */
	protected void helperTable(Token t) {
		// asserts pre condition
		Assert.condition(table.contains(t.getSymbol()), "The table doesn't contain Token. Token not defined.");

		// If the token assoicated with the symbol is a Number or Boolean, push to stack
		if (table.get(t.getSymbol()).isNumber() || table.get(t.getSymbol()).isBoolean()) {
			push(table.get(t.getSymbol()));
		} else if (table.get(t.getSymbol()).isProcedure()) {
			// Creates new reader to read through list of tokens by calling recursive function
			Reader newRead = new Reader(table.get(t.getSymbol()));
			interpret(newRead);
		}
	}
	
	/**
    * A method that interprets the Tokens (either numbers, symbols, booleans,
	* procedures) and proceeds with correct method that follows.
	* the command. For Booleans, Numbers, and Procedures, the tokens are
	* pushed to the stack. The symbols are checked for more conditions to
	* run various methods.
	* @param r is a Reader that is used to read each Token from input.
    * @pre The proper reader is initialized and is reading correct Token objects.
    * @post All the Tokens from the input are ran through correctly and the
	* output is correct.
    */
	protected void interpret(Reader r) {
		Token t;
		// reads next Token until Tokens run out
		while (r.hasNext()) {
			t = r.next();
			// checks for a user defined symbol by check first "/"
			if (t.isSymbol() && t.getSymbol().charAt(0) == '/') {
				push(new Token(t.getSymbol().substring(1)));
			} 
			// pushes token when Token is Boolean, Number, or Precedure
			else if (t.isBoolean() || t.isNumber() || t.isProcedure()) {
				push(t);
			}
			// quits programs when quit Token is inputted
			else if (t.isSymbol() && t.getSymbol().equals("quit")){
				break;
			}
			// runs through user-defined symbols in SymbolTable
			else if (t.isSymbol() && table.contains(t.getSymbol())) {
				helperTable(t);
			}
			// runs through default symbol commands.
			else if (t.isSymbol()) {
				helperSymbols(t);
			} else {
				// Pre Condition for validity of Token
				System.out.println("Token is invalid!");
			}
		}
	}

	/**
   	* Main method that creates Interpreter object inter and a Reader object
	* in order to read from System input and runs interpret method on
	* inputted Tokens to run proper functions.
	* @param args is a String[]
   	*/
	public static void main(String[] args) {

		// try catch block for making sure to read file piped from input
		// return exception if error
		Interpreter inter = new Interpreter();
		try {
			Reader r = new Reader();
			// runs interpret method
			inter.interpret(r);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
